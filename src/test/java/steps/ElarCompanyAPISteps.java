package steps;

import groovyjarjarpicocli.CommandLine;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import utilities.ConfigReader;
import utilities.JDBCUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class ElarCompanyAPISteps {
    Map<String, Object> map;
    Map<String, Object> updatedCompanyData;
    Response response;
    Integer MCField, DOTField;
    String CompanyName;
    String companyId;
    String updatedCompanyName;


    @Given("user creates company with post company api call with data")
    public void user_creates_company_with_post_yard_api_call_with_data(io.cucumber.datatable.DataTable dataTable) {
        map = dataTable.asMap(String.class, Object.class);
        MCField = new Random().nextInt(10000)+1000;
        DOTField = new Random().nextInt(10000) + 1000;//random 0- 10000
        CompanyName = map.get("Name").toString() + new Random().nextInt(10000);
        /*
        POST Company
         */
        response = given().baseUri(ConfigReader.getProperty("ElarAPIBaseURL"))
                .and().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().header("Authorization", "Token 50c79942251edf8c7fd98c8036480b4f80b84c1d")
                .and().body("{\n" +
                        "    \"company_name\": \"" + CompanyName + "\",\n" +
                        "    \"status\": \"active\",\n" +
                        "    \"mc_number\": \"" + MCField + "\",\n" +
                        "    \"dot_number\": \"" + DOTField + "\",\n" +
                        "    \"address\": \"" + map.get("Street") + "\",\n" +
                        "    \"city\": \"" + map.get("City") + "\",\n" +
                        "    \"state\": \"" + map.get("State") + "\",\n" +
                        "    \"zip_code\": \"" + map.get("Zip code") + "\",\n" +
                        "    \"insurance\": \"" + map.get("Insurance(producer company name)") + "\",\n" +
                        "    \"policy_expiration\": \"" + map.get("Policy Expiration date") + "\",\n" +
                        "  \"policy_number\": \"" + map.get("Policy number") + "\",\n" +
                        " \"contacts\": [\n" +
                        " {\n" +
                        "\"phone\": \"" + map.get("Phone number") + "\",\n" +
                        "        \"email\": \"" + map.get("Email") + "\"\n" +
                        "             } ]\n" +
                        "}")
                .and().log().all()
                .when().post("/companies/");
        response.then().log().all();
        companyId = response.body().jsonPath().getString("id");
    }

    @Then("user validates GET api call company has the right details")
    public void user_validates_get_api_call_company_has_the_right_details() {
        Response response = given().baseUri(ConfigReader.getProperty("ElarAPIBaseURL"))
                .and().accept(ContentType.JSON)
                .and().header("Authorization", "Token 50c79942251edf8c7fd98c8036480b4f80b84c1d")
                .and().log().all()
                .when().get("/companies/" + companyId + "/");
        response.then().statusCode(200);
        //map -> expected result
        //response result
        Assert.assertEquals(CompanyName, response.body().jsonPath().getString("company_name"));
        Assert.assertEquals(MCField.toString(), response.body().jsonPath().getString("mc_number"));
        Assert.assertEquals(DOTField.toString(), response.body().jsonPath().getString("dot_number"));
        Assert.assertEquals(map.get("Street").toString(), response.body().jsonPath().getString("address"));
        Assert.assertEquals(map.get("City").toString(), response.body().jsonPath().getString("city"));
        Assert.assertEquals(map.get("Zip code").toString(), response.body().jsonPath().getString("zip_code"));
        Assert.assertEquals(map.get("Insurance(producer company name)").toString(), response.body().jsonPath().getString("insurance"));
        Assert.assertEquals(map.get("Policy Expiration date").toString(), response.body().jsonPath().getString("policy_expiration"));
        Assert.assertEquals(map.get("Policy number").toString(), response.body().jsonPath().getString("policy_number"));
        Assert.assertEquals(map.get("State").toString(), response.body().jsonPath().getString("state"));


    }

    @When("user updates created company with patch api call with data")
    public void user_updates_created_company_with_patch_api_call_with_data(io.cucumber.datatable.DataTable dataTable) {
        updatedCompanyData = dataTable.asMap(String.class, Object.class);
        updatedCompanyName = updatedCompanyData.get("Name").toString();
        response = given().baseUri(ConfigReader.getProperty("ElarAPIBaseURL"))
                .and().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().header("Authorization", "Token 50c79942251edf8c7fd98c8036480b4f80b84c1d")
                .and().body("{\n" +
                        "    \"company_name\": \"" + updatedCompanyName + "\",\n" +
                        "    \"address\": \"" + updatedCompanyData.get("Street") + "\" \n" +
                        "}")
                .and().log().all()
                .when().patch("/companies/" + companyId + "/"); //unique id same id what we created
        response.then().log().all();
        response.then().statusCode(200);

    }

    @When("user get created company with get api call")
    public void user_get_created_company_with_get_api_call() {
        response = given().baseUri(ConfigReader.getProperty("ElarAPIBaseURL"))
                .and().accept(ContentType.JSON)
                .and().header("Authorization", "Token 50c79942251edf8c7fd98c8036480b4f80b84c1d")
                .and().log().all()
                .when().get("/companies/" + companyId + "/");
        response.then().statusCode(200);


    }

    @Then("user validates get call response having updated company details")
    public void user_validates_get_call_response_having_updated_company_details() {
        Assert.assertEquals(updatedCompanyData.get("Name").toString(), response.body().jsonPath().getString("company_name"));
        Assert.assertEquals(updatedCompanyData.get("Street").toString(), response.body().jsonPath().getString("address"));

    }

    @Then("user validates data is updated in DB")
    public void user_validates_data_is_updated_in_db() throws SQLException {
        JDBCUtils.establishDBBConnection( //making connection to BD
                ConfigReader.getProperty("ElarDBURL"),
                ConfigReader.getProperty("ElarDBUsername"),
                ConfigReader.getProperty("ElarDBPassword")
        );
//        String query = "select * from core_company where company_name = '"+updatedCompanyName+"'";
        //here assigning the id(companyId) to query variable . To get unique value from our updated body which is id
        String query = "select * from core_company where id = " + companyId;
        System.out.println(query);
        List<Map<String, Object>> dbData = JDBCUtils.executeQuery(query);
        JDBCUtils.closeConnection();
    String dbCompanyValue=dbData.get(0).get("company_name").toString();
    Assert.assertEquals(updatedCompanyData.get("Name").toString(),dbCompanyValue);


    }


}
