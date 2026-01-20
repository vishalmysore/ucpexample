package io.github.vishalmysore.controllers;

import com.t4a.annotations.Action;
import com.t4a.annotations.Agent;
import com.t4a.processor.ProcessorAware;
import io.github.vishalmysore.a2ui.A2UIAware;
import io.github.vishalmysore.ucp.annotation.UCPCapability;
import io.github.vishalmysore.ucp.domain.SimpleUCPResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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
@RestController(value = "/carbooking")
public class CarbookingController implements ProcessorAware, A2UIAware {

    @Action(description = "Book a car based on user preferences")
    @UCPCapability(name = "io.github.vishalmysore.car_booking", version = "2026-01-19", spec = "https://autogroup-north.com/specs/car-booking", schema = "https://autogroup-north.com/schemas/booking.json")
    @PostMapping("/bookCar")
    public SimpleUCPResult bookCar(String carType, String pickupLocation, String dropoffLocation, String pickupDate,
                                   String dropoffDate) {
        // Booking logic here
        Map<String, String> bookingDetails = new HashMap<>();
        bookingDetails.put("carType", carType);
        bookingDetails.put("pickupLocation", pickupLocation);
        bookingDetails.put("dropoffLocation", dropoffLocation);
        bookingDetails.put("pickupDate", pickupDate);
        bookingDetails.put("dropoffDate", dropoffDate);
        bookingDetails.put("confirmationNumber", "ABC123XYZ");
        return new SimpleUCPResult(bookingDetails);
    }
}
