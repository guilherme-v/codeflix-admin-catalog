package com.gsv.codeflix.admin.catalog;

import com.gsv.codeflix.admin.catalog.infrastructure.configuration.WebServerConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ActiveProfiles("test")
@ExtendWith(MYSQLCleanUpExtension.class)
@SpringBootTest(classes = WebServerConfig.class)
public @interface IntegrationTest {

}
