package com.example.sjs

import scala.scalajs.js
import org.scalajs.core.ir

trait ScalaJSIRContent extends js.Object {
  val info: ir.Infos.ClassInfo
  val tree: ir.Trees.Tree
  def showTree(): String
}
