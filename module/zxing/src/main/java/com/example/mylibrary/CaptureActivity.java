package com.example.mylibrary;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mylibrary.camera.CameraManager;
import com.example.mylibrary.camera.ViewfinderView;
import com.example.mylibrary.decoding.CaptureActivityHandler;
import com.example.mylibrary.decoding.CaptureActivityInterface;
import com.example.mylibrary.decoding.InactivityTimer;
import com.example.mylibrary.encoding.ActionUtils;
import com.example.mylibrary.encoding.QrUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.io.IOException;
import java.util.Vector;

public class CaptureActivity extends AppCompatActivity implements SurfaceHolder.Callback, CaptureActivityInterface {

    private static final String TAG = CaptureActivity.class.getSimpleName();

    public static final int SACN_CODE_FOR_LOGIN = 100;
    public static final int SACN_CODE_FOR_ECARD = 101;
    private static final int REQUEST_PERMISSION_CAMERA = 1000;
    private static final int REQUEST_PERMISSION_PHOTO = 1001;
    private static final float BEEP_VOLUME = 0.10f;
    private static final long VIBRATE_DURATION = 200L;
    /**
     * When the zxing_beep has finished playing, rewind to queue up another one.
     */
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };
    private CaptureActivity mActivity;
    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private boolean vibrate;
    private boolean flashLightOpen = false;
    private ImageView backIbtn;
    private ImageView flashIbtn;
    private ImageView galleryTv;


    /**
     * Called when the activity is first created.
     */
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zxing_activity_capture);
        mActivity = this;
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        CameraManager.init(getApplication());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        REQUEST_PERMISSION_CAMERA);
            }
        }
        decodeFormats = new Vector<>();
        decodeFormats.add(BarcodeFormat.CODE_128);
        decodeFormats.add(BarcodeFormat.QR_CODE);
        initView();
    }


    @Override
    protected void onResume() {
        super.onResume();
        initScanConfig();
    }

    private void initScanConfig() {
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        characterSet = null;

        playBeep = true;
        final AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
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
        if (flashIbtn != null) {
            flashIbtn.setImageResource(R.drawable.zxing_strobe_light);
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null && requestCode == ActionUtils.PHOTO_REQUEST_GALLERY) {
                Uri inputUri = data.getData();
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(inputUri, proj, null, null, null);
                if (cursor.moveToFirst()) {
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
                    Result result = QrUtils.decodeImage(path);
                    if (result != null) {
                  /*  if (BuildConfig.DEBUG) Log.d(TAG, result.getText());*/
                        handleDecode(result, null);
                    } else {
                        new AlertDialog.Builder(CaptureActivity.this)
                                .setTitle("提示")
                                .setMessage("未识别的二维码/条码，请重新选择")
                                .setPositiveButton("确定", null)
                                .show();
                    }
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && requestCode == REQUEST_PERMISSION_CAMERA) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                // 未获得Camera权限
                new AlertDialog.Builder(mActivity)
                        .setTitle("提示")
                        .setMessage("请在系统设置中为App开启摄像头权限后重试")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mActivity.finish();
                            }
                        })
                        .show();
            }
        } else if (grantResults.length > 0 && requestCode == REQUEST_PERMISSION_PHOTO) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                new AlertDialog.Builder(mActivity)
                        .setTitle("提示")
                        .setMessage("请在系统设置中为App中开启文件权限后重试")
                        .setPositiveButton("确定", null)
                        .show();
            } else {
                ActionUtils.startActivityForGallery(mActivity, ActionUtils.PHOTO_REQUEST_GALLERY);
            }
        }
    }


    @Override
    public void finish() {

    }

    @Override
    public void startActivity(Intent intent) {

    }

    protected void handleResult(Result result) {
        String resultString = result.getText();
        if (resultString.equals("")) {
            Toast.makeText(CaptureActivity.this, "扫描失败", Toast.LENGTH_SHORT).show();
        } else {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("result", resultString);
            resultIntent.putExtras(bundle);
            this.setResult(RESULT_OK, resultIntent);
        }
        mActivity.finish();
    }

    protected void initView() {
        backIbtn = (ImageView) findViewById(R.id.back_ibtn);
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        flashIbtn = (ImageView) findViewById(R.id.flash_ibtn);
        galleryTv = (ImageView) findViewById(R.id.gallery_tv);

        backIbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });
        flashIbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flashLightOpen) {
                    flashIbtn.setImageResource(R.drawable.zxing_strobe_light);
                } else {
                    flashIbtn.setImageResource(R.drawable.zxing_flash_lamp);
                }
                toggleFlashLight();
            }
        });
        galleryTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    /**
     * 切换散光灯状态
     */
    public void toggleFlashLight() {
        if (flashLightOpen) {
            setFlashLightOpen(false);
        } else {
            setFlashLightOpen(true);
        }
    }

    /**
     * 当前散光灯是否打开
     *
     * @return
     */
    public boolean isFlashLightOpen() {
        return flashLightOpen;
    }

    /**
     * 设置闪光灯是否打开
     *
     * @param open
     */
    public void setFlashLightOpen(boolean open) {
        if (flashLightOpen == open) return;

        flashLightOpen = !flashLightOpen;
        CameraManager.get().setFlashLight(open);
    }

    /**
     * 打开相册
     */
    public void openGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_PHOTO);
        } else {
            ActionUtils.startActivityForGallery(mActivity, ActionUtils.PHOTO_REQUEST_GALLERY);
        }
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
            handler=new CaptureActivityHandler(this,decodeFormats,characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format,
                               int width, int height) {
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

    protected void setViewfinderView(ViewfinderView view) {
        viewfinderView = view;
    }

    public Handler getHandler() {
        return handler;
    }

    @Override
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        handleResult(result);
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    /**
     * 重新扫描
     */
    protected void restartPreview() {
        Message restartMessage = Message.obtain();
        restartMessage.what = R.id.restart_preview;
        handler.handleMessage(restartMessage);
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
                    R.raw.zxing_beep);
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

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    protected void startScan() {
        initScanConfig();
    }

    protected void stopScan() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }
}