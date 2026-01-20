package io.github.vishalmysore.service;

import com.t4a.annotations.Action;
import com.t4a.annotations.Agent;
import com.t4a.processor.ProcessorAware;
import io.github.vishalmysore.a2ui.A2UIAware;
import io.github.vishalmysore.ucp.domain.discovery.UCPAware;

import java.util.Map;
import org.springframework.stereotype.Service;

@Service
@Agent(groupName = "compareCar", groupDescription = "compare 2 cars", prompt = "you are a car comparison assistant. Provide users with detailed comparisons between two car models based on their features, performance, and specifications.")
public class ShoppingService implements A2UIAware, ProcessorAware, UCPAware {
    @Override
    @Action(description = "Create a new checkout session")
    public Object createCheckout(Map<String, Object> checkoutRequest) {
        return null;
    }

    @Override
    @Action(description =" Retrieve checkout details by ID")
    public Object getCheckout(String checkoutId) {
        return null;
    }

    @Override
    @Action(description =" Update checkout information")
    public Object updateCheckout(String checkoutId, Map<String, Object> checkoutUpdate) {
        return null;
    }

    @Override
    @Action(description =" Complete the checkout process with payment")
    public Object completeCheckout(String checkoutId, Map<String, Object> paymentDetails) {
        return null;
    }

    @Override
    @Action(description =" Cancel an existing checkout session")
    public Object cancelCheckout(String checkoutId) {
        return null;
    }

    @Override
    @Action(description =" Link user identity via OAuth")
    public Object linkIdentity(Map<String, Object> oauthRequest) {
        return null;
    }

    @Override
    @Action(description =" Retrieve order details by ID")
    public Object getOrder(String orderId) {
        return null;
    }
}
