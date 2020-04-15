package com.mitchseymour.kafka.serialization.avro;

import java.util.Map;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.serialization.Deserializer;

public class AvroDeserializer<T extends SpecificRecordBase> implements Deserializer<T> {
  private Class<T> destinationClass;

  /** Default constructor needed by Kafka */
  public AvroDeserializer(Class<T> destinationClass) {
    this.destinationClass = destinationClass;
  }

  @Override
  public void configure(Map<String, ?> props, boolean isKey) {}

  @Override
  public T deserialize(String topic, byte[] bytes) {
    try {
      DatumReader<T> reader = new SpecificDatumReader<>(destinationClass.newInstance().getSchema());
      Decoder binaryDecoder = DecoderFactory.get().binaryDecoder(bytes, null);
      return reader.read(null, binaryDecoder);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void close() {}
}
