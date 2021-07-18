package APISteps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.apiConstants;
import utils.apiPayloadConstants;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class APIWorkflowSteps {
	RequestSpecification request;
	Response response;
	static String employee_id;

	@Given("a request is prepared to create an employee")
	public void a_request_is_prepared_to_create_an_employee() {

		request = given().header(apiConstants.Header_Content_type, apiConstants.Content_type)
				.header(apiConstants.Header_Authorization, GenerateTokenSteps.token)
				.body(apiPayloadConstants.createEmployeePayload());
	}

	@When("a POST call is made to create an employee")
	public void a_post_call_is_made_to_create_an_employee() {

		response = request.when().post(apiConstants.CREATE_EMPLOYEE_URI);
	}

	@Then("the status code for creating an employee is {int}")
	public void the_status_code_for_creating_an_employee_is(int statusCode) {
		
		response.then().assertThat().statusCode(statusCode);
	}

	@Then("the employee created contains key {string} and value {string}")
	public void the_employee_created_contains_key_and_value(String message, String employeeCreated) {

		response.then().assertThat().body(message, equalTo(employeeCreated) );
	}

	@Then("the employeeID {string} is stored as a global variable to be used for other calls")
	public void the_employee_id_is_stored_as_a_global_variable_to_be_used_for_other_calls(String empID) {
		
		employee_id = response.jsonPath().getString(empID);
		
	}

}
