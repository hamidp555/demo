Feature: Test All Apis

  Scenario: Create a message
    When user creates a message
    Then user retrieves the message successfully

  Scenario: Delete a message
    When user creates a message
    Then user deletes that message successfully

  Scenario: Update a message
    When user creates a message
    Then user updates that message successfully

  Scenario: Get all messages
    When user creates a message
    And user creats another message
    Then  user retrieves all messages successfully
