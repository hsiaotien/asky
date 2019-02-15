package test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pojo.User;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        String json = "{\"u\":\"hxt\",\"a\":10}";
        ObjectMapper mapper = new ObjectMapper();
        try {
            User user = mapper.readValue(json, User.class);
            System.out.println("user = " + user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //
        User user = new User();
        user.setUsername("asky");
        user.setAge(1);
        try {
            String json2 = mapper.writeValueAsString(user);
            System.out.println("json2 = " + json2);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
