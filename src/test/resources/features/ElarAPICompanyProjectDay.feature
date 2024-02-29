Feature: Validating Yards api calls

  @regression @spi @smoke @TC-102
  Scenario: Validating POST yard api call
    Given user creates yard with post yard api call with data
      | name    | Patel Yard 2  |
      | address | 456 street st |
      | city    | New York      |
      | state   | NY            |
      | zipCode | 60173         |
      | spots   | 1123          |
    When user gets created yard with get api call
    Then user validates get call response having yard details

  @regression @spi @smoke @TC-110
  Scenario:  Validating PATCH yard api call
    Given user creates yard with post yard api call with data
      | name    | Patel Yard 2  |
      | address | 456 street st |
      | city    | New York      |
      | state   | NY            |
      | zipCode | 60173         |
      | spots   | 1123          |
    When user updates created yard with Patch api call with data
      | name  | Patel Yard 2345 |
      | city  | Miami           |
      | state | FL              |
    When user gets created yard with get api call
    Then user validates get call response having updated yard details
    And user validates data is updated in BD

  @  @regression @spi @smoke @TC-111

  Scenario Outline: Validating POST yard api call - negative scenario
    Given user creates yard with post yard api call with data
      | name    | <name>        |
      | address | 456 street st |
      | city    | New York      |
      | state   | NY            |
      | zipCode | 60173         |
      | spots   | 1123          |
    Then user validates status code 400
    And  user validates "<errorMessage>" error message
    And user validates that yard is not persisted in BD
    Examples:
      | name                                                                                   | errorMessage                                                     |
      | abc!                                                                                   | Enter the correct data (leters, digits and '- & \| . ()' symbols |
      | ejhfgfggfehgdvldhvldhvlahvldfhvladhfvbadhvdfhbldhbvdhbavlhblfhdlavhdfblabvldfhbvaldhfbvladfhjkkjhv | Ensure this field has no more than 50 characters.                |

  @regression @spi @smoke @TC-112
  Scenario Outline:  Validating query parameters of GET api call
    Given user creates 20 yards if there is no less then 20 yards in BD
    When user gets yards with GET api call with guery parameters
      | status   | <status>   |
      | ordering | <ordering> |
      | offset   | <offset>   |
      | limit    | <limit>    |
    Then  user validates api response having right data
    And user validates yards data with BD
    Examples:
      | status     | ordering | offset | limit |
      | active     | -id      | 15     | 2     |
      | active     | -id      | 1      | 20    |
      | active     | id       | 3      | 4     |
      | non-active | -id      | 0      | 1     |

  @TC-113
  Scenario: Validating query parameters of GET yards api call - negative scenario (offset is more than number of yards)
    Given  user gets total numbers of yards from DB
    When user gets yards with query param offset more than total yards
    Then  user validates 0 number od yards in response


  @Homework
  Scenario:  Validating query parameters of GET yards api call - negative scenario (with offset negative number)
    Given user creates 20 yards if there is no less then 20 yards in BD
    When user  sends get yards api call with negative -5 query param offset
    Then user validates "Offset can not be negative number" query param error message

  @Homework2
  Scenario: Validating get yard api call - negative scenario (getting not exciting yard )404 status code
    Given user checks what yard id doesn't exist in BD
    When  user sends get call for not existing yard
    Then user validates status code 404

  @regression @smoke @api @TC-112
  Scenario: Validating PATCH company api call
    Given user creates company with post company api call with data
      | Name                             | MindtekOnline |
      | MC#                              | random        |
      | DOT#                             | random        |
      | Phone number                     | 555-999-2345  |
      | Street                           | 123 ABC       |
      | City                             | Chicago       |
      | State                            | IL            |
      | Zip code                         | 60655         |
      | Email                            | abc@gmail.com |
      | Insurance(producer company name) | Geico         |
      | Policy Expiration date           | 02/22/2024    |
      | Policy number                    | 12312341234   |
    Then user validates GET api call company has the right details
    When user updates created company with patch api call with data
      | Name   | MindtekOnlineEdited |
      | Street | 789 Qwert st        |
    When user get created company with get api call
    Then user validates get call response having updated company details
    And user validates data is updated in DB






