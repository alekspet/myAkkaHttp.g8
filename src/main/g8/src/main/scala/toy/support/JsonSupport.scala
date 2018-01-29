package toy.support

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import toy.protocol.EmptyDocument
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

sealed trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {

  // HERE THE JSON CONVERTERS
  implicit val emptyDocumentFormat: RootJsonFormat[EmptyDocument] = jsonFormat0(EmptyDocument)
}

object JsonSupport extends JsonSupport