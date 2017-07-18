package rs.fon.dontforgetyourmedicine.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import rs.fon.dontforgetyourmedicine.R;

/**
 * Created by radovan.bogdanic on 3/17/2017.
 */

public class HourPickerDialog extends Dialog {

    private NumberPicker hourPicker = null;
    private HourSetListener listener;

    public interface HourSetListener {
        void onSetHourRepeat(int hour);
    }

    private HourPickerDialog(Context context) {
        super(context);
        callHourPicker(context);
    }

    private HourPickerDialog (Context context, int theme){
        super(context,theme);
        callHourPicker(context);
    }

    private void callHourPicker(Context context) {
        listener = (HourSetListener) context;
        setContentView(R.layout.hour_picker);
        setTitle(R.string.alarm_hour_picker_title);

        Button buttonOk = (Button) findViewById(R.id.btn_ok);
        Button buttonCacnel = (Button) findViewById(R.id.btn_cancel);
        hourPicker = (NumberPicker) findViewById(R.id.hourPicker);

        hourPicker.setMinValue(1);
        hourPicker.setMaxValue(24);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSetHourRepeat(hourPicker.getValue());
                dismiss();
            }
        });
        buttonCacnel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void setHourOnPicker(String selectedHour) {
        if (selectedHour.equals("")) {
            hourPicker.setValue(1);
        }
        else {
            hourPicker.setValue(Integer.parseInt(selectedHour));
        }
    }

    public static void showPicker(Context context, String value) {
        HourPickerDialog dialog;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            dialog  = new HourPickerDialog(context, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            dialog = new HourPickerDialog(context);
        }
        dialog.setHourOnPicker(value);
        dialog.setTitle(R.string.alarm_hour_picker_title);
        dialog.show();
    }
}
