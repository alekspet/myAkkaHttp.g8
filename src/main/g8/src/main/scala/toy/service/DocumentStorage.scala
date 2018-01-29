package toy.service

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSelection, Props, Stash}
import akka.dispatch.MessageDispatcher
import akka.util.Timeout

import scala.concurrent.duration._
import toy.protocol._

class DocumentStorage() extends Actor with ActorLogging {


  override def receive: Receive = {
    case id =>
      log.info(s"Document by id : \$id")
      sender ! EmptyDocument()
  }
}

object DocumentStorage{

  def props(): Props = Props(classOf[DocumentStorage])
}