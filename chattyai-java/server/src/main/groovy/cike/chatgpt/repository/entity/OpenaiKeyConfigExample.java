package cike.chatgpt.repository.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpenaiKeyConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpenaiKeyConfigExample() {
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

        public Criteria andAccountIdIsNull() {
            addCriterion("account_id is null");
            return (Criteria) this;
        }

        public Criteria andAccountIdIsNotNull() {
            addCriterion("account_id is not null");
            return (Criteria) this;
        }

        public Criteria andAccountIdEqualTo(String value) {
            addCriterion("account_id =", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotEqualTo(String value) {
            addCriterion("account_id <>", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdGreaterThan(String value) {
            addCriterion("account_id >", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdGreaterThanOrEqualTo(String value) {
            addCriterion("account_id >=", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdLessThan(String value) {
            addCriterion("account_id <", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdLessThanOrEqualTo(String value) {
            addCriterion("account_id <=", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdLike(String value) {
            addCriterion("account_id like", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotLike(String value) {
            addCriterion("account_id not like", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdIn(List<String> values) {
            addCriterion("account_id in", values, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotIn(List<String> values) {
            addCriterion("account_id not in", values, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdBetween(String value1, String value2) {
            addCriterion("account_id between", value1, value2, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotBetween(String value1, String value2) {
            addCriterion("account_id not between", value1, value2, "accountId");
            return (Criteria) this;
        }

        public Criteria andOpenaiKeyIsNull() {
            addCriterion("openai_key is null");
            return (Criteria) this;
        }

        public Criteria andOpenaiKeyIsNotNull() {
            addCriterion("openai_key is not null");
            return (Criteria) this;
        }

        public Criteria andOpenaiKeyEqualTo(String value) {
            addCriterion("openai_key =", value, "openaiKey");
            return (Criteria) this;
        }

        public Criteria andOpenaiKeyNotEqualTo(String value) {
            addCriterion("openai_key <>", value, "openaiKey");
            return (Criteria) this;
        }

        public Criteria andOpenaiKeyGreaterThan(String value) {
            addCriterion("openai_key >", value, "openaiKey");
            return (Criteria) this;
        }

        public Criteria andOpenaiKeyGreaterThanOrEqualTo(String value) {
            addCriterion("openai_key >=", value, "openaiKey");
            return (Criteria) this;
        }

        public Criteria andOpenaiKeyLessThan(String value) {
            addCriterion("openai_key <", value, "openaiKey");
            return (Criteria) this;
        }

        public Criteria andOpenaiKeyLessThanOrEqualTo(String value) {
            addCriterion("openai_key <=", value, "openaiKey");
            return (Criteria) this;
        }

        public Criteria andOpenaiKeyLike(String value) {
            addCriterion("openai_key like", value, "openaiKey");
            return (Criteria) this;
        }

        public Criteria andOpenaiKeyNotLike(String value) {
            addCriterion("openai_key not like", value, "openaiKey");
            return (Criteria) this;
        }

        public Criteria andOpenaiKeyIn(List<String> values) {
            addCriterion("openai_key in", values, "openaiKey");
            return (Criteria) this;
        }

        public Criteria andOpenaiKeyNotIn(List<String> values) {
            addCriterion("openai_key not in", values, "openaiKey");
            return (Criteria) this;
        }

        public Criteria andOpenaiKeyBetween(String value1, String value2) {
            addCriterion("openai_key between", value1, value2, "openaiKey");
            return (Criteria) this;
        }

        public Criteria andOpenaiKeyNotBetween(String value1, String value2) {
            addCriterion("openai_key not between", value1, value2, "openaiKey");
            return (Criteria) this;
        }

        public Criteria andTotalUseTokenIsNull() {
            addCriterion("total_use_token is null");
            return (Criteria) this;
        }

        public Criteria andTotalUseTokenIsNotNull() {
            addCriterion("total_use_token is not null");
            return (Criteria) this;
        }

        public Criteria andTotalUseTokenEqualTo(Integer value) {
            addCriterion("total_use_token =", value, "totalUseToken");
            return (Criteria) this;
        }

        public Criteria andTotalUseTokenNotEqualTo(Integer value) {
            addCriterion("total_use_token <>", value, "totalUseToken");
            return (Criteria) this;
        }

        public Criteria andTotalUseTokenGreaterThan(Integer value) {
            addCriterion("total_use_token >", value, "totalUseToken");
            return (Criteria) this;
        }

        public Criteria andTotalUseTokenGreaterThanOrEqualTo(Integer value) {
            addCriterion("total_use_token >=", value, "totalUseToken");
            return (Criteria) this;
        }

        public Criteria andTotalUseTokenLessThan(Integer value) {
            addCriterion("total_use_token <", value, "totalUseToken");
            return (Criteria) this;
        }

        public Criteria andTotalUseTokenLessThanOrEqualTo(Integer value) {
            addCriterion("total_use_token <=", value, "totalUseToken");
            return (Criteria) this;
        }

        public Criteria andTotalUseTokenIn(List<Integer> values) {
            addCriterion("total_use_token in", values, "totalUseToken");
            return (Criteria) this;
        }

        public Criteria andTotalUseTokenNotIn(List<Integer> values) {
            addCriterion("total_use_token not in", values, "totalUseToken");
            return (Criteria) this;
        }

        public Criteria andTotalUseTokenBetween(Integer value1, Integer value2) {
            addCriterion("total_use_token between", value1, value2, "totalUseToken");
            return (Criteria) this;
        }

        public Criteria andTotalUseTokenNotBetween(Integer value1, Integer value2) {
            addCriterion("total_use_token not between", value1, value2, "totalUseToken");
            return (Criteria) this;
        }

        public Criteria andTotalUseMoneyIsNull() {
            addCriterion("total_use_money is null");
            return (Criteria) this;
        }

        public Criteria andTotalUseMoneyIsNotNull() {
            addCriterion("total_use_money is not null");
            return (Criteria) this;
        }

        public Criteria andTotalUseMoneyEqualTo(Integer value) {
            addCriterion("total_use_money =", value, "totalUseMoney");
            return (Criteria) this;
        }

        public Criteria andTotalUseMoneyNotEqualTo(Integer value) {
            addCriterion("total_use_money <>", value, "totalUseMoney");
            return (Criteria) this;
        }

        public Criteria andTotalUseMoneyGreaterThan(Integer value) {
            addCriterion("total_use_money >", value, "totalUseMoney");
            return (Criteria) this;
        }

        public Criteria andTotalUseMoneyGreaterThanOrEqualTo(Integer value) {
            addCriterion("total_use_money >=", value, "totalUseMoney");
            return (Criteria) this;
        }

        public Criteria andTotalUseMoneyLessThan(Integer value) {
            addCriterion("total_use_money <", value, "totalUseMoney");
            return (Criteria) this;
        }

        public Criteria andTotalUseMoneyLessThanOrEqualTo(Integer value) {
            addCriterion("total_use_money <=", value, "totalUseMoney");
            return (Criteria) this;
        }

        public Criteria andTotalUseMoneyIn(List<Integer> values) {
            addCriterion("total_use_money in", values, "totalUseMoney");
            return (Criteria) this;
        }

        public Criteria andTotalUseMoneyNotIn(List<Integer> values) {
            addCriterion("total_use_money not in", values, "totalUseMoney");
            return (Criteria) this;
        }

        public Criteria andTotalUseMoneyBetween(Integer value1, Integer value2) {
            addCriterion("total_use_money between", value1, value2, "totalUseMoney");
            return (Criteria) this;
        }

        public Criteria andTotalUseMoneyNotBetween(Integer value1, Integer value2) {
            addCriterion("total_use_money not between", value1, value2, "totalUseMoney");
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

        public Criteria andExpiredTimeIsNull() {
            addCriterion("expired_time is null");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeIsNotNull() {
            addCriterion("expired_time is not null");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeEqualTo(Date value) {
            addCriterion("expired_time =", value, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeNotEqualTo(Date value) {
            addCriterion("expired_time <>", value, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeGreaterThan(Date value) {
            addCriterion("expired_time >", value, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("expired_time >=", value, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeLessThan(Date value) {
            addCriterion("expired_time <", value, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeLessThanOrEqualTo(Date value) {
            addCriterion("expired_time <=", value, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeIn(List<Date> values) {
            addCriterion("expired_time in", values, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeNotIn(List<Date> values) {
            addCriterion("expired_time not in", values, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeBetween(Date value1, Date value2) {
            addCriterion("expired_time between", value1, value2, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeNotBetween(Date value1, Date value2) {
            addCriterion("expired_time not between", value1, value2, "expiredTime");
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