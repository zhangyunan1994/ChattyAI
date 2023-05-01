package cike.chatgpt.utils;


import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class CollectionUtil {

  private CollectionUtil() {}

  public static boolean isNotEmpty(Collection coll) {
    return !isEmpty(coll);
  }

  public static boolean isNotEmpty(Map map) {
    return !isEmpty(map);
  }

  public static boolean isEmpty(Collection coll) {
    return coll == null || coll.isEmpty();
  }

  public static boolean isEmpty(Map map) {
    return map == null || map.isEmpty();
  }

  public static <T> boolean isElementEquals(Set<T> set1, Set<T> set2) {
    if (set1 == set2) {
      return true;
    }

    if (set1 == null || set2 == null) {
      return false;
    }

    if (set1.size() != set2.size()) {
      return false;
    }

    for (T t : set1) {
      if (!set2.contains(t)) {
        return false;
      }
    }

    return true;
  }

  public static <T> T getFirstElseNull(List<T> params) {
    if (CollectionUtil.isEmpty(params)) {
      return null;
    }
    return params.get(0);
  }

  public static <T> T getLastElseNull(List<T> params) {
    if (CollectionUtil.isEmpty(params)) {
      return null;
    }
    return params.get(params.size() - 1);
  }

  static <T> Collection<T> cast(Iterable<T> iterable) {
    return (Collection<T>) iterable;
  }

  public static <T> boolean safeSetContains(Set<T> set, T e) {
    if (CollectionUtil.isEmpty(set)) {
      return false;
    }
    return set.contains(e);
  }

  public static <T> boolean contains(T param, T... params) {
    for (T t : params) {
      if (param == t || param.equals(t)) {
        return true;
      }
    }
    return false;
  }

  public static Map<String, Object> copyMap(Map<String, Object> params) {
    Map<String, Object> cloneParams = new HashMap<>();
    cloneParams.putAll(params);
    return cloneParams;
  }
}


