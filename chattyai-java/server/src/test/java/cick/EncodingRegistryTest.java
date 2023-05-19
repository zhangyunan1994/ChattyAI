package cick;

import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.EncodingType;
import com.knuddels.jtokkit.api.ModelType;
import java.util.List;
import org.junit.jupiter.api.Test;

public class EncodingRegistryTest {

  @Test
  public void testEncoding() throws Exception {
    EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();
    Encoding enc = registry.getEncoding(EncodingType.CL100K_BASE);
    List<Integer> encoded = enc.encode("假设段锴启去日本大阪的风俗街玩，出了事故，被日本警方拘留，该怎么处理？");
    System.out.println(encoded);
// encoded = [2028, 374, 264, 6205, 11914, 13]

    String decoded = enc.decode(encoded);
// decoded = "This is a sample sentence."

// Or get the tokenizer based on the model type
    Encoding secondEnc = registry.getEncodingForModel(ModelType.GPT_3_5_TURBO);

  }

}
