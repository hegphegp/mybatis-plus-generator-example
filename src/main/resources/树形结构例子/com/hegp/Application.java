package com.hegp;

import com.hegp.system.model.SysUser;
import com.hegp.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.util.UUID;

public class Test implements CommandLineRunner {

    @Autowired

    @Override
    public void run(String... args) throws Exception {
        SysUser user = new SysUser();
        user.setUsername(UUID.randomUUID().toString());
        userService.test(user);
    }
}
