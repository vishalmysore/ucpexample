package io.github.vishalmysore.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CompareCarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCompareCar_ToyotaVsOther() throws Exception {
        mockMvc.perform(post("/compareCar")
                .param("car1", "Toyota Camry")
                .param("car2", "Honda Civic"))
                .andExpect(status().isOk())
                .andExpect(content().string("Toyota Camry is better than Honda Civic"));
    }

    @Test
    void testCompareCar_OtherVsToyota() throws Exception {
        mockMvc.perform(post("/compareCar")
                .param("car1", "Honda Civic")
                .param("car2", "Toyota Corolla"))
                .andExpect(status().isOk())
                .andExpect(content().string("Toyota Corolla is better than Honda Civic"));
    }

    @Test
    void testCompareCar_BothNotToyota() throws Exception {
        mockMvc.perform(post("/compareCar")
                .param("car1", "Honda Civic")
                .param("car2", "Ford Focus"))
                .andExpect(status().isOk())
                .andExpect(content().string("Honda Civic is better than Ford Focus"));
    }

    @Test
    void testCheckStock() throws Exception {
        mockMvc.perform(post("/checkStock")
                .param("model", "Camry"))
                .andExpect(status().isOk())
                .andExpect(content().string("The model Camry is currently: In Stock"));
    }
}