package api;

import api.Data.*;
import org.junit.Assert;
import org.junit.Test;
import static org.hamcrest.Matchers.is;
import java.time.Clock;
import java.util.List;
import java.util.stream.Collectors;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;



public class ReqresTest {
    private final static String URL = "https://reqres.in";
    @Test
    public void checkAvatarAndIdTest (){
        Specifications.installSpec(Specifications.requestSpecification(URL),
                Specifications.responseSpecificationOK200());
        List<UserData> users = given()
                .when()
                .get("/api/users?page=2") //post, delete, put
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserData.class);
       users.forEach(x-> Assert.assertTrue(x.getAvatar().contains(x.getId().toString())));
       Assert.assertTrue(users.stream().allMatch(x->x.getEmail().endsWith("@reqres.in")));

        List<String> avatars = users.stream().map(UserData::getAvatar).collect(Collectors.toList());
        List<String> id = users.stream().map(x->x.getId().toString()).collect(Collectors.toList());
        for (int i = 0;
        i<avatars.size(); i++){
            Assert.assertTrue(avatars.get(i).contains(id.get(i)));
        }
    }
    @Test
    public void successRegTest(){
        Specifications.installSpec(Specifications.requestSpecification(URL),
                Specifications.responseSpecificationOK200());
        Integer id = 4;
        String token = "QpwL5tke4Pnpja7X4";
        Register user = new Register("eve.holt@reqres.in","pistol" );
        SuccessReg successReg = given()
                .body(user)
                .when()
                .post("/api/register")
                .then().log().all()
                .extract().as(SuccessReg.class);
        Assert.assertNotNull(successReg.getId());
        Assert.assertNotNull(successReg.getToken());
        assertEquals(id, successReg.getId());
        assertEquals(token, successReg.getToken());

    }
    @Test
    public void unSuccessRegTest(){
        Specifications.installSpec(Specifications.requestSpecification(URL),
                Specifications.responseSpecificationError400());

        Register user = new Register("sydney@fife","");
        UnSuccessRegTest unSuccessReg = given()
                .body(user)
                .post("/api/register")
                .then().log().all()
                .extract().as(UnSuccessRegTest.class);
        assertEquals("Missing email or username", unSuccessReg.getError()); //???


    }
    @Test
    public void sortedYearsTest(){
        Specifications.installSpec(Specifications.requestSpecification(URL),
                Specifications.responseSpecificationOK200());
        List<ColorData> colors = given()
                .when()
                .get("/api/unknown")
                .then().log().all()
                .extract().body().jsonPath().getList("data", ColorData.class);
        List<Integer> years = colors.stream().map(ColorData::getYear).collect(Collectors.toList());
        List<Integer> sortedYears = years.stream().sorted().collect(Collectors.toList());
        assertEquals(sortedYears, years);

    }

    @Test
    public void deleteUser(){
        Specifications.installSpec(Specifications.requestSpecification(URL),
                Specifications.responseSpecificationUnique(204));
        given()
                .when()
                .delete("/api/users/2")
                .then().log().all();
    }
    @Test
    public void timeTest(){
        Specifications.installSpec(Specifications.requestSpecification(URL),
                Specifications.responseSpecificationOK200());
        UserTime userTime = new UserTime("morpheus","zion resident");
        UserTimeRes response = given()
                .body(userTime)
                .when()
                .put("/api/users/2")
                .then().log().all()
                .extract().as(UserTimeRes.class);
        String regex = "(.{5})$";
        String currentTime = Clock.systemUTC().instant().toString().replaceAll(regex,"");
        assertEquals(currentTime, response.getUpdatedAt().replaceAll(regex,""));

    }
    @Test
    public void userNotFound() {
        Specifications.installSpec(Specifications.requestSpecification(URL),
                Specifications.responseSpecificationError404());
        List<UserData> users = given()
                .when()
                .get("/api/users/23")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserData.class);
    }
    @Test
    public void delayedResponse() {
        Specifications.installSpec(Specifications.requestSpecification(URL),
                Specifications.responseSpecificationOK200());
        List<UserData>users = given()
                .when()
                .get("/api/users?delay=3")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserData.class);

    }
   @Test
   public void singleUserCheck() {
       String firstName = "Janet";
       Integer id = 2;
       String lastName = "Weaver";
       String email = "janet.weaver@reqres.in";
       Specifications.installSpec(Specifications.requestSpecification(URL),
               Specifications.responseSpecificationOK200());
       UserData2 users = given()
               .when()
               .get("/api/users/2")
               .then().log().all()
               .extract().as(UserData2.class);

       Assert.assertEquals(users.getData().getFirst_name(),firstName);
       Assert.assertEquals(users.getData().getLast_name(),lastName);
       Assert.assertEquals(users.getData().getEmail(),email);
       Assert.assertEquals(users.getData().getId(), id);
    }
    @Test
    public void createUser () {
        String bodyCreateUser = "{ \"name\": \"yuliya\", \"job\": \"stargazer\" }";
        Specifications.installSpec(Specifications.requestSpecification(URL),
                Specifications.responseSpecificationError201());
        given()
                .body(bodyCreateUser)
                .when()
                .post("/api/users")
                .then().log().all()
                // .extract().as(UserData2.class)
                .body("name", is("yuliya"))
                .body("job", is("stargazer"));
    }
    @Test
    public void successLogin(){
        Specifications.installSpec(Specifications.requestSpecification(URL),
                Specifications.responseSpecificationOK200());
        String email = "eve.holt@reqres.in" ;
        String password = "cityslicka";
        String token = "QpwL5tke4Pnpja7X4";
        Register user = new Register("eve.holt@reqres.in","cityslicka" );
        SuccessfulLogin successLog = given()
                .body(user)
                .when()
                .post("/api/login")
                .then().log().all()
                .extract().as(SuccessfulLogin.class);

        Assert.assertNotNull(successLog.getToken());
        assertEquals(token, successLog.getToken());

    }



}