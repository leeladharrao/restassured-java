@HealthCheck
Feature: To check restful-booker status

  Scenario: To confirm whether the API is up and running
    Given User can access the endpoint "/ping"
    When User makes a request to check the status of api
    Then User should get response code as 201