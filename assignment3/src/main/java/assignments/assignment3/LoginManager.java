//import yang diperlukan
package assignments.assignment3;
import assignments.assignment3.systemCLI.AdminSystemCLI;
import assignments.assignment3.systemCLI.CustomerSystemCLI;
import assignments.assignment3.systemCLI.UserSystemCLI;

public class LoginManager {
    private final AdminSystemCLI adminSystem;
    private final CustomerSystemCLI customerSystem;
    //constructor login manager
    public LoginManager(AdminSystemCLI adminSystem, CustomerSystemCLI customerSystem) {
        this.adminSystem = adminSystem;
        this.customerSystem = customerSystem;
    }

    //mereturn 
    public UserSystemCLI getSystem(String role){
        if(role=="Customer"){
            return customerSystem;
        }else{
            return adminSystem;
        }
    }
}
