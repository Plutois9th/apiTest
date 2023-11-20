package api.Data;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class Register {

    private String email;

    public Register(String email, String password) {
        this.email = email;
        this.password = password;
    }

    private String password;
}
