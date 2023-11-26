package api.Data;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data

public class TestData {
    private Integer regId = 4;
    private String regToken = "QpwL5tke4Pnpja7X4";
    private String regError = "Missing password";
    private String regEmail = "eve.holt@reqres.in";
    private String regPassword = "pistol";
    private String unSuccsessRegEmail = "sydney@fife";
    private String unSuccsessRegPassword = "";
    private String updateJob = "zion resident";
    private String updateName = "morpheus";
    private String singleUserFirstName = "Janet";
    private Integer singleUserId = 2;
    private String singleUserLastName = "Weaver";
    private String singleUserEmail = "janet.weaver@reqres.in";
    private String createUserName = "Yuliya";
    private String createUserJob = "Stargazer";
    private String succsessLoginEmail = "eve.holt@reqres.in";
    private String succsessLoginPassword = "cityslicka";
    private String succsessLoginToken = "QpwL5tke4Pnpja7X4";


}
