#!/usr/bin/env bash


echo -n "Rebuild spark-job (y/n): "
read  -t 5 toBuild

if [ "$toBuild" = "y" ]; then
    cd /code/xap-kafka-ie/parent
    mvn clean package -pl spark-job
#    mvn clean package
fi


$INSIGHTEDGE_HOME/bin/insightedge-submit \
--class io.ie.kafka.poc.StreamingJob \
--master spark://127.0.0.1:7077 /code/xap-kafka-ie/parent/spark-job/target/spark-job-1.0.0-SNAPSHOT-jar-with-dependencies.jar \
2>&1
