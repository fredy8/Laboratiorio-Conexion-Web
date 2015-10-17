package com.itesm.a01191157.a01191157_laboconexionweb;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AsyncTaskInterface {

    private ProgressDialog progressDialog;
    private ClimaAdapter adapter;
    private List<Clima> climas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new LoadData(this).execute("http://api.openweathermap.org/data/2.5/forecast?lat=25.67&lon=-100.32&APPID=3d6031613b37f26f12834d5c19b9cd0b");
    }

    @Override
    public void onAsyncTaskSucceed(ArrayList<Clima> climasList) {
        ListView listView = (ListView)findViewById(R.id.listView);
        adapter = new ClimaAdapter(this.getApplicationContext(), R.layout.renglon, climasList);
        listView.setAdapter(adapter);
    }

    @Override
    public void onAsyncTaskFailed() {
        Toast.makeText(getApplicationContext(), "No se pudo establecer conexión.", Toast.LENGTH_LONG).show();
    }

    public class LoadData extends AsyncTask<String, Void, JSONObject> {

        private AsyncTaskInterface asyncTaskInterface;

        public LoadData(AsyncTaskInterface asyncTaskInterface) {
            this.asyncTaskInterface = asyncTaskInterface;
        }

        @Override
        protected void onPreExecute() {
//            progressDialog = new ProgressDialog(getApplicationContext());
//            progressDialog.setMessage("Loading...");
//            progressDialog.setCancelable(false);
//            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject data = null;

            try {
                data = getJSONFromUrl(params[0]);
            } catch (Exception e) {
                this.asyncTaskInterface.onAsyncTaskFailed();
                e.printStackTrace();
            }

            return data;
        }

        private String readStream(InputStream in) {
            BufferedReader reader = null;
            StringBuffer response = null;

            try {
                reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                String line = "";
                response = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                this.asyncTaskInterface.onAsyncTaskFailed();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return response.toString();
        }

        public JSONObject getJSONFromUrl(String Url) throws Exception {
            JSONObject jsonObject = null;
            HttpURLConnection httpUrlConnection = null;

            try {
                URL myUrl = new URL(Url);
                httpUrlConnection = (HttpURLConnection) myUrl.openConnection();
                httpUrlConnection.connect();

                int responseCode = httpUrlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    String responseString = readStream(httpUrlConnection.getInputStream());
                    jsonObject = new JSONObject(responseString);
                }
            } catch (Exception e) {
                this.asyncTaskInterface.onAsyncTaskFailed();
                e.printStackTrace();
                Log.e("Exception", e.toString());
            } finally {
                if (null != httpUrlConnection) {
                    httpUrlConnection.disconnect();
                }
            }

            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            ArrayList<Clima> climas = new ArrayList<>();
            try {
                JSONArray climasJson = jsonObject.getJSONArray("list");
                for (int i = 0; i < climasJson.length(); i++) {
                    double temp = climasJson.getJSONObject(i).getJSONObject("main").getDouble("temp");
                    double temp_min = climasJson.getJSONObject(i).getJSONObject("main").getDouble("temp_min");
                    double temp_max = climasJson.getJSONObject(i).getJSONObject("main").getDouble("temp_max");
                    climas.add(new Clima(temp, temp_min, temp_max));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                this.asyncTaskInterface.onAsyncTaskFailed();
            }

            this.asyncTaskInterface.onAsyncTaskSucceed(climas);
            super.onPostExecute(jsonObject);
        }
    }

}
