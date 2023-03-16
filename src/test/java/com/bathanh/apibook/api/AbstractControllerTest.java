package com.bathanh.apibook.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
public abstract class AbstractControllerTest {

    @Autowired
    private MockMvc mvc;

    final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    protected ResultActions get(final String url) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON));
    }

    protected ResultActions post(final String url, final Object object) throws Exception {
        final String requestBody = mapper.writeValueAsString(object);

        return mvc.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON).content(requestBody));
    }

    protected ResultActions put(final String url, final Object object) throws Exception {
        final String requestBody = mapper.writeValueAsString(object);

        return mvc.perform(MockMvcRequestBuilders.put(url).contentType(MediaType.APPLICATION_JSON).content(requestBody));
    }

    protected ResultActions delete(final String url) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.delete(url).contentType(MediaType.APPLICATION_JSON));
    }
}
