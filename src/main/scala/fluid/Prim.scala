package FluidIDL

sealed trait Prim
case class Prim_Bool(val bool: Boolean) extends Prim
case class Prim_Int(val int: Int) extends Prim
case class Prim_Float(val float: Double) extends Prim
case class Prim_String(val string: String) extends Prim
