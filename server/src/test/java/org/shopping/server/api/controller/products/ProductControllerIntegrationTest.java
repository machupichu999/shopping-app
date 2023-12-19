package org.shopping.server.api.controller.products;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.shopping.server.model.Product;
import org.shopping.server.model.dao.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductDAO productDAO;
    private List<Product> testProducts;

    @Before
    public void init() {
        Product product1 = new Product();
        product1.setName("Product 1");
        product1.setShortDescription("Short description 1");
        product1.setPrice(10.0);
        product1.setStock(100);

        Product product2 = new Product();
        product2.setName("Product 2");
        product2.setShortDescription("Short description 2");
        product2.setPrice(20.0);
        product2.setStock(50);

        testProducts = List.of(product1, product2);

        productDAO.saveAll(testProducts);
    }

    @After
    public void cleanup() {
        productDAO.deleteAll(testProducts);
    }

    @Test
    public void testGetProducts() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Product 1")))
                .andExpect(jsonPath("$[0].shortDescription", is("Short description 1")))
                .andExpect(jsonPath("$[0].price", is(10.0)))
                .andExpect(jsonPath("$[0].stock", is(100)))
                .andExpect(jsonPath("$[1].name", is("Product 2")))
                .andExpect(jsonPath("$[1].shortDescription", is("Short description 2")))
                .andExpect(jsonPath("$[1].price", is(20.0)))
                .andExpect(jsonPath("$[1].stock", is(50)));
    }
}

