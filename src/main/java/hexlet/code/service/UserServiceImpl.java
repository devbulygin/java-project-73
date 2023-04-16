package hexlet.code.service;

import hexlet.code.Dto.TaskDto;
import hexlet.code.Dto.UserDto;
import hexlet.code.model.Task;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import lombok.AllArgsConstructor;
//import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.core.context.SecurityContextHolder;

@Service
@Transactional
@AllArgsConstructor

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    private final PasswordEncoder passwordEncoder;
    @Override
    public User createNewUser(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, UserDto userDto) {
        final User userToUpdate = userRepository.findById(id).get();
        userToUpdate.setFirstName(userDto.getFirstName());
        userToUpdate.setLastName(userDto.getLastName());
        userToUpdate.setEmail(userDto.getEmail());
        userToUpdate.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return userRepository.save(userToUpdate);
    }

    @Override
    public String getCurrentUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
//    @Override
//    public User getUserByEmail(String email) {
//        return userRepository.findByEmail(email).get();
//    }





    @Override
    public User getCurrentUser() {
        return userRepository.findByEmail(getCurrentUserName()).get();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        return userRepository.findByEmail(username)
//                .map(this::buildSpringUser)
//                .orElseThrow(() -> new UsernameNotFoundException("Not found user with 'username': " + username));
//    }
//
//    private UserDetails buildSpringUser(final User user) {
//        return new org.springframework.security.core.userdetails.User(
//                user.getEmail(),
//                user.getPassword(),
//                DEFAULT_AUTHORITIES
//        );
//    }


}
