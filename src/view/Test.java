package view;

import dao.UserDao;
import model.Account;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import service.BankService;
import service.UserService;

import java.util.List;

public class Test {
    final static BankService bank = new BankService();

    public static void main(String[] args) {

        UserService userService = new UserService();
        UserDao userDao = new UserDao();

       /* //1)create a user
        User neda = User.builder().withName("neda").withFamily("akbari").
                withNationalCode("2560266751").withEmail("neda@gmail.com").build();

        User mona = User.builder().withName("mona").withFamily("razbani").
                withNationalCode("2560266752").withEmail("mona@gmail.com").build();
        User nasim = User.builder().withName("nasim").withFamily("zamani").
                withNationalCode("2560266753").withEmail("nasim@gmail.com").build();
          userService.createUser(neda);
          userService.createUser(mona);
          userService.createUser(nasim);

        // 2)Create an account and assign it to a user
        Account accForNeda = Account.builder()
                .withAccountType(AccountType.CURRENT_ACCOUNT).withUser(neda).withBalance(2000).build();
        Account accForNeda2 = Account.builder()
                .withAccountType(AccountType.SHORT_ACCOUNT).withUser(neda).withBalance(10000).build();
        Account accForNeda3 = Account.builder()
                .withAccountType(AccountType.LONG_ACCOUNT).withUser(neda).withBalance(20000).build();
        Account accForMona = Account.builder()
                .withAccountType(AccountType.SAVING_ACCCOUNT).withUser(mona).withBalance(5000).build();
        Account accForNasim = Account.builder()
                .withAccountType(AccountType.LOAN_ACCOUNT).withUser(nasim).withBalance(6000).build();
        bank.save(accForNeda);
        bank.save(accForMona);
        bank.save(accForNasim);
        bank.save(accForNeda2);
        bank.save(accForNeda3);
*/
        //3)find user by Name
         /*List<User> foundByName = userService.findUserByName("neda");
         foundByName.forEach(System.out::println);*/
        //4)find user family
        // List<User> foundByFamily = userService.findUserByFamily("akbari");
        //  foundByFamily.forEach(System.out::println);
        //5)find user by cardNumber
        Account accByCardNumber = bank.findAccByCardNumber(107504981682437L);
        User user = accByCardNumber.getUser();
        System.out.println("********>>>>> " + user.getName());
        /*SessionFactory sessionFactory = new Configuration().configure("config/hibernate.cfg.xml").buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();*/
        //List<User> neda = userDao.findByName("neda");
        List<User> neda = userService.findUserByName("neda");
        neda.forEach(a -> System.out.println("********** " + a.getName() + a.getFamily()));
        /*transaction.commit();
        session.close();*/
    /*    List<User> neda = userService.findUserByName("neda");
        neda.forEach(a-> System.out.println("********** "+a.getName()+ a.getFamily()));
        User user = userService.foundUserByCardNum(223745789219095L);
        System.out.println("************** "+user.getFamily());*/

        // User foundUser = session.get(User.class, 1);
        /*Account foundAcc = bank.findAccByCardNumber(accForNeda.getCardNumber());
         User foundUser = foundAcc.getUser();
        System.out.println(foundUser);
        transaction.commit();
        session.close();*/

        // 6)Withdraw=>برداشت
        //   transActionService.withdraw(accForNeda, 1000);
        // 7)Deposit
        //  transActionService.deposit(accForNeda, 3000);
        System.out.println("***************************************************************");
        System.out.println("***************************************************************");

        SessionFactory sessionFactory = new Configuration().configure("config/hibernate.cfg.xml").buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        //userService.FindLast3Update().forEach(a-> System.out.println(a.getMessage()+a.getUpdateTime()+a.getUser().getFamily()) );
        userService.FindLast3Update();
        transaction.commit();
        session.close();
        // userDao.find();


    }
}
