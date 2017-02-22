package com.xdja.zdsb.view;

import com.xdja.zdsb.R;
import com.xdja.zdsb.utils.Zzlog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

public class CarResultDialog extends Dialog {

    private static final String TAG = "CarResultDialog";

    private IClickListener clickListener;

    private String title, carColor, carNumber;

    private EditText editText_color;

    private EditText editText_number;

    private Button button_retry;

    private Button button_confirm;

    public interface IClickListener {

        public void doConfirm(String color, String number);

        public void doRetry();
    }

    public void setColorNumber(String title, String carColor, String carNumber) {
        this.title = title;
        this.carColor = carColor;
        this.carNumber = carNumber;
    }

    public CarResultDialog(Context context, IClickListener clickListener, String title, String carColor,
            String carNumber) {
        super(context, android.R.style.Theme);

        setOwnerActivity((Activity) context);
        this.clickListener = clickListener;
        this.title = title;
        this.carColor = carColor;
        this.carNumber = carNumber;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.car_plate_result, null);
        setContentView(view);

        editText_color = (EditText) view.findViewById(R.id.editText_car_plate_color);
        editText_number = (EditText) view.findViewById(R.id.editText_car_plate_num);
        TextView textView_title = (TextView) view.findViewById(R.id.title);

        TableRow tableRow_car_plate_color = (TableRow) view.findViewById(R.id.tableRow_car_plate_color);
        tableRow_car_plate_color.setVisibility(View.INVISIBLE);

        textView_title.setText(title);
        editText_color.setText(carColor);
        editText_number.setText(carNumber);

        button_retry = (Button) view.findViewById(R.id.button_retry);
        button_confirm = (Button) view.findViewById(R.id.button_confirm);
        button_retry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Zzlog.out(TAG, "button_retry click");
                if (clickListener != null) {
                    clickListener.doRetry();
                }
                CarResultDialog.this.dismiss();
            }
        });
        button_confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Zzlog.out(TAG, "button_confirm click");
                if (clickListener != null) {
                    String color = editText_color.getText().toString();
                    String number = editText_number.getText().toString();
                    clickListener.doConfirm(color, number);
                }
                CarResultDialog.this.dismiss();
            }
        });
    }

}
