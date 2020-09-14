package svetozarradic.ferit.hr.rma_project.connection;

import svetozarradic.ferit.hr.rma_project.models.FitbitParameters;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface FitbitInterface {

    @GET("/1/user/6HZS9Y/activities/date/{date}.json")
    Call<FitbitParameters> getFitbitParameters(@Path("date") String date, @Header("Authorization") String token);
}
