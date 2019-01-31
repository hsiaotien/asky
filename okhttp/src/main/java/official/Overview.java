package official;

import okhttp3.*;

import java.io.IOException;

public class Overview {
    private OkHttpClient client = new OkHttpClient();

    /**
     * GET A URL
     */
    public String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
       try (Response response = client.newCall(request).execute()) {
           return response.body().string();
       }
    }


    /**
     * POST TO A SERVER
     */
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");

    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try(Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

}
