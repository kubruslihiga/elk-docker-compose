from kafka import KafkaConsumer

consumer = KafkaConsumer('test', bootstrap_servers="10.5.0.3:9092")
for msg in consumer:
    print (msg)
