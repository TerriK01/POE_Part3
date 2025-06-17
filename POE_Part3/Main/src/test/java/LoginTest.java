/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author RC_Student_lab
 */
public class LoginTest {

    private static Login login;
   
  

    @BeforeAll
    public static void setUp() {
        login = new Login("", "", "ky_1", "Ch&&sec@ke99!", "+27838968976");
        
    }

    @Test
    public void checkUsername() {
  
         assertTrue(login.checkUsername("ky_1"), "Username correctly formatted");
        assertFalse( login.checkUsername("kyle!!!!!!!"),"Username is incorrectly formatted,please ensure that your username contains an underscore and is no more than five characters in length");
    }

  @Test
    public void checkPasswordComplexity() {
        assertFalse(login.checkPasswordComplexity("password"),"False");

     assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"),"True");
    }         
        

    @Test
    public void checkCellPhoneValid() {
        assertTrue(login.checkCellphoneNumber("+27838968976"));
         assertFalse(login.checkCellphoneNumber("08966553"));
           
    }

    @Test
public void loginUser() {
   assertTrue( login.checkUsername("ky_1"));
assertFalse( login.checkPasswordComplexity("password"));

}
}

