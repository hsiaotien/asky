package test;

import pojo.Author;

public class Test02 {

    public static void main(String[] args) {
        Author author = new Author();
        author.setName("xiaotian.huang");
        author.setAge(10);
        author.setAddress("shanghai");

        Author author02 = new Author();
        author02.setName("xiaotian.huang");
        author02.setAge(20);
        author02.setAddress("beijing");
        //

        System.out.println(author.hashCode());
        System.out.println(author02.hashCode());
    }
}
