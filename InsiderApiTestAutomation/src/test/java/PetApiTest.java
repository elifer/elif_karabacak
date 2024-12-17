import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PetApiTest {

    @BeforeTest
    void init() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Test
    public void createPetShouldReturnSuccessWhenPayloadIsValid() {
        String requestBody = examplePet("doggie", "available");
        given().body(requestBody)
                .contentType(ContentType.JSON)
                .when().post("/pet")
                .then().statusCode(HttpStatus.SC_OK)
                .log().body()
                .body("name", equalTo("doggie"))
                .body("status", equalTo("available"))
                .body("photoUrls", hasItem(equalTo("http://en.wikipedia.org/wiki")))
                .body("category.name", equalTo("category1"))
                .body("tags.name", hasItem(equalTo("test_tag")));
    }

    @Test
    public void createPetShouldReturnFailWhenBodyIsMissing() {
        given().body("null")
                .contentType(ContentType.JSON)
                .when().post("/pet")
                .then().statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
    }

    @Test
    public void updatePetDataShouldReturnSuccessWhenPayloadIsValid() {
        long id = createPetAndReturnId(examplePet("deheh", "available"));

        given()
                .formParam("name", "pukky")
                .formParam("status", "pending")
                .pathParams("id", id)
                .when()
                .post("/pet/{id}")
                .then().statusCode(HttpStatus.SC_OK)
                .body("message", equalTo(String.valueOf(id)));
    }

    @Test
    public void updatePetDataShouldReturnFailWhenFormDataIsMissing() {
        given()
                .contentType(ContentType.JSON)
                .pathParams("id", "")
                .when()
                .post("/pet/{id}")
                .then()
                .statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
    }

    @Test
    public void updateExistingPetShouldReturnSuccessWhenPayloadIsValid() {
        long id = createPetAndReturnId(examplePet("pukky", "pending"));
        String requestBody = examplePet(String.valueOf(id), "chanky", "available");

        given()
                .body(requestBody)
                .contentType(ContentType.JSON)
                .when()
                .put("/pet")
                .then().statusCode(HttpStatus.SC_OK)
                .body("name", equalTo("chanky"))
                .body("status", equalTo("available"));
    }

    @Test
    public void updateExistingPetShouldReturnFailWhenIdIsInvalid() {
        String requestBody = examplePet("invalid-id", "chanky", "available");

        given()
                .body(requestBody)
                .contentType(ContentType.JSON)
                .when()
                .put("/pet")
                .then().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void deletePetShouldReturnSuccessWhenIdIsValid() {
        long id = createPetAndReturnId(examplePet("lessy", "pending"));
        given()
                .accept(ContentType.JSON)
                .header("api_key", "special-key")
                .pathParam("id", id)
                .when().delete("/pet/{id}")
                .then().statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void deletePetShouldReturnFailWhenPetNotFound() {
        given()
                .accept(ContentType.JSON)
                .header("api_key", "special-key")
                .pathParam("id", -1)
                .when().delete("/pet/{id}")
                .then().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void getByIdShouldReturnSuccessWhenValidIdProvided() {
        long id = createPetAndReturnId(examplePet("pukky", "pending"));
        given()
                .pathParam("id", id)
                .when().get("/pet/{id}")
                .then().statusCode(HttpStatus.SC_OK)
                .body("name", equalTo("pukky"))
                .body("status", equalTo("pending"));
    }

    @Test
    public void getByIdShouldReturnFailWhenPetNotFound() {
        given()
                .pathParam("id", -1)
                .when().get("/pet/{id}")
                .then().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void getByStatusShouldReturnSuccessWhenPayloadIsValid() {
        given()
                .queryParam("status", "pending")
                .when().get("/pet/findByStatus")
                .then().statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void getByStatusShouldReturnFailWhenStatusIsInvalid() {
        given()
                .queryParam("status", "======")
                .when().get("/pet/findByStatus")
                .then().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    private String examplePet(String name, String status) {
        return """
                {
                  "category": {
                    "name": "category1"
                  },
                  "name": "%s",
                  "photoUrls": [
                    "http://en.wikipedia.org/wiki"
                  ],
                  "tags": [
                    {
                      "name": "test_tag"
                    }
                  ],
                  "status": "%s"
                }
                """.formatted(name, status);
    }

    private String examplePet(String id, String name, String status) {
        return """
                {
                  "id": "%s",
                  "category": {
                    "name": "category1"
                  },
                  "name": "%s",
                  "photoUrls": [
                    "petUrl"
                  ],
                  "tags": [
                    {
                      "name": "test_tag"
                    }
                  ],
                  "status": "%s"
                }
                """.formatted(id, name, status);
    }

    private long createPetAndReturnId(String requestBody) {
        return given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post("/pet")
                .then().statusCode(HttpStatus.SC_OK)
                .extract().path("id");
    }

    private void deletePet(long id) {
        given()
                .header("api_key", "special-key")
                .accept(ContentType.JSON)
                .pathParam("id", id)
                .when().post("/pet/{id}")
                .then().statusCode(anyOf(is(HttpStatus.SC_OK), is(HttpStatus.SC_NOT_FOUND))).log();
    }

}
