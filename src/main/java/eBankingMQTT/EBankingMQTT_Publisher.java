package eBankingMQTT;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class EBankingMQTT_Publisher {

	static int qos = 2;
	
		private static MqttClient sampleClient ;


		public static void main(String[] args) {

			String login = "/userAccount/login";
			String viewAccount = "/userAccount/viewAccount";
			String changePass = "/userAccount/changePass";
			String loginContent = "Login has been successful. Welcome User";
			String viewContent = "Here are your recent transactions.....";
			String changePassContent = "Your password has been changed succesfully";

			String broker = "tcp://localhost:1883";
			String clientId = "Publisher";

			MemoryPersistence persistence = new MemoryPersistence();

			try {

				sampleClient = new MqttClient(broker, clientId, persistence);
				MqttConnectOptions connOpts = new MqttConnectOptions();

				//if cleanSession is true before connecting the client, 
				//then all pending publication deliveries for the client are removed 
				//when the client connects.
				connOpts.setCleanSession(true);

				connOpts.setKeepAliveInterval(180);
				
				//connOpts.setUserName("");
				//connOpts.setPassword("");
				
				/*
				 * connect
				 */
				System.out.println("Connecting to broker: " + broker);
				sampleClient.connect(connOpts);
				System.out.println("Connected");


				/*
				 * sending messages
				 */

				publishMessage(login, loginContent, qos, false);
				
				publishMessage(viewAccount, viewContent,qos, false);
				
				publishMessage(changePass, changePassContent, qos, false);

				/*
				 * disconnect
				 */        		   
				sampleClient.disconnect();

				System.out.println("Disconnected");
				
				sampleClient.close();
		        
				System.exit(0);

			} catch (MqttException me) {
				System.out.println("reason " + me.getReasonCode());
				System.out.println("msg " + me.getMessage());
				System.out.println("loc-msg " + me.getLocalizedMessage());
				System.out.println("cause " + me.getCause());
				System.out.println("exception " + me);
				me.printStackTrace();
			}

		}

		private static void publishMessage(String topic, String payload, int qos, boolean retained) {

			System.out.println("Publishing message: " + payload + " on topic "+ topic );            

			MqttMessage message = new MqttMessage(payload.getBytes());
			message.setRetained(retained);
			message.setQos(qos);     

			try {

				sampleClient.publish(topic, message);

			} catch (MqttException me) {
				System.out.println("reason " + me.getReasonCode());
				System.out.println("msg " + me.getMessage());
				System.out.println("loc-msg " + me.getLocalizedMessage());
				System.out.println("cause " + me.getCause());
				System.out.println("exception " + me);
				me.printStackTrace();
			}

			System.out.println("Message published");


		}

	}
