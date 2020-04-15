package com.mitchseymour.kafka.serialization.avro;

import java.io.ByteArrayOutputStream;
import java.util.Map;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.serialization.Serializer;

class AvroSerializer<T extends SpecificRecordBase> implements Serializer<T> {
  private Class<T> sourceClass;

  /** Default constructor needed by Kafka */
  public AvroSerializer(Class<T> sourceClass) {
    this.sourceClass = sourceClass;
  }

  @Override
  public void configure(Map<String, ?> props, boolean isKey) {}

  @Override
  public byte[] serialize(String topic, T t) {
    DatumWriter<T> datumWriter = new SpecificDatumWriter<>(sourceClass);
    byte[] bytes;
    try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      Encoder binaryEncoder = EncoderFactory.get().binaryEncoder(out, null);
      datumWriter.write(t, binaryEncoder);
      binaryEncoder.flush();
      bytes = out.toByteArray();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return bytes;
  }

  @Override
  public void close() {}
}
