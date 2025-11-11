Feature: Go to the Tablets section

  Background: Main page of the intershop website is open
    Given site is open

  Scenario: Go to the catalog -> Electronics -> Tablets
    When Hover the cursor over Directory
    And Hover the cursor over Electronics
    And Click on the "Tablets" section
    Then There is a transition to the "Tablets" page.