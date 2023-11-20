package api.Data;

import lombok.Data;

@Data

public class UnSuccessRegTest {
    public UnSuccessRegTest(){

    }

    private String error;
    public UnSuccessRegTest(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
