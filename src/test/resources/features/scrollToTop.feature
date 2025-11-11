Feature: Scroll to top of page

  Background: Main page of the intershop website is open
    Given site is open

  Scenario: Find the arrow at the bottom of the page to scroll up
    When Scroll down
    And Check visability of the arrow
    And Click on arrow
    Then Go back to the header