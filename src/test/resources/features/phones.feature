Feature: Go to the Phones section

  Background: Main page of the intershop website is open
    Given site is open

  Scenario: Go to the catalog -> Electronics -> Phones
    When Hover the cursor over Directory
    And Hover the cursor over Electronics
    And Click on the "Phones" section
    Then There is a transition to the "Phones" page.