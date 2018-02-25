package fluid

import scala.math.BigDecimal

package object types {
  class Major(val value: Int) extends AnyVal
  class Minor(val value: Int) extends AnyVal
  class TypeName(val value: Int) extends AnyVal
  class EnumeralName(val value: Int) extends AnyVal
  class MemberName(val value: Int) extends AnyVal
  class Symbol(val value: Int) extends AnyVal

  case class Version(val major: Major, val minor: Minor)
  case class Pull(val protcol: String, val host: String, val port: Int, val path: String)

  sealed trait RuntimeError
  case object RuntimeError_UnparsableFormat extends RuntimeError
  case class RuntimeError_UnrecognizedCall(name: TypeName) extends RuntimeError
  case object RuntimeError_VariableLimit extends RuntimeError
  case class RuntimeError_LangServiceCallLimit(limit: Int) extends RuntimeError
  case class RuntimeError_LangLambdaLimit(limit: Int) extends RuntimeError
  case class RuntimeError_UnknownVariable(name: String) extends RuntimeError
  case object RuntimeError_IncompatiableType extends RuntimeError
  case object RuntimeError_MissingMatchCase extends RuntimeError
  case object RuntimeError_TooFewArguments extends RuntimeError
  case object RuntimeError_TooManyArguments extends RuntimeError
  case object RuntimeError_NoApiVersion extends RuntimeError
  case object RuntimeError_NoFluidVersion extends RuntimeError
  case object RuntimeError_ApiMajorVersionTooLow extends RuntimeError
  case object RuntimeError_ApiMajorVersionTooHigh extends RuntimeError
  case object RuntimeError_FluidMajorVersionTooLow extends RuntimeError
  case object RuntimeError_FluidMajorVersionTooHigh extends RuntimeError
  case object RuntimeError_FluidMinorVersionTooHigh extends RuntimeError
  case object RuntimeError_UnparsableMeta extends RuntimeError
  case object RuntimeError_UnparsableQuery extends RuntimeError
  case object RuntimeError_NotMember extends RuntimeError

  case class Limits(
    val variables: Option[Int],
    val serviceCalls: Option[Int],
    val lambdas: Option[Int],
    val expressions: Option[Int]
  )

  case class Hooks[Meta,XformMeta](
    val metaMiddleware: (Meta) => XformMeta,
    val sandboxLimits: (XformMeta) => Limits
  )

  case class Type(
    val n: TypeName,
    val p: List[Type],
    val o: Option[Type]
  )

  sealed trait Const
  case object Const_Null extends Const
  case class Const_Bool(val bool: Boolean) extends Const
  case class Const_String(val string: String) extends Const
  case object Const_Number extends Const

  sealed trait Infer
  case object Infer_Null extends Infer
  case class Infer_Number(val number: BigDecimal) extends Infer


  val defaultLimits = new Limits(Some(50), Some(50), Some(50), Some(100))
  def defaultHooks[Meta]() = new Hooks[Meta,Meta](x => x, _ => defaultLimits)
}
