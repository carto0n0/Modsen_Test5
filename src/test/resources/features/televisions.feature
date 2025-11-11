Feature: Go to the Televisions section

  Background: Main page of the intershop website is open
    Given site is open

  Scenario: Go to the catalog -> Electronics -> Televisions
    When Hover the cursor over Directory
    And Hover the cursor over Electronics
    And Click on the "Televisions" section
    Then There is a transition to the "Televisions" page.