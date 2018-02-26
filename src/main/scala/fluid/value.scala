package fluid

import scala.collection.{Map}

import fluid.types.{MemberName, Infer, EnumeralName}
import fluid.prim.{Prim}

package object value {
  sealed trait Value
  case class Value_Infer(val infer: Infer) extends Value
  case class Value_Prim(val prim: Prim) extends Value
  case class Value_ApiVal(val apiVal: ApiValue) extends Value
  case class Value_List(val list: Array[Value]) extends Value

  sealed trait ApiValue
  case class ApiValue_Struct(val struct: Struct) extends ApiValue
  case class ApiValue_Enumeral(val enumeral: Enumeral) extends ApiValue

  case class Wrap(val w: Value)

  case class Struct(val m: Map[MemberName, Value])

  case class Enumeral(val tag: EnumeralName, val m: Option[Map[MemberName, Value]])
}
