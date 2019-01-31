package other.trywithresources;

/**
 * 参考 http://ifeve.com/java-7%E4%B8%AD%E7%9A%84try-with-resources/ <br/>
 */
public class MyAutoClosable implements AutoCloseable {

    public void doIt() {
        System.out.println("MyAutoClosable doing it!");
    }

    @Override
    public void close() throws Exception {
        System.out.println("新的异常处理机制，很容易关闭在trycatch中使用的资源");
    }
}
