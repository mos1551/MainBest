package com.circlemind;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import static com.circlemind.Constant.IP;


public class CustomeDoreh extends LinearLayout {
    public static TextView txtnameDoreh;
    public static TextView txtDescriptionDoreh;
    public static TextView txtTimeDoreh;
    public static LinearLayout linearListDoreh;
    public static TextView txtNumberId;
    public static ImageView imgDoreh;

    public Integer id=0;


    public CustomeDoreh(Context context) {
        super(context);
        init(context);
    }

    public CustomeDoreh(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomeDoreh(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custome_doreh, this, true);

        txtnameDoreh=(TextView)view.findViewById(R.id.txtNameDoreh);
        txtDescriptionDoreh=(TextView)view.findViewById(R.id.txtDescriptionDoreh);
        txtTimeDoreh=(TextView)view.findViewById(R.id.txtTimeDoreh);
        linearListDoreh=(LinearLayout)view.findViewById(R.id.linearListDoreh);
        txtNumberId=(TextView)view.findViewById(R.id.txtNumberId);
        imgDoreh=(ImageView)view.findViewById(R.id.imgDoreh);

        linearListDoreh.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTaskListVoice(IP+"/listVoice.php",id+"").execute();
                final ProgressDialog dialog=new ProgressDialog(getContext());
                dialog.setMessage("لطفا منتظر بمانید...");
                dialog.show();

                final Timer timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        if(!ActivitySelectDoreh.dataListVoice.equals(""))
                        {
                            Intent intent = new Intent(getContext(), ActivitySelectDoreh.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            getContext().startActivity (intent);
                            dialog.dismiss();
                            timer.cancel();
                        }
                    }
                },1,2000);
            }
        });


    }
}
