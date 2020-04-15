# Kafka Registryless Avro Serdes
[![Maven Central](https://img.shields.io/maven-central/v/com.mitchseymour/kafka-registryless-avro-serdes.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.mitchseymour%22%20AND%20a:%22kafka-registryless-avro-serdes%22)

If you want to use Avro in your Kafka project, but aren't using [Confluent Schema Registry][schema-registry], you can use this Avro Serdes instead. Unlike Confluent's Avro Serdes, the record schema will be serialized with each message.

[schema-registry]: https://docs.confluent.io/current/schema-registry

# Install
```groovy
dependencies {
  implementation 'com.mitchseymour:kafka-registryless-avro-serdes:0.1.0'
}
```

# Usage
Once you've generated your classes from an Avro schema file, for example, with the [gradle-avro-plugin][gradle-avro-plugin], you can use the `AvroSerdes#get` method to generate an Avro Serdes for a generated class. For example, if you generated a class named `Tweet` from the following definition:

```json
{
    "namespace": "com.mitchseymour.model",
    "name": "Tweet",
    "type": "record",
    "fields": [
      {
        "name": "id",
        "type": "long"
      },
      {
        "name": "text",
        "type": "string"
      }
    ]
 }
```

You could then create an Avro Serde for that class using this code:

```java
Serde<Tweet> serde = AvroSerdes.get(Tweet.class);
```

The resulting Serde can be used anywhere you would normally use one of Kafka's built-in Serdes. For example, in a Kafka Streams app, you could do this:

```java
stream.to("tweets", Produced.with(Serdes.String(), AvroSerdes.get(Tweet.class)));
```

[gradle-avro-plugin]: https://github.com/davidmc24/gradle-avro-plugin
