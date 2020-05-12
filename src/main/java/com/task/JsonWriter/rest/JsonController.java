package com.task.JsonWriter.rest;

import com.task.JsonWriter.service.WriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import org.springframework.web.client.HttpClientErrorException;

@RestController
public class JsonController {

  private WriterService writerService;

  @Autowired
  public JsonController(WriterService writerService) {
    this.writerService = writerService;
  }

  @GetMapping("/createJson")
  @ResponseBody
  public ResponseEntity<String> getAndGenerateJsonFiles() {

    try {
      writerService.callExternalApiAndGenerate();
    } catch (IOException e) {
      return new ResponseEntity<>("Creation of Json files failed due to IOException!", null, HttpStatus.BAD_REQUEST);
    } catch (HttpClientErrorException e) {
      return new ResponseEntity<>("Creation of Json files failed due to HttpClientErrorException!", null, HttpStatus.BAD_REQUEST);
    }
    return ResponseEntity.ok("Json files has been created");
  }

}
