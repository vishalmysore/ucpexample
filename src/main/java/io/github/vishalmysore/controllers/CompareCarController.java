package io.github.vishalmysore.controllers;

import com.t4a.annotations.Action;
import com.t4a.annotations.Agent;
import com.t4a.processor.ProcessorAware;
import io.github.vishalmysore.a2ui.A2UIAware;

import io.github.vishalmysore.ucp.annotation.UCPBusiness;
import io.github.vishalmysore.ucp.annotation.UCPCapability;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * UCP Business for comparing cars
 * Only one agent can have tag ucp business in a spring boot application
 * UCP Capabilities define the individual capabilities provided by this business they can rest controllers or services
 * if its rest controller then the ucp manifest should add transport as rest else its rpc
 */
@Service
@Agent(groupName = "compareCar", groupDescription = "compare 2 cars", prompt = "you are a car comparison assistant. Provide users with detailed comparisons between two car models based on their features, performance, and specifications.")
@UCPBusiness(name = "AutoGroup North", version = "2026-01-19")
@RestController("compareCarController")
@Slf4j
public class CompareCarController implements A2UIAware, ProcessorAware {

    /**
     * Each action has access to AIProcessor and ActionCallback which are autowired
     * by tools4ai
     */

    public CompareCarController() {
        log.info("created car service");
    }

    @Action(description = "compare 2 cars")
    @UCPCapability(name = "io.github.vishalmysore.car_comparison", version = "2026-01-19", spec = "https://autogroup-north.com/specs/car-comparison", schema = "https://autogroup-north.com/schemas/comparison.json")
    @PostMapping("/compareCar")
    public Object compareCar(String car1, String car2) {
        log.info("Comparing cars: {} vs {}", car1, car2);

        // Simple comparison logic
        String betterCar;
        if (car1.toLowerCase().contains("toyota")) {
            betterCar = car1;
        } else if (car2.toLowerCase().contains("toyota")) {
            betterCar = car2;
        } else {
            betterCar = car1;
        }
        if (isUICallback(getCallback())) {
            String result = betterCar + " is better than " + (betterCar.equals(car1) ? car2 : car1);
            return createComparisonUI(car1, car2, betterCar, result);
        } else {
            return betterCar + " is better than " + (betterCar.equals(car1) ? car2 : car1);
        }
    }

    @Action(description = "Check real-time stock for a specific model")
    @UCPCapability(name = "io.github.vishalmysore.inventory_search", version = "2026-01-19", spec = "https://autogroup-north.com/specs/inventory-search", schema = "https://autogroup-north.com/schemas/inventory_search.json")
    @PostMapping("/checkStock")
    public Object checkStock(String model) {
        log.info("Checking stock for model: {}", model);
        // Simulate stock check
        String stockStatus = "In Stock"; // This would typically come from a database or API

        if (isUICallback(getCallback())) {
            return createStockUI(model, stockStatus);
        } else {
            return "The model " + model + " is currently: " + stockStatus;
        }
    }

    private Object createStockUI(String model, String stockStatus) {
        String surfaceId = "stock_check";
        String rootId = "root";

        // Define child component IDs
        List<String> childIds = Arrays.asList("title", "model_display", "stock_status");

        // Build components list
        List<Map<String, Object>> components = new ArrayList<>();

        // Add root column
        components.add(createRootColumn(rootId, childIds));

        // Add title
        components.add(createTextComponent("title", "Stock Check", "h2"));

        // Add model display
        components.add(createTextComponent("model_display", "Model: " + model));

        // Add stock status
        components.add(createTextComponent("stock_status", "Stock Status: " + stockStatus, "body"));

        // Build complete A2UI message
        return buildA2UIMessage(surfaceId, rootId, components);
    }

    private Map<String, Object> createComparisonUI(String car1, String car2, String winner, String result) {
        String surfaceId = "car_comparison";
        String rootId = "root";

        // Define child component IDs
        List<String> childIds = Arrays.asList("title", "car1_display", "car2_display", "result");

        // Build components list
        List<Map<String, Object>> components = new ArrayList<>();

        // Add root column
        components.add(createRootColumn(rootId, childIds));

        // Add title
        components.add(createTextComponent("title", "Car Comparison", "h2"));

        // Add car displays with winner trophy
        String car1Text = "Car 1: " + car1 + (car1.equals(winner) ? " 🏆" : "");
        components.add(createTextComponent("car1_display", car1Text));

        String car2Text = "Car 2: " + car2 + (car2.equals(winner) ? " 🏆" : "");
        components.add(createTextComponent("car2_display", car2Text));

        // Add result
        components.add(createTextComponent("result", result, "body"));

        // Build complete A2UI message
        return buildA2UIMessage(surfaceId, rootId, components);
    }
}
