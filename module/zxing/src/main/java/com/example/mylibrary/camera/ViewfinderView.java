package com.example.mylibrary.camera;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.example.mylibrary.R;
import com.google.zxing.ResultPoint;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by wangqing on 2018/2/24.
 */

public class ViewfinderView  extends View {
    private Bitmap laserLineBitmap;
    private static final int[] SCANNER_ALPHA = {0, 64, 128, 192, 255, 192, 128, 64};
    private static final int OPAQUE = 0xFF;
    public static int RECT_OFFSET_X; // 扫描区域偏移量 默认位于屏幕中间
    public static int RECT_OFFSET_Y;
    private static long ANIMATION_DELAY = 5L;
    private final Paint paint;
    private final int maskColor;
    private final int resultColor;
    private final int frameColor;
    private final int laserColor;
    private final int resultPointColor;
    private final int angleColor;
    private String hint;
    private int hintColor;
    private String errorHint;
    private int errorHintColor;
    private boolean showPossiblePoint;
    private Bitmap resultBitmap;
    private int scannerAlpha;
    private Collection<ResultPoint> possibleResultPoints;
    private Collection<ResultPoint> lastPossibleResultPoints;
    private int RECT_WIDTH = 675;
    private int RECT_HEIGHT = 675;

    private float translateY = 5f;
    private int cameraPermission = PackageManager.PERMISSION_DENIED;
    private boolean changeSize;

    // This constructor is used when the class is built from an XML resource.
    public ViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.qr_ViewfinderView);
        angleColor = typedArray.getColor(R.styleable.qr_ViewfinderView_qr_angleColor, Color.WHITE);
        hint = typedArray.getString(R.styleable.qr_ViewfinderView_qr_hint);
        hintColor = typedArray.getColor(R.styleable.qr_ViewfinderView_qr_textHintColor, Color.GRAY);
        errorHint = typedArray.getString(R.styleable.qr_ViewfinderView_qr_errorHint);
        errorHintColor = typedArray.getColor(R.styleable.qr_ViewfinderView_qr_textErrorHintColor, Color.WHITE);
        showPossiblePoint = typedArray.getBoolean(R.styleable.qr_ViewfinderView_qr_showPossiblePoint, false);

        RECT_OFFSET_X = (int) typedArray.getDimension(R.styleable.qr_ViewfinderView_qr_offsetX, 0);
        RECT_OFFSET_Y = (int) typedArray.getDimension(R.styleable.qr_ViewfinderView_qr_offsetY, 0);
        RECT_WIDTH = (int) typedArray.getDimension(R.styleable.qr_ViewfinderView_qr_width, 675);
        RECT_HEIGHT = (int) typedArray.getDimension(R.styleable.qr_ViewfinderView_qr_height, 675);

        if (TextUtils.isEmpty(hint)) {
            hint = "将条码/二维码放入框内";
        }
        if (TextUtils.isEmpty(errorHint)) {
            errorHint = "请允许访问摄像头后重试";
        }
        //扫描动作执行速度
        if (showPossiblePoint) {
            ANIMATION_DELAY = 5L;
        }

        // Initialize these once for performance rather than calling them every time in onDraw().
        paint = new Paint();
        Resources resources = getResources();
        maskColor = resources.getColor(R.color.zxing_viewfinder_mask);
        resultColor = resources.getColor(R.color.zxing_result_view);
        frameColor = resources.getColor(R.color.zxing_viewfinder_frame);
        laserColor = resources.getColor(R.color.zxing_viewfinder_laser);
        resultPointColor = resources.getColor(R.color.zxing_possible_result_points);
        scannerAlpha = 0;
        possibleResultPoints = new HashSet<ResultPoint>(5);

        typedArray.recycle();
    }

    @Override
    public void onDraw(Canvas canvas) {
        Rect frame = null;
        if (!isInEditMode()) {
            if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
                cameraPermission = CameraManager.get().checkCameraPermission();
            }
            frame = CameraManager.get().getFramingRect(RECT_OFFSET_X, RECT_OFFSET_Y);
            changeSize = false;
        }

        if (frame == null) {
            // Android Studio中预览时和未获得相机权限时都为null
            int screenWidth = getResources().getDisplayMetrics().widthPixels;
            int screenHeight = getResources().getDisplayMetrics().heightPixels;
            int leftOffset = (screenWidth - RECT_WIDTH) / 2;
            int topOffset = (screenHeight - RECT_HEIGHT) / 2;
            frame = new Rect(leftOffset + RECT_OFFSET_X,
                    topOffset + RECT_OFFSET_Y,
                    leftOffset + RECT_WIDTH + RECT_OFFSET_X,
                    topOffset + RECT_HEIGHT + RECT_OFFSET_Y);
//            return;
        }
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        // Draw the exterior (i.e. outside the framing rect) darkened
        paint.setColor(resultBitmap != null ? resultColor : maskColor);
        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
        canvas.drawRect(0, frame.bottom + 1, width, height, paint);

        drawText(canvas, frame);

        if (resultBitmap != null) {
            // Draw the opaque result bitmap over the scanning rectangle
            paint.setAlpha(OPAQUE);
            canvas.drawBitmap(resultBitmap, frame.left, frame.top, paint);
        } else {
            // Draw a two pixel solid black border inside the framing rect
//            paint.setColor(frameColor);
//            paint.setColor(Color.GRAY);
            paint.setColor(Color.parseColor("#27a1e5"));
            canvas.drawRect(frame.left, frame.top, frame.right + 1, frame.top + 2, paint);
            canvas.drawRect(frame.left, frame.top + 2, frame.left + 2, frame.bottom - 1, paint);
            canvas.drawRect(frame.right - 1, frame.top, frame.right + 1, frame.bottom - 1, paint);
            canvas.drawRect(frame.left, frame.bottom - 1, frame.right + 1, frame.bottom + 1, paint);

            drawAngle(canvas, frame);
            drawScanner(canvas, frame);
//            设置扫描点
//            if (showPossiblePoint) {
//                drawPossiblePoint(canvas, frame);
//            }

            // Request another update at the animation interval, but only repaint the laser line,
            // not the entire viewfinder mask.
            postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top, frame.right, frame.bottom);
        }
    }

    public void drawViewfinder() {
        resultBitmap = null;
        invalidate();
    }

    public void setWidthAndHeight(int width, int hegiht) {
        changeSize = true;
        RECT_WIDTH = width;
        RECT_HEIGHT = hegiht;
    }

    /**
     * Draw a bitmap with the result points highlighted instead of the live scanning display.
     *
     * @param barcode An image of the decoded barcode.
     */
    public void drawResultBitmap(Bitmap barcode) {
        resultBitmap = barcode;
        invalidate();
    }

    public void addPossibleResultPoint(ResultPoint point) {
        possibleResultPoints.add(point);
    }


    /**
     * 画扫描框的八个边角
     *
     * @param canvas
     * @param frame
     */
    private void drawAngle(Canvas canvas, Rect frame) {
        int angleLength = 50;
        int angleWidth = 10;
        int top = frame.top;
        int bottom = frame.bottom;
        int left = frame.left;
        int right = frame.right;

        paint.setColor(Color.parseColor("#27a1e5"));
        // 左上
        canvas.drawRect(left, top, left + angleLength, top + angleWidth, paint);
        canvas.drawRect(left, top, left + angleWidth, top + angleLength, paint);
        // 左下
        canvas.drawRect(left, bottom - angleLength, left + angleWidth, bottom, paint);
        canvas.drawRect(left, bottom - angleWidth, left + angleLength, bottom, paint);
        // 右上
        canvas.drawRect(right - angleLength, top, right, top + angleWidth, paint);
        canvas.drawRect(right - angleWidth, top, right, top + angleLength, paint);
        // 右下
        canvas.drawRect(right - angleLength, bottom - angleWidth, right, bottom, paint);
        canvas.drawRect(right - angleWidth, bottom - angleLength, right, bottom, paint);

//        // 左上
//        canvas.drawRect(left - angleWidth, top - angleWidth, left + angleLength, top, paint);
//        canvas.drawRect(left - angleWidth, top - angleWidth, left, top + angleLength, paint);
//        // 左下
//        canvas.drawRect(left - angleWidth, bottom, left + angleLength, bottom + angleWidth, paint);
//        canvas.drawRect(left - angleWidth, bottom - angleLength, left, bottom + angleWidth, paint);
//        // 右上
//        canvas.drawRect(right - angleLength, top - angleWidth, right + angleWidth, top, paint);
//        canvas.drawRect(right, top - angleWidth, right + angleWidth, top + angleLength, paint);
//        // 右下
//        canvas.drawRect(right - angleLength, bottom, right, bottom + angleWidth, paint);
//        canvas.drawRect(right, bottom - angleLength, right + angleWidth, bottom + angleWidth, paint);
    }

    private void drawText(Canvas canvas, Rect frame) {
        if (cameraPermission == PackageManager.PERMISSION_GRANTED) {
            paint.setColor(hintColor);
            paint.setTextSize(getResources().getDisplayMetrics().density*16);
            paint.setStrokeWidth(getResources().getDisplayMetrics().density*2);
            String text = hint;
            int textWidth = getTextWidth(paint, hint);
//            canvas.drawText(hint, frame.centerX() - text.length() * 36 / 2 + 5, frame.bottom + 35 + 50, paint);
//            drawBitmapPostScale(canvas, frame.centerX(), frame.bottom + 35, paint, text.length() * 50 / 2);
            int textHeight = getTextHeight(paint, hint);

            drawBitmapPostScale(canvas, frame, textWidth / 2, textHeight / 2, paint);
        } else {
            paint.setColor(errorHintColor);
            paint.setTextSize(36);
            String text = errorHint;
            canvas.drawText(errorHint, frame.centerX() - text.length() * 36 / 2, frame.bottom + 35 + 20, paint);
        }
    }

    /**
     * 缩放图片
     *
     * @param canvas
     */
    private void drawBitmapPostScale(Canvas canvas, Rect frame, int textWidth, int textHeight, Paint paint) {
        // 获取图片资源
        Bitmap bmp1 = BitmapFactory.decodeResource(getResources(),
                R.drawable.zxing_bar_code);
        Bitmap bmp2 = BitmapFactory.decodeResource(getResources(),
                R.drawable.zxing_qr_code);
        paint.setColor(Color.parseColor("#c9c9c9"));
        // Matrix类进行图片处理（缩小或者旋转）
        Matrix matrix = new Matrix();
        // 缩小一倍
        matrix.postScale(0.3f, 0.3f);
        // 生成新的图片
        Bitmap dstbmp = Bitmap.createBitmap(bmp1, 0, 0, bmp1.getWidth(),
                bmp1.getHeight(), matrix, true);
        Bitmap dstbmp1 = Bitmap.createBitmap(bmp2, 0, 0, bmp1.getWidth(),
                bmp2.getHeight(), matrix, true);
        // 添加到canvas
        canvas.drawText(hint, frame.centerX() - textWidth, frame.bottom + dstbmp.getHeight() + textHeight, paint);
        canvas.drawBitmap(dstbmp, frame.centerX() - textWidth - dstbmp.getWidth() - 10, frame.bottom + dstbmp.getHeight() / 2, paint);
        canvas.drawBitmap(dstbmp1, frame.centerX() + textWidth + 10, frame.bottom + dstbmp1.getHeight() / 2, paint);
    }

    // 绘制扫描线
    // 如果允许绘制 `possible points`则显示居中的红线
    private void drawScanner(Canvas canvas, Rect frame) {
//        if (!showPossiblePoint) {
//            // Draw a red "laser scanner" line through the middle to show decoding is active
//            paint.setColor(laserColor);
//            paint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
//            scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
//            int middle = frame.height() / 2 + frame.top;
//            canvas.drawRect(frame.left + 2, middle - 1, frame.right - 1, middle + 2, paint);
//        } else {
//            画横线
//            drawLine(canvas,frame);
//            画网格图片
        drawLaserGridScan(canvas, frame, translateY, paint);
//        }
    }


    /**
     * 画横线
     */
    private void drawLine(Canvas canvas, Rect frame) {
        paint.setColor(Color.parseColor("#03A9F4"));
        scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
        canvas.translate(0, translateY);
        canvas.drawRect(frame.left + 10, frame.top, frame.right - 10, frame.top + 10, paint);
        translateY += 10f;
        if (translateY >= frame.bottom) {
            translateY = frame.top;
        }
    }


    /**
     * 画网格图片
     *
     * @param canvas
     * @param frame
     */

    private void drawLaserGridScan(Canvas canvas, Rect frame, float bottom, Paint paint) {
        if (laserLineBitmap == null)//图片资源文件转为 Bitmap
            laserLineBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.zxing_grid_scan_line);

        // Matrix类进行图片处理（缩小或者旋转）
        Matrix matrix = new Matrix();
        // 缩小一倍
        matrix.postScale(0.2f, 0.2f);
        // 生成新的图片
        Bitmap dstbmp = Bitmap.createBitmap(laserLineBitmap, 0, 0, laserLineBitmap.getWidth(),
                laserLineBitmap.getHeight(), matrix, true);
        //网格图片
        int height = dstbmp.getHeight();//取原图高
        translateY += 10f;
        if (translateY >= frame.bottom) {
            translateY = frame.top;
        }
        RectF dstRectF = new RectF(frame.left, frame.top, frame.right, translateY);
        Rect srcRect = new Rect(0, (int) (height - dstRectF.height())
                , dstbmp.getWidth(), height);
        canvas.drawBitmap(dstbmp, srcRect, dstRectF, paint);
    }

    // Draw a yellow "possible points"
    private void drawPossiblePoint(Canvas canvas, Rect frame) {
        Collection<ResultPoint> currentPossible = possibleResultPoints;
        Collection<ResultPoint> currentLast = lastPossibleResultPoints;
        if (currentPossible.isEmpty()) {
            lastPossibleResultPoints = null;
        } else {
            possibleResultPoints = new HashSet<ResultPoint>(5);
            lastPossibleResultPoints = currentPossible;
            paint.setAlpha(OPAQUE);
            paint.setColor(resultPointColor);
            for (ResultPoint point : currentPossible) {
                canvas.drawCircle(frame.left + point.getX(), frame.top + point.getY(), 6.0f, paint);
            }
        }
        if (currentLast != null) {
            paint.setAlpha(OPAQUE / 2);
            paint.setColor(resultPointColor);
            for (ResultPoint point : currentLast) {
                canvas.drawCircle(frame.left + point.getX(), frame.top + point.getY(), 3.0f, paint);
            }
        }
    }

    // 精确计算文字宽度
    public static int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }

    public static int getTextHeight(Paint paint, String str) {
        //2. 计算文字所在矩形，可以得到宽高
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        int h = rect.height();
        return h;
    }
}