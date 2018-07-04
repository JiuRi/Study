package com.example.record;

import java.io.File;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class MainActivity extends Activity {
	ListView lv;
	Button bt;
	RecordUtil mRecorduUtil;
	LinearLayout ll_record;
	ImageView iv_volume;
	Bell bell = null;
	Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ll_record = (LinearLayout) findViewById(R.id.ll_record);
		iv_volume = (ImageView) findViewById(R.id.iv_volume);
		mRecorduUtil = new RecordUtil();
		lv = (ListView) findViewById(R.id.lv_custom_bell);
		updateBell();
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				// TODO Auto-generated method stub
				if (bell != null && mRecorduUtil.isPlaying()) {
					mRecorduUtil.stopPlay();
				}
				bell = (Bell) parent.getAdapter().getItem(position);
				mRecorduUtil.startPlay(bell.getBellPath(), false);
			}
		});

		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
										   int position, long id) {
				// TODO Auto-generated method stub
				bell = (Bell) parent.getAdapter().getItem(position);
				final File file = new File(bell.getBellPath());
				Builder builder = new Builder(MainActivity.this);
				builder.setTitle("提示");
				builder.setMessage("是否删除?");
				builder.setNegativeButton("取消", null);
				builder.setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (file.delete()) {
							Toast.makeText(MainActivity.this, "删除成功", 0).show();
							updateBell();
						}
					}
				});
				builder.show();
				return true;
			}
		});
		bt = (Button) findViewById(R.id.bt_record);
		bt.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						bt.setText("松开结束");
						mRecorduUtil.startRecord();
						if (mRecorduUtil.isRecording()) {
							ll_record.setVisibility(View.VISIBLE);
							Thread t = new Thread(mPollTask);
							t.start();
						}
						break;

					case MotionEvent.ACTION_UP:
						bt.setText("按住录音");
						ll_record.setVisibility(View.GONE);
						mRecorduUtil.stopRecord();
						updateBell();
						mHandler.removeCallbacks(mPollTask);
						break;
				}
				return true;
			}
		});
	}

	private Runnable mPollTask = new Runnable() {
		public void run() {
			int mVolume = mRecorduUtil.getVolume();
			Log.d("volume", mVolume + "");
			updateVolume(mVolume);
			mHandler.postDelayed(mPollTask, 100);
		}
	};

	// 更新列表
	private void updateBell() {
		List<Bell> list = new FileUtil().getCustomBell(RecordUtil.AUDIO_DIR);
		lv.setAdapter(new CustomBellAdapter(list, MainActivity.this));
	}

	// 更新音量图
	private void updateVolume(int volume) {
		switch (volume) {
			case 1:
				iv_volume.setImageResource(R.drawable.p1);
				break;
			case 2:
				iv_volume.setImageResource(R.drawable.p2);
				break;
			case 3:
				iv_volume.setImageResource(R.drawable.p3);
				break;
			case 4:
				iv_volume.setImageResource(R.drawable.p4);
				break;
			case 5:
				iv_volume.setImageResource(R.drawable.p5);
				break;
			case 6:
				iv_volume.setImageResource(R.drawable.p6);
				break;
			case 7:
				iv_volume.setImageResource(R.drawable.p7);
				break;
			default:
				break;
		}
	}

}
