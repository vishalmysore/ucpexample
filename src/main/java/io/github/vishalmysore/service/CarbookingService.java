package io.github.vishalmysore.service;

import com.t4a.annotations.Action;
import com.t4a.annotations.Agent;
import com.t4a.processor.ProcessorAware;
import io.github.vishalmysore.a2ui.A2UIAware;

import io.github.vishalmysore.ucp.annotation.UCPCapability;

/**
 * UCP Business for comparing cars
 * Only one agent can have tag ucp business in a spring boot application
 * UCP Capabilities define the individual capabilities provided by this business
 * they can rest controllers or services
 * if its rest controller then the ucp manifest should add transport as rest
 * else its rpc
 * this is a sample service for car booking which will use json rpc as transport
 */
@Agent(groupName = "carbooking", groupDescription = "car booking service", prompt = "You are a car booking assistant. Help users book cars based on their preferences and requirements.")
public class CarbookingService implements ProcessorAware, A2UIAware {

    @Action(description = "Book a car based on user preferences")
    public Object bookCar(String carType, String pickupLocation, String dropoffLocation, String pickupDate,
            String dropoffDate) {
        // Booking logic here
        return "Car booked: " + carType + " from " + pickupLocation + " to " + dropoffLocation +
                " between " + pickupDate + " and " + dropoffDate;
    }
}
