package data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationUser {

    String email;
    String password;


    public AuthorizationUser(String login, String password) {
        this.email = login;
        this.password = password;
    }
}
