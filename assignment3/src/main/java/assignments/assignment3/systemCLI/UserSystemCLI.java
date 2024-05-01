//mengimport abtract class
package assignments.assignment3.systemCLI;
import java.util.Scanner;
import assignments.assignment3.daritp2.User;

//Abstract class yang mencangkup method-method yang nantinya akan di Override
public abstract class UserSystemCLI {
    protected Scanner input;
    public void run(User userLoggedIn) {
        boolean isLoggedIn = true;
        while (isLoggedIn) {
            displayMenu();
            int command = input.nextInt();
            input.nextLine();
            isLoggedIn = handleMenu(command);
        }
    }
    abstract void displayMenu();
    abstract boolean handleMenu(int command);
}
