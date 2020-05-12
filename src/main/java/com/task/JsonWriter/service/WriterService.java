package com.task.JsonWriter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.JsonWriter.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class WriterService {

  private RestTemplate restTemplate;
  private FileHelper fileHelper;
  private ObjectMapper mapper;

  @Autowired
  public WriterService(RestTemplate restTemplate, FileHelper fileHelper, ObjectMapper mapper) {
    this.restTemplate = restTemplate;
    this.fileHelper = fileHelper;
    this.mapper = mapper;
  }

  public void callExternalApiAndGenerate() throws IOException, HttpClientErrorException {
    String url = "https://jsonplaceholder.typicode.com/posts";

    ResponseEntity<Post[]> response = restTemplate.getForEntity(url, Post[].class);
    Post[] posts = response.getBody();
    List<Post> listOfPost = Arrays.asList(posts);

    for (Post post : listOfPost) {
      String jsonString = mapper.writeValueAsString(post);
      fileHelper.writeJsonToFile(jsonString, post.getId());
    }
  }
}
