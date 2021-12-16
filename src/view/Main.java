package view;

import enums.AccountType;
import enums.UserType;
import model.Account;
import model.UpdateInfo;
import model.User;
import service.BankService;
import service.UserService;

import java.util.List;
import java.util.Scanner;

public class Main {
    final static Scanner scanner = new Scanner(System.in);
    final static BankService bank = new BankService();
    final static UserService userService = new UserService();

    public static void main(String[] args) {
        driver();
    }

    private static void driver() {
        System.out.println("******************  Hi  ******************\n1.Admin Menu  2.User Menu");
        if (scanner.next().trim().equals("1")) {
            adminMenu();
        } else if (scanner.next().trim().equals("2")) {
            userMenu();
        }
    }

    private static void userMenu() {
        System.out.println("for getting service enter your nationalCode: ");
        String nc = scanner.next().trim();
        User foundUser = userService.foundUserByNationalCode(nc);
        if (foundUser == null) {
            System.out.println("user not found... for register enter info");
            register();
        } else {
            signUp(foundUser);
        }
    }

    private static void register() {
        System.out.println("enter name :");
        String name = scanner.next();
        System.out.println("enter family :");
        String family = scanner.next();
        System.out.println("enter nationalCode");
        String nationalCode = scanner.next();
        System.out.println("enter email");
        String email = scanner.next();
        User user = User.builder().withName(name).withFamily(family).
                withNationalCode(nationalCode).withEmail(email).build();
        user.setUserType(UserType.NO_HISTORY);
        userService.createUser(user);
        createAccount(user);
    }

    private static void createAccount(User user) {
        System.out.println("Which Account do you want?\n1)CURRENT_ACCOUNT 2)SHORT_ACCOUNT 3)LONG_ACCOUNT 4)SAVING_ACCOUNT 5)LOAN_ACCOUNT");
        String choice = scanner.next();
        String trim = choice.trim();
        AccountType accountType = null;
        switch (trim) {
            case "1":
                accountType = AccountType.CURRENT_ACCOUNT;
                break;
            case "2":
                accountType = AccountType.SHORT_ACCOUNT;
                break;
            case "3":
                accountType = AccountType.LONG_ACCOUNT;
                break;
            case "4":
                accountType = AccountType.SAVING_ACCCOUNT;
                break;
            case "5":
                accountType = AccountType.LOAN_ACCOUNT;
                break;
        }
        System.out.println("how much do you want to deposit : ");
        double balance = scanner.nextDouble();
        Account account = Account.builder().withAccountType(accountType)
                .withUser(user).withBalance(balance).build();
        bank.save(account);
    }


    private static void signUp(User user) {
        System.out.println("owner: " + user.getName() + " " + user.getFamily());
        outer:
        while (true) {
            System.out.println("\n1.account service  2.profile service 3.create a new account");
            int choice = scanner.nextInt();
            if (choice == 1) {
                System.out.println("enter CardNumber: ");
                Long cardNumber = scanner.nextLong();
                Account account = bank.findAccByCardNumber(cardNumber);
                System.out.println("1.show balance of an account\n2.withdraw from an account\n3.deposit to an account\n4.exit");
                switch (scanner.nextInt()) {
                    case 1:
                        System.out.println("balance : " + account.getBalance());
                        signUp(user);
                    case 2:
                        System.out.println("enter amount:");
                        double withdrawAmount = scanner.nextDouble();
                        bank.withdraw(account, withdrawAmount);
                        signUp(user);
                    case 3:
                        System.out.println("enter amount:");
                        double depositAmount = scanner.nextDouble();
                        bank.deposit(account, depositAmount);
                        signUp(user);
                    case 4:
                        driver();
                }

            } else if (choice == 2) {
                System.out.println("1.edit your info  2.show last three updates  3.exit");
                switch (scanner.nextInt()) {
                    case 1:
                        editInformation(user);
                    case 2:
                        userService.FindLast3Update().forEach(a -> System.out.println(a.getMessage() + " " + a.getUpdateTime()));
                    case 3:
                        break outer;
                }
            } else if (choice == 3) {
                createAccount(user);
                signUp(user);
            }
        }

    }


    private static void editInformation(User user) {
        UpdateInfo update = new UpdateInfo();
        String message = "";
        outer:
        while (true) {
            System.out.println("1.edit name  2.edit gmail  3.exit");
            switch (scanner.nextInt()) {
                case 1:
                    System.out.println("name is now ==> " + user.getName() + "\nenter new name :");
                    String name = scanner.next();
                    String oldName = user.getName();
                    user.setName(name);
                    message = "update name from " + oldName + " to " + name;
                    break;
                case 2:
                    System.out.println("email is now ==> " + user.getEmail() + "\nenter new email :");
                    String email = scanner.next();
                    String oldEmail = user.getEmail();
                    user.setEmail(email);
                    message = "update email from " + oldEmail + " to " + email;
                    break outer;
                case 3:
                    break outer;
            }
            update.setMessage(message);
            update.setUser(user);
            userService.update(user, update);
            System.out.println(message);
        }
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("Enter pass");
            {
                if (scanner.next().equalsIgnoreCase("admin")) {
                    System.out.println("1.find user by Name  2.find user family  3.find user by cardNumber 4.back to menu");
                    int choice = scanner.nextInt();
                    switch (choice) {
                        case 1:
                            System.out.println("enter user's name: ");
                            List<User> users = userService.findUserByName(scanner.next());
                            users.forEach(user -> System.out.println("********** " + user.getName() + " " + user.getFamily()));
                            break;
                        case 2:
                            System.out.println("enter user's name: ");
                            List<User> foundUser = userService.findUserByFamily(scanner.next());
                            foundUser.forEach(user -> System.out.println("********** " + user.getName() + " " + user.getFamily()));
                            break;
                        case 3:
                            System.out.println("enter user's CardNumber: ");
                            Account accByCardNumber = bank.findAccByCardNumber(scanner.nextLong());
                            accByCardNumber.getUser();
                            break;
                        case 4:
                            driver();
                    }

                } else {
                    System.out.println("wrong pass....try again");
                }
            }
        }
    }
}
