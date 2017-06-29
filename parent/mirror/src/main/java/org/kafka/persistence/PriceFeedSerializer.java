package org.kafka.persistence;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Map;


import org.apache.kafka.common.serialization.Serializer;
import org.common.PriceFeed;

public class PriceFeedSerializer implements Serializer<PriceFeed> {

    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    public byte[] serialize(String topic, PriceFeed data) {
        // TODO copied from commons-lang3, use Kryo
        final ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
        serialize(data, baos);
        return baos.toByteArray();
    }

    private void serialize(final Serializable obj, final OutputStream outputStream) {
        if (outputStream == null) throw new RuntimeException("The OutputStream must not be null");
        try (ObjectOutputStream out = new ObjectOutputStream(outputStream)) {
            out.writeObject(obj);
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void close() {
    }

}