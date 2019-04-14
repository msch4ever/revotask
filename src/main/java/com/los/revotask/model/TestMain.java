package com.los.revotask.model;

import com.los.revotask.model.user.User;
import com.los.revotask.service.AccountService;
import com.los.revotask.service.TransferService;

import java.math.BigDecimal;

public class TestMain {

    public static void main(String[] args) {

        AccountService accountService = new AccountService();
        TransferService transferService = new TransferService(accountService);

        User bill = new User("Bill");
        User fill = new User("Fill");

        bill.getAccount().setBalance(new BigDecimal(200.0));
        fill.getAccount().setBalance(new BigDecimal(150.0));

        println(bill.toString());
        println(fill.toString());

        transferService.transferMoney(bill.getAccount(), fill.getAccount(), new BigDecimal(200.0));

        println(bill.toString());
        println(fill.toString());

        accountService.topUp(bill.getAccount(), new BigDecimal(40.0));
        accountService.withdraw(fill.getAccount(), new BigDecimal(25.0));

        println(bill.toString());
        println(fill.toString());
    }

    private static void println(String line) {
        System.out.println(line);
    }

    private static void print(String string) {
        System.out.print(string);
    }
}
