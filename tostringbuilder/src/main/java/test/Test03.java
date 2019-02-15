package test;

import pojo.Author;

public class Test03 {

    @SuppressWarnings("all")
    public static void main(String[] args) {
        Author author = new Author();
        author.setName("xiaotian.huang");
        author.setAge(10);
        author.setAddress("shanghai");

        Author author02 = new Author();
        author02.setName("mei.yang");
        author02.setAge(10);
        author02.setAddress("beijing");
        //
        System.out.println(author.equals(author02));
    }
}
