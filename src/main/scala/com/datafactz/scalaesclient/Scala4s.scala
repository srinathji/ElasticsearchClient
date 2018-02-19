package com.datafactz.scalaesclient

import com.sksamuel.elastic4s.{ElasticClient, ElasticsearchClientUri, TcpClient}
import com.sksamuel.elastic4s.http.HttpClient
import com.sksamuel.elastic4s.index.RichIndexResponse
import com.sksamuel.elastic4s.searches.RichSearchResponse
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy
import org.elasticsearch.client.ElasticsearchClient
import org.elasticsearch.common.settings.Settings
import com.sksamuel.elastic4s.circe._
import io.circe.generic.auto._

import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Srinathji on 16/2/18.
  */


object Scala4s extends App {

   case class Artist(name: String)


   val settings = Settings.builder().put("cluster.name", "elasticsearch").build()
   implicit val client = TcpClient.transport(settings, ElasticsearchClientUri("elasticsearch://localhost:9300"))

 /** Executable Code*/
//    val futureArtist = for {
//      _ <- createElasticIndex
//      _ <- insertDocument
//      resp <- queryDocument
//   } yield resp.to[Artist]
//
//   val artistList = Await.result(futureArtist, Duration.Inf)
//   artistList.foreach(println)




    /** Function to Create a  Index  */
   def createElasticIndex(implicit client: TcpClient) : Future[CreateIndexResponse] = {
      import com.sksamuel.elastic4s.ElasticDsl._
      client.execute {
         createIndex("bands").mappings(
            mapping("artist") as(
               textField("name")
               )
         )
      }
   }



  /** Function to Insert a document in the Index */
   def insertDocument(implicit client: TcpClient) : Future[RichIndexResponse] = {
      import com.sksamuel.elastic4s.ElasticDsl._
      client.execute {
         indexInto("bands" / "artists") doc Artist("nirvana") refresh(RefreshPolicy.IMMEDIATE)
      }
   }


  /** Function to Search a document in the Index */
  def queryDocument(implicit client: TcpClient) : Future[RichSearchResponse] = {
      import com.sksamuel.elastic4s.ElasticDsl._
      client.execute {
         search("bands" / "artists") query "nirvana"
      }
   }


/**  To delete an entire index you can use deleteIndex: */
  import com.sksamuel.elastic4s.ElasticDsl._
//
//  client.execute { deleteIndex("myindex") }
//  client.close()
//  client.execute { deleteIndex("_all") } // Deletes ALL indices!
  // Or alternatively:
  client.execute { delete index "movies" }
//  client.execute { delete index ("bands", "countries") } // Deletes two indices
//  AWGeNsTN56MxXjnK59Nt
}

