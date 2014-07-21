package com.yahoo.ads.reporting;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.yahoo.ads.reporting.util.ConnectionUtil;

public class VendorListActivity extends ActionBarActivity {

	private ArrayList<String> vendors;
	private ListView vendorListView;
	private ArrayAdapter<String> listAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendors);

		Intent intent = getIntent();
		vendors = intent.getStringArrayListExtra("vendors");

		listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow);

		vendorListView = (ListView) findViewById(R.id.listView1);

		// call AsynTask to perform network operation on separate thread
		new HttpAsyncTask()
				.execute("http://abysmaldismal.corp.ir2.yahoo.com:4080/latency/v1/VendorfallOut/fallOut");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.vendors, menu);
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
				JSONArray falloutArr = jsonObject
						.getJSONArray("vendorFallOutDtos");

				for (int i = 0; i < falloutArr.length(); i++) {
					JSONObject falloutDetail = falloutArr.getJSONObject(i);
					String vendorName = falloutDetail.getString("vendorName");
					String fallout = falloutDetail.getString("fallout");
					String falloutPercent = falloutDetail
							.getString("falloutPercent");

					vendors.add(vendorName + ":" + fallout + ":"
							+ falloutPercent);

					listAdapter.add(vendorName + ":" + fallout + ":"
							+ falloutPercent);
				}

				// Create ArrayAdapter using the planet list.
				// listAdapter.addAll(vendors);

				// Set the ArrayAdapter as the ListView's adapter.
				vendorListView.setAdapter(listAdapter);

			} catch (JSONException e) {
				Toast.makeText(getBaseContext(), "Unable to parse response!",
						Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}

		}
	}
}
