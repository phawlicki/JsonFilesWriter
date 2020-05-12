package com.task.JsonWriter.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.JsonWriter.model.Post;
import java.io.IOException;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
public class WriterServiceTest {

  @Spy
  private ObjectMapper objectMapper;

  @Mock
  private RestTemplate restTemplate;

  @Mock
  private FileHelper fileHelper;

  @InjectMocks
  private WriterService writerService;

  @Captor
  private ArgumentCaptor<String> captureJson;

  @Captor
  private ArgumentCaptor<String> captureId;

  @Test
  public void shouldCallExternalApiAndGenerate() throws IOException {

    //given

    String url = "https://jsonplaceholder.typicode.com/posts";
    Post arrayPost[] = new Post[2];
    Post post = new Post();
    post.setBody("Test");
    post.setId("1");
    post.setUserId("1");
    post.setTitle("Foo");
    arrayPost[0] = post;

    String jsonPost=objectMapper.writeValueAsString(post);

    Post post1 = new Post();
    post1.setBody("Test1");
    post1.setId("2");
    post1.setUserId("2");
    post1.setTitle("Foo2");
    arrayPost[1] = post1;

    String jsonPost1=objectMapper.writeValueAsString(post1);

    //when
    Mockito.when(restTemplate.getForEntity(
        url, Post[].class))
        .thenReturn(new ResponseEntity<Post[]>(arrayPost, HttpStatus.OK));

    writerService.callExternalApiAndGenerate();

    //then

    Mockito.verify(fileHelper, Mockito.times(2)).writeJsonToFile(Mockito.anyString(), Mockito.anyString());
    Mockito.verify(fileHelper, Mockito.times(2)).writeJsonToFile(captureJson.capture(), captureId.capture());

    List<String> jsonCaptureList=captureJson.getAllValues();
    List<String> idCaptureList=captureId.getAllValues();

    Assert.assertEquals(jsonCaptureList.get(0),jsonPost);
    Assert.assertEquals(jsonCaptureList.get(1),jsonPost1);
    Assert.assertEquals(idCaptureList.get(0),post.getId());
    Assert.assertEquals(idCaptureList.get(1),post1.getId());

  }
}