/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author RC_Student_lab
 */
public class Login {
    //Attributes
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellphoneNumber;
   

    
    //Constructor
    public Login(String firstName, String lastName, String Username, String Password, String cellphoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = Username;
        this.password = Password;
        this.cellphoneNumber = cellphoneNumber;
      
    }
    //Getters
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
     public String getUsername(){
        return username;
    }  
     public String getCellphoneNumber(){
         return cellphoneNumber;
     }
       public boolean checkUsername(String Username){
        return Username.contains("_")&& Username.length()<=5;
    }
     public String getPassword(){
        return password;
    }
   //Setters
     
      public void setUsername(String Username){
        this.username = Username;
    }
     public void setPassword(String Password){
        this.password = Password;
    }
    public void setCellphoneNumber(String cellphoneNumber) {
        this.cellphoneNumber = cellphoneNumber;
    }
    public static boolean checkCellphoneNumber(String cellphoneNumber) {
        return cellphoneNumber .contains("+27") && cellphoneNumber.length()==12;
            
    }
 
    
    public boolean checkPasswordComplexity(String password) {
    return password.length() >= 8 &&
           password.matches(".*[A-Z].*") &&        // at least one uppercase
           password.matches(".*[a-z].*") &&        // at least one lowercase
           password.matches(".*[0-9].*") &&        // at least one digit
           password.matches(".*[!@#$%^&*()].*");   // at least one special character
}
        
    public String registerUser(String checkUsername, String Password, String cellphoneNumber) {
        if(checkUsername(username)){
            System.out.print("");
    }else {
            System.out.print("");
} 
        if(checkPasswordComplexity(password)) {
            System.out.print("");
        }else {
            System.out.print("");
        }
        
        return "User registered successfully!";
    }
        
    public boolean loginUser(String Username, String Password, String cellphoneNumber){
        return this.username.equals(Username) && this.password.equals(Password)&& this.cellphoneNumber.equals(cellphoneNumber);
    } 
   
   public String returnLoginStatus(boolean checkUsername, String Password){
     if (checkUsername(username)){
           return "A successful login";
      }else{ 
           return "A failed login";
       }
  
       
     }            
        
       
      
        
    } 


