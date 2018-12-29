package fastjson;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import fastjson.pojo.DepthPriceVolume;

import java.util.List;
import java.util.stream.Collectors;

public class Test01 {
    @Test
    public void testJson() {
        String data = "{\"ch\":\"market.htusdt.depth.step3\",\"ts\":1545795865467," +
                "\"tick\":{\"bids\":[[1.100000000000000000,60.780000000000000000],[1.000000000000000000,778827.130000000000000000],[0.900000000000000000,61888.350000000000000000]],\"asks\":[[1.200000000000000000,436868.733975184452044000],[1.300000000000000000,483357.526371224165341820]],\"ts\":1545795865057,\"version\":33983728763}}";
        JSONObject jsonObject = JSONObject.parseObject(data);
        JSONObject tick = jsonObject.getJSONObject("tick");
        JSONArray bidArray = tick.getJSONArray("bids");
        JSONArray askArray = tick.getJSONArray("asks");
        List<DepthPriceVolume> list1 = bidArray.stream().map(b-> {
            JSONArray c = (JSONArray)b;
            return new DepthPriceVolume(c.get(0).toString(),c.get(1).toString());
        }).collect(Collectors.toList());

        List<DepthPriceVolume> list2 = askArray.stream().map(b-> {
            JSONArray c = (JSONArray)b;
            return new DepthPriceVolume(c.get(0).toString(),c.get(1).toString());
        }).collect(Collectors.toList());
        String target01 = JSONObject.toJSONString(list1);
        System.out.println(target01);
        System.out.println(JSONObject.toJSONString(list2));
    }
}
