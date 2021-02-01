package com.edison.demo01.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.Random;

/**
 * @author edison
 * @version 1.0
 * @create 2021-01-29 16:59
 **/
public class Producer {
    public static String topic = "hello_kafka";//定义主题

    public static void main(String[] args) throws InterruptedException {
        Properties p = new Properties();
        /**
         * kafka地址，如果是多个集群，多个地址用逗号分割
         * 例如：192.168.23.76:9092,192.168.23.77:9092
         */
        p.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        //设置序列化类，可以写类的全路径
        p.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        p.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(p);

        try {
            while (true) {
                String msg = "Hello," + new Random().nextInt(100);
                ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, msg);
                /**
                 * 可以通过返回的Future来判断是否已经发送到kafka，增强消息的可靠性。
                 * 同时也可以使用send的第二个参数来回调，通过回调判断是否发送成功。
                 */
                kafkaProducer.send(record);
                System.out.println("消息发送成功:" + msg);
                Thread.sleep(500);
            }
        } finally {
            kafkaProducer.close();
        }
    }
}
