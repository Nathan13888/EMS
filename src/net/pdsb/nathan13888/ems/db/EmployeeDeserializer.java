package net.pdsb.nathan13888.ems.db;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.pdsb.nathan13888.ems.types.EmployeeInfo;
import net.pdsb.nathan13888.ems.types.EmployeeType;
import net.pdsb.nathan13888.ems.types.FullTimeEmployee;
import net.pdsb.nathan13888.ems.types.PartTimeEmployee;

public class EmployeeDeserializer implements JsonDeserializer<EmployeeInfo> {

//	private EmployeeType type;
	private Gson gson;
//	public ArrayList<EmployeeInfo>[] buckets;

	public EmployeeDeserializer(/* EmployeeType type */) {
//		this.type = type;
		this.gson = new Gson();
//		for (int i = 0; i < Config.BUCKETS; i++) {
//			buckets[i] = new ArrayList<EmployeeInfo>();
//		}
	}

	@Override
	public EmployeeInfo deserialize(JsonElement json, Type type, JsonDeserializationContext cont)
			throws JsonParseException {
		JsonObject obj = json.getAsJsonObject();
		JsonElement element = obj.get("type");

		Class<? extends EmployeeInfo> ET;
		String s = element.getAsString();
		if (s.contains(EmployeeType.FULLTIME.toString()))
			ET = FullTimeEmployee.class;
		else if (s.contains(EmployeeType.PARTTIME.toString()))
			ET = PartTimeEmployee.class;
		else {
//			System.err.println("YOU HAVE SOME PROBLEMS WITH THIS...");
			System.out.println("EmployeeDeserializer: WAS ASKED TO DESERIALIZE " + element);
			ET = EmployeeInfo.class;
		}

		return gson.fromJson(obj, ET);
	}

}
