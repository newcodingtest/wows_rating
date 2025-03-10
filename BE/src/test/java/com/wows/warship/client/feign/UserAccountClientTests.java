package com.wows.warship.client.feign;

import com.wows.warship.config.FeignClientConfiguration;
import com.wows.warship.service.TestService;
import com.wows.warship.service.UserAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
@SpringBootTest
public class UserAccountClientTests {

    @Autowired
    private UserAccountClient userAccountService;

    @Test
    void testFeignClient() {
        Map<String, Object> response = userAccountService.getAccountList("4a5f774ca91614ec9e42bdb76474af15",
                "nocap");

        assertNotNull(response);
        // 적절한 응답을 확인하는 로직을 추가
        System.out.println(response);
    }
}