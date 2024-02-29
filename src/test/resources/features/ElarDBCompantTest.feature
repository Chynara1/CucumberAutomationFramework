Feature: Company database validation

  Background: Navigation to the ElarLogisticsApp  and add company page
    Given user navigates to the elar Logistic application
    When user login with user name "student1@mindtekbootcamp.com" and password "mindtek109"
    Then user clicks on Bag Icon button
    And user clicks on + Add Company button


  @TC-0101
  Scenario: Validate edit company is updated in DB
    And user creates company with data
      | Name                             | Patel Patel 123 |
      | MC#                              | random          |
      | DOT#                             | random          |
      | Phone number                     | 5559999999      |
      | Street                           | 123 ABC         |
      | City                             | Chicago         |
      | State                            | IL              |
      | Zip code                         | 60655           |
      | Email                            | abc@gmail.com   |
      | Insurance(producer company name) | Geico           |
      | Policy Expiration date           | 02/29/2024      |
      | Policy number                    | 12312341234     |
    Then The user clicks on + Add Company button
    And The user validates success message  of creating "successfully created"
    And user validates created company persists in DB
    When user clicks on button
    And user clicks on edit button
    And user edits created company with data
      | Name   | MindtekOnlineEdited |
      | Street | Druid DR 123        |
    Then user validates success message of editing "successfully changed"
    And user validates edited company is updated in DB




