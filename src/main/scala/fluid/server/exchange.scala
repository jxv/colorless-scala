package fluid.server

import org.json4s._
import fluid.types.{RuntimeError, Limits}

package object exchange {
  case class Request(
    val meta: JValue,
    val query: JValue
  )

  sealed trait ResponseError
  case class ResponseError_Service(val service: JValue) extends ResponseError
  case class ResponseError_Runtime(val error: RuntimeError) extends ResponseError

  sealed trait Response
  case class Response_Error(val error: ResponseError) extends Response
  case class Response_Success(val success: JValue, val limits: Limits) extends Response
}
