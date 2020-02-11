package com.example.pkkbajukita.TAB;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pkkbajukita.API.RequestInterface;
import com.example.pkkbajukita.Adapter.AdapterTab1_unggahan;
import com.example.pkkbajukita.Adapter.AdapterTab2_selesai;
import com.example.pkkbajukita.R;
import com.example.pkkbajukita.Response.JSONResponse;
import com.example.pkkbajukita.Value.ValueSelesai;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab2_Selesai extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<ValueSelesai> dataproses;
    private AdapterTab2_selesai adapterx;

    public Tab2_Selesai() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab2__selesai, container, false);

        recyclerView = view.findViewById(R.id.Tab2_recycler_view);
        recyclerView.setHasFixedSize(true);
    //    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
      //  recyclerView.setLayoutManager(layoutManager);
        loadJSON();

        return view;
    }

    public boolean isConnectingToInternet(){
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService( Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private void loadJSON() {
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
        String user_id = "1";
        Call<JSONResponse> call = request.selesai(user_id);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {

                JSONResponse jsonResponse = response.body();
                dataproses = new ArrayList<>(Arrays.asList(jsonResponse.getSelesai()));
                adapterx = new AdapterTab2_selesai(dataproses);
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
                        .removeHeader("Pragma")
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
            cache = new Cache( new File(Tab2_Selesai.this.getActivity().getCacheDir(), "http-cache" ),
                    10 * 1024 * 1024 ); // 10 MB
        }
        catch (Exception e) {
            Log.e( "Error", e.toString() );
        }
        return cache;
    }

}
