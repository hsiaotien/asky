package jackjson.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import json.App;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * @author xiaotian.huang
 * @date 2019/01/18
 * note that all file of mytest package is my test class, you can ignore these class
 */
@SpringBootTest(classes = App.class)
@RunWith(SpringRunner.class)
public class ReadJsonTest {

    /** test read json file by jackson */
    @Test
    public void testReadJson01() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File("/Users/apple/keepStudy/asky/util/json/src/main/resources/test.json");
            TestObj testObj = mapper.readValue(file, TestObj.class);
            Assert.assertEquals("abc",testObj.getApiKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 加强，解耦 */
    @Test
    public void testReadJson02() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            ClassLoader classLoader = this.getClass().getClassLoader();
            URL url = classLoader.getResource("test.json");
            File file = new File(url.getFile());
            // 上述已经解耦，spring-boot 有更方便的方式
            TestObj testObj = mapper.readValue(file, TestObj.class);
            Assert.assertEquals("abc",testObj.getApiKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Value("classpath:test.json")
    private Resource res;

    /** spring-boot方式 */
    @Test
    public void testReadJson03() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = res.getFile();
            // 上述已经解耦，spring-boot 有更方便的方式
            TestObj testObj = mapper.readValue(file, TestObj.class);
            Assert.assertEquals("abc",testObj.getApiKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 另外一种方式, 读流 */
    @Test
    public void testReadJson04() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            ClassLoader classLoader = this.getClass().getClassLoader();
            InputStream in = classLoader.getResourceAsStream("test.json");
            // 上述已经解耦，spring-boot 有更方便的方式
            TestObj testObj = mapper.readValue(in, TestObj.class);
            Assert.assertEquals("abc",testObj.getApiKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class TestObj {
        private String apiKey;
        private String secret;

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }
    }
}
