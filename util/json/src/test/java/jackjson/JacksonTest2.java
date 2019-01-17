package jackjson;

import com.fasterxml.jackson.databind.ObjectMapper;
import jackjson.pojo.User2;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;

@Slf4j
public class JacksonTest2 {

    @Test
    public void test01() {
        String u = "[\"lily\",12]";
        ObjectMapper mapper = new ObjectMapper();
        try {
            User2 user2 = mapper.readValue(u, User2.class);
            Assert.assertEquals("lily",user2.getUsername());
        } catch (IOException e) {
            fail(e);
        }
    }

    private void fail(IOException e) {
        log.info("e.getMessage() = " + e.getMessage());
    }
}
