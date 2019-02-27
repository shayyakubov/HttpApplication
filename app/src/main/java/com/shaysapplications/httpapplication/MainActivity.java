package com.shaysapplications.httpapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
//https://beeceptor.com/console/shayy
    private TextView textViewResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewResult = (TextView)findViewById(R.id.text_view_result);

        OkHttpClient client = new OkHttpClient();
        String url = "https://shayy.free.beeceptor.com/postShit?filename=test&data=1234567890";
        MediaType json = MediaType.parse("application/json;charset=utf-8");
        JSONObject actualData = new JSONObject();
        try{
        actualData.put("name","shay");
        actualData.put("age",22);
        }
        catch (org.json.JSONException e){
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(json,actualData.toString());
        HashMap<String,String> headersMap = new HashMap<>();
        headersMap.put("Authorization","GW#123");
        Headers headers = Headers.of(headersMap);
        Request request = new Request.Builder().url(url).headers(headers).post(requestBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    final String myResponse = response.body().string();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textViewResult.setText(myResponse);
                        }
                    });
                }

            }
        });
    }
}
