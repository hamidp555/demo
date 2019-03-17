package com.message.demo.test.component.steps;

import com.message.demo.test.component.common.Context;
import com.message.demo.test.component.domain.Client;
import cucumber.api.java8.En;

public class TestApisSteps implements En {

    public TestApisSteps(Context context) {
        Client client = context.getClient();
        When("^user creates a message$", () -> {
            client.createAndConfirm();
        });

        And("^user creats another message$", () -> {
            client.createAndConfirm();
        });

        Then("^user retrieves the message successfully$", () -> {
            client.getAndConfirm();
        });

        Then("^user retrieves all messages successfully$", () -> {
            client.getAllAndConfirm();
        });

        Then("^user deletes that message successfully$", () -> {
            client.deleteAndConfirm();
        });

        Then("^user updates that message successfully$", () -> {
            client.updateAndConfirm();
        });

    }

}
