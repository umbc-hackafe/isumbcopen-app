package com.isumbcopen.android;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


public class MainActivity extends Activity
{

    private class UpdateTask extends AsyncTask<String, Void, String> {
	@Override
	protected String doInBackground(String... urls) {
	    String response = "";
	    for (String url : urls) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);

		try {
		    HttpResponse execute = client.execute(httpGet);
		    InputStream content = execute.getEntity().getContent();

		    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));

		    String s = "";
		    while ((s = buffer.readLine()) != null) {
			response += s;
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		    return "IDK";
		}
	    }
	    return response;
	}

	@Override
	protected void onPostExecute(String result) {
	    status.setText(result);
	}
    }

    private TextView status;
    private String openState;
    private long lastUpdate;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

	new UpdateTask().execute("http://isumbcopen.com/api");
	this.status = (TextView)findViewById(R.id.status);
    }
}
