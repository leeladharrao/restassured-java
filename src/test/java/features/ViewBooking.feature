@ViewBooking
Feature: View Booking in restful-booker

  Scenario: View all Booking IDs
    Given User can access the endpoint "/booking"
    When User makes a request to view all Booking IDs
    Then User should get response code as 200
    And User should be able to see all booking IDs

  Scenario: VIew Booking details with Booking Id
    Given User can access the endpoint "/booking"
    Then User makes a request to view all Booking IDs
    And User makes a request to view details of a Booking with Id
    Then User should get response code as 200
    And User validates response with JSON Schema "BookingDetailsSchema.json"