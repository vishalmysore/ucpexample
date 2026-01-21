# Universal Commerce Protocol (UCP) in Java: Travel Booking Implementation
The Universal Commerce Protocol (UCP) provides a standardized framework that enables seamless interoperability between AI agents, businesses, and payment platforms in today's fragmented e-commerce landscape  . Implementing UCP in Java is strategically crucial for enterprises because Java's robust ecosystem, Spring Boot framework, and enterprise-grade security features make it the ideal choice for building scalable, secure commerce systems that can handle complex transactions while maintaining compliance with industry standards like PCI-DSS and GDPR . Java's mature ecosystem provides the necessary tools for implementing UCP's transport-agnostic architecture, supporting REST APIs, Model Context Protocol (MCP), and Agent-to-Agent (A2A) communications, while its strong typing and comprehensive libraries ensure reliable handling of sensitive payment data and complex business logic across multiple commerce verticals .

Why Java is Essential for UCP Implementation
Java's enterprise features make it uniquely positioned for UCP implementations:

Enterprise-Grade Security
Java's proven security frameworks and built-in features provide the foundation for handling sensitive commerce data and payment credentials securely  .

Multi-Transport Support
Java's extensive HTTP client libraries and JSON processing capabilities enable seamless implementation of UCP's transport-agnostic design, supporting REST, MCP, and A2A protocols simultaneously  .

Scalability and Performance
Java's mature ecosystem and Spring Boot framework provide the infrastructure needed to handle high-volume commerce transactions while maintaining the reliability required for production systems  .

Annotation-Driven Development
The annotation-driven approach in Java implementations simplifies UCP capability discovery and automatic profile generation, reducing development complexity while ensuring compliance with the specification
Complete Guide to Building UCP-Enabled Travel Services with AI Agent Integration

Lets talk about use case of travel booking systems.
As the travel industry embraces digital transformation and AI-powered assistants, the need for standardized interoperability between booking platforms, airlines, hotels, and service providers has become critical. This comprehensive guide demonstrates how to implement UCP (Universal Commerce Protocol) in Java for travel booking systems, providing practical patterns for building connected travel ecosystems.

## What is UCP?

UCP (Universal Commerce Protocol) is an open standard that enables interoperability between commerce entities by providing a standardized common language and functional primitives  . It allows platforms, businesses, payment providers, and credential providers to communicate effectively, ensuring secure and consistent commerce experiences  .

For travel booking, UCP addresses the fragmentation between airlines, hotels, car rentals, and tour operators, enabling AI agents to seamlessly book complete travel experiences across multiple providers.

## Core Libraries

This travel booking implementation uses:

- tools4ai - Core framework for AI agent tools
- a2ajava - Agent-to-agent communication
- ucpjava - UCP protocol implementation

## Why UCP Matters for Travel

The travel ecosystem suffers from extreme fragmentation:

- Airlines use GDS systems (Amadeus, Sabre)
- Hotels have their own booking engines
- Car rental companies use proprietary APIs
- Tour operators have custom systems

UCP solves this by providing:

- Standardized Booking Flow: Common checkout process across all travel providers
- Dynamic Discovery: AI agents can discover available travel services automatically
- Secure Payments: Unified payment handling across different providers
- Composable Itineraries: Book flights, hotels, and activities as a single transaction

## UCP Business for Travel

```java
@UCPBusiness(name = "TravelHub", version = "2026-01-21")  
@Service  
public class TravelBookingService implements UCPAware {

    @Override  
    @UCPCapability(  
        name = "dev.ucp.shopping.checkout",  
        version = "2026-01-11",  
        spec = "https://ucp.dev/specification/checkout",  
        schema = "https://ucp.dev/schemas/shopping/checkout.json"  
    )  
    public Object createCheckout(Map<String, Object> request) {  
        // Create travel booking session  
        return createTravelBookingSession(request);  
    }  
      
    @Override  
    @UCPCapability(  
        name = "dev.ucp.shopping.order",  
        version = "2026-01-11",  
        spec = "https://ucp.dev/specification/order",  
        schema = "https://ucp.dev/schemas/shopping/order.json"  
    )  
    public Object getOrder(String orderId) {  
        // Retrieve travel itinerary details  
        return getTravelItinerary(orderId);  
    }  
      
    @Override  
    @UCPCapability(  
        name = "dev.ucp.common.identity_linking",  
        version = "2026-01-11",  
        spec = "https://ucp.dev/specification/identity-linking",  
        schema = "https://ucp.dev/schemas/common/identity_linking.json"  
    )  
    public Object linkIdentity(Map<String, Object> authRequest) {  
        // Link traveler loyalty programs  
        return linkTravelerAccounts(authRequest);  
    }  
}
```

## Custom Travel Capabilities

### Flight Search Controller

```java
@RestController(value = "/flights")  
@Agent(  
groupName = "travelSearch",  
groupDescription = "Travel search and booking services",  
prompt = "You are a travel booking assistant. Help users find and book flights, hotels, and activities."  
)  
public class FlightSearchController implements ProcessorAware, A2UIAware {

    @Action(description = "Search for flights based on origin, destination, and dates")  
    @UCPCapability(  
        name = "io.travelhub.flight_search",  
        version = "2026-01-21",  
        spec = "https://travelhub.com/specs/flight-search",  
        schema = "https://travelhub.com/schemas/flight_search.json"  
    )  
    @PostMapping("/searchFlights")  
    public SimpleUCPResult searchFlights(  
            String origin,  
            String destination,  
            String departureDate,  
            String returnDate,  
            Integer passengers) {  
          
        Map<String, Object> searchResults = new HashMap<>();  
        searchResults.put("origin", origin);  
        searchResults.put("destination", destination);  
        searchResults.put("departureDate", departureDate);  
        searchResults.put("returnDate", returnDate);  
        searchResults.put("passengers", passengers);  
          
        // Mock flight search results  
        List<Map<String, String>> flights = Arrays.asList(  
            Map.of("flightNumber", "TH123", "airline", "TravelHub Airlines",   
                   "price", "$450", "duration", "2h 30m"),  
            Map.of("flightNumber", "TH456", "airline", "TravelHub Airlines",  
                   "price", "$380", "duration", "2h 45m")  
        );  
        searchResults.put("flights", flights);  
          
        return new SimpleUCPResult(searchResults);  
    }  
}
```

### Hotel Booking Controller

```java
@RestController(value = "/hotels")  
@Agent(  
groupName = "hotelBooking",  
groupDescription = "Hotel search and reservation services"  
)  
public class HotelBookingController implements ProcessorAware, A2UIAware {

    @Action(description = "Search for available hotels in a city")  
    @UCPCapability(  
        name = "io.travelhub.hotel_search",  
        version = "2026-01-21",  
        spec = "https://travelhub.com/specs/hotel-search",  
        schema = "https://travelhub.com/schemas/hotel_search.json"  
    )  
    @PostMapping("/searchHotels")  
    public SimpleUCPResult searchHotels(  
            String city,  
            String checkInDate,  
            String checkOutDate,  
            Integer guests,  
            Integer rooms) {  
          
        Map<String, Object> searchResults = new HashMap<>();  
        searchResults.put("city", city);  
        searchResults.put("checkInDate", checkInDate);  
        searchResults.put("checkOutDate", checkOutDate);  
        searchResults.put("guests", guests);  
        searchResults.put("rooms", rooms);  
          
        List<Map<String, String>> hotels = Arrays.asList(  
            Map.of("name", "TravelHub Grand Hotel", "rating", "4.5",  
                   "price", "$120/night", "location", "City Center"),  
            Map.of("name", "TravelHub Airport Inn", "rating", "4.0",  
                   "price", "$85/night", "location", "Near Airport")  
        );  
        searchResults.put("hotels", hotels);  
          
        return new SimpleUCPResult(searchResults);  
    }  
  
    @Action(description = "Book a hotel room")  
    @UCPCapability(  
        name = "io.travelhub.hotel_booking",  
        version = "2026-01-21",  
        spec = "https://travelhub.com/specs/hotel-booking",  
        schema = "https://travelhub.com/schemas/hotel_booking.json"  
    )  
    @PostMapping("/bookHotel")  
    public SimpleUCPResult bookHotel(  
            String hotelId,  
            String checkInDate,  
            String checkOutDate,  
            String guestName,  
            String guestEmail) {  
          
        Map<String, String> bookingDetails = new HashMap<>();  
        bookingDetails.put("hotelId", hotelId);  
        bookingDetails.put("checkInDate", checkInDate);  
        bookingDetails.put("checkOutDate", checkOutDate);  
        bookingDetails.put("guestName", guestName);  
        bookingDetails.put("guestEmail", guestEmail);  
        bookingDetails.put("confirmationNumber", "HTL" + System.currentTimeMillis());  
          
        return new SimpleUCPResult(bookingDetails);  
    }  
}
```

### Activity Booking Service (Agentic Only)

```java
@Service  
@Agent(  
groupName = "activities",  
groupDescription = "Tour and activity booking services",  
prompt = "You are a local activity expert. Help travelers discover and book tours, excursions, and activities."  
)  
public class ActivityBookingService implements ProcessorAware, A2UIAware {

    @Action(description = "Search for local activities and tours")  
    public Object searchActivities(  
            String destination,  
            String activityDate,  
            Integer participants) {  
          
        return Map.of(  
            "destination", destination,  
            "activities", Arrays.asList(  
                Map.of("name", "City Walking Tour", "duration", "3 hours", "price", "$45"),  
                Map.of("name", "Museum Entry", "duration", "2 hours", "price", "$25"),  
                Map.of("name", "Food Tasting Tour", "duration", "4 hours", "price", "$75")  
            )  
        );  
    }  
  
    @Action(description = "Book an activity or tour")  
    public Object bookActivity(  
            String activityId,  
            String activityDate,  
            Integer participants,  
            String customerName) {  
          
        return Map.of(  
            "activityId", activityId,  
            "date", activityDate,  
            "participants", participants,  
            "customerName", customerName,  
            "bookingReference", "ACT" + System.currentTimeMillis()  
        );  
    }  
}
```

## Travel Discovery Profile

The generated UCP discovery profile for travel services:

```json
{  
"ucp": {  
"version": "2026-01-21",  
"capabilities": [  
{  
"schema": "https://travelhub.com/schemas/flight_search.json",  
"name": "io.travelhub.flight_search",  
"version": "2026-01-21",  
"spec": "https://travelhub.com/specs/flight-search"  
},  
{  
"schema": "https://travelhub.com/schemas/hotel_search.json",  
"name": "io.travelhub.hotel_search",  
"version": "2026-01-21",  
"spec": "https://travelhub.com/specs/hotel-search"  
},  
{  
"schema": "https://travelhub.com/schemas/hotel_booking.json",  
"name": "io.travelhub.hotel_booking",  
"version": "2026-01-21",  
"spec": "https://travelhub.com/specs/hotel-booking"  
},  
{  
"schema": "https://ucp.dev/schemas/shopping/checkout.json",  
"name": "dev.ucp.shopping.checkout",  
"version": "2026-01-11",  
"spec": "https://ucp.dev/specification/checkout"  
},  
{  
"schema": "https://ucp.dev/schemas/shopping/order.json",  
"name": "dev.ucp.shopping.order",  
"version": "2026-01-11",  
"spec": "https://ucp.dev/specification/order"  
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
}  
}  
}
```

## Travel Schema Examples

### Flight Search Schema

```json
{  
"$schema": "https://json-schema.org/draft/2020-12/schema",  
"$id": "https://travelhub.com/schemas/flight_search.json",  
"name": "io.travelhub.flight_search",  
"version": "2026-01-21",  
"title": "Flight Search",  
"description": "Schema for flight search requests and responses",  
"type": "object",  
"required": ["origin", "destination", "departureDate", "passengers"],  
"properties": {  
"origin": {  
"type": "string",  
"pattern": "^[A-Z]{3}$",  
"description": "Airport code (IATA)"  
},  
"destination": {  
"type": "string",   
"pattern": "^[A-Z]{3}$",  
"description": "Airport code (IATA)"  
},  
"departureDate": {  
"type": "string",  
"format": "date",  
"description": "Departure date"  
},  
"returnDate": {  
"type": "string",  
"format": "date",  
"description": "Return date (optional for one-way)"  
},  
"passengers": {  
"type": "integer",  
"minimum": 1,  
"maximum": 9,  
"description": "Number of passengers"  
},  
"flights": {  
"type": "array",  
"items": {  
"type": "object",  
"properties": {  
"flightNumber": {"type": "string"},  
"airline": {"type": "string"},  
"price": {"type": "string"},  
"duration": {"type": "string"}  
}  
}  
}  
}  
}
```

## MCP Integration for Travel Agents

### Flight Search via MCP

```bash
curl -X POST http://localhost:7860/ucp/mcp \  
-H "Content-Type: application/json" \  
-d '{  
"jsonrpc": "2.0",  
"method": "io.travelhub.flight_search",  
"params": {  
"_meta": {  
"ucp": {  
"profile": "https://travel-platform.com/profiles/v2026-01/travel-agent.json"  
}  
},  
"origin": "NYC",  
"destination": "LAX",  
"departureDate": "2026-02-15",  
"returnDate": "2026-02-22",  
"passengers": 2  
},  
"id": 1  
}'
```

### Response

```json
{  
"result": {  
"ucp": {  
"version": "2026-01-11",  
"capabilities": [  
{  
"name": "io.travelhub.flight_search",  
"version": "2026-01-21"  
}  
]  
},  
"origin": "NYC",  
"destination": "LAX",  
"departureDate": "2026-02-15",  
"returnDate": "2026-02-22",  
"passengers": 2,  
"flights": [  
{  
"flightNumber": "TH123",  
"airline": "TravelHub Airlines",  
"price": "$450",  
"duration": "2h 30m"  
}  
]  
},  
"id": 1,  
"jsonrpc": "2.0"  
}
```

## Complete Travel Booking Flow

### 1. Multi-Modal Search

AI agents can search across all travel services:

#### Search flights

```bash
curl -X POST http://localhost:7860/flights/searchFlights \  
-H "Content-Type: application/json" \  
-d '{"origin": "NYC", "destination": "LAX", "departureDate": "2026-02-15", "passengers": 2}'
```

#### Search hotels

```bash
curl -X POST http://localhost:7860/hotels/searchHotels \  
-H "Content-Type: application/json" \  
-d '{"city": "Los Angeles", "checkInDate": "2026-02-15", "checkOutDate": "2026-02-22", "guests": 2, "rooms": 1}'
```

### 2. Unified Checkout

Create a single checkout session for the complete trip:

```bash
curl -X POST http://localhost:7860/ucp/v1/checkout-sessions \  
-H "Content-Type: application/json" \  
-d '{  
"ucp": {  
"version": "2026-01-11",  
"capabilities": [  
{"name": "dev.ucp.shopping.checkout", "version": "2026-01-11"},  
{"name": "io.travelhub.flight_search", "version": "2026-01-21"},  
{"name": "io.travelhub.hotel_booking", "version": "2026-01-21"}  
]  
},  
"cart": {  
"line_items": [  
{"sku": "FLIGHT-TH123", "quantity": 2},  
{"sku": "HOTEL-TH-GRAND", "quantity": 1}  
]  
}  
}'
```
