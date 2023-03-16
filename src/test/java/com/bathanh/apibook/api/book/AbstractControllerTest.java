package com.bathanh.apibook.api.book;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class AbstractControllerTest {

    public ResultActions getRequest(final MockMvc mvc, final String url) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON));
    }

    public ResultActions postRequest(final MockMvc mvc, final String url, final String requestBody) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON).content(requestBody));
    }

    public ResultActions putRequest(final MockMvc mvc, final String url, final String requestBody) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.put(url).contentType(MediaType.APPLICATION_JSON).content(requestBody));
    }

    public ResultActions deleteRequest(final MockMvc mvc, final String url) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.delete(url).contentType(MediaType.APPLICATION_JSON));
    }
}
