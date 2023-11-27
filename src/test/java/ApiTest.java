import api.steps.ReqresStepsTest;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;



public class ApiTest {
    ReqresStepsTest reqresSteps = new ReqresStepsTest();
   @Test
    @DisplayName("Проверка списка пользователей")
   public void checkUserList(){
        reqresSteps.delayedResponse();
        reqresSteps.checkAvatarAndIdTest();
        reqresSteps.sortedYearsTest();
        reqresSteps.timeTest();
        reqresSteps.singleUserCheck();

    }
   @Test
    @DisplayName("Проверка создания, удаления и регистрации пользователей")
   public void checkCreateAndRegister(){
        reqresSteps.createUser();
        reqresSteps.deleteUser();
        reqresSteps.successRegTest();
        reqresSteps.unSuccessRegTest();
        reqresSteps.userNotFound();
    }
   @Test
    @DisplayName("Проверка логина пользователей")
   public void checkUserLogin(){
        reqresSteps.successLogin();

    }

}
