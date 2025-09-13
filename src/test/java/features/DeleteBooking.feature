@DeleteBooking
Feature: Delete a Booking in restful-booker

  Background: generate auth token
    Given User can access the endpoint "/auth"
    When User generates an auth token with credentials as "admin" and "password123"
    Then User should get response code as 200

  Scenario: Delete a Booking
    Given User can access the endpoint "/booking"
    Then User makes a request to view all Booking IDs
    And User makes a request to delete a booking with Id
    Then User should get response code as 201
