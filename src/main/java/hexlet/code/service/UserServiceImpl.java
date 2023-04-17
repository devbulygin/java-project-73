package hexlet.code.service;

import hexlet.code.Dto.UserDto;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

    @Override
    public User getCurrentUser() {
        return userRepository.findByEmail(getCurrentUserName()).get();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User " + id + " not found"));
        userRepository.deleteById(id);
    }

//    public void deleteUserById (long id) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("User " + id + " not found"));
//
//        Set<Task> tasksAuthor = user.getTasksAuthor();
//        Set<Task> tasksExecutor = user.getTasksExecutor();
//        if (!tasksAuthor.isEmpty()) {
//            throw new RuntimeException("User is task author, cannot delete");
//        }
//        if (!tasksExecutor.isEmpty()) {
//            throw new RuntimeException("User has task, cannot delete");
//        }
//        userRepository.deleteById(id);
//    }


}
