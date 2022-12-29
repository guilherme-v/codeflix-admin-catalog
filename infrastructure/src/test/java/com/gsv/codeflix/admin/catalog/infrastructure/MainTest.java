package com.gsv.codeflix.admin.catalog.infrastructure;

import com.gsv.codeflix.admin.catalog.infrastructure.configuration.WebServerConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.AbstractEnvironment;

class MainTest {

    @Test
    public void testMain() {
        System.setProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, "test");
        SpringApplication.run(WebServerConfig.class, new String[]{});
    }
}