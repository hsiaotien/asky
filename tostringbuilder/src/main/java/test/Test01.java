package test;

import pojo.Author;

public class Test01 {

    public static void main(String[] args) {
        Author author = new Author();
        author.setName("xiaotian.huang");
        author.setAge(10);
        author.setAddress("shanghai");
        //
        System.out.println("DEFAULT_STYLE =" + author);
        System.out.println("JSON_STYLE = " + author.toString01());
        System.out.println("MULTI_LINE_STYLE = " + author.toString02());
        System.out.println("NO_CLASS_NAME_STYLE = " + author.toString03());
        System.out.println("NO_FIELD_NAMES_STYLE = " + author.toString04());
        System.out.println("SHORT_PREFIX_STYLE = " + author.toString05());
        System.out.println("SIMPLE_STYLE = " + author.toString06());

        //
        System.out.println(author.toString000());
        System.out.println(author.toString001());
    }
}
