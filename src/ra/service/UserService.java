package ra.service;

import ra.config.Config;
import ra.model.Products;
import ra.model.account.RoleName;
import ra.model.account.Users;
import ra.reponsitory.UserReponsitory;

import java.util.ArrayList;
import java.util.List;

public class UserService implements UserReponsitory {
    static Config<List<Users>>config=new Config<>();
    public static List<Users>usersList;
    static {
        usersList=config.readFile(Config.URL_USERS);
        if (usersList==null){
            usersList=new ArrayList<>();
            usersList.add(new Users(0,"admin","admin@gmail.com","ADMIN","admin",true, RoleName.ADMIN,"0968783032"));
            new UserService().updateData();
        }
    }
    @Override
    public List<Users> findAll() {
        return usersList;
    }

    @Override
    public void save(Users users) {
        if (findById(users.getId())==null){
            usersList.add(users);
            updateData();
        }else {
            usersList.set(usersList.indexOf(users),users);
            updateData();
        }
    }

    @Override
    public void delete(int id) {
        usersList.remove(findById(id));
        updateData();
    }

    @Override
    public Users findById(int id) {
        for (Users user : usersList) {
            if (user.getId()==id){
                return user;
            }
        }

        return null;
    }

    @Override
    public int getNewId() {
        int idMax=0;
        for (Users user : usersList) {
            if (user.getId()>idMax){
                idMax=user.getId();
            }
        }
        return idMax+1;
    }

    @Override
    public void updateData() {
        config.writeFile(Config.URL_USERS,usersList);
    }

    @Override
    public Users checkLogin(String username, String password ) {
        for (Users user : usersList) {
            if (user.getUsername().equals(username)&&user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean existsUsername(String username) {
        for (Users user : usersList) {
            if (user.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean existsEmail(String email) {
        for (Users user : usersList) {
            if (user.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

}
