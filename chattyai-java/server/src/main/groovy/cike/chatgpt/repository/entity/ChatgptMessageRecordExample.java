package cike.chatgpt.repository.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatgptMessageRecordExample {

  protected String orderByClause;

  protected boolean distinct;

  protected List<Criteria> oredCriteria;

  public ChatgptMessageRecordExample() {
    oredCriteria = new ArrayList<>();
  }

  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  public String getOrderByClause() {
    return orderByClause;
  }

  public void setDistinct(boolean distinct) {
    this.distinct = distinct;
  }

  public boolean isDistinct() {
    return distinct;
  }

  public List<Criteria> getOredCriteria() {
    return oredCriteria;
  }

  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  public Criteria or() {
    Criteria criteria = createCriteriaInternal();
    oredCriteria.add(criteria);
    return criteria;
  }

  public Criteria createCriteria() {
    Criteria criteria = createCriteriaInternal();
    if (oredCriteria.size() == 0) {
      oredCriteria.add(criteria);
    }
    return criteria;
  }

  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  public void clear() {
    oredCriteria.clear();
    orderByClause = null;
    distinct = false;
  }

  protected abstract static class GeneratedCriteria {

    protected List<Criterion> criteria;

    protected GeneratedCriteria() {
      super();
      criteria = new ArrayList<>();
    }

    public boolean isValid() {
      return criteria.size() > 0;
    }

    public List<Criterion> getAllCriteria() {
      return criteria;
    }

    public List<Criterion> getCriteria() {
      return criteria;
    }

    protected void addCriterion(String condition) {
      if (condition == null) {
        throw new RuntimeException("Value for condition cannot be null");
      }
      criteria.add(new Criterion(condition));
    }

    protected void addCriterion(String condition, Object value, String property) {
      if (value == null) {
        throw new RuntimeException("Value for " + property + " cannot be null");
      }
      criteria.add(new Criterion(condition, value));
    }

    protected void addCriterion(String condition, Object value1, Object value2, String property) {
      if (value1 == null || value2 == null) {
        throw new RuntimeException("Between values for " + property + " cannot be null");
      }
      criteria.add(new Criterion(condition, value1, value2));
    }

    public Criteria andIdIsNull() {
      addCriterion("id is null");
      return (Criteria) this;
    }

    public Criteria andIdIsNotNull() {
      addCriterion("id is not null");
      return (Criteria) this;
    }

    public Criteria andIdEqualTo(Long value) {
      addCriterion("id =", value, "id");
      return (Criteria) this;
    }

    public Criteria andIdNotEqualTo(Long value) {
      addCriterion("id <>", value, "id");
      return (Criteria) this;
    }

    public Criteria andIdGreaterThan(Long value) {
      addCriterion("id >", value, "id");
      return (Criteria) this;
    }

    public Criteria andIdGreaterThanOrEqualTo(Long value) {
      addCriterion("id >=", value, "id");
      return (Criteria) this;
    }

    public Criteria andIdLessThan(Long value) {
      addCriterion("id <", value, "id");
      return (Criteria) this;
    }

    public Criteria andIdLessThanOrEqualTo(Long value) {
      addCriterion("id <=", value, "id");
      return (Criteria) this;
    }

    public Criteria andIdIn(List<Long> values) {
      addCriterion("id in", values, "id");
      return (Criteria) this;
    }

    public Criteria andIdNotIn(List<Long> values) {
      addCriterion("id not in", values, "id");
      return (Criteria) this;
    }

    public Criteria andIdBetween(Long value1, Long value2) {
      addCriterion("id between", value1, value2, "id");
      return (Criteria) this;
    }

    public Criteria andIdNotBetween(Long value1, Long value2) {
      addCriterion("id not between", value1, value2, "id");
      return (Criteria) this;
    }

    public Criteria andUidIsNull() {
      addCriterion("`uid` is null");
      return (Criteria) this;
    }

    public Criteria andUidIsNotNull() {
      addCriterion("`uid` is not null");
      return (Criteria) this;
    }

    public Criteria andUidEqualTo(String value) {
      addCriterion("`uid` =", value, "uid");
      return (Criteria) this;
    }

    public Criteria andUidNotEqualTo(String value) {
      addCriterion("`uid` <>", value, "uid");
      return (Criteria) this;
    }

    public Criteria andUidGreaterThan(String value) {
      addCriterion("`uid` >", value, "uid");
      return (Criteria) this;
    }

    public Criteria andUidGreaterThanOrEqualTo(String value) {
      addCriterion("`uid` >=", value, "uid");
      return (Criteria) this;
    }

    public Criteria andUidLessThan(String value) {
      addCriterion("`uid` <", value, "uid");
      return (Criteria) this;
    }

    public Criteria andUidLessThanOrEqualTo(String value) {
      addCriterion("`uid` <=", value, "uid");
      return (Criteria) this;
    }

    public Criteria andUidLike(String value) {
      addCriterion("`uid` like", value, "uid");
      return (Criteria) this;
    }

    public Criteria andUidNotLike(String value) {
      addCriterion("`uid` not like", value, "uid");
      return (Criteria) this;
    }

    public Criteria andUidIn(List<String> values) {
      addCriterion("`uid` in", values, "uid");
      return (Criteria) this;
    }

    public Criteria andUidNotIn(List<String> values) {
      addCriterion("`uid` not in", values, "uid");
      return (Criteria) this;
    }

    public Criteria andUidBetween(String value1, String value2) {
      addCriterion("`uid` between", value1, value2, "uid");
      return (Criteria) this;
    }

    public Criteria andUidNotBetween(String value1, String value2) {
      addCriterion("`uid` not between", value1, value2, "uid");
      return (Criteria) this;
    }

    public Criteria andConversationIdIsNull() {
      addCriterion("conversation_id is null");
      return (Criteria) this;
    }

    public Criteria andConversationIdIsNotNull() {
      addCriterion("conversation_id is not null");
      return (Criteria) this;
    }

    public Criteria andConversationIdEqualTo(String value) {
      addCriterion("conversation_id =", value, "conversationId");
      return (Criteria) this;
    }

    public Criteria andConversationIdNotEqualTo(String value) {
      addCriterion("conversation_id <>", value, "conversationId");
      return (Criteria) this;
    }

    public Criteria andConversationIdGreaterThan(String value) {
      addCriterion("conversation_id >", value, "conversationId");
      return (Criteria) this;
    }

    public Criteria andConversationIdGreaterThanOrEqualTo(String value) {
      addCriterion("conversation_id >=", value, "conversationId");
      return (Criteria) this;
    }

    public Criteria andConversationIdLessThan(String value) {
      addCriterion("conversation_id <", value, "conversationId");
      return (Criteria) this;
    }

    public Criteria andConversationIdLessThanOrEqualTo(String value) {
      addCriterion("conversation_id <=", value, "conversationId");
      return (Criteria) this;
    }

    public Criteria andConversationIdLike(String value) {
      addCriterion("conversation_id like", value, "conversationId");
      return (Criteria) this;
    }

    public Criteria andConversationIdNotLike(String value) {
      addCriterion("conversation_id not like", value, "conversationId");
      return (Criteria) this;
    }

    public Criteria andConversationIdIn(List<String> values) {
      addCriterion("conversation_id in", values, "conversationId");
      return (Criteria) this;
    }

    public Criteria andConversationIdNotIn(List<String> values) {
      addCriterion("conversation_id not in", values, "conversationId");
      return (Criteria) this;
    }

    public Criteria andConversationIdBetween(String value1, String value2) {
      addCriterion("conversation_id between", value1, value2, "conversationId");
      return (Criteria) this;
    }

    public Criteria andConversationIdNotBetween(String value1, String value2) {
      addCriterion("conversation_id not between", value1, value2, "conversationId");
      return (Criteria) this;
    }

    public Criteria andSystemMessageIsNull() {
      addCriterion("system_message is null");
      return (Criteria) this;
    }

    public Criteria andSystemMessageIsNotNull() {
      addCriterion("system_message is not null");
      return (Criteria) this;
    }

    public Criteria andSystemMessageEqualTo(String value) {
      addCriterion("system_message =", value, "systemMessage");
      return (Criteria) this;
    }

    public Criteria andSystemMessageNotEqualTo(String value) {
      addCriterion("system_message <>", value, "systemMessage");
      return (Criteria) this;
    }

    public Criteria andSystemMessageGreaterThan(String value) {
      addCriterion("system_message >", value, "systemMessage");
      return (Criteria) this;
    }

    public Criteria andSystemMessageGreaterThanOrEqualTo(String value) {
      addCriterion("system_message >=", value, "systemMessage");
      return (Criteria) this;
    }

    public Criteria andSystemMessageLessThan(String value) {
      addCriterion("system_message <", value, "systemMessage");
      return (Criteria) this;
    }

    public Criteria andSystemMessageLessThanOrEqualTo(String value) {
      addCriterion("system_message <=", value, "systemMessage");
      return (Criteria) this;
    }

    public Criteria andSystemMessageLike(String value) {
      addCriterion("system_message like", value, "systemMessage");
      return (Criteria) this;
    }

    public Criteria andSystemMessageNotLike(String value) {
      addCriterion("system_message not like", value, "systemMessage");
      return (Criteria) this;
    }

    public Criteria andSystemMessageIn(List<String> values) {
      addCriterion("system_message in", values, "systemMessage");
      return (Criteria) this;
    }

    public Criteria andSystemMessageNotIn(List<String> values) {
      addCriterion("system_message not in", values, "systemMessage");
      return (Criteria) this;
    }

    public Criteria andSystemMessageBetween(String value1, String value2) {
      addCriterion("system_message between", value1, value2, "systemMessage");
      return (Criteria) this;
    }

    public Criteria andSystemMessageNotBetween(String value1, String value2) {
      addCriterion("system_message not between", value1, value2, "systemMessage");
      return (Criteria) this;
    }

    public Criteria andRoleIsNull() {
      addCriterion("`role` is null");
      return (Criteria) this;
    }

    public Criteria andRoleIsNotNull() {
      addCriterion("`role` is not null");
      return (Criteria) this;
    }

    public Criteria andRoleEqualTo(String value) {
      addCriterion("`role` =", value, "role");
      return (Criteria) this;
    }

    public Criteria andRoleNotEqualTo(String value) {
      addCriterion("`role` <>", value, "role");
      return (Criteria) this;
    }

    public Criteria andRoleGreaterThan(String value) {
      addCriterion("`role` >", value, "role");
      return (Criteria) this;
    }

    public Criteria andRoleGreaterThanOrEqualTo(String value) {
      addCriterion("`role` >=", value, "role");
      return (Criteria) this;
    }

    public Criteria andRoleLessThan(String value) {
      addCriterion("`role` <", value, "role");
      return (Criteria) this;
    }

    public Criteria andRoleLessThanOrEqualTo(String value) {
      addCriterion("`role` <=", value, "role");
      return (Criteria) this;
    }

    public Criteria andRoleLike(String value) {
      addCriterion("`role` like", value, "role");
      return (Criteria) this;
    }

    public Criteria andRoleNotLike(String value) {
      addCriterion("`role` not like", value, "role");
      return (Criteria) this;
    }

    public Criteria andRoleIn(List<String> values) {
      addCriterion("`role` in", values, "role");
      return (Criteria) this;
    }

    public Criteria andRoleNotIn(List<String> values) {
      addCriterion("`role` not in", values, "role");
      return (Criteria) this;
    }

    public Criteria andRoleBetween(String value1, String value2) {
      addCriterion("`role` between", value1, value2, "role");
      return (Criteria) this;
    }

    public Criteria andRoleNotBetween(String value1, String value2) {
      addCriterion("`role` not between", value1, value2, "role");
      return (Criteria) this;
    }

    public Criteria andRoleMessageIsNull() {
      addCriterion("role_message is null");
      return (Criteria) this;
    }

    public Criteria andRoleMessageIsNotNull() {
      addCriterion("role_message is not null");
      return (Criteria) this;
    }

    public Criteria andRoleMessageEqualTo(String value) {
      addCriterion("role_message =", value, "roleMessage");
      return (Criteria) this;
    }

    public Criteria andRoleMessageNotEqualTo(String value) {
      addCriterion("role_message <>", value, "roleMessage");
      return (Criteria) this;
    }

    public Criteria andRoleMessageGreaterThan(String value) {
      addCriterion("role_message >", value, "roleMessage");
      return (Criteria) this;
    }

    public Criteria andRoleMessageGreaterThanOrEqualTo(String value) {
      addCriterion("role_message >=", value, "roleMessage");
      return (Criteria) this;
    }

    public Criteria andRoleMessageLessThan(String value) {
      addCriterion("role_message <", value, "roleMessage");
      return (Criteria) this;
    }

    public Criteria andRoleMessageLessThanOrEqualTo(String value) {
      addCriterion("role_message <=", value, "roleMessage");
      return (Criteria) this;
    }

    public Criteria andRoleMessageLike(String value) {
      addCriterion("role_message like", value, "roleMessage");
      return (Criteria) this;
    }

    public Criteria andRoleMessageNotLike(String value) {
      addCriterion("role_message not like", value, "roleMessage");
      return (Criteria) this;
    }

    public Criteria andRoleMessageIn(List<String> values) {
      addCriterion("role_message in", values, "roleMessage");
      return (Criteria) this;
    }

    public Criteria andRoleMessageNotIn(List<String> values) {
      addCriterion("role_message not in", values, "roleMessage");
      return (Criteria) this;
    }

    public Criteria andRoleMessageBetween(String value1, String value2) {
      addCriterion("role_message between", value1, value2, "roleMessage");
      return (Criteria) this;
    }

    public Criteria andRoleMessageNotBetween(String value1, String value2) {
      addCriterion("role_message not between", value1, value2, "roleMessage");
      return (Criteria) this;
    }

    public Criteria andMessageIdIsNull() {
      addCriterion("message_id is null");
      return (Criteria) this;
    }

    public Criteria andMessageIdIsNotNull() {
      addCriterion("message_id is not null");
      return (Criteria) this;
    }

    public Criteria andMessageIdEqualTo(String value) {
      addCriterion("message_id =", value, "messageId");
      return (Criteria) this;
    }

    public Criteria andMessageIdNotEqualTo(String value) {
      addCriterion("message_id <>", value, "messageId");
      return (Criteria) this;
    }

    public Criteria andMessageIdGreaterThan(String value) {
      addCriterion("message_id >", value, "messageId");
      return (Criteria) this;
    }

    public Criteria andMessageIdGreaterThanOrEqualTo(String value) {
      addCriterion("message_id >=", value, "messageId");
      return (Criteria) this;
    }

    public Criteria andMessageIdLessThan(String value) {
      addCriterion("message_id <", value, "messageId");
      return (Criteria) this;
    }

    public Criteria andMessageIdLessThanOrEqualTo(String value) {
      addCriterion("message_id <=", value, "messageId");
      return (Criteria) this;
    }

    public Criteria andMessageIdLike(String value) {
      addCriterion("message_id like", value, "messageId");
      return (Criteria) this;
    }

    public Criteria andMessageIdNotLike(String value) {
      addCriterion("message_id not like", value, "messageId");
      return (Criteria) this;
    }

    public Criteria andMessageIdIn(List<String> values) {
      addCriterion("message_id in", values, "messageId");
      return (Criteria) this;
    }

    public Criteria andMessageIdNotIn(List<String> values) {
      addCriterion("message_id not in", values, "messageId");
      return (Criteria) this;
    }

    public Criteria andMessageIdBetween(String value1, String value2) {
      addCriterion("message_id between", value1, value2, "messageId");
      return (Criteria) this;
    }

    public Criteria andMessageIdNotBetween(String value1, String value2) {
      addCriterion("message_id not between", value1, value2, "messageId");
      return (Criteria) this;
    }

    public Criteria andCreatedIsNull() {
      addCriterion("created is null");
      return (Criteria) this;
    }

    public Criteria andCreatedIsNotNull() {
      addCriterion("created is not null");
      return (Criteria) this;
    }

    public Criteria andCreatedEqualTo(Long value) {
      addCriterion("created =", value, "created");
      return (Criteria) this;
    }

    public Criteria andCreatedNotEqualTo(Long value) {
      addCriterion("created <>", value, "created");
      return (Criteria) this;
    }

    public Criteria andCreatedGreaterThan(Long value) {
      addCriterion("created >", value, "created");
      return (Criteria) this;
    }

    public Criteria andCreatedGreaterThanOrEqualTo(Long value) {
      addCriterion("created >=", value, "created");
      return (Criteria) this;
    }

    public Criteria andCreatedLessThan(Long value) {
      addCriterion("created <", value, "created");
      return (Criteria) this;
    }

    public Criteria andCreatedLessThanOrEqualTo(Long value) {
      addCriterion("created <=", value, "created");
      return (Criteria) this;
    }

    public Criteria andCreatedIn(List<Long> values) {
      addCriterion("created in", values, "created");
      return (Criteria) this;
    }

    public Criteria andCreatedNotIn(List<Long> values) {
      addCriterion("created not in", values, "created");
      return (Criteria) this;
    }

    public Criteria andCreatedBetween(Long value1, Long value2) {
      addCriterion("created between", value1, value2, "created");
      return (Criteria) this;
    }

    public Criteria andCreatedNotBetween(Long value1, Long value2) {
      addCriterion("created not between", value1, value2, "created");
      return (Criteria) this;
    }

    public Criteria andModelIsNull() {
      addCriterion("model is null");
      return (Criteria) this;
    }

    public Criteria andModelIsNotNull() {
      addCriterion("model is not null");
      return (Criteria) this;
    }

    public Criteria andModelEqualTo(String value) {
      addCriterion("model =", value, "model");
      return (Criteria) this;
    }

    public Criteria andModelNotEqualTo(String value) {
      addCriterion("model <>", value, "model");
      return (Criteria) this;
    }

    public Criteria andModelGreaterThan(String value) {
      addCriterion("model >", value, "model");
      return (Criteria) this;
    }

    public Criteria andModelGreaterThanOrEqualTo(String value) {
      addCriterion("model >=", value, "model");
      return (Criteria) this;
    }

    public Criteria andModelLessThan(String value) {
      addCriterion("model <", value, "model");
      return (Criteria) this;
    }

    public Criteria andModelLessThanOrEqualTo(String value) {
      addCriterion("model <=", value, "model");
      return (Criteria) this;
    }

    public Criteria andModelLike(String value) {
      addCriterion("model like", value, "model");
      return (Criteria) this;
    }

    public Criteria andModelNotLike(String value) {
      addCriterion("model not like", value, "model");
      return (Criteria) this;
    }

    public Criteria andModelIn(List<String> values) {
      addCriterion("model in", values, "model");
      return (Criteria) this;
    }

    public Criteria andModelNotIn(List<String> values) {
      addCriterion("model not in", values, "model");
      return (Criteria) this;
    }

    public Criteria andModelBetween(String value1, String value2) {
      addCriterion("model between", value1, value2, "model");
      return (Criteria) this;
    }

    public Criteria andModelNotBetween(String value1, String value2) {
      addCriterion("model not between", value1, value2, "model");
      return (Criteria) this;
    }

    public Criteria andContextCountIsNull() {
      addCriterion("context_count is null");
      return (Criteria) this;
    }

    public Criteria andContextCountIsNotNull() {
      addCriterion("context_count is not null");
      return (Criteria) this;
    }

    public Criteria andContextCountEqualTo(Integer value) {
      addCriterion("context_count =", value, "contextCount");
      return (Criteria) this;
    }

    public Criteria andContextCountNotEqualTo(Integer value) {
      addCriterion("context_count <>", value, "contextCount");
      return (Criteria) this;
    }

    public Criteria andContextCountGreaterThan(Integer value) {
      addCriterion("context_count >", value, "contextCount");
      return (Criteria) this;
    }

    public Criteria andContextCountGreaterThanOrEqualTo(Integer value) {
      addCriterion("context_count >=", value, "contextCount");
      return (Criteria) this;
    }

    public Criteria andContextCountLessThan(Integer value) {
      addCriterion("context_count <", value, "contextCount");
      return (Criteria) this;
    }

    public Criteria andContextCountLessThanOrEqualTo(Integer value) {
      addCriterion("context_count <=", value, "contextCount");
      return (Criteria) this;
    }

    public Criteria andContextCountIn(List<Integer> values) {
      addCriterion("context_count in", values, "contextCount");
      return (Criteria) this;
    }

    public Criteria andContextCountNotIn(List<Integer> values) {
      addCriterion("context_count not in", values, "contextCount");
      return (Criteria) this;
    }

    public Criteria andContextCountBetween(Integer value1, Integer value2) {
      addCriterion("context_count between", value1, value2, "contextCount");
      return (Criteria) this;
    }

    public Criteria andContextCountNotBetween(Integer value1, Integer value2) {
      addCriterion("context_count not between", value1, value2, "contextCount");
      return (Criteria) this;
    }

    public Criteria andPromptTokensIsNull() {
      addCriterion("prompt_tokens is null");
      return (Criteria) this;
    }

    public Criteria andPromptTokensIsNotNull() {
      addCriterion("prompt_tokens is not null");
      return (Criteria) this;
    }

    public Criteria andPromptTokensEqualTo(Integer value) {
      addCriterion("prompt_tokens =", value, "promptTokens");
      return (Criteria) this;
    }

    public Criteria andPromptTokensNotEqualTo(Integer value) {
      addCriterion("prompt_tokens <>", value, "promptTokens");
      return (Criteria) this;
    }

    public Criteria andPromptTokensGreaterThan(Integer value) {
      addCriterion("prompt_tokens >", value, "promptTokens");
      return (Criteria) this;
    }

    public Criteria andPromptTokensGreaterThanOrEqualTo(Integer value) {
      addCriterion("prompt_tokens >=", value, "promptTokens");
      return (Criteria) this;
    }

    public Criteria andPromptTokensLessThan(Integer value) {
      addCriterion("prompt_tokens <", value, "promptTokens");
      return (Criteria) this;
    }

    public Criteria andPromptTokensLessThanOrEqualTo(Integer value) {
      addCriterion("prompt_tokens <=", value, "promptTokens");
      return (Criteria) this;
    }

    public Criteria andPromptTokensIn(List<Integer> values) {
      addCriterion("prompt_tokens in", values, "promptTokens");
      return (Criteria) this;
    }

    public Criteria andPromptTokensNotIn(List<Integer> values) {
      addCriterion("prompt_tokens not in", values, "promptTokens");
      return (Criteria) this;
    }

    public Criteria andPromptTokensBetween(Integer value1, Integer value2) {
      addCriterion("prompt_tokens between", value1, value2, "promptTokens");
      return (Criteria) this;
    }

    public Criteria andPromptTokensNotBetween(Integer value1, Integer value2) {
      addCriterion("prompt_tokens not between", value1, value2, "promptTokens");
      return (Criteria) this;
    }

    public Criteria andStatusIsNull() {
      addCriterion("`status` is null");
      return (Criteria) this;
    }

    public Criteria andStatusIsNotNull() {
      addCriterion("`status` is not null");
      return (Criteria) this;
    }

    public Criteria andStatusEqualTo(Byte value) {
      addCriterion("`status` =", value, "status");
      return (Criteria) this;
    }

    public Criteria andStatusNotEqualTo(Byte value) {
      addCriterion("`status` <>", value, "status");
      return (Criteria) this;
    }

    public Criteria andStatusGreaterThan(Byte value) {
      addCriterion("`status` >", value, "status");
      return (Criteria) this;
    }

    public Criteria andStatusGreaterThanOrEqualTo(Byte value) {
      addCriterion("`status` >=", value, "status");
      return (Criteria) this;
    }

    public Criteria andStatusLessThan(Byte value) {
      addCriterion("`status` <", value, "status");
      return (Criteria) this;
    }

    public Criteria andStatusLessThanOrEqualTo(Byte value) {
      addCriterion("`status` <=", value, "status");
      return (Criteria) this;
    }

    public Criteria andStatusIn(List<Byte> values) {
      addCriterion("`status` in", values, "status");
      return (Criteria) this;
    }

    public Criteria andStatusNotIn(List<Byte> values) {
      addCriterion("`status` not in", values, "status");
      return (Criteria) this;
    }

    public Criteria andStatusBetween(Byte value1, Byte value2) {
      addCriterion("`status` between", value1, value2, "status");
      return (Criteria) this;
    }

    public Criteria andStatusNotBetween(Byte value1, Byte value2) {
      addCriterion("`status` not between", value1, value2, "status");
      return (Criteria) this;
    }

    public Criteria andCreateTimeIsNull() {
      addCriterion("create_time is null");
      return (Criteria) this;
    }

    public Criteria andCreateTimeIsNotNull() {
      addCriterion("create_time is not null");
      return (Criteria) this;
    }

    public Criteria andCreateTimeEqualTo(Date value) {
      addCriterion("create_time =", value, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeNotEqualTo(Date value) {
      addCriterion("create_time <>", value, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeGreaterThan(Date value) {
      addCriterion("create_time >", value, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
      addCriterion("create_time >=", value, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeLessThan(Date value) {
      addCriterion("create_time <", value, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
      addCriterion("create_time <=", value, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeIn(List<Date> values) {
      addCriterion("create_time in", values, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeNotIn(List<Date> values) {
      addCriterion("create_time not in", values, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeBetween(Date value1, Date value2) {
      addCriterion("create_time between", value1, value2, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
      addCriterion("create_time not between", value1, value2, "createTime");
      return (Criteria) this;
    }
  }

  public static class Criteria extends GeneratedCriteria {

    protected Criteria() {
      super();
    }
  }

  public static class Criterion {

    private String condition;

    private Object value;

    private Object secondValue;

    private boolean noValue;

    private boolean singleValue;

    private boolean betweenValue;

    private boolean listValue;

    private String typeHandler;

    public String getCondition() {
      return condition;
    }

    public Object getValue() {
      return value;
    }

    public Object getSecondValue() {
      return secondValue;
    }

    public boolean isNoValue() {
      return noValue;
    }

    public boolean isSingleValue() {
      return singleValue;
    }

    public boolean isBetweenValue() {
      return betweenValue;
    }

    public boolean isListValue() {
      return listValue;
    }

    public String getTypeHandler() {
      return typeHandler;
    }

    protected Criterion(String condition) {
      super();
      this.condition = condition;
      this.typeHandler = null;
      this.noValue = true;
    }

    protected Criterion(String condition, Object value, String typeHandler) {
      super();
      this.condition = condition;
      this.value = value;
      this.typeHandler = typeHandler;
      if (value instanceof List<?>) {
        this.listValue = true;
      }
      else {
        this.singleValue = true;
      }
    }

    protected Criterion(String condition, Object value) {
      this(condition, value, null);
    }

    protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
      super();
      this.condition = condition;
      this.value = value;
      this.secondValue = secondValue;
      this.typeHandler = typeHandler;
      this.betweenValue = true;
    }

    protected Criterion(String condition, Object value, Object secondValue) {
      this(condition, value, secondValue, null);
    }
  }
}