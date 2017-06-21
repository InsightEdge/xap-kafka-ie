# xap-kafka-ie
Feeder --> Grid --> mirror --> Kafka --> InsightEdge --> Grid

steps to run demo on windows machine
1. .\sbin\insightedge.cmd --mode demo
2. .\bin\windows\zookeeper-server-start.bat config/zookeeper.properties // start Zookeeper server
3. .\bin\windows\kafka-server-start.bat config/server.properties // start kafka server
4. .\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic priceFeed --from-beginning // start command line consumer to verify feed comming from mirror service
5. deploy price-space.jar with cluster schema: partitioned, number of instances: 1, backups: 1
6. deploy mirro.jar
7. open Spark Streaming Kafka Access in browser using <Ipaddress:8090> "run all paragraphs"
8. open gs-ui --> go to pricespace // it will keep adding price data


// list topics
.\bin\windows\kafka-topics.bat --list -zookeeper localhost:2181 

// send message from command line
.\bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic priceFeed
