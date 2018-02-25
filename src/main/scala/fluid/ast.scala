package fluid

import scala.collection.{Map}
import fluid.types.{Symbol, EnumeralName, Type, MemberName, TypeName, Const}

package object ast {
  sealed trait Ast
  case class Ast_Ref(val ref: Ref) extends Ast
  case class Ast_If(val iF: If) extends Ast
  case class Ast_Iflet(val iflet: Iflet) extends Ast
  case class Ast_Get(val get: Get) extends Ast
  case class Ast_Set(val set: Set) extends Ast
  case class Ast_Define(val define: Define) extends Ast
  case class Ast_Do(val dO: Do) extends Ast
  case class Ast_FnCall(val fnCall: FnCall) extends Ast
  case class Ast_WrapCall(val wrapCall: WrapCall) extends Ast
  case class Ast_StructCall(val structCall: StructCall) extends Ast
  case class Ast_EnumerationCall(val enumerationCall: EnumerationCall) extends Ast
  case class Ast_HollowCall(val hollowCall: HollowCall) extends Ast
  case class Ast_Enumeral(val enumeral: Enumeral) extends Ast
  case class Ast_Struct(val struct: Struct) extends Ast
  case class Ast_Wrap(val wrap: Wrap) extends Ast
  case class Ast_Const(val const: Const) extends Ast

  case class Ref(
    val symbol: Symbol
  )

  case class If(
    val cond: Ast,
    val t: Ast,
    val f: Ast
  )

  case class Iflet(
    val symbol: Symbol,
    val option: Ast,
    val some: Ast,
    val none: Ast
  )

  case class Get(
    val path: Array[String],
    val value: Ast
  )

  case class Set(
    val path: Array[String],
    val src: Ast,
    val dest: Ast
  )

  case class Define(
    val variable: Symbol,
    val expr: Ast
  )

  sealed trait MatchCase
  case class MatchCase_Tag(val name: EnumeralName, val expr: Ast) extends MatchCase
  case class MatchCase_Members(val name: EnumeralName, val symbol: Symbol, val expr: Ast) extends MatchCase

  case class Match(
    val enumeral: Ast,
    val cases: Array[MatchCase]
  )

  case class Lambda(
    val args: Array[Tuple2[Symbol, Type]],
    val expr: Ast
  )

  case class List(
    val list: Array[Ast]
  )

  case class Tuple(
    val tuple: Array[Ast]
  )

  case class Do(
    val vals: Array[Ast]
  )

  case class FnCall(
    val fn: Ast,
    val args: Array[Ast]
  )

  case class EnumerationCall(
    val n: TypeName,
    val e: Ast
  )

  case class WrapCall(
    val n: TypeName,
    val e: Ast
  )

  case class StructCall(
    val n: TypeName,
    val m: Ast
  )

  case class HollowCall(
    val n: TypeName
  )

  case class Enumeral(
    val tag: TypeName,
    val m: Option[Map[MemberName, Ast]]
  )

  case class Struct(
    val m: Map[MemberName, Ast]
  )

  case class Wrap(
    val w: Ast
  )
}
