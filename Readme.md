List of tools

curl -H "Content-Type: application/json" -d '{
"jsonrpc": "2.0",
"method": "tools/list",
"params": {},
"id": 1
}' http://localhost:7860/
{"result":{"_meta":{},"tools":[{"parameters":null,"inputSchema":{"type":"object","properties":{"provideAllValuesInPlainEnglish":{"type":"string","description":"{\n    \"parameters\": {\n        \"carType\": \"\",\n        \"pickupLocation\": \"\",\n        \"dropoffLocation\": \"\",\n        \"pickupDate\": \"\",\n        \"dropoffDate\": \"\"\n    }\n}","additionalProperties":{},"items":false}},"required":["provideAllValuesInPlainEnglish"]},"annotations":{"properties":{"usage":"To reserve a car for a specific period and location.","name":"Car Booking Tool","description":"This tool allows users to book a car by specifying the car type, pickup location, dropoff location, and pickup and dropoff dates.","parameters":"carType, pickupLocation, dropoffLocation, pickupDate, dropoffDate"}},"description":"Book a car based on user preferences","name":"bookCar","type":null},{"parameters":null,"inputSchema":{"type":"object","properties":{"provideAllValuesInPlainEnglish":{"type":"string","description":"{\n    \"parameters\": [\n        {\n            \"name\": \"personName\",\n            \"type\": \"String\",\n            \"fieldValue\": \"\"\n        }\n    ]\n}","additionalProperties":{},"items":false}},"required":["provideAllValuesInPlainEnglish"]},"annotations":{"properties":{"description":"Retrieves the favorite car of a specified person based on their name."}},"description":"Get the favorite car of a person","name":"getFavoriteCar","type":null},{"parameters":null,"inputSchema":{"type":"object","properties":{"provideAllValuesInPlainEnglish":{"type":"string","description":"{\n    \"model\": {\n        \"type\": \"String\",\n        \"fieldValue\": \"\"\n    }\n}","additionalProperties":{},"items":false}},"required":["provideAllValuesInPlainEnglish"]},"annotations":{"properties":{"description":"This tool checks the stock availability of a specified model."}},"description":"Check real-time stock for a specific model","name":"checkStock","type":null},{"parameters":null,"inputSchema":{"type":"object","properties":{"provideAllValuesInPlainEnglish":{"type":"string","description":"{\n    \"car1\": {\n        \"type\": \"String\",\n        \"fieldValue\": \"\"\n    },\n    \"car2\": {\n        \"type\": \"String\",\n        \"fieldValue\": \"\"\n    }\n}","additionalProperties":{},"items":false}},"required":["provideAllValuesInPlainEnglish"]},"annotations":{"properties":{"name":"Car Comparison Tool","description":"This tool compares two cars based on various parameters to help users make informed decisions.","inputParameters":"car1 and car2 (String) - Names of the two cars to be compared.","returnType":"Object - Returns the comparison result between the two cars."}},"description":"compare 2 cars","name":"compareCar","type":null}]},"id":1,"jsonrpc":"2.0"}




Tool calling normal MCP

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
{"result":{"content":[{"type":"text","annotations":null,"text":"Tesla Model 3 is better than BMW i4","type":"text"}],"textResult":"Tesla Model 3 is better than BMW i4"},"id":2,"jsonrpc":"2.0"}

For UCP Request

/**
*
* Request is like this
*
* {
* "jsonrpc": "2.0",
* "method": "whatThisPersonFavFood",
* "params": {
* "_meta": {
* "ucp": {
* "profile": "https://platform.example/profiles/v2026-01/shopping-agent.json"
* }
* },
* "provideAllValuesInPlainEnglish": "vishal is coming home what should i cook"
* },
* "id": 17
* }
*
* response is like this
* {
* "jsonrpc": "2.0",
* "id": 17,
* "result": {
* "ucp": {
* "version": "2026-01-11",
* "capabilities": [
     * {
     * "name": "io.github.vishalmysore.car_booking",
     * "version": "2026-01-19"
     * }
     * ]
* },
* "booking_id": "bk_1234567890",
* "status": "confirmed",
* "car_model": "Toyota Camry",
* "booking_date": "2026-01-25"
* }
* }
