package stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import pojo.Booking;
import pojo.BookingDetails;
import utils.ResponseHandler;
import utils.TestContext;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

public class CreateBookingStepDefinition {
    private TestContext context;
    private static final Logger LOG = LogManager.getLogger(CreateBookingStepDefinition.class);

    public CreateBookingStepDefinition(TestContext context) {
        this.context = context;
    }

    @Given("User can access the endpoint {string}")
    public void user_can_access_the_endpoint(String endpoint) {
        context.session.put("endpoint", endpoint);
    }

    @When("User creates a new booking")
    public void user_creates_a_new_booking(DataTable dataTable) {
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

        context.response = context.requestSetup().body(bookingBody.toString())
                .post(context.session.get("endpoint").toString());

        Booking booking = ResponseHandler.deserializedResponse(context.response, Booking.class);
        assertNotNull(booking, "Booking not created");

        LOG.info("Newly Created Booking Id: "+booking.getBookingid());
        validateBookingData(new JSONObject(bookingData), booking);
    }

    private void validateBookingData(JSONObject bookingData, Booking booking) {
        assertNotNull(booking.getBookingid(), "Booking is missing");
        assertEquals(bookingData.get("firstname"), booking.getBookingDetails().getFirstname(), "firstname not correct");
        assertEquals(bookingData.get("lastname"), booking.getBookingDetails().getLastname(), "lastname not correct");
        assertEquals(Integer.parseInt((String) bookingData.get("totalprice")), booking.getBookingDetails().getTotalprice(), "totalprice not correct");
        assertEquals(Boolean.parseBoolean((String) bookingData.get("depositpaid")), booking.getBookingDetails().isDepositpaid(), "depositpaid not correct");
        assertEquals(bookingData.get("checkin"), booking.getBookingDetails().getBookingdates().getCheckin(), "checkin not correct");
        assertEquals(bookingData.get("checkout"), booking.getBookingDetails().getBookingdates().getCheckout(), "checkout not correct");
        assertEquals(bookingData.get("additionalneeds"), booking.getBookingDetails().getAdditionalneeds(), "Firstname not correct");
    }

    @Then("User should get response code as {int}")
    public void user_should_get_response_code_as(int statusCode) {
        assertEquals(statusCode, context.response.getStatusCode());
    }

    @Then("User validates response with JSON Schema {string}")
    public void user_validates_response_with_json_schema(String filename) {
        context.response.then().assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/"+filename));
        LOG.info("Create Booking Schema Validated");
    }
}