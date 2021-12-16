package service;

import dao.AccountDao;
import dao.UserDao;
import model.Account;
import model.UpdateInfo;
import model.User;

import java.util.List;

public class UserService {
    public static final AccountDao accountDao = new AccountDao();
    UserDao userDao = new UserDao();

    public User createUser(User user) {
        userDao.save(user);
        return user;
    }

    public List<User> findUserByName(String name) {
        List<User> foundUsers = userDao.findByName(name);
        return foundUsers;
    }

    public List<User> findUserByFamily(String lastName) {
        List<User> foundUsers = userDao.findByFamily(lastName);
        return foundUsers;
    }

    public User foundUserByNationalCode(String nationalCode) {
        User foundUser = userDao.findByNationalCode(nationalCode);
        return foundUser;
    }

    public User foundUserByCardNum(Long cardNumber) {
        Account accByCardNumber = accountDao.findAccByCardNumber(cardNumber);
        User foundUser = accByCardNumber.getUser();
        return foundUser;
    }

    public void update(User user, UpdateInfo update) {
        userDao.update(user);
        userDao.saveEditInfo(update);
    }

    public List<UpdateInfo> FindLast3Update() {
        return userDao.findLast3Update();
    }

}
