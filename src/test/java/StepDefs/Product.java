package StepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

public class Product {
    private String apiKey;
    private Response response;

    @Given("a valid API key")
    public void setValidAPIKey() {
        // Set your API key here
        apiKey = "live_T9F0EagiMRUhXXWjmuz0n9JZLHoDyFpL3aZFSf6PIvgG3N8AJNVsyZc2EAuddkXa";
    }

    @When("the user requests {int} images")
    public void requestImages(int count) {
        response = RestAssured.given()
                .header("x-api-key", apiKey)
                .queryParam("limit", count)
                .get("https://api.thecatapi.com/v1/images/search");
    }

    @Then("the API should return a successful response")
    public void checkSuccessfulResponse() {
        Assert.assertEquals(200, response.getStatusCode());
        System.out.println("The Response Status code is: " + response.getStatusCode());
    }

    @And("the response should contain {int} images")
    public void checkImageCount(int count) {
        String responseBodyAsString = response.getBody().asString();
        System.out.println("Response Body: " + responseBodyAsString);
        response.then().body("size()", equalTo(count));
        System.out.println("Number of images: " + response.jsonPath().getList("$").size());
    }

    @When("the user requests {int} images without an API key")
    public void requestImagesWithoutAPIKey(int count) {
        response = RestAssured.given()
                .queryParam("limit", count)
                .get("https://api.thecatapi.com/v1/images/search");
    }

    @And("the response should contain a maximum of {int} images")
    public void checkMaxImageCount(int count) {
        String responseBodyAsString = response.getBody().asString();
        System.out.println("Response Body: " + responseBodyAsString);
        assertTrue(response.jsonPath().getList("$").size() <= count);

        System.out.println("Number of images: " + response.jsonPath().getList("$").size());
    }
}
