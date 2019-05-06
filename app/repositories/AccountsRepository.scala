package repositories

import javax.inject.{Inject, Singleton}
import play.api.libs.json.JsObject
import play.modules.reactivemongo.{ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.api.commands.WriteResult
import reactivemongo.play.json.JsObjectDocumentWriter
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AccountsRepository @Inject()(val reactiveMongoApi: ReactiveMongoApi)
                                  (implicit val ec: ExecutionContext) extends ReactiveMongoComponents {

  private def collection: Future[JSONCollection] = reactiveMongoApi.database map {
    _.collection[JSONCollection]("sign-up")
  }

  def insert(json: JsObject): Future[WriteResult] = collection flatMap {
    _.insert.one(json)
  }

  def findByField(field: JsObject): Future[Option[JsObject]] = collection flatMap {
    _.find(field, None).one[JsObject]
  }

}
