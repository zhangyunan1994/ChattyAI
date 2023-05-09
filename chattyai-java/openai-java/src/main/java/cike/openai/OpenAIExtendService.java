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

public class OpenAIExtendService {

  private static final ObjectMapper mapper = defaultObjectMapper();

  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  private final OpenAIExtendAPI api;

  public OpenAIExtendService(final String token, final Duration timeout, String baseUrl) {
    ObjectMapper mapper = defaultObjectMapper();
    OkHttpClient client = defaultClient(token, timeout);
    Retrofit retrofit = defaultRetrofit(client, mapper, baseUrl);

    this.api = retrofit.create(OpenAIExtendAPI.class);
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
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(JacksonConverterFactory.create(mapper))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();
  }
}
