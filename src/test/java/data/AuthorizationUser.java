package data;

public class AuthorizationUser {

    String email;
    String password;


    public AuthorizationUser(String login, String password) {
        this.email = login;
        this.password = password;
    }
}
