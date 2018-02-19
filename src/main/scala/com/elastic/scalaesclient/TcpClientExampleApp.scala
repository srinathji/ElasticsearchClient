package com.elastic.scalaesclient

import com.sksamuel.elastic4s.{ElasticClient, ElasticsearchClientUri, TcpClient}
import com.sksamuel.elastic4s.ElasticDsl._
import com.sksamuel.elastic4s.http.HttpClient
import com.sksamuel.elastic4s.http.search.SearchResponse
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

  /** Function to Create a  Index  */

  // await is a helper method to make this operation synchronous instead of async
  // You would normally avoid doing this in a real program as it will block your thread
//  client.execute { createIndex("19feb").mappings( mapping("threepm") as( textField("name") ) ) }.await

  // await is a helper method to make this operation synchronous instead of async
  // You would normally avoid doing this in a real program as it will block your thread
  client.execute { indexInto("19feb" / "threepm") fields ("name" -> "vicky") refresh(RefreshPolicy.IMMEDIATE)}.await



  // now we can search for the document we just indexed
  val resp1 = client.execute { search("19feb" / "threepm") query "vicky" }.await

//  println(resp)

  client.execute {
    refresh index "19feb"
  }


//
//  val resp2 = client execute { search in "*" query "findme" }
//  println(resp2)
//
//  val resp = client.execute { search in "*" query "bananas" }.await
//  println(resp.getHits.totalHits)
}