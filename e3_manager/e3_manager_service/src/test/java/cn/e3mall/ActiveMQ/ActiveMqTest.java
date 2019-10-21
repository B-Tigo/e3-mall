package cn.e3mall.ActiveMQ;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;
import org.springframework.jms.connection.ConnectionFactoryUtils;

import javax.jms.*;

public class ActiveMqTest {
/*

    */
/**
     * 点到点发送消息
     * @throws Exception
     *//*


    @Test
    public void testQueueProducer() throws Exception {
        //1.创建一个连接工厂对象，需要指定服务的ip及端口
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.130:61616");
        //2.使用工厂对象创建一个connection对象
        Connection connection = connectionFactory.createConnection();
        //3.开启连接，调用connection对象的start方法
        connection.start();
        //4.创建一个session对象
        //第一个参数：是否开启事务，一般不开启事务。如果true开启事务，第二个参数无意义。
        //第二个参数：应答模式。一般是自动应答或者手动应答。一般是自动应答
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5.使用session对象创建一个Destination对象，两种形式queue、topic，现在使用queue
        Queue queue = session.createQueue("test_queue");
        //6.使用session对象创建一个Producer对象
        MessageProducer producer = session.createProducer(queue);
        //7.创建一个Message对象，可以使用TextMessage
//        TextMessage textMessage = new ActiveMQTextMessage();
//        textMessage.setText("Hello,activemq");
        TextMessage textMessage = session.createTextMessage("hello,Activemq");
        //8.发送消息
        producer.send(textMessage);
        //9.关闭资源
        producer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testQueueConsumer() throws Exception {
        //创建一个connectionFactory对象连接MQ服务器
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.130:61616");
        //创建一个连接对象
        Connection connection = connectionFactory.createConnection();
        //开启连接
        connection.start();
        //使用Connection对象创建一个session对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建一个Destination对象.queue对象
        Queue queue = session.createQueue("spring-queue");
        //使用Session对象创建一个消费者对象
        MessageConsumer consumer = session.createConsumer(queue);
        //接收消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                //打印结果
                TextMessage textMessage = (TextMessage) message;
                String text;
                try {
                    text = textMessage.getText();
                    System.out.println(text);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        //等待接收消息
        System.in.read();
        //关闭资源
        consumer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testTopicProducer() throws Exception {
        //1.创建一个连接工厂对象，需要指定服务的ip及端口
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.130:61616");
        //2.使用工厂对象创建一个connection对象
        Connection connection = connectionFactory.createConnection();
        //3.开启连接，调用connection对象的start方法
        connection.start();
        //4.创建一个session对象
        //第一个参数：是否开启事务，一般不开启事务。如果true开启事务，第二个参数无意义。
        //第二个参数：应答模式。一般是自动应答或者手动应答。一般是自动应答
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5.使用session对象创建一个Destination对象，两种形式queue、topic，现在使用topic
        Topic topic = session.createTopic("test_Topic");
        //6.使用session对象创建一个Producer对象
        MessageProducer producer = session.createProducer(topic);
        //7.创建一个Message对象，可以使用TextMessage
//        TextMessage textMessage = new ActiveMQTextMessage();
//        textMessage.setText("Hello,activemq");
        TextMessage textMessage = session.createTextMessage("topic message  ");
        //8.发送消息
        producer.send(textMessage);
        //9.关闭资源
        producer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testTopicConsumer() throws Exception {
        //创建一个connectionFactory对象连接MQ服务器
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.130:61616");
        //创建一个连接对象
        Connection connection = connectionFactory.createConnection();
        //开启连接
        connection.start();
        //使用Connection对象创建一个session对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建一个Destination对象.topic对象
        Topic topic = session.createTopic("test_Topic");
        //使用Session对象创建一个消费者对象
        MessageConsumer consumer = session.createConsumer(topic);
        //接收消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                //打印结果
                TextMessage textMessage = (TextMessage) message;
                String text;
                try {
                    text = textMessage.getText();
                    System.out.println(text);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("消费者1启动...");
        //等待接收消息
        System.in.read();
        //关闭资源
        consumer.close();
        session.close();
        connection.close();
    }
*/

}
