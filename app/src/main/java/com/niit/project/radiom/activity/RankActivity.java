package com.niit.project.radiom.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.niit.project.radiom.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class RankActivity extends Activity {
	private ListView rankList;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rank);
		
		rankList = (ListView) findViewById(R.id.rankList);
		
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		Map<String, String> item1 = new HashMap<String, String>();
		item1.put("roundTopScore", "第一关 " + sp.getInt("round1TopScore", 0));
		Map<String, String> item2 = new HashMap<String, String>();
		item2.put("roundTopScore", "第二关 " + sp.getInt("round2TopScore", 0));
		Map<String, String> item3 = new HashMap<String, String>();
		item3.put("roundTopScore", "第三关 " + sp.getInt("round3TopScore", 0));
		data.add(item1);
		data.add(item2);
		data.add(item3);
		rankList.setAdapter( new SimpleAdapter(
				this, data, R.layout.item_layout, 
				new String[]{"roundTopScore"}, 
				new int[]{R.id.score})
		);
	}
}
