@UpdateBooking
Feature: Update Booking in restful-booker

  Background: Generate auth token
    Given User can access the endpoint "/auth"
    When User generates an auth token with credentials as "admin" and "password123"
    Then User should get response code as 200

  Scenario: Update a Booking
    Given User can access the endpoint "/booking"
    Then User makes a request to view all Booking IDs
    When User updates booking details
      | firstname | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds |
      | Ruth      | Doe      | 1200       | true        | 2021-05-05 | 2021-05-15 | Breakfast       |
    Then User should get response code as 200
    And User validates response with JSON Schema "BookingDetailsSchema.json"

  Scenario: Update a Booking with Parital Data
    Given User can access the endpoint "/booking"
    Then User makes a request to view all Booking IDs
    And User makes a request to update firstname "Amanda" and lastname "Jost"
    Then User should get response code as 200
    And User validates response with JSON Schema "BookingDetailsSchema.json"
