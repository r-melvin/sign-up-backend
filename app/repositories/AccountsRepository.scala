package repositories

import javax.inject.{Inject, Singleton}
import play.api.libs.json.{JsObject, Json}
import play.modules.reactivemongo.{ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.api.commands.{UpdateWriteResult, WriteResult}
import reactivemongo.play.json.JsObjectDocumentWriter
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AccountsRepository @Inject()(val reactiveMongoApi: ReactiveMongoApi)
                                  (implicit val ec: ExecutionContext) extends ReactiveMongoComponents {

  private def collection: Future[JSONCollection] = reactiveMongoApi.database map {
    _.collection[JSONCollection]("sign-up")
  }

  def update(id: String, updates: JsObject): Future[UpdateWriteResult] = collection flatMap {
    _.update(ordered = true).one(
      q = Json.obj("_id" -> id),
      u = updates
    )
  }

  def insert(id: String, json: JsObject): Future[WriteResult] = collection flatMap {
    _.insert.one(Json.obj("_id" -> id) ++ json)
  }

  def findById(id: String): Future[Option[JsObject]] = collection flatMap {
    _.find(
      selector = Json.obj("_id" -> id),
      projection = None
    ).one[JsObject]
  }

  def delete(id: String): Future[WriteResult] = collection flatMap {
    _.delete.one(Json.obj("_id" -> id))
  }

  def drop: Future[Boolean] = collection flatMap {
    _.drop(failIfNotFound = false)
  }

}
