@Login
Feature: Login Functionality

  Background: Navigating to SmartBear application
    Given user navigates to the SmartBear application
  @LoginPositive
  Scenario: Validating login functionality with valid credentials
    When  user logs in with user name "Tester" and password "test"
    Then user is successfully logged in and title is "Web Orders"

@LoginNegative
Scenario: Validating login functionality with invalid credentials
  When  user logs in with user name "Tester" and password "testtt"
  Then Verifying the invalid message "Invalid Login or Password."
