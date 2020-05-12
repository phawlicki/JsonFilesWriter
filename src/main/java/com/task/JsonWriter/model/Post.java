package com.task.JsonWriter.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Post implements Serializable {

  @JsonProperty
  private String userId;
  @JsonProperty
  private String id;
  @JsonProperty
  private String title;
  @JsonProperty
  private String body;

  public Post() {
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }
}
