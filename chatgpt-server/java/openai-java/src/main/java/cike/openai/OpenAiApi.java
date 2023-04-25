package cike.openai;

import cike.openai.dashboard.billing.Usage;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenAiApi {

    @GET("/dashboard/billing/usage")
    Single<Usage> dashboardBillingUsage(@Query("start_date") String startDate, @Query("end_date") String endDate);

}
