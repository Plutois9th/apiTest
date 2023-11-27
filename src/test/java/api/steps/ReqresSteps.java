package api.steps;

import api.Data.*;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import org.junit.Assert;
import org.junit.Test;

import java.time.Clock;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.Assert.assertEquals;



public class ReqresSteps {
    private final static String URL = "https://reqres.in";

    TestData testData = new TestData();

//@Step("Проверка совпадения id пользователя с id его аватара")
    @Test
    public void  checkAvatarAndIdTest() {
    List<UserData> users = given()
            .log().all()
            .contentType(ContentType.JSON)
            .when()
            .get(URL + "/api/users?page=2") //post, delete, put
            .then()
            .log().status()
            .log().body()
            .statusCode(200)
            .extract().body().jsonPath().getList("data", UserData.class);
    users.forEach(x -> Assert.assertTrue(x.getAvatar().contains(x.getId().toString())));
    Assert.assertTrue(users.stream().allMatch(x -> x.getEmail().endsWith("@reqres.in")));

    List<String> avatars = users.stream().map(UserData::getAvatar).collect(Collectors.toList());
    List<String> id = users.stream().map(x -> x.getId().toString()).collect(Collectors.toList());
    for (int i = 0;
         i < avatars.size(); i++) {
        Assert.assertTrue(avatars.get(i).contains(id.get(i)));
    }

}
    //@Step("Регистрация нового пользователя")
    @Test
    public void successRegTest() {
        Register user = new Register(testData.getRegEmail(),testData.getRegPassword());
        SuccessReg successReg = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(URL+"/api/register")
                .then().log().all()
                .statusCode(200)
                .extract().as(SuccessReg.class);
        Assert.assertNotNull(successReg.getId());
        Assert.assertNotNull(successReg.getToken());
        assertEquals(testData.getRegId(),successReg.getId());
      assertEquals(testData.getRegToken(), successReg.getToken());

    }

   // @Step("Регистрация без пароля")
    @Test
    public void unSuccessRegTest() {

        Register user = new Register(testData.getUnSuccsessRegEmail(),
                                    testData.getUnSuccsessRegPassword());
        UnSuccessRegTest unSuccessReg = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(user)
                .post(URL+"/api/register")
                .then().log().all()
                .statusCode(400)
                .extract().as(UnSuccessRegTest.class);
        assertEquals(testData.getRegError(), unSuccessReg.getError());


    }

    //@Step("Проверка корректной сортировки дат")
    @Test
    public void sortedYearsTest() {
        List<ColorData> colors = given()
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .get(URL+"/api/unknown")
                .then().log().all()
                .statusCode(200)
                .extract().body().jsonPath().getList("data", ColorData.class);
        List<Integer> years = colors.stream().map(ColorData::getYear).collect(Collectors.toList());
        List<Integer> sortedYears = years.stream().sorted().collect(Collectors.toList());
        assertEquals(sortedYears, years);

    }

   // @Step("Удаление пользователя")
    @Test
    public void deleteUser() {
            given()
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .delete(URL+"/api/users/2")
                .then().log().all()
                .statusCode(204);
    }

    //@Step("Проверка времени обновления пользователя")
    @Test
    public void timeTest() {
        UserTime userTime = new UserTime(testData.getUpdateName(), testData.getUpdateJob());
        UserTimeRes response = given()
                .body(userTime)
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .put(URL+"/api/users/2")
                .then().log().all()
                .statusCode(200)
                .extract().as(UserTimeRes.class);
        String regex = "(.{5})$";
        String currentTime = Clock.systemUTC().instant().toString().replaceAll(regex, "");
        assertEquals(currentTime, response.getUpdatedAt().replaceAll(regex, ""));

    }

   // @Step("Проверка отсутствия пользователя")
    @Test
    public void userNotFound() {
        List<UserData> users = given()
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .get(URL+"/api/users/23")
                .then().log().all()
                .statusCode(404)
                .extract().body().jsonPath().getList("data", UserData.class);
    }

    //@Step("Проверка отложенного ответа")
    @Test
    public void delayedResponse() {
        List<UserData> users = given()
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .get(URL+"/api/users?delay=3")
                .then().log().all()
                .statusCode(200)
                .extract().body().jsonPath().getList("data", UserData.class);

    }

   // @Step("Проверка id и имени одного пользователя")
    @Test
    public void singleUserCheck() {

        UserData2 users = given()
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .get(URL+"/api/users/2")
                .then().log().all()
                .statusCode(200)
                .extract().as(UserData2.class);

        Assert.assertEquals(users.getData().getFirst_name(),testData.getSingleUserFirstName());
        Assert.assertEquals(users.getData().getLast_name(),testData.getSingleUserLastName());
        Assert.assertEquals(users.getData().getEmail(), testData.getSingleUserEmail());
        Assert.assertEquals(users.getData().getId(), testData.getSingleUserId());
    }

   // @Step("Создание пользователя")
   @Test
    public void createUser() {
        CreateUser createUser = new CreateUser(testData.getCreateUserName(),
                testData.getCreateUserJob());
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(createUser)
                .when()
                .post(URL+"/api/users")
                .then().log().all()
                .statusCode(201);

    }

    //@Step("Успешный логин")
    @Test
    public void successLogin() {

        Register user = new Register(testData.getSuccsessLoginEmail(),
                                    testData.getSuccsessLoginPassword());
        SuccessfulLogin successLog = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(URL+"/api/login")
                .then().log().all()
                .statusCode(200)
                .extract().as(SuccessfulLogin.class);

        Assert.assertNotNull(successLog.getToken());
        assertEquals(testData.getSuccsessLoginToken(), successLog.getToken());

    }


}