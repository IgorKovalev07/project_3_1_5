package web.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.dao.UserDao;
import web.model.User;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public void saveUser(User user) {
        userDao.saveUser(user);
    }


    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    public void updateUser(Long id, String firstName, String lastName, String email) {
        userDao.updateUser(id, firstName, lastName, email);
    }


    public User showUser(Long id) {
        return userDao.showUser(id);
    }
}