package com.example.sjs

import scala.scalajs.js.annotation.JSExportAll
import org.scalajs.core.tools.linker.Linker
import org.scalajs.core.tools.linker.StandardLinker

@JSExportAll
class LinkerWrapper(val config: StandardLinker.Config) {

  def this() = this(StandardLinker.Config())

  final val linker: Linker =
    StandardLinker(config)
}