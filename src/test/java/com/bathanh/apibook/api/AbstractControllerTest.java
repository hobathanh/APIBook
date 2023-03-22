package com.bathanh.apibook.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@AutoConfigureMockMvc
@ActiveProfiles("TEST")
public abstract class AbstractControllerTest {

    @Autowired
    private MockMvc mvc;

    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    protected ResultActions get(final String url) throws Exception {
        return perform(MockMvcRequestBuilders.get(url));
    }

    protected ResultActions post(final String url, final Object object) throws Exception {
        final String requestBody = mapper.writeValueAsString(object);

        return perform(MockMvcRequestBuilders.post(url).content(requestBody).with(csrf()));
    }

    protected ResultActions put(final String url, final Object object) throws Exception {
        final String requestBody = mapper.writeValueAsString(object);

        return perform(MockMvcRequestBuilders.put(url).content(requestBody).with(csrf()));
    }

    protected ResultActions delete(final String url) throws Exception {
        return perform(MockMvcRequestBuilders.delete(url).with(csrf()));
    }

    private ResultActions perform(final MockHttpServletRequestBuilder mockHttpServletRequestBuilder) throws Exception {
        return mvc.perform(mockHttpServletRequestBuilder.contentType(MediaType.APPLICATION_JSON));
    }
}
