package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import utilities.ConfigReader;
import utilities.ElarAPIUtils;
import utilities.JDBCUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;

public class ElarYardsAPISteps {
    String yardId;
    Map<String, Object> map;

    Response response;
    Map<String, Object> updatedYard;
    Map<String, Object> queryParams;
    Map<String, Object> offsetQueryParams;

    int totalYards;

    @Given("user creates yard with post yard api call with data")
    public void user_creates_yard_with_post_yard_api_call_with_data(io.cucumber.datatable.DataTable dataTable) {
        map = dataTable.asMap(String.class, Object.class);

       /*
       POst Yard
        */
        response = given().baseUri(ConfigReader.getProperty("ElarAPIBaseURL"))
                .and().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().header("Authorization", "Token 50c79942251edf8c7fd98c8036480b4f80b84c1d")
                .and().body("{\n" +
                        "    \"location\": \"" + map.get("name") + "\",\n" +
                        "    \"status\": \"active\",\n" +
                        "    \"address\": \"" + map.get("address") + "\",\n" +
                        "    \"city\": \"" + map.get("city") + "\",\n" +
                        "    \"state\": \"" + map.get("state") + "\",\n" +
                        "    \"zip_code\": \"" + map.get("zipCode") + "\",\n" +
                        "    \"spots\": \"" + map.get("spots") + "\",\n" +
                        "    \"contacts\": []\n" +
                        "}")
                .and().log().all()
                .when().post("/yards/");
        response.then().log().all();
//        response.then().statusCode(201);
        yardId = response.body().jsonPath().getString("id");
    }


    @When("user gets created yard with get api call")
    public void user_gets_created_yard_with_get_api_call() {
        Response response = given().baseUri(ConfigReader.getProperty("ElarAPIBaseURL"))
                .and().accept(ContentType.JSON)
                .and().header("Authorization", "Token 50c79942251edf8c7fd98c8036480b4f80b84c1d")
                .and().log().all()
                .when().get("/yards/" + yardId + "/");
        response.then().statusCode(200);
    }

    @Then("user validates get call response having yard details")
    public void user_validates_get_call_response_having_yard_details() {
        //map -> expected result
        //response result
        Assert.assertEquals(map.get("name").toString(), response.body().jsonPath().getString("location"));
        Assert.assertEquals(map.get("address").toString(), response.body().jsonPath().getString("address"));
        Assert.assertEquals(map.get("city").toString(), response.body().jsonPath().getString("city"));
        Assert.assertEquals(map.get("state").toString(), response.body().jsonPath().getString("state"));
        Assert.assertEquals(map.get("zipCode").toString(), response.body().jsonPath().getString("zip_code"));
        Assert.assertEquals(map.get("spots").toString(), response.body().jsonPath().getString("spots"));

    }

    @When("user updates created yard with Patch api call with data")
    public void user_updates_created_yard_with_patch_api_call_with_data(io.cucumber.datatable.DataTable dataTable) {
        updatedYard = dataTable.asMap(String.class, Object.class);
        /*
        PATCH call
        1. request
         a. URL
         b. Headers
           1. Accept
           2. Authorization
           3. Content-Type
         c. Body

         2 Response
         */
        response = given().baseUri(ConfigReader.getProperty("ElarAPIBaseURL"))
                .and().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().header("Authorization", "Token 50c79942251edf8c7fd98c8036480b4f80b84c1d")
                .and().body("{\n" +
                        "    \"location\": \"" + updatedYard.get("name") + "\",\n" +
                        "    \"city\": \"" + updatedYard.get("city") + "\",\n" +
                        "    \"state\": \"" + updatedYard.get("state") + "\",\n" +
                        "    \"zip_code\": \"60173\"\n" +
                        "}")
                .and().log().all()
                .when().patch("/yards/" + yardId + "/");
        response.then().log().all();
        response.then().statusCode(200);
    }


    @Then("user validates get call response having updated yard details")
    public void user_validates_get_call_response_having_updated_yard_details() {
        //expected data -> updatedYard
        //actual data   -> response
        Assert.assertEquals(updatedYard.get("name").toString(), response.body().jsonPath().getString("location"));
        Assert.assertEquals(updatedYard.get("city").toString(), response.body().jsonPath().getString("city"));
        Assert.assertEquals(updatedYard.get("state").toString(), response.body().jsonPath().getString("state"));

    }

    @Then("user validates data is updated in BD")
    public void user_validates_data_is_updated_in_bd() throws SQLException {
        //expected data -> from map
        //actual data -> list of map from DB
        JDBCUtils.establishDBBConnection(
                ConfigReader.getProperty("ElarDBURL"),
                ConfigReader.getProperty("ElarDBUsername"),
                ConfigReader.getProperty("ElarDBPassword")
        );
        String query = "select * from core_company where company_name = '";
        List<Map<String, Object>> dbData = JDBCUtils.executeQuery("select location, city, state from core_yard where id = " + yardId);
        JDBCUtils.closeConnection();
        Assert.assertEquals(updatedYard.get("name"), dbData.get(0).get("location"));
        Assert.assertEquals(updatedYard.get("city"), dbData.get(0).get("city"));
        Assert.assertEquals(updatedYard.get("state"), dbData.get(0).get("state"));
    }

    @Then("user validates status code {int}")
    public void user_validates_status_code(Integer statusCode) {
        response.then().statusCode(statusCode);
    }

    @Then("user validates {string} error message")
    public void user_validates_error_message(String expectedErrorMessage) {
        Assert.assertEquals(expectedErrorMessage, response.body().jsonPath().getString("location[0]"));

    }

    @Then("user validates that yard is not persisted in BD")
    public void user_validates_that_yard_is_not_persisted_in_bd() throws SQLException {
        JDBCUtils.establishDBBConnection(
                ConfigReader.getProperty("ElarDBURL"),
                ConfigReader.getProperty("ElarDBUsername"),
                ConfigReader.getProperty("ElarDBPassword"));
        List<Map<String, Object>> dbData = JDBCUtils.executeQuery("select * from core_yard where id = " + yardId);
        JDBCUtils.closeConnection();

    }

    @Given("user creates {int} yards if there is no less then {int} yards in BD")
    public void user_creates_yards_if_there_is_no_less_then_yards_in_bd(Integer yardsNum, Integer yardsNum2) throws SQLException {
        JDBCUtils.establishDBBConnection( //making connection to BD
                ConfigReader.getProperty("ElarDBURL"),
                ConfigReader.getProperty("ElarDBUsername"),
                ConfigReader.getProperty("ElarDBPassword")
        );
        String query = "select * from core_company where company_name = '";
        List<Map<String, Object>> dbData = JDBCUtils.executeQuery("select * from core_yard");
        JDBCUtils.closeConnection();
        if (dbData.size() < 20) {
            //Here we will create 20 yards

        }

    }

    @When("user gets yards with GET api call with guery parameters")
    public void user_gets_yards_with_get_api_call_with_guery_parameters(io.cucumber.datatable.DataTable dataTable) {
        queryParams = dataTable.asMap(String.class, Object.class);
        //GET yard call
        response = ElarAPIUtils.getCall("/yards/", queryParams);
        response.then().statusCode(200);

    }

    @Then("user validates api response having right data")
    public void user_validates_api_response_having_right_data() {
        //queryParams -> status = active , ordering -id (desc),offset=15, limit=2
        List<String> statuses = response.body().jsonPath().getList("results.status");
        for (String status : statuses) {
            Assert.assertEquals(queryParams.get("status").toString(), status);
        }
        List<Integer> ids = response.body().jsonPath().getList("results.id");
        for (int i = 1; i < ids.size(); i++) {
            int id1 = (ids.get(i - 1));
            int id2 = (ids.get(i));
            if (queryParams.get("ordering").toString().equals("-id")) {
                Assert.assertTrue(id2 < id1);
            } else {
                Assert.assertTrue(id2 > id1);
            }
            Assert.assertTrue(ids.size() == Integer.parseInt(queryParams.get("limit").toString()));

        }
    }

    @Then("user validates yards data with BD")
    public void user_validates_yards_data_with_bd() throws SQLException {
        String order = "";
        order = queryParams.get("ordering").toString().equals("-id") ? "desc" : "asc";
        JDBCUtils.establishDBBConnection( //making connection to BD
                ConfigReader.getProperty("ElarDBURL"),
                ConfigReader.getProperty("ElarDBUsername"),
                ConfigReader.getProperty("ElarDBPassword")
        );
        // String query = "select * from core_company where company_name = '";
        List<Map<String, Object>> dbData = JDBCUtils.executeQuery("select *\n" +
                "from core_yard\n" +
                "where status='" + queryParams.get("name") + "'\n" +
                "order by id " + order + "\n" +
                "limit " + queryParams.get("limit") + " offset " + queryParams.get("offset") + ";");
        JDBCUtils.closeConnection();

        //Expected -> dbData
        //Actual -> response
        // status = active , ordering -id (desc),offset=15, limit=2
        List<Integer> ids = response.body().jsonPath().getList("results.id");

        for (int i = 0; i < dbData.size(); i++) {
            Assert.assertEquals(dbData.get(i).get("id"), (ids.get(i).toString()));
        }

    }

    @Given("user gets total numbers of yards from DB")
    public void user_gets_total_numbers_of_yards_from_db() throws SQLException {
        JDBCUtils.establishDBBConnection( //making connection to BD
                ConfigReader.getProperty("ElarDBURL"),
                ConfigReader.getProperty("ElarDBUsername"),
                ConfigReader.getProperty("ElarDBPassword")
        );
        String query = "select * from core_company where company_name = '";
        List<Map<String, Object>> dbData = JDBCUtils.executeQuery("select * from core_yard");
        JDBCUtils.closeConnection();
        totalYards = dbData.size();
    }


    @When("user gets yards with query param offset more than total yards")
    public void user_gets_yards_with_query_param_offset_more_than_total_yards() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("offset", totalYards + 1);
        response = ElarAPIUtils.getCall("/yards/", queryParams);
    }

    @Then("user validates {int} number od yards in response")
    public void user_validates_number_od_yards_in_response(Integer numberOfYards) {
        Assert.assertTrue(response.body().jsonPath().getList("results").size()==numberOfYards);
    }



    //Homework
    @When("user  sends get yards api call with negative {int} query param offset")
    public void user_sends_get_yards_api_call_with_negative_query_param_offset(Integer offsetParameter) throws SQLException {
        response = ElarAPIUtils.getCall("/yards/?status=active&offset="+offsetParameter);

    }

    @Then("user validates {string} query param error message")
    public void user_validates_query_param_error_message(String errorMessage) {
        Assert.assertEquals(response.body().jsonPath().getString("errorMessage"),errorMessage);


    }
    @Given("user deletes created yard in BD")
    public void user_deletes_created_yard_in_bd() throws SQLException {
        JDBCUtils.establishDBBConnection( //making connection to BD
                ConfigReader.getProperty("ElarDBURL"),
                ConfigReader.getProperty("ElarDBUsername"),
                ConfigReader.getProperty("ElarDBPassword")
        );
//        String query = "select * from core_company where company_name = '";
        List<Map<String, Object>> dbData = JDBCUtils.executeQuery("Delete from core_yard where id='"+yardId+"'");
        JDBCUtils.closeConnection();
    }


    //Homework2
    @Given("user checks what yard id doesn't exist in BD")
    public void user_checks_what_yard_id_doesn_t_exist_in_bd() {



    }

    @When("user sends get call for not existing yard")
    public void user_sends_get_call_for_not_existing_yard() {

    }

}
