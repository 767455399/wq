package com.example.hasee.taiheapp.activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.hasee.taiheapp.R;
import com.example.hasee.taiheapp.base.BaseActivity;
import com.example.hasee.taiheapp.tools.ToastUtil;
import com.example.lianlianpay.LianLianPayModle;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by wangqing on 2017/12/9.
 */

public class CanvasActivity extends BaseActivity {
    private Paint paint = new Paint();
    private LinearLayout linearLayout;
    private Button backButton;
    private CanvasView canvasView;
    private int backStep=0;
    String aaa="天道酬勤";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_canvas);
//        EventBus.getDefault().register(this);
        drawCanvas();
    }

    @Override
    public void initData() {

    }

    private void drawCanvas() {
        canvasView = new CanvasView(this);
        backButton=(Button)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backStep++;
                canvasView.invalidate();
            }
        });
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
//        canvasView.setMinimumHeight(100);
//        canvasView.setMinimumWidth(100);
        //重新绘制
//        canvasView.invalidate();
        linearLayout.addView(canvasView);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receive(LianLianPayModle lianLianPayModle){
        ToastUtil.showNormalToast("wq");
        aaa="天道酬勤aaa";
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        EventBus.getDefault().unregister(this);
//    }

    public class CanvasView extends View {
        public CanvasView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            int canvasWidth = canvas.getWidth();
            int canvasHeight = canvas.getHeight();
            int count = 3;
            int D = canvasHeight / (count + 1);
            int R = D / 2;
            paint.setColor(0xff123456);
            Shader shader = new LinearGradient(0, 0, 100, 100, new int[]{Color.BLUE, Color.RED, Color.GREEN}, null, Shader.TileMode.REPEAT);
            paint.setShader(shader);
            paint.setAntiAlias(true);
            paint.setTextSize(40);
            //设置下划线
//            paint.setUnderlineText(true);
            //设置粗体
            paint.setFakeBoldText(true);
            canvas.rotate(40);
            canvas.drawText(aaa, 200, 200, paint);
            canvas.save();

            /**
             * 通过线条绘图模式绘制圆环
             */
            canvas.translate(0, D + D / (count + 1));
            paint.setStyle(Paint.Style.STROKE);
            float strokeWidth = (float) (R * 0.25);
            paint.setStrokeWidth(strokeWidth);
            canvas.drawCircle(canvasWidth / 2, R, R, paint);
            canvas.save();
            canvas.drawText("天道酬勤", canvasWidth / 2, R, paint);
            canvas.scale(2,14);
            canvas.save();
            if(0!=backStep){
                canvas.restore();
            }
        }

    }
}
