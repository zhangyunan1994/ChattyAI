package cike.openai;

import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.EncodingType;
import com.knuddels.jtokkit.api.ModelType;
import com.theokanning.openai.completion.chat.ChatMessage;
import java.util.List;

public class TokenizerUtil {

  public static int tokenCount(String token) {
    EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();
    Encoding enc = registry.getEncoding(EncodingType.CL100K_BASE);
    return enc.countTokens(token);
  }

  public static int numTokensFromMessages(List<ChatMessage> messages, ModelType modelType) {

    int tokens_per_message = 0;
    int tokens_per_name = 0;
    int num_tokens = 0;

    if (modelType == ModelType.GPT_3_5_TURBO) {
      tokens_per_message = 4;
      tokens_per_name = -1;
    }
    else if (modelType == ModelType.GPT_4) {
      tokens_per_message = 3;
      tokens_per_name = 1;
    }

    for (ChatMessage message : messages) {
      num_tokens += tokens_per_message;
      num_tokens += tokenCount(message.getRole());
      num_tokens += tokenCount(message.getContent());
    }

    num_tokens += 3;
    return num_tokens;
  }

}
