package casebook.service;

import casebook.domain.models.service.UserServiceModel;


import java.util.List;

public interface UserService {
    UserServiceModel userRegister(UserServiceModel userServiceModel);
    UserServiceModel userLogin(UserServiceModel userServiceModel);
    List<UserServiceModel> getAllUsers();
    UserServiceModel findUserByUsername(String username);
    UserServiceModel findUserById(String id);
    boolean addFriend(UserServiceModel user, UserServiceModel userForAdding);
    void removeFriend(UserServiceModel userServiceModel,String friendId);
}
