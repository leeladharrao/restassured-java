package stepDefinitions;

import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.TestContext;

public class HeathCheckStepDefinition {

    private TestContext context;
    private static final Logger LOG = LogManager.getLogger(CreateBookingStepDefinition.class);

    public HeathCheckStepDefinition(TestContext context) {
        this.context = context;
    }

    @When("User makes a request to check the status of api")
    public void user_makes_a_request_to_check_the_status_of_api() {
        context.response = context.requestSetup().get(context.session.get("endpoint").toString());
    }
}
