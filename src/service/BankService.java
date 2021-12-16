package service;

import dao.AccountDao;
import enums.TransActionType;
import model.Account;
import model.TransAction;

public class BankService {
    public static final AccountDao accountDao = new AccountDao();

    public void save(Account account) {
        accountDao.save(account);
    }


    public Account findAccByCardNumber(Long cardNumber) {
        return accountDao.findAccByCardNumber(cardNumber);
    }

    public void withdraw(Account account, double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("amount cannot be negative.");
        } else if (amount > account.getBalance()) {
            throw new IllegalArgumentException("amount to withdraw cannot be more than balance.....Not enough balance");
        } else {
            account.setBalance(account.getBalance() - amount);
            accountDao.update(account);
            TransAction transAction = new TransAction(amount, TransActionType.WITHDRAW, account);
            accountDao.saveTransAction(transAction);
        }
    }

    public void deposit(Account account, double amount) {
        if (amount > 0) {
            account.setBalance(account.getBalance() + amount);
            accountDao.update(account);
            TransAction transAction = new TransAction(amount, TransActionType.DEPOSIT, account);
            accountDao.saveTransAction(transAction);
        } else {
            throw new RuntimeException("Input a valid amount");
        }
    }

}
