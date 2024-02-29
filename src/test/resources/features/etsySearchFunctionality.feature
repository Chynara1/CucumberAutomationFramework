@etsy @etsySearch
Feature: Etsy Search Functionality
# Backround uns steps before every scenario
  Background: Etsy setup
    Given  user navigates to the etsy application
    When user searches for keyword "carpet"

  @etsySearchValidation
  Scenario: Validating search result contains searched item
    Then user verifying the search result contains
      | item    |
      | rug     |
      | turkish |
      | persian |
#The values between pipes are in DataTable
  @etsyPriceRange
  Scenario: validating price range filter for searched item
    And user selects price range over thousand dollar
    Then user validating price range for items over 1000.00