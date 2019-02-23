package casebook.web.beans;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@RequestScoped
public class UserLogoutBean extends BaseBean {
    public void logoutUser(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        this.redirect("");
    }
}
