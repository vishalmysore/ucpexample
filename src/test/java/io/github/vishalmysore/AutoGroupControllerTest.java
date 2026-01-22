package io.github.vishalmysore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AutoGroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetComparisonSchema() throws Exception {
        mockMvc.perform(get("/schemas/comparison"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("object"))
                .andExpect(jsonPath("$.properties.car1.type").value("string"))
                .andExpect(jsonPath("$.properties.car2.type").value("string"))
                .andExpect(jsonPath("$.required").isArray());
    }
}