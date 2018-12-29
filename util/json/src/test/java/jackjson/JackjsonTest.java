package jackjson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fastjson.pojo.DepthPriceVolume;
import jackjson.pojo.User;
import org.apache.commons.collections.IteratorUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class JackjsonTest {

	private ObjectMapper objectMapper = new ObjectMapper();

	/*
	ObjectMapper序列化和反序列化的方法test
	 */

	// 序列化 object => json
	@Test
	public void test01() throws JsonProcessingException {
		User user = new User();
		user.setUsername("lisi");
		user.setAge(16);
		// 序列化简单对象
		String userJson = objectMapper.writeValueAsString(user);
		System.out.println("userJson = " + userJson);

		// 序列化集合对象
		List<User> listUser = Arrays.asList(user, user);
		String listUserJson = objectMapper.writeValueAsString(listUser);
		System.out.println("listUserJson = " + listUserJson);
	}


	// json -> object  简单对象的反序列化
	@Test
	public void test02() throws IOException {
		String json = "{\"username\":\"zhangsan\",\"age\":18}";//可以假设这个json是http远程调用返回的json数据
		User user = objectMapper.readValue(json, User.class);
		System.out.println("user = " + user);
	}

	// 集合的反序列化
	@Test
	public void test03() throws IOException {
		// 拿到集合序列化后的json数据
		User user = new User();
		user.setUsername("lisi");
		user.setAge(16);
		List<User> listUserPre = Arrays.asList(user, user, user);
		String listUserJson = objectMapper.writeValueAsString(listUserPre);
		System.out.println("listUserJson = " + listUserJson);

		//反序列化一个集合
		List<User> listUser = objectMapper.readValue(listUserJson,
				objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));

		System.out.println("listUser = " + listUser);
	}

	// 超级复杂的对象，反序列化过程， 比如集合中套集合的json如何序列化？
	// 根据泛型反射，通用的反序列化的方式，可以应对简单或者复杂都可以。
	@Test
	public void test04() throws IOException {
		List<List<User>> listComplex = new ArrayList<>(); //嵌套的集合
		User user = new User();
		user.setUsername("lisi");
		user.setAge(16);
		List<User> listUserPre01 = Arrays.asList(user, user);
		List<User> listUserPre02 = Arrays.asList(user, user, user);
		listComplex.add(listUserPre01);
		listComplex.add(listUserPre02);

		//序列化 (可以看出虚拟化不需要区分简单复杂，都是同一个方法搞定，接收Object即所有类型对象）
		String listComplexJson = objectMapper.writeValueAsString(listComplex);
		System.out.println("listComplexJson = " + listComplexJson);

		//反序列化01， jackjson包提供了下面的类，根据泛型反射，实际上所有类型都可以通过该类来实现反序列化
		List<List<User>> list = objectMapper.readValue(listComplexJson, new TypeReference<List<List<User>>>() {
		});// 复杂的json
		System.out.println("list = " + list);
		/*
		要注意的是，这里的泛型是编译器的泛型，而不是运行期的泛型。
		编译期的泛型，不会擦除，直接编译到class文件中。
		 */

		//反序列化02
		String json = "{\"username\":\"zhangsan\",\"age\":18}";
		User u = objectMapper.readValue(json, new TypeReference<User>() {
		});
		System.out.println("u = " + u);
	}

	/*
	在util包中，自行封装了处理json的工具类。原理是相同的，即上述示例的方式进行封装抽取。
	 */
	// 后续测试这些工具中的方法。。。

    /**
     * 测试 对比fastjson：test01 完成了相同的功能
     */
    @Test
    public void test05() throws IOException {
        String data = "{\"ch\":\"market.htusdt.depth.step3\",\"ts\":1545795865467," +
                "\"tick\":{\"bids\":[[1.100000000000000000,60.780000000000000000],[1.000000000000000000,778827.130000000000000000],[0.900000000000000000,61888.350000000000000000]],\"asks\":[[1.200000000000000000,436868.733975184452044000],[1.300000000000000000,483357.526371224165341820]],\"ts\":1545795865057,\"version\":33983728763}}";
        JsonNode jsonNode = objectMapper.readTree(data);
        JsonNode tick = jsonNode.get("tick");
        JsonNode bids = tick.get("bids");
        // 可以链式，也可以用findValue
        Iterator<JsonNode> iterator = bids.iterator();
        List<DepthPriceVolume> list = new ArrayList<>();
        while (iterator.hasNext()) {
            JsonNode next = iterator.next();
            DepthPriceVolume depthPriceVolume = new DepthPriceVolume(next.get(0).toString(), next.get(1).toString());
            list.add(depthPriceVolume);
        }
        String target = objectMapper.writeValueAsString(list);
        System.out.println("target = " + target);
    }

	@Test
    public void test06() throws IOException {
        String data = "{\"ch\":\"market.htusdt.depth.step3\",\"ts\":1545795865467," +
                "\"tick\":{\"bids\":[[1.100000000000000000,60.780000000000000000],[1.000000000000000000,778827.130000000000000000],[0.900000000000000000,61888.350000000000000000]],\"asks\":[[1.200000000000000000,436868.733975184452044000],[1.300000000000000000,483357.526371224165341820]],\"ts\":1545795865057,\"version\":33983728763}}";
		System.out.println("data = " + data);
        JsonNode jsonNode = objectMapper.readTree(data);
        JsonNode bids = jsonNode.findValue("bids");
        Iterator<JsonNode> iterator = bids.iterator();
		List<ArrayNode> list = IteratorUtils.toList(iterator);
		/*List<DepthPriceVolume> dpvList = new ArrayList<>();
		for (ArrayNode arrayNode : list) {
			JsonNode priceNode = arrayNode.get(0);
			JsonNode volumeNode = arrayNode.get(1);
			dpvList.add(new DepthPriceVolume(priceNode.toString(),volumeNode.toString()));
		}*/
		// java8
		List<DepthPriceVolume> list01 = list.stream().map(a -> new DepthPriceVolume(a.get(0).toString(), a.get(1).toString())).collect(Collectors.toList());
		String target = objectMapper.writeValueAsString(list01);
		System.out.println("target = " + target);
	}

	/**
	 * 创造节点
	 * @throws JsonProcessingException
	 */
	@Test
	public void test07() throws JsonProcessingException {
		ObjectNode objectNode = objectMapper.createObjectNode();
		ObjectNode node = objectNode.put("12.00", 100);
		String json = objectMapper.writeValueAsString(node);
		System.out.println("json = " + json);
		//
		ArrayNode arrayNode = objectMapper.createArrayNode();
		arrayNode.add("[11,123]").add("[22,456]");
		String arrStr = objectMapper.writeValueAsString(arrayNode);
		System.out.println("arrStr = " + arrStr);
	}
}