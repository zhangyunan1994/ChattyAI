package cike.openai;


import cike.openai.dashboard.billing.Usage;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import io.reactivex.Single;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class OpenAiService {

  private static final String BASE_URL = "https://api.openai.com/";
  private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);
  private static final ObjectMapper mapper = defaultObjectMapper();

  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  private final OpenAiApi api;

  /**
   * Creates a new OpenAiService that wraps OpenAiApi
   *
   * @param token OpenAi token string "sk-XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
   */
  public OpenAiService(final String token) {
    this(token, DEFAULT_TIMEOUT);
  }

  /**
   * Creates a new OpenAiService that wraps OpenAiApi
   *
   * @param token   OpenAi token string "sk-XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
   * @param timeout http read timeout, Duration.ZERO means no timeout
   */
  public OpenAiService(final String token, final Duration timeout) {
    ObjectMapper mapper = defaultObjectMapper();
    OkHttpClient client = defaultClient(token, timeout);
    Retrofit retrofit = defaultRetrofit(client, mapper);

    this.api = retrofit.create(OpenAiApi.class);
  }

  /**
   * Creates a new OpenAiService that wraps OpenAiApi. Use this if you need more customization, but use
   * OpenAiService(api, executorService) if you use streaming and want to shut down instantly
   *
   * @param api OpenAiApi instance to use for all methods
   */
  public OpenAiService(final OpenAiApi api) {
    this.api = api;
  }

  public Usage dashboardBillingUsage(LocalDate startDate, LocalDate endDate) {
    return execute(
        api.dashboardBillingUsage(startDate.format(DATE_TIME_FORMATTER), endDate.format(DATE_TIME_FORMATTER)));
  }

  /**
   * Calls the Open AI api, returns the response, and parses error messages if the request fails
   */
  public static <T> T execute(Single<T> apiCall) {
    try {
      return apiCall.blockingGet();
    }
    catch (HttpException e) {
      try {
        if (e.response() == null || e.response().errorBody() == null) {
          throw e;
        }
        String errorBody = e.response().errorBody().string();

        OpenAiError error = mapper.readValue(errorBody, OpenAiError.class);
        throw new OpenAiHttpException(error, e, e.code());
      }
      catch (IOException ex) {
        // couldn't parse OpenAI error
        throw e;
      }
    }
  }

  public static OpenAiApi buildApi(String token, Duration timeout) {
    ObjectMapper mapper = defaultObjectMapper();
    OkHttpClient client = defaultClient(token, timeout);
    Retrofit retrofit = defaultRetrofit(client, mapper);

    return retrofit.create(OpenAiApi.class);
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

  public static Retrofit defaultRetrofit(OkHttpClient client, ObjectMapper mapper) {
    return new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(JacksonConverterFactory.create(mapper))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();
  }
}
