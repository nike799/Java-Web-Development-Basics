package casebook.web.beans;

import casebook.domain.models.service.UserServiceModel;
import casebook.domain.models.view.UserHomeViewModel;
import casebook.service.UserService;
import org.modelmapper.ModelMapper;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

@Named
@RequestScoped
public class UserHomeBean extends BaseBean {
    private List<UserHomeViewModel> userHomeViewModels;
    private UserService userService;
    private ModelMapper modelMapper;
    public UserHomeBean() {
    }

    @Inject
    public UserHomeBean(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    private void init() {
        var session = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        String username = (String) session.get("username");
        List<String> friends =
                List.of(this.modelMapper.map(this.userService.findUserByUsername(username).getFriends().toArray(),UserServiceModel[].class))
                .stream().map(UserServiceModel::getUsername).collect(Collectors.toList());
        this.userHomeViewModels = List.of(this.modelMapper.map(this.userService.getAllUsers()
                .stream()
                .filter(user -> !user.getUsername().equals(username) &&
                        !friends.contains(user.getUsername()))
                .toArray(), UserHomeViewModel[].class));
    }

    public void addFriend(String username) throws IllegalAccessException {
        var session = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
       String userUsername = String.valueOf(session.get("username"));
        UserServiceModel user = this.userService.findUserByUsername(userUsername);
        UserServiceModel userForAdding = this.userService.findUserByUsername(username);

        if(!this.userService.addFriend(user,userForAdding)){
            throw new IllegalAccessException("Something went wrong");
        }
        this.redirect("home");
    }

    public List<UserHomeViewModel> getUserHomeViewModels() {
        return userHomeViewModels;
    }

    public void setUserHomeViewModels(List<UserHomeViewModel> userHomeViewModels) {
        this.userHomeViewModels = userHomeViewModels;
    }
}
