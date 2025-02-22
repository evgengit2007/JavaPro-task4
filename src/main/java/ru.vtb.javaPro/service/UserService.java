package ru.vtb.javaPro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.vtb.javaPro.dao.UserDao;
import ru.vtb.javaPro.dto.Users;

import java.util.List;

@Component
public class UserService {
    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Users insertRow(Users users) {
        userDao.insertData(users);
        return users;
    }

    public List<Users> readAll() {
        List<Users> usersList = userDao.readData();
        return usersList;
    }


    public Users getUser(Long id) {
        return userDao.getUserById(id);
    }

    public Users updateUser(Users users) {
        return userDao.updateData(users);
    }

    public Boolean deleteUser(Users users) {
        return userDao.deleteData(users);
    }
}
