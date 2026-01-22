package io.github.vishalmysore.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CarSellingServiceTest {

    private final CarSellingService carSellingService = new CarSellingService();

    @Test
    void testSellCar() {
        Object result = carSellingService.sellCar("Sedan", "LocationA", "LocationB", "2023-01-01", "2023-01-05");
        assertNotNull(result);
        assertEquals("Car sold: Sedan from LocationA to LocationB between 2023-01-01 and 2023-01-05", result);
    }
}