package com.zhangbin.testproject;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.Toast;

import java.text.DecimalFormat;

import tv.danmaku.ijk.media.example.widget.media.AndroidMediaController;
import tv.danmaku.ijk.media.example.widget.media.IjkDragVideoView;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class MainActivity extends AppCompatActivity {
    String url="";
    private IjkDragVideoView mDragIjkVideoView;
    private String ijkVideoUrl = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
    private AndroidMediaController mMediaController;
    private Toast mToast;
    private TableLayout mHudView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        url= "https://www.sogou.com";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToast = Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT);
        playiJKVideo();

    }
    /**
     * 播放IJK视频
     */
    private void playiJKVideo() {
        mDragIjkVideoView = findViewById(R.id.ijk_videoView);
        mHudView = findViewById(R.id.hud_view);
        Uri uri = Uri.parse(ijkVideoUrl);
        mMediaController = new AndroidMediaController(this, false);
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        mMediaController.setVisibility(View.VISIBLE);
        mDragIjkVideoView.setMediaController(mMediaController);
        mDragIjkVideoView.setHudView(mHudView);
        if (TextUtils.isEmpty(ijkVideoUrl)) {
            mToast.setText("没有发现视频，请退出！");
            mToast.show();
        } else {
            mDragIjkVideoView.setVideoURI(uri);
            mDragIjkVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(IMediaPlayer mp) {
                    DecimalFormat df = new DecimalFormat("0.00");//格式化小数
                    float ratio = Float.parseFloat(df.format((float)mp.getVideoWidth()/mp.getVideoHeight()));
                    ViewGroup.LayoutParams lp;
                    lp = mDragIjkVideoView.getLayoutParams();
                    lp.width = mDragIjkVideoView.getWidth();
                    lp.height =  Math.round(mDragIjkVideoView.getWidth()/ratio);
                    mDragIjkVideoView.setLayoutParams(lp);
                    mDragIjkVideoView.start();

                }
            });
        }
        mDragIjkVideoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return false;
            }
        });
    }
}
