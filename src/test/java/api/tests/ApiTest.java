package api.tests;

import api.steps.ReqresSteps;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class ApiTest {
    ReqresSteps reqresSteps = new ReqresSteps();
    @Test
    @DisplayName("Проверка списка пользователей")
    void checkUserList(){
        reqresSteps.delayedResponse();
        reqresSteps.checkAvatarAndIdTest();
        reqresSteps.sortedYearsTest();
        reqresSteps.timeTest();
        reqresSteps.singleUserCheck();

    }
    @Test
    @DisplayName("Проверка создания, удаления и регистрации пользователей")
    void checkCreateAndRegister(){
        reqresSteps.createUser();
        reqresSteps.deleteUser();
        reqresSteps.successRegTest();
        reqresSteps.unSuccessRegTest();
        reqresSteps.userNotFound();
    }
    @Test
    @DisplayName("Проверка логина пользователей")
    void checkUserLogin(){
        reqresSteps.successLogin();

    }

}
