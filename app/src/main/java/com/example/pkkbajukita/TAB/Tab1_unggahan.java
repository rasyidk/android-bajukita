package com.example.pkkbajukita.TAB;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pkkbajukita.API.RequestInterface;
import com.example.pkkbajukita.Adapter.AdapterTab1_unggahan;
import com.example.pkkbajukita.R;
import com.example.pkkbajukita.Response.JSONResponse;
import com.example.pkkbajukita.Value.ValueUnggahan;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1_unggahan extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<ValueUnggahan> dataproses;
    private AdapterTab1_unggahan adapterx;


    public Tab1_unggahan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab1_unggahan, container, false);

        recyclerView = view.findViewById(R.id.Tab1_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        loadJSON();

        return view;
    }

    public boolean isConnectingToInternet(){
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService( Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void loadJSON() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .cache(provideCache())
                .addInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor( provideCacheInterceptor() )
                .addInterceptor( provideOfflineCacheInterceptor() )
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://retrofitbuos.000webhostapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        RequestInterface request = retrofit.create(RequestInterface.class);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user",MODE_PRIVATE);
        String user_id = sharedPreferences.getString("userid","");

        Call<JSONResponse> call = request.unggahan(user_id);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {

                JSONResponse jsonResponse = response.body();
                dataproses = new ArrayList<>(Arrays.asList(jsonResponse.getUnggahan()));
                adapterx = new AdapterTab1_unggahan(dataproses);
                recyclerView.setAdapter(adapterx);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }

    private Interceptor provideCacheInterceptor() {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept (Chain chain) throws IOException {
                okhttp3.Response response = chain.proceed( chain.request() );
                // re-write response header to force use of cache
                CacheControl cacheControl;

                if (isConnectingToInternet()) {
                    cacheControl = new CacheControl.Builder()
                            .maxAge(0, TimeUnit.SECONDS)
                            .build();
                } else {
                    cacheControl = new CacheControl.Builder()
                            .maxStale(7, TimeUnit.DAYS)
                            .build();
                }
                return response.newBuilder()
                        .removeHeader("Pragmax")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", cacheControl.toString())
                        .build();
            }
        };
    }

    public Interceptor provideOfflineCacheInterceptor () {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept (Chain chain) throws IOException {
                Request request = chain.request();
                if (!isConnectingToInternet()) {
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale(7, TimeUnit.DAYS)
                            .build();

                    request = request.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .cacheControl(cacheControl)
                            .build();
                }

                return chain.proceed(request);
            }
        };
    }

    private Cache provideCache() {
        Cache cache = null;
        try {
            cache = new Cache( new File(Tab1_unggahan.this.getActivity().getCacheDir(), "http-cache2" ),
                    10 * 1024 * 1024 ); // 10 MB
        }
        catch (Exception e) {
            Log.e( "Error", e.toString());
        }
        return cache;
    }

}
