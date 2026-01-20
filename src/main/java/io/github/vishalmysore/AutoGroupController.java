package io.github.vishalmysore;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController("superautos/v1/ucp")
public class AutoGroupController {


    @GetMapping("/schemas/comparison")
    public Map<String, Object> getComparisonSchema() {
        Map<String, Object> car1 = new HashMap<>();
        car1.put("type", "string");
        car1.put("description", "First car to compare");

        Map<String, Object> car2 = new HashMap<>();
        car2.put("type", "string");
        car2.put("description", "Second car to compare");

        Map<String, Object> properties = new HashMap<>();
        properties.put("car1", car1);
        properties.put("car2", car2);

        Map<String, Object> schema = new HashMap<>();
        schema.put("type", "object");
        schema.put("properties", properties);
        schema.put("required", Arrays.asList("car1", "car2"));

        return schema;
    }
}
