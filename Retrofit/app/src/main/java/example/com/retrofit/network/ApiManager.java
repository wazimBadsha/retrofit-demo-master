package example.com.retrofit.network;

import example.com.retrofit.model.Profile;
import example.com.retrofit.network.response.ProfileListResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by michaellimantara on 20/3/16.
 */
public class ApiManager {

    private static ApiService apiService;
    private static Retrofit retrofit;

    private ApiManager() { }

    public static ApiService getApiClient() {
        if (apiService == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiService = retrofit.create(ApiService.class);
        }

        return apiService;
    }

    public interface ApiService {

        @GET(ApiConfig.EndPoint.PROFILE)
        Call<ProfileListResponse> getProfiles();

        @FormUrlEncoded
        @POST(ApiConfig.EndPoint.PROFILE)
        Call<Profile> postProfile(@Field("name") String name,
                                  @Field("phone") String contactNo,
                                  @Field("email") String email);

    }
}

