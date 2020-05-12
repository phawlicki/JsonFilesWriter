package com.task.JsonWriter.service;

import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileHelper {

  public void writeJsonToFile(String json, String id) throws IOException {
    Path pathToCreate = Paths.get("src", "main", "resources", "JsonFiles");

    if (!Files.exists(pathToCreate)) {
      Files.createDirectories(pathToCreate);
    }
    Path path = Paths.get("src", "main", "resources", "JsonFiles/" + id + ".json");
    Files.write(path, json.getBytes());
  }
}
