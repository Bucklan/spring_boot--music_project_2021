package qs.sukaworkplea.qq.narxoz1963.config.servises;

import org.springframework.security.core.userdetails.UserDetailsService;
import qs.sukaworkplea.qq.narxoz1963.joins.Users;

public interface UserService extends UserDetailsService {

    Users getUserByEmail(String email);
    Users createUser(Users user);

}
