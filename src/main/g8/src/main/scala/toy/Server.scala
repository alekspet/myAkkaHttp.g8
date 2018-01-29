package toy

import akka.actor.{ActorRef, ActorSelection, ActorSystem}
import akka.dispatch.MessageDispatcher
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.server.RouteResult.route2HandlerFlow
import akka.stream.ActorMaterializer
import akka.util.Timeout

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

/**
  * Server runner on Akka-HTTP.
  */
object Server {

  implicit val system: ActorSystem = ActorSystem("server-system")
  implicit val executionContext: MessageDispatcher = system.dispatchers.lookup("blocking-dispatcher")
  implicit val mat: ActorMaterializer = ActorMaterializer()
  implicit val timeout: Timeout = Duration.fromNanos(system.settings.config.getDuration("service.timeout").toNanos)

  def main(args: Array[String]): Unit = {

    val restApiService = new RestApiService()

    val serverEndpoint: Future[ServerBinding] = Http().bindAndHandle(
      route2HandlerFlow(restApiService.mainRoute),
      system.settings.config.getString("service.host"),
      system.settings.config.getInt("service.port"))

    scala.sys.addShutdownHook(
      shutdown(serverEndpoint)
    )
  }

  private def shutdown(serverEndpoint: Future[ServerBinding]): Unit = {
    Await.result(Http().shutdownAllConnectionPools(), 1.minute)
    Await.result(serverEndpoint.map(_.unbind()), 1.minute)
    system.terminate()
    Await.result(system.whenTerminated, 1.minute)
  }
}