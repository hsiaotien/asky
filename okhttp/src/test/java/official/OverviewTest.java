package official;


import java.io.IOException;

/**
 * 参加官网 https://square.github.io/okhttp/<br/>
 */

public class OverviewTest {

    @org.junit.Test
    public void run() {
        String url = "http://www.baidu.com";
        Overview overview = new Overview();
        try {
            String response = overview.run(url);
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 参考可用于测试的服务器  https://stackoverflow.com/questions/5725430/http-test-server-accepting-get-post-requests <br/>
     */
    @org.junit.Test
    public void post() {
        String url = "http://httpbin.org/post";
        String json = "{\"username\":\"hxt\"}";
        Overview overview = new Overview();
        try {
            String response = overview.post(url, json);
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
