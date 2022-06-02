package service;

import com.yang.wsClient.WSApplication;
import com.yang.wsClient.service.IWSClient;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WSApplication.class)
@ActiveProfiles("dev")
//@TestPropertySource(locations = "classpath:application-dev.properties")
@DisplayName("我的第一个测试用例")
@Slf4j
public class TestWSClient {
    // 下面三个mock对象是由spring提供的
    @Resource
    public MockHttpServletRequest request;

    @Resource
    public MockHttpSession session;

    @Resource
    public MockHttpServletResponse response;

    @Autowired
    private IWSClient wSClient;


    @BeforeAll
    public void init() {
        System.out.println("初始化数据");
        request.addHeader("token", "test_token");
    }

    @AfterAll
    public void cleanup() {
        System.out.println("清理数据");
    }

    @BeforeEach
    public void tearup() {
        System.out.println("当前测试方法开始");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("当前测试方法结束");
    }

    @SneakyThrows
    //@Disabled
    @Test
    public void callWebSV(){
        String[] paramArray = new String[2];
        paramArray[0]="1";
        paramArray[1]="2";
        //String data = wSClient.callWebSV("http://127.0.0.1:20801/wsService/services/test?wsdl","http://service.wsService.yang.com/","getTestStr","","",paramArray);
        System.out.println("接口调用");
    }

}
