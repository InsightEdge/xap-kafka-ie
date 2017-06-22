# xap-kafka-ie
Feeder --> Grid --> mirror --> Kafka --> InsightEdge --> Grid

steps to run demo on windows machine
Go to Insight home directory
-->  .\sbin\insightedge.cmd --mode demo
-->  .\datagrid\bin\gs-ui.bat

Go to Kakka home directory
-->  .\bin\windows\zookeeper-server-start.bat config/zookeeper.properties // start Zookeeper server
-->  .\bin\windows\kafka-server-start.bat config/server.properties // start kafka server
-->  .\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic priceFeed --from-beginning // start command line consumer to verify feed comming from mirror service
-->  deploy price-space.jar with cluster schema: partitioned, number of instances: 1, backups: 1
-->  deploy mirror.jar
-->  open Spark Streaming Kafka Access in browser using <Ipaddress:8090> "run all paragraphs"
-->  In gs-ui --> go to pricespace // it will keep adding price data

-->  .\bin\windows\kafka-topics.bat --list -zookeeper localhost:2181 // list topics

-->  .\bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic priceFeed // send message from command line

-->  .\bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic priceFeed // create topic