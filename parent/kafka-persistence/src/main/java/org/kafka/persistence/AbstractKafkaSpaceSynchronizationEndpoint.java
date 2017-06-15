package org.kafka.persistence;

import com.gigaspaces.document.SpaceDocument;
import com.gigaspaces.sync.DataSyncOperation;
import com.gigaspaces.sync.OperationsBatchData;
import com.gigaspaces.sync.SpaceSynchronizationEndpoint;
import com.gigaspaces.sync.TransactionData;



//import util.properties packages
import java.util.Properties;


//import simple producer packages
import org.apache.kafka.clients.producer.Producer;
//import KafkaProducer packages
import org.apache.kafka.clients.producer.KafkaProducer;
//import ProducerRecord packages
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Incomplete implementation of Space Synchronization Endpoint which uses Apache Kafka as external data store.
 * Space synchronization operations are converted to XAP-Kafka protocol and sent to Kafka server.
 *
 * @author Rajiv Shah
 */
public abstract class AbstractKafkaSpaceSynchronizationEndpoint
        extends SpaceSynchronizationEndpoint {

    protected Log logger = LogFactory.getLog(this.getClass().getName());

    protected Producer<String, String> producer = null;

    @Override
    public void onTransactionSynchronization(TransactionData transactionData) {
    	logger.info("onTransactionSynchronization called");
        executeDataSyncOperations(transactionData.getTransactionParticipantDataItems());
    }

    @Override
    public void onOperationsBatchSynchronization(OperationsBatchData batchData) {
    	logger.info("onOperationsBatchSynchronization called");
        executeDataSyncOperations(batchData.getBatchDataItems());
    }

    protected void executeDataSyncOperations(DataSyncOperation[] transactionParticipantDataItems) {
        sendToKafka(transactionParticipantDataItems);
    }

    /**
     * converts XAP data sync operations to Kafka messages (protocol objects) and sends them to Kafka server
     */
    protected void sendToKafka(DataSyncOperation[] dataSyncOperations) {
    	logger.info("sendToKafka called");
    	//Assign topicName to string variable
    	String topicName = "priceFeed";
    	if (producer == null) {
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
				  
				  props.put("key.serializer", 
				     "org.apache.kafka.common.serializa-tion.StringSerializer");
				     
				  props.put("value.serializer", 
				     "org.apache.kafka.common.serializa-tion.StringSerializer");
				  producer = new KafkaProducer <String, String>(props);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}

        for (DataSyncOperation dataSyncOperation : dataSyncOperations) {
            try {
            	Object obj = dataSyncOperation.getDataAsObject();
            	producer.send(new ProducerRecord<String, String>(topicName,obj.toString()));
            	logger.info("Message sent successfully");
            } catch (Exception e) {
                logger.error("Exception during Kafka protocol object creation. This data operation will not be persisted", e);
            }
        }
    }
}
