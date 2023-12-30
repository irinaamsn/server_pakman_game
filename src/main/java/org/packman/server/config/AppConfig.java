package org.packman.server.config;

import org.packman.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class AppConfig {
    private final DataSource dataSource;
    private static UserService userService;

    @Autowired
    public AppConfig(DataSource dataSource, UserService userService) {
        this.dataSource = dataSource;
        AppConfig.userService = userService;
    }

    @Autowired
    public static UserService getUserService() {
        return userService;
    }
}
