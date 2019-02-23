package casebook.repository.user;

import casebook.domain.entities.User;
import casebook.repository.GenericRepository;

import java.util.List;

public interface UserRepository extends GenericRepository<User, String> {
    User findByUsername(String username);
}
