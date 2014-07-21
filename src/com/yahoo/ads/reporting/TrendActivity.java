package com.yahoo.ads.reporting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yahoo.ads.reporting.util.ConnectionUtil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class TrendActivity extends ActionBarActivity {

	EditText etResponse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trend);
		// get reference to the views
		etResponse = (EditText) findViewById(R.id.editText1);

		// call AsynTask to perform network operation on separate thread
		new HttpAsyncTask()
				.execute("http://abysmaldismal.corp.ir2.yahoo.com:4080/latency/v1/fallOutReport");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trend, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class HttpAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {

			return ConnectionUtil.GET(urls[0]);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {

			try {
				JSONObject jsonObject = new JSONObject(result);
				JSONArray falloutArr = jsonObject.getJSONArray("fallOutDtos");

				String str = new String();
				for (int i = 0; i < falloutArr.length(); i++) {
					JSONObject falloutDetail = falloutArr.getJSONObject(i);
					String fallout = falloutDetail.getString("fallout");
					String filled = falloutDetail.getString("filled");
					String unsold = falloutDetail.getString("unsold");
					String adBlocker = falloutDetail.getString("adBlocker");

					str = fallout + filled + unsold + adBlocker + "\n";
				}

				etResponse.setText(str);
			} catch (JSONException e) {
				Toast.makeText(getBaseContext(), "Unable to parse response!",
						Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}

		}
	}
}
