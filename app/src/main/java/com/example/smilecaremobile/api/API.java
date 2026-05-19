package com.example.smilecaremobile.api;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class API {
    public static final String URL = "http://10.0.2.2/";
    private final OkHttpClient client = new OkHttpClient.Builder().build();
    public interface ApiCallback{
        void onSuccess(String response) throws JSONException;
        void onFailure(String error);
    }

    public void get(ApiCallback callback, String apiRequest, String token) {
        Request request = new Request.Builder()
                .url(URL + apiRequest)
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .get()
                .build();

        client.newCall(request).enqueue((new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    String jsonReponse = response.body().string();
                    System.out.println("Réponse GET JSON : " + jsonReponse);
                    try {
                        callback.onSuccess(jsonReponse);
                    } catch(Exception e) {
                        callback.onFailure("Erreur de parsing JSON");
                    }
                } else {
                    callback.onFailure("Erreur: " + response.code());
                }
            }
        }));
    }

    public void put(ApiCallback callback, String apiRequest, String body, String token) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Request request = new Request.Builder()
                .url(URL + apiRequest)
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .put(RequestBody.create(body, JSON))
                .build();

        client.newCall(request).enqueue((new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    String jsonReponse = response.body().string();
                    System.out.println("Réponse PUT JSON : " + jsonReponse);
                    try {
                        JSONObject jsonObject = new JSONObject(jsonReponse);
                        callback.onSuccess(jsonObject.toString());
                    } catch(Exception e) {
                        callback.onFailure("Erreur de parsing JSON");
                    }
                } else {
                    callback.onFailure("Erreur: " + response.code());
                }
            }
        }));
    }

    public void post(ApiCallback callback, String apiRequest, String body, String token) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Request request = new Request.Builder()
                .url(URL + apiRequest)
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .post(RequestBody.create(body, JSON))
                .build();

        client.newCall(request).enqueue((new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    String jsonReponse = response.body().string();
                    System.out.println("Réponse POST JSON : " + jsonReponse);
                    try {
                        JSONObject jsonObject = new JSONObject(jsonReponse);
                        callback.onSuccess(jsonObject.toString());
                    } catch(Exception e) {
                        callback.onFailure("Erreur de parsing JSON");
                    }
                } else {
                    callback.onFailure("Erreur: " + response.code());
                }
            }
        }));
    }

    public void delete(ApiCallback callback, String apiRequest, String token) {
        Request request = new Request.Builder()
                .url(URL + apiRequest)
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .delete()
                .build();

        client.newCall(request).enqueue((new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    String jsonReponse = response.body().string();
                    System.out.println("Réponse DELETE JSON : " + jsonReponse);
                    try {
                        JSONObject jsonObject = new JSONObject(jsonReponse);
                        callback.onSuccess(jsonObject.toString());
                    } catch(Exception e) {
                        callback.onFailure("Erreur de parsing JSON");
                    }
                } else {
                    callback.onFailure("Erreur: " + response.code());
                }
            }
        }));
    }

    public void getToken(ApiCallback callback) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String jsonString = "{\"courriel\":\"admin@mail.com\",\"mot_de_passe\":\"=user123\",\"nom_token\":\"mobile\"}";
        RequestBody body = RequestBody.create(jsonString, JSON);
        Request request = new Request.Builder()
                .url(URL + "api/token")
                .post(body)
                .build();

        client.newCall(request).enqueue((new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    String jsonReponse = response.body().string();
                    System.out.println("Réponse TOKEN JSON : " + jsonReponse);
                    try {
                        JSONObject jsonObject = new JSONObject(jsonReponse);
                        callback.onSuccess(jsonObject.getString("SUCCÈS").toString());
                    } catch(Exception e) {
                        callback.onFailure("Erreur de parsing JSON");
                    }
                } else {
                    callback.onFailure("Erreur: " + response.code());
                }
            }
        }));
    }
}