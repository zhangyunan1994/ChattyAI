package cike.openai;

import cike.openai.dashboard.billing.Usage;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.theokanning.openai.DeleteResult;
import com.theokanning.openai.OpenAiApi;
import com.theokanning.openai.completion.CompletionChunk;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.completion.chat.ChatCompletionChunk;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.edit.EditRequest;
import com.theokanning.openai.edit.EditResult;
import com.theokanning.openai.embedding.EmbeddingRequest;
import com.theokanning.openai.embedding.EmbeddingResult;
import com.theokanning.openai.file.File;
import com.theokanning.openai.finetune.FineTuneEvent;
import com.theokanning.openai.finetune.FineTuneRequest;
import com.theokanning.openai.finetune.FineTuneResult;
import com.theokanning.openai.image.CreateImageEditRequest;
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.image.CreateImageVariationRequest;
import com.theokanning.openai.image.ImageResult;
import com.theokanning.openai.model.Model;
import com.theokanning.openai.moderation.ModerationRequest;
import com.theokanning.openai.moderation.ModerationResult;
import com.theokanning.openai.service.OpenAiService;
import io.reactivex.Flowable;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ChattyAIService {

  private final OpenAIExtendService extendService;

  private final OpenAiService openAiService;

  public static final String BASE_URL = "https://api.openai.com/";
  private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);

  public ChattyAIService(final String token) {
    this(token, DEFAULT_TIMEOUT, BASE_URL);
  }

  public ChattyAIService(final String token, final Duration timeout) {
    this(token, timeout, BASE_URL);
  }

  public ChattyAIService(final String token, final Duration timeout, final String baseUrl) {
    this.extendService = new OpenAIExtendService(token, timeout, baseUrl);

    ObjectMapper mapper = defaultObjectMapper();
    OkHttpClient client = defaultClient(token, timeout);
    Retrofit retrofit = defaultRetrofit(client, mapper, baseUrl);

    OpenAiApi api = retrofit.create(OpenAiApi.class);
    this.openAiService = new OpenAiService(api);
  }

  public static ObjectMapper defaultObjectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    return mapper;
  }

  public static OkHttpClient defaultClient(String token, Duration timeout) {
    return new OkHttpClient.Builder()
        .addInterceptor(new AuthenticationInterceptor(token))
        .connectionPool(new ConnectionPool(5, 1, TimeUnit.SECONDS))
        .readTimeout(timeout.toMillis(), TimeUnit.MILLISECONDS)
        .build();
  }

  public static Retrofit defaultRetrofit(OkHttpClient client, ObjectMapper mapper, String baseUrl) {
    return new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(JacksonConverterFactory.create(mapper))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();
  }

  public Usage dashboardBillingUsage(LocalDate startDate, LocalDate endDate) {
    return extendService.dashboardBillingUsage(startDate, endDate);
  }

  public List<Model> listModels() {
    return openAiService.listModels();
  }

  public Model getModel(String modelId) {
    return openAiService.getModel(modelId);
  }

  public CompletionResult createCompletion(CompletionRequest request) {
    return openAiService.createCompletion(request);
  }

  public Flowable<CompletionChunk> streamCompletion(CompletionRequest request) {
    return openAiService.streamCompletion(request);
  }

  public ChatCompletionResult createChatCompletion(ChatCompletionRequest request) {
    return openAiService.createChatCompletion(request);
  }

  public Flowable<ChatCompletionChunk> streamChatCompletion(ChatCompletionRequest request) {
    return openAiService.streamChatCompletion(request);
  }

  public EditResult createEdit(EditRequest request) {
    return openAiService.createEdit(request);
  }

  public EmbeddingResult createEmbeddings(EmbeddingRequest request) {
    return openAiService.createEmbeddings(request);
  }

  public List<File> listFiles() {
    return openAiService.listFiles();
  }

  public File uploadFile(String purpose, String filepath) {
    return openAiService.uploadFile(purpose, filepath);
  }

  public DeleteResult deleteFile(String fileId) {
    return openAiService.deleteFile(fileId);
  }

  public File retrieveFile(String fileId) {
    return openAiService.retrieveFile(fileId);
  }

  public FineTuneResult createFineTune(FineTuneRequest request) {
    return openAiService.createFineTune(request);
  }

  public CompletionResult createFineTuneCompletion(CompletionRequest request) {
    return openAiService.createFineTuneCompletion(request);
  }

  public List<FineTuneResult> listFineTunes() {
    return openAiService.listFineTunes();
  }

  public FineTuneResult retrieveFineTune(String fineTuneId) {
    return openAiService.retrieveFineTune(fineTuneId);
  }

  public FineTuneResult cancelFineTune(String fineTuneId) {
    return openAiService.cancelFineTune(fineTuneId);
  }

  public List<FineTuneEvent> listFineTuneEvents(String fineTuneId) {
    return openAiService.listFineTuneEvents(fineTuneId);
  }

  public DeleteResult deleteFineTune(String fineTuneId) {
    return openAiService.deleteFineTune(fineTuneId);
  }

  public ImageResult createImage(CreateImageRequest request) {
    return openAiService.createImage(request);
  }

  public ImageResult createImageEdit(CreateImageEditRequest request, String imagePath, String maskPath) {
    return openAiService.createImageEdit(request, imagePath, maskPath);
  }

  public ImageResult createImageEdit(CreateImageEditRequest request, java.io.File image, java.io.File mask) {
    return openAiService.createImageEdit(request, image, mask);
  }

  public ImageResult createImageVariation(CreateImageVariationRequest request, String imagePath) {
    return openAiService.createImageVariation(request, imagePath);
  }

  public ImageResult createImageVariation(CreateImageVariationRequest request, java.io.File image) {
    return openAiService.createImageVariation(request, image);
  }

  public ModerationResult createModeration(ModerationRequest request) {
    return openAiService.createModeration(request);
  }


}
