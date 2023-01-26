package qs.sukaworkplea.qq.narxoz1963.config.servises;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import qs.sukaworkplea.qq.narxoz1963.Repository.RoleRepository;
import qs.sukaworkplea.qq.narxoz1963.Repository.UsersRepository;
import qs.sukaworkplea.qq.narxoz1963.joins.Roles;
import qs.sukaworkplea.qq.narxoz1963.joins.Users;

import java.util.ArrayList;

@Service
public class UserServiseImp implements UserService {

    @Autowired
    private UsersRepository usersRepository;



    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    public BCryptPasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users myUser = usersRepository.findByEmail(username);
        if (myUser != null) {
//            myUser.getEmail(),myUser.getPassword(),myUser.getRoles();
            User user = new User(myUser.getEmail(), myUser.getPassword(), myUser.getRoles());
            return user;
        }
        throw new UsernameNotFoundException("User not found");
    }

    @Override
    public Users getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    @Override
    public Users createUser(Users user) {
        Users checkUser = usersRepository.findByEmail(user.getEmail());
        if (checkUser == null) {
            Roles role = roleRepository.findByRole("ROLE_USER");
            if (role != null) {
                ArrayList<Roles> roles = new ArrayList<>();
                roles.add(role);
                user.setRoles(roles);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                return usersRepository.save(user);

            }
        }
        return null;
    }
}
