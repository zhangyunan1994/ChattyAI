package cike.chatgpt.controller;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class PageList<T> {

  private int currentPage;
  private int pageSize;
  private long totalCount;
  private int totalPage;
  private List<T> dataList;

  public static <T> PageList<T> of(int currentPage, int pageSize, long totalCount, List<T> dataList) {
    return new PageList<>(currentPage, pageSize, totalCount, dataList);
  }

  public PageList(int currentPage, int pageSize, long totalCount, List<T> dataList) {
    this.currentPage = currentPage;
    this.pageSize = pageSize;
    this.totalCount = totalCount;
    this.totalPage = (int) (totalCount / pageSize + (totalCount % pageSize > 0L ? 1 : 0));
    this.dataList = dataList;
  }

}