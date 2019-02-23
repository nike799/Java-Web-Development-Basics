package casebook.service;

import casebook.domain.entities.User;
import casebook.domain.models.service.UserServiceModel;
import casebook.repository.user.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Inject
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserServiceModel userRegister(UserServiceModel userServiceModel) {
        userServiceModel.setPassword(DigestUtils.sha256Hex(userServiceModel.getPassword()));
        return this.modelMapper.map(
                this.userRepository.save(this.modelMapper.map(userServiceModel, User.class)), UserServiceModel.class
        );
    }

    @Override
    public UserServiceModel userLogin(UserServiceModel userServiceModel) {
        return this.modelMapper.map(this.userRepository.findByUsername(userServiceModel.getUsername()), UserServiceModel.class);
    }

    @Override
    public List<UserServiceModel> getAllUsers() {
        return List.of(this.modelMapper.map(this.userRepository.findAll().toArray(), UserServiceModel[].class));
    }

    @Override
    public UserServiceModel findUserByUsername(String username) {
        return this.modelMapper.map(this.userRepository.findByUsername(username),UserServiceModel.class);
    }

    @Override
    public UserServiceModel findUserById(String id) {
        return this.modelMapper.map(this.userRepository.findById(id),UserServiceModel.class);
    }

    @Override
    public boolean addFriend(UserServiceModel user, UserServiceModel userForAdding) {
        User entity = this.modelMapper.map(user,User.class);
        User friend = this.modelMapper.map(userForAdding,User.class);
        entity.getFriends().add(friend);
        return this.userRepository.update(entity) != null;
    }

    @Override
    public void removeFriend(UserServiceModel userServiceModel,String id) {
        User user = this.userRepository.findByUsername(userServiceModel.getUsername());
        user.getFriends().removeIf(friend -> friend.getId().equals(id));
        this.userRepository.update(user);
        System.out.println();
    }
}
