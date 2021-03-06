# xap-kafka-ie
Feeder --> Grid --> mirror --> Kafka --> InsightEdge --> Grid

steps to run demo on windows machine
Go to Insight home directory
1.  .\sbin\insightedge.cmd --mode demo
2.  .\datagrid\bin\gs-ui.bat

Go to Kakka home directory

3.  .\bin\windows\zookeeper-server-start.bat config/zookeeper.properties // start Zookeeper server
4.  .\bin\windows\kafka-server-start.bat config/server.properties // start kafka server
5.  .\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic priceFeed --from-beginning // start command line consumer to verify feed comming from mirror service
6.  deploy price-space.jar with cluster schema: partitioned, number of instances: 1, backups: 1
7.  deploy mirror.jar
8.  open Spark Streaming Kafka Access in browser using <Ipaddress:8090> "run all paragraphs"
9.  In gs-ui --> go to pricespace // it will keep adding price data

10.  .\bin\windows\kafka-topics.bat --list -zookeeper localhost:2181 // list topics
11.  .\bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic priceFeed // send message from command line
12.  .\bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic priceFeed // create topic

For unix:

cd $INSIGHTEDGE_HOME
./sbin/insightedge.sh --mode demo
./datagrid/bin/gs-ui.sh

cd $KAFKA_HOME
    // start Zookeeper server
./bin/zookeeper-server-start.sh config/zookeeper.properties
    // start kafka server
./bin/kafka-server-start.sh config/server.properties
    // start command line consumer to verify feed comming from mirror service
./bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic priceFeed --from-beginning
 
./bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic priceFeed --from-beginning
 
    // list topics
./bin/kafka-topics.sh --list -zookeeper localhost:2181

    // create topic
./bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic priceFeed
