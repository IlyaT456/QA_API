package in.reqres;

import Spec.LoginSpec;
import data.AuthorizationUser;
import data.CreatedUser;
import data.RegistrationUser;
import data.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

public class APITestsWithAllure {
    @Test
    @DisplayName("Проверяем id пользователей на 2 странице")
    public void listUser() {
        given()
                .spec(LoginSpec.loginRequestSpec)
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
        CreatedUser createdUser =
                step("Отправляем post запрос на создание нового пользователя", () ->
                        given()
                                .spec(LoginSpec.loginRequestSpec)
                                .body(user1)
                                .when()
                                .post("/users")
                                .then()
                                .statusCode(201)
                                .spec(LoginSpec.loginResponseSpec)
                                .extract().as(CreatedUser.class));

        step("Проверяем данные пользователя", () -> {
            assertThat(createdUser.getName()).isEqualTo("Krot");
            assertThat(createdUser.getJob()).isEqualTo("QA");
            assertThat(createdUser.getId()).isNotNull();
            assertThat(createdUser.getCreatedAt()).isNotNull();
        });
    }

    @Test
    @DisplayName("Запрашиваем пользователя с id №9 и проверяем его first_name")
    public void userRequest() {
        given()
                .spec(LoginSpec.loginRequestSpec)
                .when()
                .get("/users/9")
                .then()
                .statusCode(200)
                .spec(LoginSpec.loginResponseSpec)
                .body("data.id", is(9), "data.first_name", is("Tobias"));
    }

    @Test
    @DisplayName("Регистрием нового пользователя")
    public void registrationUser() {

        AuthorizationUser authorizationUser = new AuthorizationUser("eve.holt@reqres.in", "pistol");
        RegistrationUser registrationUser =
                step("Отправляем post запрос на регистрацию нового пользователя", () ->
                        given()
                                .spec(LoginSpec.loginRequestSpec)
                                .body(authorizationUser)
                                .when()
                                .post("/register")
                                .then()
                                .statusCode(200)
                                .spec(LoginSpec.loginResponseSpec)
                                .extract().as(RegistrationUser.class));

        step("Проверяем токена и id пользователя", () -> {
            assertThat(registrationUser.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
            assertThat(registrationUser.getId()).isEqualTo("4");
        });
    }

    @Test
    @DisplayName("Удаление пользоваталя")
    public void deleteUser() {
        given()
                .spec(LoginSpec.loginRequestSpec)
                .when()
                .delete("/users/2")
                .then()
                .statusCode(204)
                .spec(LoginSpec.loginResponseSpec);
    }
}
