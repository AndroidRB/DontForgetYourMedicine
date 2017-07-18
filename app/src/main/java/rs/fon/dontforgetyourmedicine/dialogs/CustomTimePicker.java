package rs.fon.dontforgetyourmedicine.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import rs.fon.dontforgetyourmedicine.R;

/**
 * Created by radovan.bogdanic on 3/23/2017.
 */

public class CustomTimePicker extends Dialog {

    private TimePicker timePicker = null;
    private TimeSetListener listener;

    public interface TimeSetListener {
        void onSetTime(int hour,int minute);
    }

    private CustomTimePicker(Context context) {
        super(context);
        callTimePicker(context);
    }

    private CustomTimePicker(Context context, int theme){
        super(context,theme);
        callTimePicker(context);
    }

    private void callTimePicker(Context context) {

        listener = (TimeSetListener) context;
        setContentView(R.layout.time_picker);
        Button btnOK = (Button) findViewById(R.id.tp_ok);
        Button btnCancel = (Button) findViewById(R.id.tp_cancel);
        timePicker = (TimePicker) findViewById(R.id.customTimePicker);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    listener.onSetTime(timePicker.getHour(),timePicker.getMinute());
                }
                else {
                    listener.onSetTime(timePicker.getCurrentHour(),timePicker.getCurrentMinute());
                }
                dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public static void showTimePicker(Context context) {
        CustomTimePicker dialog;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            dialog  = new CustomTimePicker(context, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            dialog = new CustomTimePicker(context);
        }
        dialog.setTitle("Set time");
        dialog.show();
    }
}
