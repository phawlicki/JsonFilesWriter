package com.task.JsonWriter.service;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.JsonWriter.model.Post;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FileHelperTest {

  @Autowired
  private FileHelper fileHelper;

  private ObjectMapper objectMapper = new ObjectMapper();


  @Before
  public void cleanup() throws IOException {
    if (Files.isDirectory(Paths.get("src", "main", "resources", "JsonFiles"))) {

      Stream<Path> walk = Files.walk(Paths.get("src", "main", "resources", "JsonFiles"));
      List<Path> result = walk.filter(Files::isRegularFile).collect(Collectors.toList());

      for (Path path : result) {
        Files.delete(path);
      }
      Files.deleteIfExists(Paths.get("src", "main", "resources", "JsonFiles"));
    }
  }


  @Test
  public void shouldWriteJsonToFile() throws IOException, URISyntaxException {

    //given

    Post post = new Post();
    post.setTitle("Test");
    post.setUserId("1");
    post.setId("1");
    post.setBody("This is test JSON");

    String jsonToSave = objectMapper.writeValueAsString(post);
    String fileName = "1.json";
    Path createdPath = Paths.get("src", "main", "resources", "JsonFiles").resolve(fileName);

    Post post2 = new Post();
    post2.setTitle("Test2");
    post2.setUserId("2");
    post2.setId("2");
    post2.setBody("This is test JSON2");

    String jsonToSave2 = objectMapper.writeValueAsString(post2);
    String fileName2 = "2.json";
    Path createdPath2 = Paths.get("src", "main", "resources", "JsonFiles").resolve(fileName2);

    //when

    fileHelper.writeJsonToFile(jsonToSave, post.getId());
    fileHelper.writeJsonToFile(jsonToSave2, post2.getId());

    //then

    String savedString = new String(Files.readAllBytes(createdPath));
    String savedString2 = new String(Files.readAllBytes(createdPath2));
    assertTrue(Files.exists(createdPath));
    assertTrue(Files.exists(createdPath2));
    assertEquals(jsonToSave, savedString);
    assertEquals(jsonToSave2, savedString2);
  }

}
