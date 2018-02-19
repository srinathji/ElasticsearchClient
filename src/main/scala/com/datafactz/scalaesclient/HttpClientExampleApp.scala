package com.datafactz.scalaesclient

import com.sksamuel.elastic4s.ElasticsearchClientUri
import com.sksamuel.elastic4s.http.HttpClient
import com.sksamuel.elastic4s.http.search.SearchResponse
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy

object HttpClientExampleApp extends App {

  // you must import the DSL to use the syntax helpers
  import com.sksamuel.elastic4s.http.ElasticDsl._

  val client = HttpClient(ElasticsearchClientUri("localhost", 9200))

  //  Function to create the index
  //  Function to Insert a document in the Index

  client.execute {
    bulk(
      indexInto("DFZ" / "vikat").fields("country" -> "shiva", "capital" -> "Kalish"),
      indexInto("DFZ" / "vikat").fields("country" -> "srinathji", "capital" -> "Himalaya")
    ).refresh(RefreshPolicy.WAIT_UNTIL)
  }.await

  val result: SearchResponse = client.execute {
    search("DFZ").matchQuery("capital", "Himalaya")
    search("DFZ").matchQuery("capital", "Himalaya")
  }.await








  // prints out the original json
  println(result.hits.hits.head.sourceAsString)

  client.close()

}
