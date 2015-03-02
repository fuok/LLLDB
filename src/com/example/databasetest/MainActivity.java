package com.example.databasetest;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.model.HooMsgBean;
import com.example.utils.DBHelper;

//呵呵
public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			return rootView;
		}

		@Override
		public void onStart() {
			super.onStart();

		}

	}

	public void myClick(View view) {
		switch (view.getId()) {
		case R.id.btn_create:
			Toast.makeText(this, "haha", Toast.LENGTH_SHORT).show();
			//
			HooMsgBean bean1 = new HooMsgBean();
			bean1.setReceiveId("哈哈哈哈哈哈");
			bean1.setMsgId(1234567890123456L);
			HooMsgBean bean2 = new HooMsgBean();
			bean2.setReceiveId("呵呵呵呵");
			bean2.setMsgId(1345);
			ArrayList<HooMsgBean> arrayList = new ArrayList<>();
			arrayList.add(bean1);
			arrayList.add(bean2);
			//
			// ClassUtil.saveObj2List(bean);
			DBHelper.iniDB(this, bean1);// 必须，可以考虑把bean放这里一起inidth,用可变参数
			DBHelper.insert(arrayList);
			// DBHelper.createTable(bean);
			break;
		case R.id.btn_delete:
			Toast.makeText(this, "横", Toast.LENGTH_SHORT).show();
			HooMsgBean bean = new HooMsgBean();
			DBHelper.delete(bean);

			break;
		case R.id.btn_find:

			break;
		}
	}

}
