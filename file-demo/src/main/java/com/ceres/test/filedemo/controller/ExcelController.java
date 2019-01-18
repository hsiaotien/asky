package com.ceres.test.filedemo.controller;

import com.ceres.test.filedemo.pojo.Book;
import com.ceres.test.filedemo.util.ExcelUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("test")
public class ExcelController {



    @GetMapping("xml")
    public void downloadXml(HttpServletRequest request, HttpServletResponse response) {
        List<Book> listBook = Arrays.asList(new Book("spring", "12.00", "hxt"),
                new Book("mybatis", "10.00", "coder"));
        String[] secondTitles = {"姓名","价格","作者"};
        String[] objFields = {"bookName","price","author"};
        try {
            ExcelUtil.compatibleFileName(request,response,"书册记录");
            ExcelUtil.exportExcel(response.getOutputStream(),listBook,
                    "书册",null,1,secondTitles,objFields);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
