package com.message.demo.test.component;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features/test-apis.feature",
        plugin = {
                "pretty",
                "json:build/reports/cucumber/messages/cucumber.json",
                "html:build/reports/cucumber/messages"},
        monochrome = true)
public class MessageServiceTests extends AbstractTests {

}
