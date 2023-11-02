package ra.reponsitory;

import ra.model.account.Users;

import java.util.List;

public interface UserReponsitory extends Responsitory<Users> {
   Users checkLogin(String username,String password);
   boolean existsUsername(String username);
   boolean existsEmail(String password);

}
