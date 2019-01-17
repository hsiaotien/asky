package jackjson.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
public class User2 {
	private String username;
	private Integer age;
}