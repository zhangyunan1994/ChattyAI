package cike.openai.dashboard.billing;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreditGrants {

  @JsonProperty("total_granted")
  private String totalGranted;

  @JsonProperty("total_used")
  private String totalUsed;

  @JsonProperty("total_available")
  private String totalAvailable;

  public String getTotalGranted() {
    return totalGranted;
  }

  public void setTotalGranted(String totalGranted) {
    this.totalGranted = totalGranted;
  }

  public String getTotalUsed() {
    return totalUsed;
  }

  public void setTotalUsed(String totalUsed) {
    this.totalUsed = totalUsed;
  }

  public String getTotalAvailable() {
    return totalAvailable;
  }

  public void setTotalAvailable(String totalAvailable) {
    this.totalAvailable = totalAvailable;
  }

  @Override
  public String toString() {
    return "CreditGrants{" +
        "totalGranted='" + totalGranted + '\'' +
        ", totalUsed='" + totalUsed + '\'' +
        ", totalAvailable='" + totalAvailable + '\'' +
        '}';
  }
}
