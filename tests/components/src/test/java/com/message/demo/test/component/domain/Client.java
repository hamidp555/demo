package com.message.demo.test.component.domain;

import com.message.demo.test.component.common.JsonSerializer;
import com.message.demo.test.component.common.MessageService;
import io.restassured.response.Response;
import org.testcontainers.shaded.org.apache.http.HttpStatus;
import org.testcontainers.shaded.javax.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class Client {

    List<Message> messages = new ArrayList<>();

    public void createAndConfirm() {

        Message message = new Message();
        message.setMessage("message one");

        CreateMessageRequest req = new CreateMessageRequest();
        req.setMessage(message.getMessage());

        String payload = JsonSerializer.serialize(req);
        Response response = given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .body(payload)
                .post(uri() + "/v1/messages");

        response.then().statusCode(HttpStatus.SC_CREATED);

        String body = response.print();
        CreateMessageResponse createMessageResponse = JsonSerializer.deserialize(body, CreateMessageResponse.class);

        assertThat(createMessageResponse.getId()).isNotNull();

        message.setId(createMessageResponse.getId());
        messages.add(message);
    }

    public void getAndConfirm() {
        Message message = messages.get(0);
        String path = uri() + "/v1/messages/" + message.getId();
        Response response = given()
                .when()
                .get(path);

        response.then().statusCode(HttpStatus.SC_OK);
        String body = response.print();
        GetMessageResponse getMessageResponse = JsonSerializer.deserialize(body, GetMessageResponse.class);
        assertThat(getMessageResponse.getId()).isEqualTo(message.getId());
    }

    public void updateAndConfirm() {
        Message message = messages.get(0);
        String path = uri() + "/v1/messages/" + message.getId();

        UpdateMessageRequest req = new UpdateMessageRequest();
        req.setMessage("message one updated");
        String payload = JsonSerializer.serialize(req);

        Response putResp = given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .body(payload)
                .put(path);

        putResp.then().statusCode(HttpStatus.SC_NO_CONTENT);

        Response getResp = given()
                .when()
                .get(path);

        getResp.then().statusCode(HttpStatus.SC_OK);
        String body = getResp.print();
        GetMessageResponse getMessageResponse = JsonSerializer.deserialize(body, GetMessageResponse.class);
        assertThat(getMessageResponse.getMessage()).isEqualTo("message one updated");
    }

    public void deleteAndConfirm() {
        Message message = messages.get(0);
        String path = uri() + "/v1/messages/" + message.getId();
        Response deleteResp = given()
                .when()
                .delete(path);
        deleteResp.then().statusCode(HttpStatus.SC_NO_CONTENT);

        Response getResp = given()
                .when()
                .get(path);
        getResp.then().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    public void getAllAndConfirm() {
        Response resp = given()
                .when()
                .get(uri() + "/v1/messages");
        resp.then().statusCode(HttpStatus.SC_OK);
        String body = resp.print();
        GetMessagesResponse getMessagesResponse = JsonSerializer.deserialize(body, GetMessagesResponse.class);
    }

    private static String uri() {
        return MessageService.getServiceUri();
    }
}
