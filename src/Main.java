import ra.config.Config;
import ra.model.account.Users;
import ra.view.account.Singin;
import ra.view.home.Home;

public class Main {
    public static void main(String[] args) {

        Users userLogin=new Config<Users>().readFile(Config.URL_USERS_LOGIN);
        if (userLogin!=null){
            new Singin().checkRoleLogin(userLogin);
        }else {
            new Home().menuHome();
        }
    }

}