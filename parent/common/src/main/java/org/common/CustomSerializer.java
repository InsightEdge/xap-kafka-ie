package org.common;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.common.serialization.Serializer;

public class CustomSerializer<T extends Serializable> implements Serializer<T> {

	public void configure(Map<String, ?> configs, boolean isKey) {
	}

	public byte[] serialize(String topic, T data) {
		return SerializationUtils.serialize(data);
	}

	public void close() {
	}

}