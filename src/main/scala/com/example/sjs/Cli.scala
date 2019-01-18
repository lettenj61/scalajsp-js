package com.example.sjs

import scala.scalajs.js
import scala.scalajs.js.annotation._
import scala.scalajs.js.Dynamic.{ global => g }

object Cli {

  @js.native
  @JSImport("fs", JSImport.Namespace)
  object NodeFS extends js.Object

  @js.native
  @JSImport("path", JSImport.Namespace)
  private object NodePath extends js.Object {
    def parse(name: String): PathObject = js.native
    def resolve(base: String, rest: String): String = js.native
  }

  @js.native
  trait PathObject extends js.Object {
    val root: String
  }

  @js.native
  trait Process extends js.Object {
    def cwd(): String
    def exit(code: Int): Unit
    val argv: js.Array[String]
  }

  @js.native
  trait Console extends js.Object {
    def log(args: scala.Any*): Unit
    def error(args: scala.Any*): Unit
  }

  private val process: Process =
    g.process.asInstanceOf[Process]

  private val console: Console =
    g.console.asInstanceOf[Console]

  case class Options(file: String = "", flags: Set[String] = Set())
  def parseOptions(args: Seq[String]): Options =
    args.foldLeft(Options()) { (opts, arg) =>
      if (arg(0) == '-')
        opts.copy(flags = opts.flags + arg.replaceAll("^[\\-]+", ""))
      else
        opts.copy(file = arg)
    }

  def main(javaArgs: Array[String]): Unit = {

    // hack to expose `fs` to NodeVirtualScalaJSIRFile
    val requireFS: js.Function1[String, js.Any] =
      moduleName => NodeFS

    g.require = requireFS

    val argv = process.argv.drop(2)
    if (argv.isEmpty) {
      console.error("usage: sjs-tools.js [OPTIONS] <FILE>")
      process.exit(1)
    }

    val opts = parseOptions(argv)
    if (opts.file == "") {
      console.error("path to .sjsir file not specified")
      process.exit(1)
    }

    val input = {
      val parsed = NodePath.parse(opts.file)
      if (parsed.root == "") NodePath.resolve(process.cwd(), opts.file)
      else opts.file
    }

    try {
      val content = Exports.readIR(input)
      if (opts.flags("J")) {
        console.log("%j", content.tree)
      } else {
        console.log(content.showTree)
      }
    } catch {
      case ex: Exception =>
        console.error(ex.getMessage)
    }
  }
}