package com.circlemind;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import static com.circlemind.Constant.IP;
import static com.circlemind.Constant.PATHMAIN;


public class CustomeVoice extends LinearLayout {
    public static TextView txtDownload;
    public static TextView txtNameDownload;
    public static TextView txtTimeDownload;
    public static TextView txtاHajmDownload;
    public LinearLayout linearDownload;
    public LinearLayout linearPlay;
    public LinearLayout linearMainDonPlay;
    public ProgressBar progressDownload;
    public ImageView btnPlay;
    public ImageView btnStop;
    public MediaPlayer player;
    public Runnable run;
    public SeekBar seekPlay;

    public String path = "";
    public boolean playpause = true;
    public String direc = "";
    public Integer id = 0;
    public int stopPoint = 0;

    Handler seekHandler;

    public CustomeVoice(Context context) {
        super(context);
        init(context);
    }

    public CustomeVoice(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomeVoice(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    public void seekUpdate() {
        seekPlay.setProgress(player.getCurrentPosition());
        seekHandler.postDelayed(run, 1000);
    }

    public LinearLayout getLinearMainDonPlay() {
        return linearMainDonPlay;
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custome_voice, this, true);

        txtDownload = (TextView) view.findViewById(R.id.txtDownload);
        txtNameDownload = (TextView) view.findViewById(R.id.txtNameDownload);
        txtTimeDownload = (TextView) view.findViewById(R.id.txtTimeDownload);
        txtاHajmDownload = (TextView) view.findViewById(R.id.txtHajmDownload);
        progressDownload = (ProgressBar) view.findViewById(R.id.progressDownload);
        linearDownload = (LinearLayout) view.findViewById(R.id.linearDownload);
        linearPlay = (LinearLayout) view.findViewById(R.id.linearPLay);
        linearMainDonPlay = (LinearLayout) view.findViewById(R.id.linearMainDonPlay);
        seekPlay = (SeekBar) view.findViewById(R.id.seekPlay);
        btnPlay = (ImageView) view.findViewById(R.id.btnPlay);
        btnStop = (ImageView) view.findViewById(R.id.btnStop);


        btnPlay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playpause) {
                    player = MediaPlayer.create(getContext(), Uri.parse(direc + PATHMAIN + "/" + path));
                    if (stopPoint != 0) {
                        player.seekTo(stopPoint);
                    }
                    player.start();
                    btnStop.setVisibility(VISIBLE);
                    seekPlay.setMax(player.getDuration());
                    seekHandler = new Handler();
                    run = new Runnable() {
                        @Override
                        public void run() {
                            seekUpdate();
                        }
                    };
                    btnPlay.setImageResource(R.drawable.ic_pause);
                    playpause = false;
                } else {
                    btnPlay.setImageResource(R.drawable.ic_play);
                    stopPoint = player.getCurrentPosition();
                    player.pause();
                    playpause = true;
                }


            }
        });

        btnStop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                player.stop();
                stopPoint = 0;
                seekPlay.setProgress(0);
                btnStop.setVisibility(GONE);
                btnPlay.setImageResource(R.drawable.ic_play);
                playpause = true;

            }
        });


        seekPlay.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                player.seekTo(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        direc = Environment.getExternalStorageDirectory() + File.separator;
        File folder = new File(direc, PATHMAIN);
        if (!folder.exists()) {
            folder.mkdir();
        }

        linearMainDonPlay.setOnClickListener(new OnClickListener() {
                                                 @Override
                                                 public void onClick(View view) {

                                                     File fileVoice = new File(direc + PATHMAIN + "/", path);
                                                     if (!fileVoice.exists()) {
                                                         linearPlay.setVisibility(GONE);
                                                         linearDownload.setVisibility(VISIBLE);
                                                     } else {
                                                         linearPlay.setVisibility(VISIBLE);
                                                         linearDownload.setVisibility(GONE);
                                                     }
                                                 }
                                             }

        );


        txtDownload.setOnClickListener(new OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               File fileVoicePlay = new File(direc + PATHMAIN + "/", path);
                                               if (!fileVoicePlay.exists()) {
                                                   final DownloadTask downloadTask = new DownloadTask(getContext(), progressDownload, path);
                                                   downloadTask.execute(IP + "/file/" + path);
                                                   Toast.makeText(getContext(), "برای انصراف از دانلود برگشت را فشار دهید", Toast.LENGTH_SHORT).show();
                                               } else {
                                                   linearPlay.setVisibility(VISIBLE);
                                                   linearDownload.setVisibility(GONE);
                                               }
                                           }
                                       }

        );
    }
}
