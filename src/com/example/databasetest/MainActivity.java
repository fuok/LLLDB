package com.example.databasetest;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.model.HooMsgBean;
import com.lll.dbtool.LYDB;

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
			Toast.makeText(this, "添加", Toast.LENGTH_SHORT).show();
			//
			HooMsgBean bean1 = new HooMsgBean();
			bean1.setReceiveId("哈哈哈哈哈哈");
			bean1.setMsgId(1234567890123456L);
			bean1.setGroupId(345.8976);
			ArrayList<String> urlList = new ArrayList<>();
			urlList.add("测试数据1");
			urlList.add("测试数据2");
			bean1.setUrlList(urlList);

			HooMsgBean bean2 = new HooMsgBean();
			bean2.setReceiveId("呵呵呵呵");
			bean2.setMsgId(1345);
			bean1.setGroupId(5.77);
			ArrayList<HooMsgBean> arrayList = new ArrayList<>();
			arrayList.add(bean1);
			arrayList.add(bean2);
			//
			// ClassUtil.saveObj2List(bean);
			LYDB.iniDB(this, bean1);// 必须，可以考虑把bean放这里一起inidth,用可变参数
			LYDB.insert(arrayList);
			// DBHelper.createTable(bean);
			break;
		case R.id.btn_delete:
			Toast.makeText(this, "删除", Toast.LENGTH_SHORT).show();
			HooMsgBean bean = new HooMsgBean();
			LYDB.delete(bean);

			break;
		case R.id.btn_find:
			Toast.makeText(this, "查找", Toast.LENGTH_SHORT).show();
			HooMsgBean bean3 = new HooMsgBean();
			List<Object> testList = LYDB.lookFor(bean3);

			// 测试
			for (int i = 0; i < testList.size(); i++) {
				HooMsgBean object = (HooMsgBean) testList.get(i);
				// Log.i("liuy", "chu来了"+object.getClass());

			}
			Log.i("liuy", "break");
			break;
		case R.id.btn_update:
			Toast.makeText(this, "修改", Toast.LENGTH_SHORT).show();
			HooMsgBean bean4 = new HooMsgBean();
			bean4.setMsgId(1345);
			bean4.setSubject("刘大爷测试");
			LYDB.update(bean4, "msgId", "subject");
			break;
		}
	}

}
