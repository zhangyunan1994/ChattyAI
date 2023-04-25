package cike.openai.dashboard.billing;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Usage {

  @JsonProperty("total_usage")
  private Double totalUsage;
}
