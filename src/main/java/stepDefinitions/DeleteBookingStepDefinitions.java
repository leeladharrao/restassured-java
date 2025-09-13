package stepDefinitions;

import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.TestContext;

public class DeleteBookingStepDefinitions {

    private static final Logger LOG = LogManager.getLogger(CreateBookingStepDefinition.class);
    private final TestContext context;

    public DeleteBookingStepDefinitions(TestContext context) {
        this.context = context;
    }

    @Then("User makes a request to delete a booking with Id")
    public void user_makes_a_request_to_delete_a_booking_with_id() {

        String bookingId = context.session.get("bookingId").toString();
        LOG.info("Deleting booking id: " + bookingId);
        context.response = context.requestSetup()
                .pathParam("bookingId", bookingId)
                .cookie("token", context.session.get("token").toString())
                .when().delete(context.session.get("endpoint").toString() + "/{bookingId}");
    }
}
