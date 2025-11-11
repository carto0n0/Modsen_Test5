Feature: Go to the PhotoAndVideos section

  Background: Main page of the intershop website is open
    Given site is open

  Scenario: Go to the catalog -> Electronics -> PhotoAndVideos
    When Hover the cursor over Directory
    And Hover the cursor over Electronics
    And Click on the "Photos/videos" section
    Then There is a transition to the "Photos/videos" page.