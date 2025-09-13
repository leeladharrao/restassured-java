package stepDefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pojo.BookingDetails;
import utils.ResponseHandler;
import utils.TestContext;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ViewBookingStepDefinition {
    private static final Logger LOG = LogManager.getLogger(CreateBookingStepDefinition.class);
    private final TestContext context;

    public ViewBookingStepDefinition(TestContext context) {
        this.context = context;
    }

    @When("User makes a request to view all Booking IDs")
    public void user_makes_a_request_to_view_all_booking_i_ds() {
        context.response = context.requestSetup().get(context.session.get("endpoint").toString());

        List<Integer> bookingIds = context.response.getBody().jsonPath().getList("bookingid", Integer.class);
        int randomBookingId = bookingIds.get(new Random().nextInt(bookingIds.size()));
        assertNotNull(randomBookingId, "Booking ID not found!");
        LOG.info("Booking Id: " + randomBookingId);
        context.session.put("bookingId", randomBookingId);
    }

    @Then("User should be able to see all booking IDs")
    public void user_should_be_able_to_see_all_booking_i_ds() {
        assertNotNull(context.response.getBody().jsonPath().get("[0].bookingid"), "Booking ID not found!!");
        for (int bookingId : context.response.getBody().jsonPath().getList("bookingid", Integer.class)) {
            LOG.info("Fetched random booking ID: " + bookingId);
        }
    }

    @Then("User makes a request to view details of a Booking with Id")
    public void user_makes_a_request_to_view_details_of_a_booking_with_id() {
        LOG.info("Getting details for Booking Id: " + context.session.get("bookingId"));

        context.response = context.requestSetup()
                .pathParam("bookingId", context.session.get("bookingId"))
                .when().get(context.session.get("endpoint") + "/{bookingId}");

        BookingDetails booking = ResponseHandler.deserializedResponse(context.response, BookingDetails.class);

        assertNotNull(booking, "Booking not fetched");
    }
}
