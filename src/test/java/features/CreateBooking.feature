@CreateBooking
Feature: Create a new Booking in restful-booker

  Scenario: Create a new Booking
    Given User can access the endpoint "/booking"
    When User creates a new booking
      | firstname | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds |
      | John      | Doe      | 1200       | true        | 2021-05-05 | 2021-05-15 | Breakfast       |
    Then User should get response code as 200
    And User validates response with JSON Schema "CreateBookingSchema.json"
