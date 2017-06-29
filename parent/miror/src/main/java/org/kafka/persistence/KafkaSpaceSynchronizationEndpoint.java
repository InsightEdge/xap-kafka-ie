package org.kafka.persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.common.PriceFeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gigaspaces.sync.DataSyncOperation;
import com.gigaspaces.sync.OperationsBatchData;
import com.gigaspaces.sync.SpaceSynchronizationEndpoint;
import com.gigaspaces.sync.TransactionData;

/**
 * Default implementation of Space Synchronization Endpoint which uses Apache Kafka as external data
 * store. Space synchronization operations are converted to XAP-Kafka protocol and sent to Kafka
 * server.
 *
 * @author Rajiv Shah
 */

@Component
public class KafkaSpaceSynchronizationEndpoint extends SpaceSynchronizationEndpoint {

    Log logger = LogFactory.getLog(this.getClass().getName());

    @Autowired
    KafkaConnection kafkaConnection;

    public KafkaConnection getKafkaConnection() {
        return kafkaConnection;
    }

    public void setKafkaConnection(KafkaConnection kafkaConnection) {
        this.kafkaConnection = kafkaConnection;
    }

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
     * converts XAP data sync operations to Kafka messages (protocol objects) and sends them to
     * Kafka server
     */
    protected void sendToKafka(DataSyncOperation[] dataSyncOperations) {
        logger.info("sendToKafka called");
        //Assign topicName to string variable
        logger.info("dataSyncOperations.length=" + dataSyncOperations.length);

        for (DataSyncOperation dataSyncOperation : dataSyncOperations) {
            try {
                Object obj = dataSyncOperation.getDataAsObject();
                if (obj == null) logger.error("obj is null");
                else logger.info("dataSyncOperation=" + obj.toString());
                if (kafkaConnection == null) logger.error("kafkaConnection is null");
                if (kafkaConnection.getProducer() == null)
                    logger.error("kafkaConnection.getProducer() is null");

                kafkaConnection.getProducer().send(new ProducerRecord<String, PriceFeed>(KafkaConnection.topicName, (PriceFeed)obj));
                logger.info("Message sent successfully");
            } catch (Exception e) {
                logger.error("Exception during Kafka protocol object creation. This data operation will not be persisted", e);
            }
        }
    }
}