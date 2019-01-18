package com.example.sjs

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel

import org.scalajs.core.tools.io._
import org.scalajs.core.tools.logging.ScalaConsoleLogger

object Exports {
  @JSExportTopLevel("linkerWrapper")
  def linkerWrapper(): LinkerWrapper = new LinkerWrapper()

  @JSExportTopLevel("readIR")
  def readIR(path: String): ScalaJSIRContent = {
    val file = new NodeVirtualScalaJSIRFile(path)
    new ScalaJSIRContent {
      val info = file.info
      val tree = file.tree
      def showTree() = tree.show
    }
  }

  @JSExportTopLevel("linkIR")
  def linkIR(src: String): js.Dynamic = {
    val output = WritableMemVirtualJSFile("test.js")
    val irFile = new NodeVirtualScalaJSIRFile(src)
    linkerWrapper.linker.link(
      List(irFile),
      Nil, // moduleInitializers
      output,
      new ScalaConsoleLogger()
    )

    irFile.asInstanceOf[js.Dynamic]
  }
}