package com.message.demo.test.component.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.time.Duration;

public class MessageService {

    private static final Logger LOG = LoggerFactory.getLogger(MessageService.class);
    private static final int MESSAGE_SERVICE_PORT = 8080;
    private static final int DB_SERVICE_PORT = 5432;

    private static final String POSTGRES_USER = "messageservice";
    private static final String POSTGRES_PASSWORD = "secret";
    private static final String POSTGRES_DB = "demo";


    private static final String SERVICE_NETWORK_ALIAS = RandomNames.randomize("message-service");
    private static final String DB_NETWORK_ALIAS = RandomNames.randomize("postgres");

    private static GenericContainer messageServiec;
    private static GenericContainer db;
    private static Network network;

    public static void startService() {

        Slf4jLogConsumer dbLogConsumer = new Slf4jLogConsumer(LoggerFactory.getLogger("database-service"));
        Slf4jLogConsumer keystoneLogConsumer = new Slf4jLogConsumer(LoggerFactory.getLogger("message-service"));


        db = new GenericContainer("postgres:9.6")
                .withExposedPorts(DB_SERVICE_PORT)
                .withNetwork(getNetwork())
                .withNetworkAliases(DB_NETWORK_ALIAS)
                .withLogConsumer(dbLogConsumer)
                .withEnv("POSTGRES_USER", POSTGRES_USER)
                .withEnv("POSTGRES_PASSWORD", POSTGRES_PASSWORD)
                .withEnv("POSTGRES_DB", POSTGRES_DB)
                .waitingFor(Wait.forListeningPort());
        db.start();

        messageServiec = new GenericContainer("demo/message-service:1.0-SNAPSHOT")
                .withExposedPorts(MESSAGE_SERVICE_PORT)
                .withNetwork(getNetwork())
                .withNetworkAliases(SERVICE_NETWORK_ALIAS)
                .withLogConsumer(keystoneLogConsumer)
                .withEnv("spring_jpa_hibernate_ddlauto", "none")
                .withEnv("spring_profiles_active", "test")
                .withEnv("demo_database_name", POSTGRES_DB)
                .withEnv("demo_database_host", DB_NETWORK_ALIAS)
                .withEnv("demo_database_port", String.valueOf(DB_SERVICE_PORT))
                .withEnv("demo_database_username", POSTGRES_USER)
                .withEnv("demo_database_password", POSTGRES_PASSWORD)
                .waitingFor(
                        Wait.forHttp("/actuator/health")
                                .forStatusCode(200)
                                .withStartupTimeout(Duration.ofSeconds(60)));
        messageServiec.start();

    }

    public static String getServiceUri() {
        return "http://"
                + messageServiec.getContainerIpAddress()
                + ":" + messageServiec.getMappedPort(MESSAGE_SERVICE_PORT);
    }

    static Network getNetwork() {
        if (network == null) {
            network = Network.newNetwork();
        }
        return network;
    }
}
