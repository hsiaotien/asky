package other.trywithresources;

public class MyAutoClosableTest {

    @org.junit.Test
    public void testAutoClosable() {
        // 新的异常处理机制
        try(MyAutoClosable myAutoClosable = new MyAutoClosable()) {
            myAutoClosable.doIt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
