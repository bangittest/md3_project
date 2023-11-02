package ra.model.account;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Set;

public class Users implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String username;
    private String email;
    private String fullName;
    private String password;
    private boolean status = true;
    private RoleName roles = RoleName.USER;
    private String phoneNumber;

    public Users() {
    }

    public Users(int id, String username, String email, String fullName, String password, boolean status, RoleName roles, String phoneNumber) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.status = status;
        this.roles = roles;
        this.phoneNumber = phoneNumber;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public RoleName getRoles() {
        return roles;
    }

    public void setRoles(RoleName roles) {
        this.roles = roles;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        String format = "%-10s %-20s %-20s %-20s %-20s %-20s %-20s%n";
        System.out.println();
        return String.format(format, id, username, email, fullName, status, roles, phoneNumber);
    }
}