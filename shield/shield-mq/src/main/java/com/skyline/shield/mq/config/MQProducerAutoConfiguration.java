package com.skyline.shield.mq.config;

import com.skyline.shield.mq.producer.Producer;
import com.skyline.shield.mq.producer.RocketMqProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RocketMQ自动配置
 */
@Slf4j
@Configuration
@ConditionalOnBean(MQBaseAutoConfiguration.class)
@ConditionalOnProperty(MQProducerAutoConfiguration.PRODUCER_GROUP_NAME_PROPERTY)
public class MQProducerAutoConfiguration extends MQBaseAutoConfiguration {

    protected static final String PRODUCER_GROUP_NAME_PROPERTY = "spring.rocketmq.producerGroupName";

    /**
     * 初始化向rocketmq发送普通消息的生产者
     */
    @Bean
    public DefaultMQProducer defaultProducer() throws MQClientException {
        /**
         * 一个应用创建一个Producer，由应用来维护此对象，可以设置为全局对象或者单例<br>
         * 注意：ProducerGroupName需要由应用来保证唯一<br>
         * ProducerGroup这个概念发送普通的消息时，作用不大，但是发送分布式事务消息时，比较关键，
         * 因为服务器会回查这个Group下的任意一个Producer
         */
        DefaultMQProducer producer = new DefaultMQProducer(mqProperties.getProducerGroupName(), true);
        producer.setNamesrvAddr(mqProperties.getNamesrvAddr());
        producer.setSendMsgTimeout(mqProperties.getSendMsgTimeout());
        producer.setVipChannelEnabled(mqProperties.isVipChannelEnabled());

        /**
         * Producer对象在使用之前必须要调用start初始化，初始化一次即可<br>
         * 注意：切记不可以在每次发送消息时，都调用start方法
         */
        producer.start();
        log.info("RocketMq defaultProducer Started.");
        return producer;
    }

    @Bean
    public Producer rocketMqProducer(DefaultMQProducer defaultMQProducer) {
        RocketMqProducer rocketMqProducer = new RocketMqProducer();
        rocketMqProducer.setProducer(defaultMQProducer);
        rocketMqProducer.setRetryNum(mqProperties.getRetryNum());
        return rocketMqProducer;
    }
}