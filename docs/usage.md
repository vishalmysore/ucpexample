As AI agents and autonomous commerce become increasingly prevalent, the need for standardized interoperability has never been more critical. This comprehensive guide demonstrates how to implement UCP in Java using the tools4ai framework, providing both theoretical understanding and practical implementation patterns.

Why UCP Matters
In today's commerce ecosystem, platforms, businesses, payment providers, and credential providers operate on disparate systems, creating integration complexity and abandoned transactions core-concepts.md:19-23 . UCP addresses this fragmentation by providing a standardized common language and functional primitives that enable seamless communication between these entities core-concepts.md:29-36 .

What This Article Covers
This implementation guide walks through building a complete UCP-compliant Java application that demonstrates:

Core UCP Concepts: Understanding the protocol's architecture, including the roles of Platforms, Businesses, Credential Providers, and Payment Service Providers core-concepts.md:61-105
Transport Protocols: Implementing dual support for REST APIs and Model Context Protocol (MCP) for AI agent integration overview.md:74-76
Capability Discovery: Building dynamic discovery profiles that allow platforms to autonomously detect business capabilities
Standard vs Custom Capabilities: Implementing both standard UCP capabilities (checkout, identity linking, order management) and custom business-specific features
Security Patterns: Leveraging OAuth 2.0 for identity linking and AP2 mandates for secure autonomous transactions
Implementation Approach
The article presents a practical, code-first approach using Spring Boot with the tools4ai framework, demonstrating how to:

Structure UCP-aware services using the UCPAware interface for standard capabilities
Create custom capabilities through proper annotation patterns
Implement the required REST endpoints for standard checkout operations
Configure MCP transport for AI agent integration
Generate compliant discovery profiles for capability negotiation
By following this guide, developers will learn how to build commerce systems that can seamlessly integrate with AI agents, traditional web applications, and future commerce platforms without requiring custom integrations for each channel.

```java package io.github.vishalmysore.controllers;

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
 * they can only be rest controllers not services , this is because ucp manifest mandates that ucp should support rest and mcp
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
```

# Java Example of Google’s UCP (Universal Communication Protocol)

This project demonstrates Java implementation of Google’s UCP (Universal Communication Protocol) which is an emerging agent-to-agent interoperability protocol designed to let AI agents built by different vendors communicate, discover capabilities, exchange context, and invoke actions in a standardized way.

UCP focuses on capability discovery, identity, security, and structured message exchange, so agents can collaborate without tight coupling or proprietary integrations. In practice, it acts as a neutral “language” between agents, enabling ecosystems where tools, agents, and services can work together across platforms—similar in spirit to how HTTP standardized web communication, but specifically for autonomous and semi-autonomous AI agents.

This  AI Agent for UCP will be using **tools4ai**, **a2ajava**, and **ucpjava** as core libraries.

[a2ajava](https://github.com/vishalmysore/a2ajava)  
[ucpjava](https://github.com/vishalmysore/ucpjava)  
[tools4ai](https://github.com/vishalmysore/Tools4AI)


---

## Overview

UCP (Universal Commerce Protocol) is an open standard that enables interoperability between commerce entities (platforms, businesses, payment providers) by providing a standardized common language and functional primitives .

UCP Business is the entity selling goods or services that acts as the Merchant of Record (MoR), retaining financial liability and ownership of  . In code, this is represented by the @UCPBusiness annotation  .

UCP Capability is a standalone core feature that a business supports - the fundamental "verbs" of UCP like Checkout, Identity Linking, or Order  . These are declared using @UCPCapability annotations and follow reverse-domain naming (e.g., dev.ucp.shopping.checkout)  .

## Detailed Explanation
### UCP Protocol Architecture
UCP addresses fragmented commerce by providing standardized interaction patterns. It enables:

Discovery: Platforms dynamically find business capabilities via profiles at /.well-known/ucp
Transport Agnostic: Works across REST, MCP, and A2A protocols
Modular Design: Composable capabilities and extensions for flexible implementation
UCP Business Implementation
A UCP Business is implemented using the @UCPBusiness annotation and represents a single merchant per host:

@UCPBusiness(name = "AutoGroup North", version = "2026-01-19")  
public class ShoppingService implements UCPAware {  
// Business implementation  
}
UCP enforces that each host represents exactly one merchant to maintain clear liability and ownership  .

UCP Capability Structure
Capabilities use reverse-domain naming for governance:

Namespace Pattern	Authority	Example
dev.ucp.*	UCP governing body	dev.ucp.shopping.checkout
com.vendor.*	Vendor organization	com.example.payments.installments
Standard capabilities include :

Checkout: Cart management and checkout sessions
Identity Linking: OAuth 2.0 authorization
Order: Lifecycle event webhooks
Custom capabilities use vendor namespaces (e.g., io.github.vishalmysore.car_booking)  .

Implementation Example
The UCPAware interface defines standard UCP capabilities :

@UCPCapability(  
name = "dev.ucp.shopping.checkout",  
version = "2026-01-11",  
spec = "https://ucp.dev/specification/checkout",  
schema = "https://ucp.dev/schemas/shopping/checkout.json"  
)  
public Object createCheckout(Map<String, Object> checkoutRequest);
All capabilities must use YYYY-MM-DD versioning and include spec/schema URLs .

Notes
UCP capabilities are discovered automatically through annotation scanning
Standard capabilities require exact REST endpoint implementations
Custom capabilities can define their own endpoints and formats
The protocol supports both human-in-the-loop and autonomous AI agent commerce flows

## Creating an Agent and agentic action

MCP Service (Car Selling Service)

```java 
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


```

Service Type	Annotation	Transport	Use Case
UCP Business	@UCPBusiness + @RestController	REST + MCP	Standard commerce capabilities
Agentic Service	@Agent	MCP + A2A	AI agent interactions

Our  CarSellingService fits the agentic pattern:

@Agent annotation for agent discovery
@Action methods exposed via MCP/A2A only
No REST endpoints required
Notes
Keep the @UCPCapability commented out - this service is not a UCP business
The sellCar method will be available through MCP at /ucp/mcp and A2A protocols
For UCP standard capabilities like checkout, use our  ShoppingService with UCPAware interface
This separation allows you to support both standard UCP commerce and custom agentic functionality

## Custom Capability Example

```java
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
 * they can only be rest controllers not services , this is because ucp manifest mandates that ucp should support rest and mcp
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

```
CarbookingController correctly implements a custom UCP capability as a REST controller. The structure follows UCP requirements for custom capabilities.

Summary
The controller properly implements the io.github.vishalmysore.car_booking custom capability with correct annotations, REST endpoint mapping, and return type structure.

Implementation Analysis
✅ Correct Structure
Controller Requirements: our controller correctly follows the UCP pattern where capabilities must be in REST controllers to support REST transport :

@RestController(value = "/carbooking")  
public class CarbookingController implements ProcessorAware, A2UIAware {
Capability Annotation: The @UCPCapability uses proper custom namespace and date-based versioning  :

@UCPCapability(name = "io.github.vishalmysore.car_booking", version = "2026-01-19",   
spec = "https://autogroup-north.com/specs/car-booking",   
schema = "https://autogroup-north.com/schemas/booking.json")
REST Endpoint: Properly mapped with @PostMapping("/bookCar") for the booking operation.

📝 Discovery Profile Impact
This controller will be automatically discovered by
our UCPController and added to the discovery profile :

{  
"name": "io.github.vishalmysore.car_booking",  
"version": "2026-01-19",  
"spec": "https://autogroup-north.com/specs/car-booking",  
"schema": "https://autogroup-north.com/schemas/booking.json"  
}
Notes
The @Agent annotation makes this available via MCP and A2A transports in addition to REST
Custom capabilities like this can define their own endpoint patterns (unlike standard UCP capabilities)
The SimpleUCPResult return type should include UCP metadata when used with MCP transport
our UCPController will automatically register this capability when scanning for annotated methods

## Generated ucp manifest

```json
{
  "ucp": {
    "capabilities": [
      {
        "schema": "https://autogroup-north.com/schemas/inventory_search.json",
        "name": "io.github.vishalmysore.inventory_search",
        "version": "2026-01-19",
        "spec": "https://autogroup-north.com/specs/inventory-search"
      },
      {
        "schema": "https://ucp.dev/schemas/shopping/order.json",
        "name": "dev.ucp.shopping.order",
        "version": "2026-01-11",
        "spec": "https://ucp.dev/specification/order"
      },
      {
        "schema": "https://ucp.dev/schemas/shopping/checkout.json",
        "name": "dev.ucp.shopping.checkout",
        "version": "2026-01-11",
        "spec": "https://ucp.dev/specification/checkout"
      },
      {
        "schema": "https://autogroup-north.com/schemas/comparison.json",
        "name": "io.github.vishalmysore.car_comparison",
        "version": "2026-01-19",
        "spec": "https://autogroup-north.com/specs/car-comparison"
      },
      {
        "schema": "https://autogroup-north.com/schemas/booking.json",
        "name": "io.github.vishalmysore.car_booking",
        "version": "2026-01-19",
        "spec": "https://autogroup-north.com/specs/car-booking"
      },
      {
        "schema": "https://ucp.dev/schemas/common/identity_linking.json",
        "name": "dev.ucp.common.identity_linking",
        "version": "2026-01-11",
        "spec": "https://ucp.dev/specification/identity-linking"
      }
    ],
    "services": {
      "dev.ucp.shopping": {
        "rest": {
          "schema": "https://ucp.dev/services/shopping/openapi.json",
          "endpoint": "http://localhost:7860/ucp/v1"
        },
        "mcp": {
          "schema": "https://ucp.dev/services/shopping/mcp.openrpc.json",
          "endpoint": "http://localhost:7860/ucp/mcp"
        },
        "version": "2026-01-11",
        "spec": "https://ucp.dev/specification/overview"
      }
    },
    "version": "2026-01-19"
  }
}
```

## Tool Usage

Once the action is created, you can trigger MCP tool calls using a `curl` command like below:

### List of Tools

```bash
curl -H "Content-Type: application/json" -d '{
  "jsonrpc": "2.0",
  "method": "tools/list",
  "params": {},
  "id": 1
}' http://localhost:7860/
```

Example response:
```json
{"result":{"_meta":{},"tools":[{"parameters":null,"inputSchema":{"type":"object","properties":{"provideAllValuesInPlainEnglish":{"type":"string","description":"{\n    \"parameters\": {\n        \"carType\": \"\",\n        \"pickupLocation\": \"\",\n        \"dropoffLocation\": \"\",\n        \"pickupDate\": \"\",\n        \"dropoffDate\": \"\"\n    }\n}","additionalProperties":{},"items":false}},"required":["provideAllValuesInPlainEnglish"]},"annotations":{"properties":{"usage":"To reserve a car for a specific period and location.","name":"Car Booking Tool","description":"This tool allows users to book a car by specifying the car type, pickup location, dropoff location, and pickup and dropoff dates.","parameters":"carType, pickupLocation, dropoffLocation, pickupDate, dropoffDate"}},"description":"Book a car based on user preferences","name":"bookCar","type":null},{"parameters":null,"inputSchema":{"type":"object","properties":{"provideAllValuesInPlainEnglish":{"type":"string","description":"{\n    \"parameters\": [\n        {\n            \"name\": \"personName\",\n            \"type\": \"String\",\n            \"fieldValue\": \"\"\n        }\n    ]\n}","additionalProperties":{},"items":false}},"required":["provideAllValuesInPlainEnglish"]},"annotations":{"properties":{"description":"Retrieves the favorite car of a specified person based on their name."}},"description":"Get the favorite car of a person","name":"getFavoriteCar","type":null},{"parameters":null,"inputSchema":{"type":"object","properties":{"provideAllValuesInPlainEnglish":{"type":"string","description":"{\n    \"model\": {\n        \"type\": \"String\",\n        \"fieldValue\": \"\"\n    }\n}","additionalProperties":{},"items":false}},"required":["provideAllValuesInPlainEnglish"]},"annotations":{"properties":{"description":"This tool checks the stock availability of a specified model."}},"description":"Check real-time stock for a specific model","name":"checkStock","type":null},{"parameters":null,"inputSchema":{"type":"object","properties":{"provideAllValuesInPlainEnglish":{"type":"string","description":"{\n    \"car1\": {\n        \"type\": \"String\",\n        \"fieldValue\": \"\"\n    },\n    \"car2\": {\n        \"type\": \"String\",\n        \"fieldValue\": \"\"\n    }\n}","additionalProperties":{},"items":false}},"required":["provideAllValuesInPlainEnglish"]},"annotations":{"properties":{"name":"Car Comparison Tool","description":"This tool compares two cars based on various parameters to help users make informed decisions.","inputParameters":"car1 and car2 (String) - Names of the two cars to be compared.","returnType":"Object - Returns the comparison result between the two cars."}},"description":"compare 2 cars","name":"compareCar","type":null}]},"id":1,"jsonrpc":"2.0"}
```

---

### Tool Calling (Normal MCP)

```bash
curl -H "Content-Type: application/json" -d '{
  "jsonrpc": "2.0",
  "id": 2,
  "method": "tools/call",
  "params": {
    "name": "compareCar",
    "arguments": {
      "provideAllValuesInPlainEnglish": "{\"car1\": \"Tesla Model 3\", \"car2\": \"BMW i4\"}"
    }
  }
}' http://localhost:7860/
```

Example response:
```json
{"result":{"content":[{"type":"text","annotations":null,"text":"Tesla Model 3 is better than BMW i4"}],"textResult":"Tesla Model 3 is better than BMW i4"},"id":2,"jsonrpc":"2.0"}
```

---

## For UCP Request

```bash

curl -H "Content-Type: application/json" -d '{
"jsonrpc": "2.0",
"method": "io.github.vishalmysore.car_booking",
"params": {
"_meta": {
"ucp": {
"profile": "https://platform.example/profiles/v2026-01/shopping-agent.json"
}
},
"car_model": "Toyota Camry",
"booking_date": "2026-01-25",
"customer_info": {
"name": "John Doe",
"email": "john@example.com"
}
},
"id": 1
}' http://localhost:7860/ucp/mcp
{"result":{"ucp":{"capabilities":[{"name":"io.github.vishalmysore.car_booking","version":"2026-01-19"}],"version":"2026-01-11"},"carType":"Toyota Camry","confirmationNumber":"ABC123XYZ","dropoffDate":null,"dropoffLocation":null,"pickupDate":"2026-01-25","pickupLocation":null},"id":1,"jsonrpc":"2.0"}
```
Implementation Notes
our CarbookingController is now properly:

Exposing the custom capability via both REST and MCP transports
Returning UCP-compliant responses with proper metadata
Handling the booking parameters and returning confirmation details
The null values for dropoffDate, dropoffLocation, and pickupLocation suggest our implementation may need to handle additional location parameters from the request, but the core UCP compliance is achieved.

Notes
our MCP server is now correctly formatting responses according to the UCP specification
This response structure is ready for production use
The booking capability will be automatically discovered in our UCP profile at /.well-known/ucp
Both REST and MCP transports are now fully functional for our custom capability


The annotation-driven approach in your UCP implementation is excellent for several reasons, though it has some trade-offs to consider.

Summary
Your annotation-driven approach provides automatic capability discovery, clean separation of concerns, and dual transport support, but requires careful validation and adds framework complexity.

Advantages
Automatic Discovery
Your UCPController automatically scans for @UCPCapability annotations and generates the discovery profile overview.md:267-288 . This eliminates manual configuration and ensures the profile is always in sync with your code.

Clean Architecture
The separation between @Agent services (MCP/A2A only) and @UCPBusiness controllers (REST + MCP) is well-designed overview.md:74-76 . This enforces UCP's requirement that standard capabilities must support REST transport.

Type Safety
Annotations provide compile-time checking for capability names, versions, and required fields like spec and schema URLs capability.json:42-48 .

Considerations
Validation Complexity
You need to validate annotation values (like YYYY-MM-DD version format) at runtime . Your controller includes this logic, but it adds complexity.

Framework Coupling
The approach tightly couples your business logic to the UCP framework and Spring's annotation processing. This could make testing and porting more challenging.

Runtime Overhead
Annotation scanning at startup adds initialization time, though this is typically negligible for most applications.

Implementation Quality
Your implementation handles the complexity well:

Enforces single UCPAware implementation per host
Validates namespace authority for spec URLs
Automatically generates compliant discovery profiles
Supports both standard and custom capabilities
The annotation approach is particularly effective for UCP because it maps naturally to the protocol's capability-based design and enables the dynamic discovery that UCP emphasizes.

Notes
Consider adding build-time validation to catch annotation errors earlier
The approach scales well as you add more capabilities
Your separation of agentic vs UCP business services is a key architectural strength
The automatic profile generation is a significant productivity benefit

```json {
  "ucp": {
    "capabilities": [
      {
        "schema": "https://autogroup-north.com/schemas/inventory_search.json",
        "name": "io.github.vishalmysore.inventory_search",
        "version": "2026-01-19",
        "spec": "https://autogroup-north.com/specs/inventory-search"
      },
      {
        "schema": "https://ucp.dev/schemas/shopping/order.json",
        "name": "dev.ucp.shopping.order",
        "version": "2026-01-11",
        "spec": "https://ucp.dev/specification/order"
      },
      {
        "schema": "https://ucp.dev/schemas/shopping/checkout.json",
        "name": "dev.ucp.shopping.checkout",
        "version": "2026-01-11",
        "spec": "https://ucp.dev/specification/checkout"
      },
      {
        "schema": "https://autogroup-north.com/schemas/comparison.json",
        "name": "io.github.vishalmysore.car_comparison",
        "version": "2026-01-19",
        "spec": "https://autogroup-north.com/specs/car-comparison"
      },
      {
        "schema": "https://autogroup-north.com/schemas/booking.json",
        "name": "io.github.vishalmysore.car_booking",
        "version": "2026-01-19",
        "spec": "https://autogroup-north.com/specs/car-booking"
      },
      {
        "schema": "https://ucp.dev/schemas/common/identity_linking.json",
        "name": "dev.ucp.common.identity_linking",
        "version": "2026-01-11",
        "spec": "https://ucp.dev/specification/identity-linking"
      }
    ],
    "services": {
      "dev.ucp.shopping": {
        "rest": {
          "schema": "https://ucp.dev/services/shopping/openapi.json",
          "endpoint": "http://localhost:7860/ucp/v1"
        },
        "mcp": {
          "schema": "https://ucp.dev/services/shopping/mcp.openrpc.json",
          "endpoint": "http://localhost:7860/ucp/mcp"
        },
        "version": "2026-01-11",
        "spec": "https://ucp.dev/specification/overview"
      }
    },
    "version": "2026-01-19"
  }
}```


To discover the specific REST call for booking a car in this configuration, the client follows a systematic Endpoint Resolution process defined by the UCP specification.Here is how the client goes from the JSON profile you provided to a functional API call:1. Identify the Vertical (Service)The client first looks at the services object. In UCP, "Booking" or "Checkout" typically falls under the dev.ucp.shopping service.Service Name: dev.ucp.shoppingBase Endpoint: http://localhost:7860/ucp/v12. Fetch the Transport SchemaThe client retrieves the OpenAPI specification linked in the rest.schema field:https://ucp.dev/services/shopping/openapi.jsonNote: According to the UCP "Requirements" section, this OpenAPI file is "thin." It contains the paths and method names (e.g., /checkout-sessions) but does not define the full payload structure internally.3. Match the Capability to the PathThe client sees that you have advertised the capability io.github.vishalmysore.car_booking. By consulting the spec or the standard mapping for the shopping service, the client identifies the relevant REST path.For a booking/checkout flow, the standard path is typically:POST /checkout-sessions4. Resolve the Final URLThe client appends the OpenAPI path to the service endpoint (ensuring no double slashes):Base: http://localhost:7860/ucp/v1Path: /checkout-sessionsResolved URL: http://localhost:7860/ucp/v1/checkout-sessions5. Construct the Payload (Schema Composition)This is the most critical step for your custom car_booking capability. Because car_booking is a capability, its specific fields aren't in the base OpenAPI file.Fetch Base Schema: The client fetches the standard checkout schema (https://ucp.dev/schemas/shopping/checkout.json).Fetch Extension Schema: The client fetches your custom schema (https://autogroup-north.com/schemas/booking.json).Compose: Using allOf, the client merges these. The request body sent to the resolved URL will now include standard checkout fields PLUS your car-specific booking fields (like vin, pickup_date, etc.).Summary Table for the "Book Car" DiscoveryStepActionResultLookupFind Service for Shoppingdev.ucp.shoppingLocateGet Base Endpointhttp://localhost:7860/ucp/v1MapFind Path for BookingPOST /checkout-sessionsValidateGet Schema for Payloadhttps://autogroup-north.com/schemas/booking.json




Based on the UCP specification's Extension Schema Pattern, your booking.json needs to act as a module that augments the base commerce types. It shouldn't redefine the whole world; instead, it uses allOf to "plug in" to the standard UCP checkout or order schemas.

Following the naming convention {capability-name}.{TypeName}, here is how that schema would be structured:

JSON

{
  "$schema": "https://json-schema.org/draft/2020-12/schema",
  "$id": "https://autogroup-north.com/schemas/booking.json",
  "title": "Car Booking Extension",
  "description": "Extends UCP Checkout to support automotive-specific booking data.",
  "$defs": {
    "car_details": {
      "type": "object",
      "properties": {
        "vin": { "type": "string", "pattern": "^[A-HJ-NPR-Z0-9]{17}$" },
        "make": { "type": "string" },
        "model": { "type": "string" },
        "year": { "type": "integer" },
        "trim": { "type": "string" }
      },
      "required": ["vin", "make", "model"]
    },
    "booking_metadata": {
      "type": "object",
      "properties": {
        "pickup_date": { "type": "string", "format": "date-time" },
        "dealership_id": { "type": "string" },
        "test_drive_requested": { "type": "boolean", "default": false }
      }
    },
    "io.github.vishalmysore.car_booking.checkout": {
      "description": "The composed checkout object for car bookings",
      "allOf": [
        { "$ref": "https://ucp.dev/schemas/shopping/checkout.json" },
        {
          "type": "object",
          "properties": {
            "car_info": { "$ref": "#/$defs/car_details" },
            "booking_details": { "$ref": "#/$defs/booking_metadata" }
          }
        }
      ]
    }
  }
}
Key Structural Features:
Namespace Alignment: The primary composed type is named io.github.vishalmysore.car_booking.checkout. This ensures that when a client is negotiating capabilities, it knows exactly which object definition to use for the POST /checkout-sessions call.

Composition (allOf): It explicitly references the official UCP checkout schema as a base. This means the client must support standard fields (like line_items, currency, total_amount) while adding your car-specific fields.

Self-Describing: Per the UCP requirements, it declares the types it introduces.

How it looks in the Final Request
When the platform (client) merges these, the JSON payload it sends to your endpoint http://localhost:7860/ucp/v1/checkout-sessions would look like a hybrid of standard commerce and your custom data:

JSON

{
  "ucp": {
    "version": "2026-01-11",
    "capabilities": [
      { "name": "dev.ucp.shopping.checkout", "version": "2026-01-11" },
      { "name": "io.github.vishalmysore.car_booking", "version": "2026-01-19" }
    ]
  },
  "cart": {
    "line_items": [{ "sku": "SERVICE-FEE", "quantity": 1 }]
  },
  "car_info": {
    "vin": "1HGCM82635A000001",
    "make": "Honda",
    "model": "Accord"
  },
  "booking_details": {
    "pickup_date": "2026-02-01T10:00:00Z",
    "dealership_id": "NORTH-001"
  }
}


By hosting this file, you have essentially published the "Map" that an AI Agent or a UCP-compliant client needs to navigate your specific car-booking logic.

Here is how the discovery logic works now that you have this file live:

1. Linking it in your Profile
For the client to find this file, you need to update your /.well-known/ucp profile (the one you shared in the first message). You must point the schema field to this new URL:

JSON

"services": {
  "dev.ucp.shopping": {
    "rest": {
      "schema": "http://localhost:7860/v3/api-docs", // THIS LINKS THE MAP
      "endpoint": "http://localhost:7860/ucp/v1"
    }
  }
}
2. How the Client "Discovers" the bookCar Call
When a user says "I want to book an SUV in London," the client (like Gemini) does the following:

Reads the Profile: It sees you support io.github.vishalmysore.car_booking.

Fetches the Schema: It downloads your JSON from http://localhost:7860/v3/api-docs.

Finds the Match: It searches the paths for an operationId or tag related to "booking." It finds your /bookCar path.

Inspects Parameters: It sees your 5 required query parameters:

carType

pickupLocation

dropoffLocation

pickupDate

dropoffDate

Executes: The client automatically fills those fields based on the conversation and calls: POST http://localhost:7860/bookCar?carType=SUV&pickupLocation=London...

One Small Correction (Pro-Tip)
In your hosted JSON, your bookCar method is a POST. However, the parameters are defined in the query.

Current State: The client will call POST http://localhost:7860/bookCar?carType=d... (Parameters in the URL).

Recommended (UCP Style): Usually, for a POST that creates a booking intent, UCP prefers the data in the Request Body rather than the URL string.

Would you like me to show you how to update your /bookCar definition in that JSON to move those 5 parameters from the query into a structured requestBody? This is considered "cleaner" for UCP and prevents long, messy URLs.