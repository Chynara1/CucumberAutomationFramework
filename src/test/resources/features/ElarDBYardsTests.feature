Feature:  Yards database validation

  Background:
    Given user navigates to the elar  application
    When user log in to Elar app
    And user clicks on Yard tab
    And user clicks om add yard button


  @regression @smoke @db @TC-100
  Scenario Outline: Add yard database validation
    And user creates yard with data
      | Name     | random      |
      | Street   | 444 abc st. |
      | City     | <City>      |
      | State    | <State>     |
      | Zip code | 61378       |
      | spots    | 1234        |
    Then user validates success message "successfully created"
    And user validates yard is persisted in BD
    Examples:
      | City        | State |
      | Chicago     | IL    |
      | New York    | NY    |
      | Los Angeles | CA    |


  Scenario Outline:  Edit yard database validation
    And user creates yard with data
      | Name     | random      |
      | Street   | 444 abc st. |
      | City     | <City>      |
      | State    | <State>     |
      | Zip code | 61378       |
      | spots    | 1234        |
    And user goes to created  yard page and clicks edit button
    And user updates name with "random"
    Then user validates success message "successfully changed"
    And user validates yard is persisted in BD
    Examples:
      | City        | State |
      | Chicago     | IL    |
      | New York    | NY    |
      | Los Angeles | CA    |

