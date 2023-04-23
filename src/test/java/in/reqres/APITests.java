package in.reqres;

import data.AuthorizationUser;
import data.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class APITests {

    @BeforeEach
    public void before() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test
    @DisplayName("Проверяем id пользователей на 2 странице")
    public void listUser() {
        given()
                .when()
                .get("/users?page=2")
                .then()
                .statusCode(200)
                .body("data.id", hasItems(7, 8, 9, 10, 11, 12));
    }

    @Test
    @DisplayName("Создаем нового пользователя и проверяем данные")
    public void greatUser() {
        User user1 = new User("Krot", "QA");
        given()
                .contentType(ContentType.JSON)
                .body(user1)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .body("name", is("Krot"), "job", is("QA"), "id", notNullValue(), "createdAt", notNullValue());
    }

    @Test
    @DisplayName("Запрашиваем пользователя с id №9 и проверяем его first_name")
    public void userRequest() {
        given()
                .when()
                .get("/users/9")
                .then()
                .statusCode(200)
                .body("data.id", is(9), "data.first_name", is("Tobias"));
    }

    @Test
    @DisplayName("Регистрием нового пользователя")
    public void registrationUser() {
        AuthorizationUser authorizationUser = new AuthorizationUser("eve.holt@reqres.in", "pistol");
        given()
                .contentType(ContentType.JSON)
                .body(authorizationUser)
                .when()
                .post("/register")
                .then()
                .statusCode(200)
                .body("id", is(4), "token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    @DisplayName("Удаление пользоваталя")
    public void deleteUser() {
        given()
                .when()
                .delete("/users/2")
                .then()
                .statusCode(204);
    }
}
