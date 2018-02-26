package fluid.server

import scala.collection.{Map}
import fluid.types.{Limits, TypeName, MemberName, EnumeralName, Symbol, Const, Type}
import fluid.value.{Value, Struct, Enumeral, Wrap}

object expr {
  case class EvalConfig(
    val limits: Limits,
    var langServiceCallLimit: Int,
    var langLambdaCount: Int,
    var langExprCount: Int,
    val apiCall: (ApiCall) => Value
  )

  sealed trait Expr
  case class Expr_Ref(val ref: Ref) extends Expr
  case class Expr_UnValue(val unValue: UnValue) extends Expr
  case class Expr_Value(val value: Value) extends Expr
  case class Expr_If(val iF: If) extends Expr
  case class Expr_Iflet(val iflet: Iflet) extends Expr
  case class Expr_Get(val get: Get) extends Expr
  case class Expr_Set(val set: Set) extends Expr
  case class Expr_Match(val m: Match) extends Expr
  case class Expr_Define(val define: Define) extends Expr
  case class Expr_Lambda(val lambda: Lambda) extends Expr
  case class Expr_List(val list: List) extends Expr
  case class Expr_Tuple(val tuple: Tuple) extends Expr
  case class Expr_Fn(val fn: Fn) extends Expr
  case class Expr_FnCall(val fnCall: FnCall) extends Expr
  case class Expr_Do(val dO: Do) extends Expr
  case class Expr_ApiUnCall(val apiUnCall: ApiUnCall) extends Expr

  case class Ref(
    val symbol: Symbol
  )

  sealed trait UnValue
  case class UnValue_Const(val const: Const) extends UnValue
  case class UnValue_UnWrap(val unWrap: UnWrap) extends UnValue
  case class UnValue_UnStruct(val unStruct: UnStruct) extends UnValue
  case class UnValue_UnEnumeral(val unEnumeral: UnEnumeral) extends UnValue

  case class UnWrap(val w: Expr)

  case class UnStruct(val m: Map[MemberName, Expr])

  case class UnEnumeral(val tag: EnumeralName, val m: Option[Map[MemberName, Expr]])

  case class If(
    val cond: Expr,
    val t: Expr,
    val f: Expr
  )

  case class Iflet(
    val symbol: Symbol,
    val option: Expr,
    val some: Expr,
    val none: Expr
  )

  case class Get(
    val path: Array[String],
    val value: Expr
  )

  case class Set(
    val path: Array[String],
    val src: Expr,
    val dest: Expr
  )

  sealed trait MatchCase
  case class MatchCase_Tag(val expr: Expr) extends MatchCase
  case class MatchCase_Members(val symbol: Symbol, val expr: Expr) extends MatchCase

  case class Match(
    val enumeral: Expr,
    val cases: Map[EnumeralName, MatchCase]
  )

  case class Define(
    val variable: Symbol,
    val expr: Expr
  )

  case class Lambda(
    val params: Array[Tuple2[Symbol, Type]],
    val expr: Expr
  )

  case class Fn(
    val run: (EvalConfig, Array[Expr]) => Expr
  )

  case class List(
    val list: Array[Expr]
  )

  case class Tuple(
    val tuple: Array[Expr]
  )

  case class Do(
    val exprs: Array[Expr]
  )

  case class FnCall(
    val fn: Expr,
    val args: Array[Expr]
  )

  sealed trait ApiUnCall
  case class ApiUnCall_HollowUnCall(val hollowUnCall: HollowUnCall) extends ApiUnCall
  case class ApiUnCall_WrapUnCall(val wrapUnCall: WrapUnCall) extends ApiUnCall
  case class ApiUnCall_StructUnCall(val structUnCall: StructUnCall) extends ApiUnCall
  case class ApiUnCall_EnumerationUnCall(val enumerationUnCall: EnumerationUnCall) extends ApiUnCall

  case class HollowUnCall(
    val n: TypeName
  )

  case class WrapUnCall(
    val n: TypeName,
    val w: Expr
  )

  case class StructUnCall(
    val n: TypeName,
    val m: Expr
  )

  case class EnumerationUnCall(
    val n: TypeName,
    val m: Expr
  )

  sealed trait ApiCall
  case class ApiCall_Hollow(val name: TypeName) extends ApiCall
  case class ApiCall_Struct(val name: TypeName, val struct: Struct) extends ApiCall
  case class ApiCall_Enumeral(val name: TypeName, val enumeral: Enumeral) extends ApiCall
  case class ApiCall_Wrap(val name: TypeName, val wrap: Wrap) extends ApiCall

  //

  case class ApiParser[Api](
    val hollow: Map[TypeName, Api],
    val struct: Map[TypeName, (Value) => Option[Api]],
    val enumeration: Map[TypeName, (Value) => Option[Api]],
    val wrap: Map[TypeName, (Value) => Option[Api]]
  )

}
