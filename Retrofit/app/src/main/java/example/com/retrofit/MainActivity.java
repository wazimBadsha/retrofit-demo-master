package example.com.retrofit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import example.com.retrofit.adapter.ProfileAdapter;
import example.com.retrofit.model.Profile;
import example.com.retrofit.network.ApiManager;
import example.com.retrofit.network.response.ProfileListResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    static final int ADD_PROFILE = 1000;

    @Bind(R.id.rv_profile)
    RecyclerView rvProfile;

    private ProfileAdapter profileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setProfileLayout();
        fetchData();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddProfileActivity.class);
                startActivityForResult(intent, ADD_PROFILE);
            }
        });
    }

    private void setProfileLayout() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvProfile.setLayoutManager(layoutManager);

        profileAdapter = new ProfileAdapter();
    }

    private void fetchData() {
        Call<ProfileListResponse> profileListCallback = ApiManager.getApiClient().getProfiles();
        profileListCallback.enqueue(new Callback<ProfileListResponse>() {
            @Override
            public void onResponse(Call<ProfileListResponse> call, Response<ProfileListResponse> response) {
                if (response.isSuccess()) {
                    ProfileListResponse profileListResponse = response.body();
                    List<Profile> profileList = profileListResponse.getProfileList();
                    Log.d("PROFILE LIST", profileList.size() + "");
                    if (profileList != null && profileList.size() > 0) {
                        profileAdapter.setItems(profileList);
                        rvProfile.setAdapter(profileAdapter);
                    }

                    Snackbar.make(rvProfile, "Successful", Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(rvProfile, "No profiles", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileListResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_PROFILE) {
            if (resultCode == RESULT_OK) {
                Profile newProfile = new Profile();
                newProfile.setId(data.getLongExtra("id", -1));
                newProfile.setName(data.getStringExtra("name"));
                newProfile.setContactNo(data.getStringExtra("contact_no"));
                newProfile.setEmail(data.getStringExtra("email"));

                profileAdapter.addItem(newProfile);
                profileAdapter.notifyDataSetChanged();
            }
        }
    }
}
