package cike.chatgpt.repository.entity;

import java.util.Date;

public class SensitiveWordsHitRecord {

  private Long id;

  private String uid;

  private String conversationId;

  private String systemMessage;

  private String userMessage;

  private Date createTime;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getConversationId() {
    return conversationId;
  }

  public void setConversationId(String conversationId) {
    this.conversationId = conversationId;
  }

  public String getSystemMessage() {
    return systemMessage;
  }

  public void setSystemMessage(String systemMessage) {
    this.systemMessage = systemMessage;
  }

  public String getUserMessage() {
    return userMessage;
  }

  public void setUserMessage(String userMessage) {
    this.userMessage = userMessage;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }
}