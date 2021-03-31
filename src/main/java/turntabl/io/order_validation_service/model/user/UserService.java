package turntabl.io.order_validation_service.model.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() { return userRepository.findAll(); }

    public void addNewUser(User user) {

        Optional<User> userOptional =  userRepository.findUserByEmail(user.getEmail());
        if(userOptional.isPresent()) {
            throw new IllegalStateException("Email taken");
        }
        userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        boolean exists = userRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("User with id " + id + " doesn't exist");
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public void updateUser(Integer userId, String first_name, String last_name, String email) {
        User user =  userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "User with id "+ userId + " does not exist"
                ));

        if (first_name != null &&  !Objects.equals(user.getFirst_name() , first_name)) {
            user.setFirst_name(first_name);
        }

        if (last_name != null &&  !Objects.equals(user.getLast_name() , last_name)) {
            user.setLast_name(last_name);
        }

        if (email != null &&  !Objects.equals(user.getEmail() , email)) {
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                throw new IllegalStateException("user is taken");
            }
            user.setEmail(email);
        }
    }


    public boolean findIfUserExists(String email) {
        Optional<User> userOptional =  userRepository.findUserByEmail(email);
        return userOptional.isPresent();
    }

    public User findUserByMail (String email) {
        Optional<User> userOptional =  userRepository.findUserByEmail(email);
        return userOptional.get();
    }

    public User findUserById (int id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.get();
    }

}
