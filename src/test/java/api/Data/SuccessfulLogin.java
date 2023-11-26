package api.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;


@JsonIgnoreProperties(ignoreUnknown = true)
@Data

public class SuccessfulLogin {
    String email = "eve.holt@reqres.in" ;
    String password = "cityslicka";
    String token = "QpwL5tke4Pnpja7X4";

}
