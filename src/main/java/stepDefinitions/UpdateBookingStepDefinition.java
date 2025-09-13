package stepDefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import pojo.BookingDetails;
import utils.ResponseHandler;
import utils.TestContext;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UpdateBookingStepDefinition {

    private static final Logger LOG = LogManager.getLogger(CreateBookingStepDefinition.class);
    private final TestContext context;

    public UpdateBookingStepDefinition(TestContext context) {
        this.context = context;
    }

    @When("User generates an auth token with credentials as {string} and {string}")
    public void user_generates_an_auth_token_with_credentials_as_and(String username, String password) {
        JSONObject creds = new JSONObject();
        creds.put("username", username);
        creds.put("password", password);
        context.response = context.requestSetup().body(creds.toString()).when().post(context.session.get("endpoint").toString());
        String token = context.response.path("token");
        assertNotNull(token, "Token is not generated!!");
        LOG.info("Generated token: " + token);
        context.session.put("token", token);
    }

    @When("User updates booking details")
    public void user_updates_booking_details(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> bookingData = dataTable.asMaps().get(0);
        JSONObject bookingBody = new JSONObject();
        bookingBody.put("firstname", bookingData.get("firstname"));
        bookingBody.put("lastname", bookingData.get("lastname"));
        bookingBody.put("totalprice", bookingData.get("totalprice"));
        bookingBody.put("depositpaid", bookingData.get("depositpaid"));
        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", bookingData.get("checkin"));
        bookingDates.put("checkout", bookingData.get("checkout"));
        bookingBody.put("bookingdates", bookingDates);
        bookingBody.put("additionalneeds", bookingData.get("additionalneeds"));

        context.response = context.requestSetup()
                .pathParam("bookingId", context.session.get("bookingId"))
                .cookie("token", context.session.get("token").toString())
                .body(bookingBody.toString())
                .put(context.session.get("endpoint").toString() + "/{bookingId}");

        BookingDetails booking = ResponseHandler.deserializedResponse(context.response, BookingDetails.class);
        assertNotNull(booking, "Booking not updated");
    }

    @Then("User makes a request to update firstname {string} and lastname {string}")
    public void user_makes_a_request_to_update_firstname_and_lastname(String firstname, String lastname) {
        JSONObject body = new JSONObject();
        body.put("firstname", firstname);
        body.put("lastname", lastname);
        context.response = context.requestSetup().body(body.toString())
                .pathParam("bookingId", context.session.get("bookingId"))
                .cookie("token", context.session.get("token").toString())
                .when().patch(context.session.get("endpoint").toString() + "/{bookingId}");

        BookingDetails booking = ResponseHandler.deserializedResponse(context.response, BookingDetails.class);
        assertNotNull(booking, "Booking not updated");

    }
}
