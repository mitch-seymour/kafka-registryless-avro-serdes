package com.mitchseymour.kafka.serialization.avro;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

public class AvroSerdes {
  private AvroSerdes() {
    // do not allow instantiation
  }

  public static <T extends SpecificRecordBase> Serde<T> get(Class<T> clazz) {
    AvroSerializer<T> serializer = new AvroSerializer<>(clazz);
    AvroDeserializer<T> deserializer = new AvroDeserializer<>(clazz);
    return Serdes.serdeFrom(serializer, deserializer);
  }
}
