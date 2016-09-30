package tw.brad.myaudiorecorder;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private MediaRecorder recorder;
    private File sdroot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    1);
        }


        sdroot = Environment.getExternalStorageDirectory();

    }

    public void startRecording(View v){
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        recorder.setOutputFile(sdroot.getAbsolutePath() + "/brad.3gp");

        try {
            recorder.prepare();
            recorder.start();
            Log.d("brad", "starting...");
        }catch (Exception e){
            Log.d("brad", "e1: " + e.toString());
        }

    }
    public void pauseRecording(View v){
        // 超誇張的, 到了Android 7+ 才有 pause() 功能
        if (Build.VERSION.SDK_INT >= 24) {
            if (recorder != null) {
                recorder.pause();
            }
        }
    }
    public void stopRecording(View v){
        if (recorder != null){
            recorder.stop();
            recorder.release();
        }
    }
    public void playRecording(View v){
        MediaPlayer player = new MediaPlayer();
        try {
            player.setDataSource(sdroot.getAbsolutePath() + "/brad.3gp");
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
