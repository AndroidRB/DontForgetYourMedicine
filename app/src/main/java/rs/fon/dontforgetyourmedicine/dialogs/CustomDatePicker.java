package rs.fon.dontforgetyourmedicine.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

import rs.fon.dontforgetyourmedicine.R;

/**
 * Created by radovan.bogdanic on 3/23/2017.
 */

public class CustomDatePicker extends Dialog {

    private DatePicker datePicker = null;
    private DateSetListener listener;

    public interface DateSetListener {
        void onSetDate(int year, int mounth, int day);
    }

    private CustomDatePicker(Context context) {
        super(context);
        callDatePicker(context);
    }

    private CustomDatePicker(Context context, int theme) {
        super(context,theme);
        callDatePicker(context);
    }

    private void callDatePicker(Context context) {

        listener = (DateSetListener) context;
        setContentView(R.layout.date_picker);

        Button btnOK = (Button) findViewById(R.id.dp_ok);
        Button btnCancel = (Button) findViewById(R.id.dp_cancel);

        datePicker = (DatePicker) findViewById(R.id.customDatePicker);
        datePicker.setMinDate(System.currentTimeMillis());
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSetDate(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth());
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

    public static void showDatePicker(Context context) {
        CustomDatePicker datePickerDialog;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            datePickerDialog  = new CustomDatePicker(context, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            datePickerDialog = new CustomDatePicker(context);
        }
        datePickerDialog.setTitle("Set date");
        datePickerDialog.show();
    }
}
