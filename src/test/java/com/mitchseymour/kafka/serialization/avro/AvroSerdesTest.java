package com.mitchseymour.kafka.serialization.avro;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mitchseymour.model.Tweet;
import org.apache.kafka.common.serialization.Serde;
import org.junit.jupiter.api.Test;

class AvroSerdesTest {
  private static final String SOME_TOPIC = "some-topic";

  @Test
  void testSerdes() {
    // create an instance of the auto-generated Avro class for serializing
    Tweet tweet = new Tweet();
    tweet.setId(123L);
    tweet.setText("hello, world");

    // serialize the Tweet instance
    Serde<Tweet> avroSerde = AvroSerdes.get(Tweet.class);
    byte[] bytes = avroSerde.serializer().serialize(SOME_TOPIC, tweet);

    // now, deserialize the resulting byte array
    Tweet deserializedTweet = avroSerde.deserializer().deserialize(SOME_TOPIC, bytes);

    // make sure the two tweets are equal
    assertEquals(tweet, deserializedTweet, "Original tweet should equal deserialized tweet");
  }
}
