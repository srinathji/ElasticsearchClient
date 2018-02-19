package com.elastic.scalaesclient
/**
  * Created by Srinathji on 16/2/18.
  */
import com.sksamuel.elastic4s.http.HttpClient
import com.sksamuel.elastic4s.http.search.SearchResponse
import com.elastic.scalaesclient.HttpClientExampleApp.client
import com.sksamuel.elastic4s.{ElasticClient, ElasticsearchClientUri, TcpClient}
import com.sksamuel.elastic4s.ElasticDsl._
import com.sksamuel.elastic4s.http.ElasticDsl.{bulk, indexInto}
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy


object TcpClientExampleApp extends App {
// Here we create an instance of the TCP client
//  val client = TcpClient.transport(ElasticsearchClientUri("127.0.0.1", 9200))
//  val uri = ElasticsearchClientUri("elasticsearch://127.0.0.1:9300")
//  val settings = ImmutableSettings.settingsBuilder().put("cluster.name", "myClusterName").build()
//  val client = ElasticClient.remote(settings, uri)


  //  You want 9300 for TCP. 9200 is for rest.
  val uri = ElasticsearchClientUri("elasticsearch://localhost:9300")
  val client = ElasticClient.remote(uri)

  /** Function to Create an  Index  */
  // await is a helper method to make this operation synchronous instead of async
  // You would normally avoid doing this in a real program as it will block your thread
  println(client.execute {
    createIndex("19febsix").mappings(mapping("six") as (textField("Name")))
  }.await)



  /** Function to Insert a Document into Index  */
  // await is a helper method to make this operation synchronous instead of async
  // You would normally avoid doing this in a real program as it will block your thread
  val insertIndex = client.execute {
                                    indexInto("19febsix" / "six").fields ("Name" -> "vikat") // two at a time wont work we need to use either bulk or single
                             bulk(
                                  indexInto("19febsix" / "six").fields("Name" -> "shiva", "capital" -> "Kalish"),
                                  indexInto("19febsix" / "six").fields("Name" -> "srinathji", "capital" -> "Himalaya")
//                                ).refresh(RefreshPolicy.IMMEDIATE)
                             ).refresh(RefreshPolicy.WAIT_UNTIL)
  }.await // this will execute the index into function

//  println("\n Index Inserted:" +insertIndex) // will result the output message of insertIndex variable

  /** Function to Insert  Bulk documents into the Index */
  /* client.execute {
     bulk(
       indexInto("19febfivepm" / "five").fields("country" -> "shiva", "capital" -> "Kalish"),
       indexInto("19febfivepm" / "five").fields("country" -> "srinathji", "capital" -> "Himalaya")
     ).refresh(RefreshPolicy.WAIT_UNTIL)
   }.await

 */

  /** Function to search Document / Query into Index  */
  // now we can search for the document we just indexed
  println(client.execute {
    search("19febsix" / "six") query "sri"
  }.await)
//  println("\n Search Result: "+searchIndex)



  /** Function to Refresh Index  */

  /*
    client.execute {
      refresh index "19feb"
    }
  */


  //
  //  val resp2 = client execute { search in "*" query "findme" }
  //  println(resp2)
  //
  //  val resp = client.execute { search in "*" query "bananas" }.await
  //  println(resp.getHits.totalHits)
}