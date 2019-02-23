package casebook.web.beans;

import casebook.domain.models.service.UserServiceModel;
import casebook.service.UserService;
import casebook.domain.models.view.UserFriendsViewModel;
import org.modelmapper.ModelMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class UserFriendsBean {
    private List<UserFriendsViewModel> friends;
    private UserService userService;
    private ModelMapper modelMapper;

    public UserFriendsBean() {
    }

    @Inject
    public UserFriendsBean(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    private void init(){
        String id = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");
        UserServiceModel userServiceModel = this.userService.findUserById(id);
        friends = List.of(this.modelMapper.map(userServiceModel.getFriends().toArray(),UserFriendsViewModel[].class));
    }

    public List<UserFriendsViewModel> getFriends() {
        return friends;
    }

    public void setFriends(List<UserFriendsViewModel> friends) {
        this.friends = friends;
    }
    public void removeFriend(String id){
        String userId = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");
        UserServiceModel userServiceModel = this.userService.findUserById(userId);
        this.userService.removeFriend(userServiceModel,id);
    }
}
