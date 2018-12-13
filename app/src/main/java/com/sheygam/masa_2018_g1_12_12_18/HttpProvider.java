package com.sheygam.masa_2018_g1_12_12_18;

import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

class HttpProvider {
    private Gson gson;
    private OkHttpClient client;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String BASE_URL = "https://contacts-telran.herokuapp.com";
    private static final HttpProvider ourInstance = new HttpProvider();

    static HttpProvider getInstance() {
        return ourInstance;
    }

    private HttpProvider() {
        gson = new Gson();
//        client = new OkHttpClient();
        client = new OkHttpClient.Builder()
                .connectTimeout(15,TimeUnit.SECONDS)
                .readTimeout(15,TimeUnit.SECONDS)
                .build();
    }

    public String registration(String email, String password) throws Exception {
        AuthDto authDto = new AuthDto(email,password);
        String json = gson.toJson(authDto);

        URL url = new URL(BASE_URL + "/api/registration");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type","application/json");
        connection.setReadTimeout(15000);
        connection.setConnectTimeout(15000);
        connection.setDoOutput(true);
        connection.setDoInput(true);

        OutputStream os = connection.getOutputStream();

        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);
        bw.write(json);
        bw.flush();
        bw.close();

        int code = connection.getResponseCode();
        String line;
        String result = "";
        if(code >= 200 && code < 300){
            InputStreamReader isr = new InputStreamReader(connection.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            while((line = br.readLine())!= null){
                result += line;
            }
            br.close();
            AuthResponseDto responseDto = gson.fromJson(result,AuthResponseDto.class);
            return responseDto.getToken();
        }else {
            InputStreamReader isr = new InputStreamReader(connection.getErrorStream());
            BufferedReader br = new BufferedReader(isr);
            while((line = br.readLine())!=null){
                result+=line;
            }
            br.close();
            ErrorDto error = gson.fromJson(result,ErrorDto.class);
            if(code == 409){
                throw new Exception(error.getMessage());
            }else{
                Log.d("MY_TAG", "registration error: " + error.toString());
                throw new Exception("Server error! Call to support!");
            }
        }
    }

    public String login(String email, String password) throws Exception {
        AuthDto authDto = new AuthDto(email,password);
        String json = gson.toJson(authDto);

        RequestBody requestBody = RequestBody.create(JSON,json);

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/login")
                .post(requestBody)
                .addHeader("Authorized","token")
                .build();

        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
            String responseJson = response.body().string();
            AuthResponseDto responseDto = gson.fromJson(responseJson,AuthResponseDto.class);
            return responseDto.getToken();
        }else if(response.code() == 401){
            throw new Exception("Wrong email or password!");
        }else{
            Log.d("MY_TAG", "login: " + response.body().string());
            throw new Exception("Server error!");
        }
    }

    public void asynclogin(String email,
                           String password,
                           Callback callback){
        AuthDto authDto = new AuthDto(email,password);
        String json = gson.toJson(authDto);
        RequestBody requestBody = RequestBody.create(JSON,json);
        Request request = new Request.Builder()
                .url(BASE_URL + "/api/login")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(callback);
    }

}
