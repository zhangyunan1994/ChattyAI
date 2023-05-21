package cike.chatgpt.repository.mapper.specific;

import cike.chatgpt.repository.entity.ChatgptMessageRecord;

public class ChatgptMessageSpecificRecord extends ChatgptMessageRecord {

  private String username;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
