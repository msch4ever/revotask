package com.los.revotask;

import com.los.revotask.persistence.PersistanceContext;
import com.los.revotask.model.user.User;
import com.los.revotask.service.ServiceContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PersistanceMainTest {

    public static void main(String[] args) {
        PersistanceContext persistanceContext = new PersistanceContext();
        ServiceContext serviceContext = new ServiceContext(persistanceContext);

        List<User> users = Stream.of(1, 2, 3, 4, 5, 6)
                .map(serviceContext.userService::findById)
                .collect(Collectors.toList());
        users.stream().map(User::toString).forEach(PersistanceMainTest::println);

        serviceContext.userService.createUser("Kill", "EUR", new BigDecimal(200));
        users = serviceContext.userService.getAll();
        users.forEach(it -> println(it.toString()));

        serviceContext.transferService.transferMoney(4L, 1L, new BigDecimal(150.0)); // 1
        serviceContext.transferService.transferMoney(1L, 2L, new BigDecimal(50.0));  // 2
        serviceContext.transferService.transferMoney(3L, 2L, new BigDecimal(10.0));  // 3
        serviceContext.transferService.transferMoney(2L, 1L, new BigDecimal(15.0));  // 4
        serviceContext.transferService.transferMoney(2L, 6L, new BigDecimal(25.0));  // 5
        serviceContext.transferService.transferMoney(6L, 7L, new BigDecimal(13.0));  // 6
        serviceContext.transferService.transferMoney(7L, 4L, new BigDecimal(84.0));  // 7
        serviceContext.transferService.transferMoney(1L, 2L, new BigDecimal(37.0));  // 8
        serviceContext.transferService.transferMoney(2L, 1L, new BigDecimal(250.0)); // 9
        serviceContext.transferService.transferMoney(5L, 5L, new BigDecimal(17.0));  // 10
        serviceContext.transferService.transferMoney(7L, 6L, new BigDecimal(3.0));   // 11

        serviceContext.transferService.getAll().forEach(it -> println(it.toString()));

    }

    private static void println(String line) {
        System.out.println(line);
    }
}
