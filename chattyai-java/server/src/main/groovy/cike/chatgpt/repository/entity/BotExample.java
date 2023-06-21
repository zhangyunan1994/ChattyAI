package cike.chatgpt.repository.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BotExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BotExample() {
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

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andBotIdIsNull() {
            addCriterion("bot_id is null");
            return (Criteria) this;
        }

        public Criteria andBotIdIsNotNull() {
            addCriterion("bot_id is not null");
            return (Criteria) this;
        }

        public Criteria andBotIdEqualTo(String value) {
            addCriterion("bot_id =", value, "botId");
            return (Criteria) this;
        }

        public Criteria andBotIdNotEqualTo(String value) {
            addCriterion("bot_id <>", value, "botId");
            return (Criteria) this;
        }

        public Criteria andBotIdGreaterThan(String value) {
            addCriterion("bot_id >", value, "botId");
            return (Criteria) this;
        }

        public Criteria andBotIdGreaterThanOrEqualTo(String value) {
            addCriterion("bot_id >=", value, "botId");
            return (Criteria) this;
        }

        public Criteria andBotIdLessThan(String value) {
            addCriterion("bot_id <", value, "botId");
            return (Criteria) this;
        }

        public Criteria andBotIdLessThanOrEqualTo(String value) {
            addCriterion("bot_id <=", value, "botId");
            return (Criteria) this;
        }

        public Criteria andBotIdLike(String value) {
            addCriterion("bot_id like", value, "botId");
            return (Criteria) this;
        }

        public Criteria andBotIdNotLike(String value) {
            addCriterion("bot_id not like", value, "botId");
            return (Criteria) this;
        }

        public Criteria andBotIdIn(List<String> values) {
            addCriterion("bot_id in", values, "botId");
            return (Criteria) this;
        }

        public Criteria andBotIdNotIn(List<String> values) {
            addCriterion("bot_id not in", values, "botId");
            return (Criteria) this;
        }

        public Criteria andBotIdBetween(String value1, String value2) {
            addCriterion("bot_id between", value1, value2, "botId");
            return (Criteria) this;
        }

        public Criteria andBotIdNotBetween(String value1, String value2) {
            addCriterion("bot_id not between", value1, value2, "botId");
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

        public Criteria andAccessLevelIsNull() {
            addCriterion("access_level is null");
            return (Criteria) this;
        }

        public Criteria andAccessLevelIsNotNull() {
            addCriterion("access_level is not null");
            return (Criteria) this;
        }

        public Criteria andAccessLevelEqualTo(Byte value) {
            addCriterion("access_level =", value, "accessLevel");
            return (Criteria) this;
        }

        public Criteria andAccessLevelNotEqualTo(Byte value) {
            addCriterion("access_level <>", value, "accessLevel");
            return (Criteria) this;
        }

        public Criteria andAccessLevelGreaterThan(Byte value) {
            addCriterion("access_level >", value, "accessLevel");
            return (Criteria) this;
        }

        public Criteria andAccessLevelGreaterThanOrEqualTo(Byte value) {
            addCriterion("access_level >=", value, "accessLevel");
            return (Criteria) this;
        }

        public Criteria andAccessLevelLessThan(Byte value) {
            addCriterion("access_level <", value, "accessLevel");
            return (Criteria) this;
        }

        public Criteria andAccessLevelLessThanOrEqualTo(Byte value) {
            addCriterion("access_level <=", value, "accessLevel");
            return (Criteria) this;
        }

        public Criteria andAccessLevelIn(List<Byte> values) {
            addCriterion("access_level in", values, "accessLevel");
            return (Criteria) this;
        }

        public Criteria andAccessLevelNotIn(List<Byte> values) {
            addCriterion("access_level not in", values, "accessLevel");
            return (Criteria) this;
        }

        public Criteria andAccessLevelBetween(Byte value1, Byte value2) {
            addCriterion("access_level between", value1, value2, "accessLevel");
            return (Criteria) this;
        }

        public Criteria andAccessLevelNotBetween(Byte value1, Byte value2) {
            addCriterion("access_level not between", value1, value2, "accessLevel");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("`name` is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("`name` is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("`name` =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("`name` <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("`name` >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("`name` >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("`name` <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("`name` <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("`name` like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("`name` not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("`name` in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("`name` not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("`name` between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("`name` not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andAvatarIsNull() {
            addCriterion("avatar is null");
            return (Criteria) this;
        }

        public Criteria andAvatarIsNotNull() {
            addCriterion("avatar is not null");
            return (Criteria) this;
        }

        public Criteria andAvatarEqualTo(String value) {
            addCriterion("avatar =", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarNotEqualTo(String value) {
            addCriterion("avatar <>", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarGreaterThan(String value) {
            addCriterion("avatar >", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarGreaterThanOrEqualTo(String value) {
            addCriterion("avatar >=", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarLessThan(String value) {
            addCriterion("avatar <", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarLessThanOrEqualTo(String value) {
            addCriterion("avatar <=", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarLike(String value) {
            addCriterion("avatar like", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarNotLike(String value) {
            addCriterion("avatar not like", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarIn(List<String> values) {
            addCriterion("avatar in", values, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarNotIn(List<String> values) {
            addCriterion("avatar not in", values, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarBetween(String value1, String value2) {
            addCriterion("avatar between", value1, value2, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarNotBetween(String value1, String value2) {
            addCriterion("avatar not between", value1, value2, "avatar");
            return (Criteria) this;
        }

        public Criteria andSystemPromptIsNull() {
            addCriterion("system_prompt is null");
            return (Criteria) this;
        }

        public Criteria andSystemPromptIsNotNull() {
            addCriterion("system_prompt is not null");
            return (Criteria) this;
        }

        public Criteria andSystemPromptEqualTo(String value) {
            addCriterion("system_prompt =", value, "systemPrompt");
            return (Criteria) this;
        }

        public Criteria andSystemPromptNotEqualTo(String value) {
            addCriterion("system_prompt <>", value, "systemPrompt");
            return (Criteria) this;
        }

        public Criteria andSystemPromptGreaterThan(String value) {
            addCriterion("system_prompt >", value, "systemPrompt");
            return (Criteria) this;
        }

        public Criteria andSystemPromptGreaterThanOrEqualTo(String value) {
            addCriterion("system_prompt >=", value, "systemPrompt");
            return (Criteria) this;
        }

        public Criteria andSystemPromptLessThan(String value) {
            addCriterion("system_prompt <", value, "systemPrompt");
            return (Criteria) this;
        }

        public Criteria andSystemPromptLessThanOrEqualTo(String value) {
            addCriterion("system_prompt <=", value, "systemPrompt");
            return (Criteria) this;
        }

        public Criteria andSystemPromptLike(String value) {
            addCriterion("system_prompt like", value, "systemPrompt");
            return (Criteria) this;
        }

        public Criteria andSystemPromptNotLike(String value) {
            addCriterion("system_prompt not like", value, "systemPrompt");
            return (Criteria) this;
        }

        public Criteria andSystemPromptIn(List<String> values) {
            addCriterion("system_prompt in", values, "systemPrompt");
            return (Criteria) this;
        }

        public Criteria andSystemPromptNotIn(List<String> values) {
            addCriterion("system_prompt not in", values, "systemPrompt");
            return (Criteria) this;
        }

        public Criteria andSystemPromptBetween(String value1, String value2) {
            addCriterion("system_prompt between", value1, value2, "systemPrompt");
            return (Criteria) this;
        }

        public Criteria andSystemPromptNotBetween(String value1, String value2) {
            addCriterion("system_prompt not between", value1, value2, "systemPrompt");
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

        public Criteria andMaxTokensIsNull() {
            addCriterion("max_tokens is null");
            return (Criteria) this;
        }

        public Criteria andMaxTokensIsNotNull() {
            addCriterion("max_tokens is not null");
            return (Criteria) this;
        }

        public Criteria andMaxTokensEqualTo(Integer value) {
            addCriterion("max_tokens =", value, "maxTokens");
            return (Criteria) this;
        }

        public Criteria andMaxTokensNotEqualTo(Integer value) {
            addCriterion("max_tokens <>", value, "maxTokens");
            return (Criteria) this;
        }

        public Criteria andMaxTokensGreaterThan(Integer value) {
            addCriterion("max_tokens >", value, "maxTokens");
            return (Criteria) this;
        }

        public Criteria andMaxTokensGreaterThanOrEqualTo(Integer value) {
            addCriterion("max_tokens >=", value, "maxTokens");
            return (Criteria) this;
        }

        public Criteria andMaxTokensLessThan(Integer value) {
            addCriterion("max_tokens <", value, "maxTokens");
            return (Criteria) this;
        }

        public Criteria andMaxTokensLessThanOrEqualTo(Integer value) {
            addCriterion("max_tokens <=", value, "maxTokens");
            return (Criteria) this;
        }

        public Criteria andMaxTokensIn(List<Integer> values) {
            addCriterion("max_tokens in", values, "maxTokens");
            return (Criteria) this;
        }

        public Criteria andMaxTokensNotIn(List<Integer> values) {
            addCriterion("max_tokens not in", values, "maxTokens");
            return (Criteria) this;
        }

        public Criteria andMaxTokensBetween(Integer value1, Integer value2) {
            addCriterion("max_tokens between", value1, value2, "maxTokens");
            return (Criteria) this;
        }

        public Criteria andMaxTokensNotBetween(Integer value1, Integer value2) {
            addCriterion("max_tokens not between", value1, value2, "maxTokens");
            return (Criteria) this;
        }

        public Criteria andTemperatureIsNull() {
            addCriterion("temperature is null");
            return (Criteria) this;
        }

        public Criteria andTemperatureIsNotNull() {
            addCriterion("temperature is not null");
            return (Criteria) this;
        }

        public Criteria andTemperatureEqualTo(Integer value) {
            addCriterion("temperature =", value, "temperature");
            return (Criteria) this;
        }

        public Criteria andTemperatureNotEqualTo(Integer value) {
            addCriterion("temperature <>", value, "temperature");
            return (Criteria) this;
        }

        public Criteria andTemperatureGreaterThan(Integer value) {
            addCriterion("temperature >", value, "temperature");
            return (Criteria) this;
        }

        public Criteria andTemperatureGreaterThanOrEqualTo(Integer value) {
            addCriterion("temperature >=", value, "temperature");
            return (Criteria) this;
        }

        public Criteria andTemperatureLessThan(Integer value) {
            addCriterion("temperature <", value, "temperature");
            return (Criteria) this;
        }

        public Criteria andTemperatureLessThanOrEqualTo(Integer value) {
            addCriterion("temperature <=", value, "temperature");
            return (Criteria) this;
        }

        public Criteria andTemperatureIn(List<Integer> values) {
            addCriterion("temperature in", values, "temperature");
            return (Criteria) this;
        }

        public Criteria andTemperatureNotIn(List<Integer> values) {
            addCriterion("temperature not in", values, "temperature");
            return (Criteria) this;
        }

        public Criteria andTemperatureBetween(Integer value1, Integer value2) {
            addCriterion("temperature between", value1, value2, "temperature");
            return (Criteria) this;
        }

        public Criteria andTemperatureNotBetween(Integer value1, Integer value2) {
            addCriterion("temperature not between", value1, value2, "temperature");
            return (Criteria) this;
        }

        public Criteria andTopPIsNull() {
            addCriterion("top_p is null");
            return (Criteria) this;
        }

        public Criteria andTopPIsNotNull() {
            addCriterion("top_p is not null");
            return (Criteria) this;
        }

        public Criteria andTopPEqualTo(Integer value) {
            addCriterion("top_p =", value, "topP");
            return (Criteria) this;
        }

        public Criteria andTopPNotEqualTo(Integer value) {
            addCriterion("top_p <>", value, "topP");
            return (Criteria) this;
        }

        public Criteria andTopPGreaterThan(Integer value) {
            addCriterion("top_p >", value, "topP");
            return (Criteria) this;
        }

        public Criteria andTopPGreaterThanOrEqualTo(Integer value) {
            addCriterion("top_p >=", value, "topP");
            return (Criteria) this;
        }

        public Criteria andTopPLessThan(Integer value) {
            addCriterion("top_p <", value, "topP");
            return (Criteria) this;
        }

        public Criteria andTopPLessThanOrEqualTo(Integer value) {
            addCriterion("top_p <=", value, "topP");
            return (Criteria) this;
        }

        public Criteria andTopPIn(List<Integer> values) {
            addCriterion("top_p in", values, "topP");
            return (Criteria) this;
        }

        public Criteria andTopPNotIn(List<Integer> values) {
            addCriterion("top_p not in", values, "topP");
            return (Criteria) this;
        }

        public Criteria andTopPBetween(Integer value1, Integer value2) {
            addCriterion("top_p between", value1, value2, "topP");
            return (Criteria) this;
        }

        public Criteria andTopPNotBetween(Integer value1, Integer value2) {
            addCriterion("top_p not between", value1, value2, "topP");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
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

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
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
            } else {
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