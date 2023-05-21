package cike.chatgpt.repository.mapper.specific;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChatgptMessageRecordSpecificMapper {

  List<ChatgptMessageSpecificRecord> find(@Param("username") String username, @Param("searchText") String searchText,
      @Param("startTime") String startTime, @Param("endTime") String endTime);
}