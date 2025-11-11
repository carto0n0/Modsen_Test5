Feature: Adding and deleting record

  Background: Open the planner
    Given  planner site is open

  Scenario: Add 10 entries, delete top entry, scroll down, verify that original notes remain
    When Add 10 new entries
    And delete top entry
    And scroll down
    Then check the original records