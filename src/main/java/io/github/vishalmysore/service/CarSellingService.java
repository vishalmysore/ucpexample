package io.github.vishalmysore.service;

import com.t4a.annotations.Action;
import com.t4a.annotations.Agent;
import com.t4a.processor.ProcessorAware;
import io.github.vishalmysore.a2ui.A2UIAware;


/**
 * UCP Business for comparing cars
 * Only one agent can have tag ucp business in a spring boot application
 * UCP Capabilities define the individual capabilities provided by this business
 * they can only be in rest controllers
 * As ucp mandates that business should support rest as well s mcp we need to make sure
 * all ucp business are rest only controllers

 * this is a sample service for car booking which will use json mcp as transport and only mcp protocol is supported
 * this is not ucp business or ucp capability if you try to add capability here it will throw error during startup
 */
@Agent(groupName = "carbooking", groupDescription = "car booking service", prompt = "You are a car booking assistant. Help users book cars based on their preferences and requirements.")
public class CarSellingService implements ProcessorAware, A2UIAware {

    /**
     * This is agentic action and is exposed only as MCP and A2A , note that it is not exposed as ucp business or capablity
     *
     * @param carType
     * @param pickupLocation
     * @param dropoffLocation
     * @param pickupDate
     * @param dropoffDate
     * @return
     */
    @Action(description = "Sell a car based on user preferences")
   //@UCPCapability(name = "io.github.vishalmysore.sell_car", version = "2026-01-19", spec = "https://autogroup-north.com/specs/inventory-search", schema = "https://autogroup-north.com/schemas/inventory_search.json")
    //if you uncomment above line and try to add ucp capablity it will throw error , UCP Violation: Agent CarSellingService must be annotated with @RestController to support REST transport.
    public Object sellCar(String carType, String pickupLocation, String dropoffLocation, String pickupDate,
                          String dropoffDate) {
        // Booking logic here
        return "Car sold: " + carType + " from " + pickupLocation + " to " + dropoffLocation +
                " between " + pickupDate + " and " + dropoffDate;
    }
}
