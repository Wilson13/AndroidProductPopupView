package sg.com.bitwave.shoppingsystem.Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import sg.com.bitwave.shoppingsystem.R;

public class PopupCardImageView extends View implements View.OnTouchListener {

    private Bitmap imageBitmap;
    private Bitmap resizedBitmap;
    private Paint paint;
    private Path path;
    private Canvas mCanvas;
    private Rect rect;

    private int width;
    private int height;

    private float imageX;

    public PopupCardImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PopupCardImageView, 0, 0);
        Drawable srcImage = a.getDrawable(R.styleable.PopupCardImageView_srcImage);
        imageBitmap = ((BitmapDrawable) srcImage).getBitmap();

        Log.i("POPCARD", "imageBitmap width: " + imageBitmap.getWidth());
        Log.i("POPCARD", "imageBitmap height: " + imageBitmap.getHeight());

        /*paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(3);*/

        //path.moveTo(100, 100);
        //path.lineTo(300, 300);

        /*path.moveTo(point2_returned.x, point2_returned.y);
        path.lineTo(point3_returned.x, point3_returned.y);
        path.moveTo(point3_returned.x, point3_returned.y);
        path.lineTo(point1_returned.x, point1_returned.y);*/
        //path.close();

        this.setOnTouchListener(this);
        mCanvas = new Canvas();
    }

    private Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {

        // CREATE A MATRIX FOR THE MANIPULATION
        /*Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();*/

        Bitmap newBitmap = Bitmap.createBitmap(newWidth, newHeight, bm.getConfig());
        Canvas canvas = new Canvas(newBitmap);
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setShader(new BitmapShader(bm, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect((new RectF(0, 0, newWidth, newHeight)), 25, 25, mPaint);// Round Image Corner 100 100 100 100
        //canvas.drawRect(0, newHeight-25, newWidth, newHeight, mPaint);// Round Image Corner 100 100 100 100
        //mimageView.setImageBitmap(imageRounded);

        /*Bitmap newBitmap = Bitmap.createScaledBitmap(bm, newWidth,
                newHeight, false);*/

        return newBitmap;
    }

    private void drawSlantShape(int width, int height) {

        // Draw this shape according to view's width and image's height.
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3);

        path = new Path();

        // Starting point, bottom left corner
        path.moveTo(0, resizedBitmap.getHeight()-50);

        // Top right corner
        path.lineTo(width, resizedBitmap.getHeight()/2);

        // Bottom right corner
        path.lineTo(width, resizedBitmap.getHeight()-50);

        path.close();

        // Draw rect to fill up the btm rounded corners
        rect = new Rect(0, height-50, width, height);
    }

    @Override
    public void onDraw(Canvas canvas) {

        // imageX centers the image
        canvas.drawBitmap(resizedBitmap, imageX, 0, null);
        canvas.drawPath(path, paint);
        canvas.drawRect(rect, paint);

        //canvas.drawBitmap(imageBitmap, imageX, 0, null);
        //super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){

        if ( imageBitmap != null ) {
            width = MeasureSpec.getSize(widthMeasureSpec);
            height = MeasureSpec.getSize(heightMeasureSpec);

            if ( width - imageBitmap.getWidth() > 0 ) {
                // If image is smaller than the view,
                // get space available between view and image, divided by 2.
                imageX = (width - imageBitmap.getWidth()) >> 1;

                // Draw image accordingly to its original size.
                resizedBitmap = getResizedBitmap(imageBitmap, imageBitmap.getWidth(), imageBitmap.getHeight());
                drawSlantShape(width, imageBitmap.getHeight());
            } else {
                // If image is smaller than the view,
                // draw image accordingly to the view size.
                imageX = 0;
                resizedBitmap = getResizedBitmap(imageBitmap, width, height);
                drawSlantShape(width, height);
            }
            setMeasuredDimension(width, height);
        }
        /*else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }*/
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(eventX, eventY);
                path.lineTo(eventX, eventY);
                return true;
            case MotionEvent.ACTION_MOVE:
                //path.quadTo(eventX, eventY, (x + eventX)/2, (y + eventY)/2);
                break;
            case MotionEvent.ACTION_UP:
                //mCanvas.drawPath(path, paint);
                // kill this so we don't double draw
                //path.reset();
                break;
            default:
                return false;
        }

        // Schedules a repaint.
        invalidate();
        return true;
    }
}
