package com.ebusbar.pile;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ebusbar.utils.ActivityControl;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.zxing.camera.CameraManager;
import com.zxing.decoding.CaptureActivityHandler;
import com.zxing.decoding.InactivityTimer;
import com.zxing.view.ViewfinderView;

import java.io.IOException;
import java.util.Vector;

/**
 * Created by Jelly on 2016/3/8.
 */
public class QRActivity extends BaseActivity implements SurfaceHolder.Callback{
    /**
     * TAG
     */
    public String TAG = "QRActivity";

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    /**
     * 开关灯状态
     */
    private boolean isOpen = false;

    /**
     * 输入框
     */
    private EditText input_et;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_layout);
        //ViewUtil.addTopView(getApplicationContext(), this, R.string.scan_card);
        CameraManager.init(getApplication());
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        setListener();
        init();
    }

    @Override
    public void init() {
        input_et = (EditText) this.findViewById(R.id.input_et);
    }

    @Override
    public void loadObjectAttribute() {

    }

    @Override
    public void setListener() {
    }

    @Override
    public void setActivityView() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
        if(isOpen){ //如果灯是开的，关闭
            isOpen = false;
            //CameraManager.get().disableFlash(); //关灯
        }
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    /**
     * 处理扫描结果
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        if (resultString.equals("")) {
            Toast.makeText(QRActivity.this, "Scan failed!", Toast.LENGTH_SHORT).show();
        }else { //扫码成功
//            Intent resultIntent = new Intent();
//            Bundle bundle = new Bundle();
//            bundle.putString("result", resultString);
//            bundle.putParcelable("bitmap", barcode);
//            resultIntent.putExtras(bundle);
//            this.setResult(RESULT_OK, resultIntent);
            //Toast.makeText(this,resultString,Toast.LENGTH_LONG).show();
            String QRId = (String) resultString.subSequence(resultString.length()-12,resultString.length());
            ChargeActivity.startAppActivity(this,QRId);
        }
        ActivityControl.finishAct(this);
    }

    /**
     * 输入二维码编号
     * @return
     */
    public View inputCode(View view){
        String QRId = input_et.getText().toString();
        if(TextUtils.isEmpty(QRId)){
            Toast.makeText(this,"请输入二维码编号",Toast.LENGTH_SHORT).show();
        }
        ActivityControl.finishAct(this);
        ChargeActivity.startAppActivity(this,QRId);
        return view;
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    /**
     * 开灯
     * @param view
     * @return
     */
    public View onLight(View view){
        // TODO 开灯
        Button light_btn = (Button) view;
        if (!isOpen) {
            light_btn.setText("关灯");
            CameraManager.get().enableFlash();
            isOpen = true;
        } else {  // 关灯
            light_btn.setText("开灯");
            CameraManager.get().disableFlash();
            isOpen = false;
        }
        return view;
    }

    /**
     * 开启Activity
     */
    public static void startAppActivity(Context context){
        Intent intent = new Intent(context,QRActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }


    @Override
    public String getTAG() {
        return TAG;
    }
}
