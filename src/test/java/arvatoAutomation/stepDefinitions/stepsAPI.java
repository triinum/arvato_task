package arvatoAutomation.stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.Assert;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;

public class stepsAPI {

    private String baseUri = System.getenv("BASE_URI");
    private String authToken = System.getenv("AUTH_TOKEN");
    private String bankAcc = System.getenv("BANK_ACCOUNT");
    private String countryCode;
    private String bankAccount;
    private RequestSpecification request;
    private Response response;

    @Given("the bank account validation request with a valid IBAN")
    public void bankAccountValidationWithValidIban() {
        // If no valid bank account is entered into env. variable, fake bank account is created within the length of 7 and 34 chars
        if (bankAcc.length() == 0) {
            String[] countryCodes = Locale.getISOCountries();
            int randomIndex = new Random().nextInt(countryCodes.length);
            countryCode = countryCodes[randomIndex];
            bankAcc = countryCode + RandomStringUtils.randomNumeric(5, 32);
        } // Else the bank account is taken from the env. variable
        else {
            bankAccount = bankAcc;
        }
    }

    // This step definition combines two steps since they perform the same action
    @Given("^(?:{string} authentication token|the bank account validation request with {string} JWT token)$")
    public void passAuth(String isValid) {
        String token = isValid == "valid" ? authToken : "";

        // Setting the uri, authorisation token and content type for the request method
        RestAssured.baseURI = baseUri;
        request = RestAssured.given().
                headers("X-Auth-Key", token).
                contentType("application/json");
    }

    // This step definition combines two steps since they perform the same action
    @When("^(?:sample request is sent to the server|bank account validation request is sent to the server)$")
    public void makeRequest() {
        // Creating JSON object with one required field: bankAccount
        JSONObject requestParams = new JSONObject();
        requestParams.put("bankAccount", bankAccount);

        // Making a POST request with the payload
        request.body(requestParams.toString());
        response = request.post(RestAssured.baseURI);
    }
    @Then("server responds with HTTP response code {int}")
    public void checkStatusCode(int statusCode) {
        // Asserting that status code from the response is equal to expected status code
        Assert.assertEquals(response.getStatusCode(), statusCode);
    }

    @And("response body contains isValid {string}")
    public void checkIsValid(String isValid) {
        // Asserting the value of isValid flag from the response
        Assert.assertEquals(response.path("isValid"), Boolean.valueOf(isValid));
    }

    @And("response body contains {string} message.")
    public void checkResponseMessage(String message) {
        // Asserting that contains the expected message
        Assert.assertEquals(response.path("message"), message);
    }
}
