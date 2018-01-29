import akka.actor.ActorRef
import akka.dispatch.MessageDispatcher
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import akka.util.Timeout
import search.protocol._
import search.server.support.JsonSupport._
import search.server.support.TextTokenizer._

/**
  * Rest service with endpoints.
  **/
class RestApiService()(implicit val timeout: Timeout, val blockingExecutor: MessageDispatcher) {

  val mainRoute: Route = getHello


  private def getHello: Route = path("get" / Segment) {
    id =>
      get {
        complete {
          s"Hello \$id"
        }
      }
  }
}