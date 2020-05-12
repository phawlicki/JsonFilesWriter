package com.task.JsonWriter.rest;


import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.JsonWriter.service.WriterService;
import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.HttpClientErrorException;

@RunWith(SpringRunner.class)
@WebMvcTest(JsonController.class)
public class JsonControllerTest {


  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private WriterService writerService;

  @Autowired
  ObjectMapper objectMapper;


  @Test
  public void shouldGetAndGenerateJsonFiles() throws Exception {

    //when
    this.mockMvc.perform(get("/createJson")).andDo(print()).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string("Json files "
        + "has been created"));
    //then
    verify(writerService, times(1)).callExternalApiAndGenerate();

  }


  @Test
  public void shouldThrowIOExcpetionWhenGetAndGenerateJsonFiles() throws Exception {

    //when
    doThrow(IOException.class).when(writerService).callExternalApiAndGenerate();

    this.mockMvc.perform(get("/createJson")).andDo(print()).andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().string("Creation "
            + "of Json files failed due to IOException!"));
    //then
    verify(writerService, times(1)).callExternalApiAndGenerate();

  }

  @Test
  public void shouldThrowHttpClientErrorExceptionWhenGetAndGenerateJsonFiles() throws Exception {
    //when
    doThrow(HttpClientErrorException.class).when(writerService).callExternalApiAndGenerate();

    this.mockMvc.perform(get("/createJson")).andDo(print()).andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().string("Creation of Json files failed due to HttpClientErrorException!"));
    //then
    verify(writerService, times(1)).callExternalApiAndGenerate();

  }
}

