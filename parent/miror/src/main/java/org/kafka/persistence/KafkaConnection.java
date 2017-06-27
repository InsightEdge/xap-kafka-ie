package org.kafka.persistence;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

public class KafkaConnection {

	Log logger = LogFactory.getLog(this.getClass().getName());
	Producer<String, byte[]> producer = null;
	static final String topicName = "priceFeed";
	
	public Producer<String, byte[]> getProducer() {
		if (producer == null) {
			createProducer();
		}
		return producer;
	}
	
	public KafkaConnection() {
		logger.info("KafkaConnection constructor called.");
		createProducer();
	}
	
	private synchronized void createProducer () {
		logger.info("createProducer started");
		if (producer == null) {
			logger.info("producer is null so create.");
			try {
				// create instance for properties to access producer configs   
				Properties props = new Properties();

				//Assign localhost id
				props.put("bootstrap.servers", "localhost:9092");

				//Set acknowledgements for producer requests.      
				props.put("acks", "all");

				//If the request fails, the producer can automatically retry,
				props.put("retries", 0);

				//Specify buffer size in config
				props.put("batch.size", 16384);

				//Reduce the no of requests less than 0   
				props.put("linger.ms", 1);

				//The buffer.memory controls the total amount of memory available to the producer for buffering.   
				props.put("buffer.memory", 33554432);

				props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");

				//props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
				props.put("value.serializer", "org.common.CustomSerializer");
				producer = new KafkaProducer <String, byte[]>(props);
				logger.info("producer created for topicName="+topicName);
			} catch (Exception e) {
				logger.info("Problem creating producer for topicName="+topicName);
				logger.error(e);
				e.printStackTrace();
			}
		}
		logger.info("createProducer ended");
	}
}