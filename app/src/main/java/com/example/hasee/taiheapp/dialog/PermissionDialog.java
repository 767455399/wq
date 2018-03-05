package com.example.hasee.taiheapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hasee.taiheapp.R;

/**
 * Created by wangqing on 2018/1/24.
 */

public class PermissionDialog extends Dialog {
    public PermissionDialog(@NonNull Context context, String content, final Listener listener) {
        super(context, R.style.WqDialogStyle);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setGravity(Gravity.CENTER);

//        getWindow().setLayout(1300,500);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null);
        TextView contentTextView = (TextView) view.findViewById(R.id.contentTextView);
        TextView cancleTextView = (TextView) view.findViewById(R.id.cancleTextView);
        TextView toSetTextView = (TextView) view.findViewById(R.id.toSetTextView);
        if (!TextUtils.isEmpty(content)) {
            contentTextView.setText(content);
        }
        cancleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                listener.cancle();
            }
        });

        toSetTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                listener.toSet();
            }
        });
        //点击dialog外部是否消失
        setCanceledOnTouchOutside(true);
        setContentView(view);
        //方法一,下面的代码一定要在setContentView(view)方法以后执行才有用。;
        getWindow().setLayout((ViewGroup.LayoutParams.WRAP_CONTENT), ViewGroup.LayoutParams.MATCH_PARENT);
//方法二：
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        int width = display.getWidth() - 300;
        int height = display.getHeight() - 400;
        //设置dialog的宽高为屏幕的宽高
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, height);
        setContentView(view, layoutParams);

    }


    public interface Listener {
        void toSet();

        void cancle();
    }
}
