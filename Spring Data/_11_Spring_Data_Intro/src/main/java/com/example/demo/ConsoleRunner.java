package com.example.demo;

import com.example.demo.models.User;
import com.example.demo.services.AccountService;
import com.example.demo.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ConsoleRunner implements CommandLineRunner  {
    private final UserService userService;
    private final AccountService accountService;

    @Autowired
    public ConsoleRunner(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    @Transactional //всички заявки по-долу да се изпълнят в една транзакция
    //Или колекцията трябва да стане EAGER или трябва да добавим @Transactional в метода в който ще имаме нужда от тези данни
    public void run(String... args) throws Exception {

        //регистриране на потребители
        this.userService.register("Edin Gospodin", 18);
        this.userService.register("Vtori Gospodin", 20);

        //Намиране на потребители по име
        User firstuser = this.userService.findByUsername("Edin Gospodin");
        User secondUser = this.userService.findByUsername("Vtori Gospodin");

        //Депозиране на пари в акаунт
        this.accountService
                .depositMoney(BigDecimal.TEN, firstuser.getAccountIds().get(0));

        //Теглене на пари от акаунт
        this.accountService
                .withdrawMoney(BigDecimal.ONE, firstuser.getAccountIds().get(0));

        //Трансфер на пари от един акаунт към друг
        this.accountService.transferMoney(firstuser.getAccountIds().get(0), secondUser.getAccountIds().get(0), new BigDecimal(2));
    }
}
