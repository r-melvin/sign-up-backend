package repositories

import javax.inject.{Inject, Singleton}
import play.api.libs.json._
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.commands.WriteResult
import reactivemongo.play.json.collection.JSONCollection
import reactivemongo.play.json._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
abstract class AccountsRepository @Inject()(mongo: ReactiveMongoApi)(implicit val ec: ExecutionContext) {

  private def collection: Future[JSONCollection] = mongo.database map {
    _.collection[JSONCollection]("sign-up")
  }

  def insert(json: JsObject): Future[WriteResult] = collection flatMap {
    _.insert.one(json)
  }

}