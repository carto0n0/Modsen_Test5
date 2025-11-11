Feature: Go to the Watches section

  Background: Main page of the intershop website is open
    Given site is open

  Scenario: Go to the catalog -> Electronics -> Watches
    When Hover the cursor over Directory
    And Hover the cursor over Electronics
    And Click on the "Watches" section
    Then There is a transition to the "Watches" page.