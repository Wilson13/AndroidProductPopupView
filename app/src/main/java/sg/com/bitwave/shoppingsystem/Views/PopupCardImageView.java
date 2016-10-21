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
import android.media.ThumbnailUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import sg.com.bitwave.shoppingsystem.R;

public class PopupCardImageView extends View implements View.OnTouchListener {

    private Bitmap imageBitmap;
    private Bitmap resizedBitmap;
    private Bitmap smallBitmps[];
    private Paint paint;
    private Path path;
    private Canvas mCanvas;
    private Rect rect;

    private int width;
    private int height;

    private int slantHeight;
    private int bubbleRadius;
    private int bubbleOneX;
    private int bubbleTwoX;
    private int bubbleThreeX;
    private int bubbleOneY;
    private int bubbleTwoY;
    private int bubbleThreeY;

    private float imageX;

    public PopupCardImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PopupCardImageView, 0, 0);
        Drawable srcImage = a.getDrawable(R.styleable.PopupCardImageView_srcImage);
        imageBitmap = ((BitmapDrawable) srcImage).getBitmap();

        Log.i("POPCARD", "imageBitmap width: " + imageBitmap.getWidth());
        Log.i("POPCARD", "imageBitmap height: " + imageBitmap.getHeight());

        this.setOnTouchListener(this);
        mCanvas = new Canvas();
    }

    private Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {

        // Center crop the bitmap using ThumbnailUtils
        // OPTIONS_RECYCLE_INPUT is the constant used to indicate we should recycle the input in
        //      extractThumbnail(Bitmap, int, int, int)
        // unless the output is the input. We should not do that as the image is still used.
        Bitmap thumbnailBitmap = ThumbnailUtils.extractThumbnail(bm, newWidth, newHeight);

        // Draw rounded corner on image
        Bitmap newBitmap = Bitmap.createBitmap(newWidth, newHeight, thumbnailBitmap.getConfig());
        Canvas canvas = new Canvas(newBitmap);
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setShader(new BitmapShader(thumbnailBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect((new RectF(0, 0, newWidth, newHeight)), 10, 10, mPaint);// Round Image Corner 100 100 100 100

        return newBitmap;
    }

    private void setSmallBitmaps() {
        for ( int i = 0; i < smallBitmps.length ; i++ ) {
            if ( smallBitmps[i] != null ) {

                // Create a squared center-cropped thumbnail with same width as bubbles.
                int bitmapRadius = width/4;
                Bitmap thumbnailBitmap = ThumbnailUtils.extractThumbnail(smallBitmps[i], bitmapRadius,bitmapRadius);

                // Re-draw the bitmap as rounded bitmap
                Bitmap newBitmap = Bitmap.createBitmap(bitmapRadius, bitmapRadius, thumbnailBitmap.getConfig());
                Canvas canvas = new Canvas(newBitmap);
                Paint mPaint = new Paint();
                mPaint.setAntiAlias(true);
                mPaint.setShader(new BitmapShader(thumbnailBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
                canvas.drawCircle(bitmapRadius/2, bitmapRadius/2, bitmapRadius/2 - 5, mPaint);
                //canvas.drawRect(new Rect(0, 0, width/4, width/4), mPaint);
                smallBitmps[i] = newBitmap;
            }
        }
    }

    private void setSlantShape(int width, int height) {

        // Draw this shape according to view's width and image's height.
        // Image's height - 50 is to leave space for rectangle that
        // cover up the image's btm rounded corner.
        int slantHeight = resizedBitmap.getHeight()-50;

        paint = new Paint();
        paint.setColor(Color.WHITE);
        //paint.setColor(Color.parseColor("#484A47"));
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3);

        path = new Path();

        // Starting point, bottom left corner
        path.moveTo(0, slantHeight);

        // Top right corner
        path.lineTo(width, slantHeight/2);

        // Bottom right corner
        path.lineTo(width, slantHeight);

        path.close();

        // Rect to fill up the btm rounded corners
        rect = new Rect(0, slantHeight, width, height);
    }

    private void setBubbles() {
        // A different slantHeight that is based on the height of view, not the image.
        slantHeight = height - 50;

        // Distribute 1/4 of the width to three bubbles,
        // first bubble shift leftward by 1/4 of width / 3,
        // second bubble remain at center,
        // third bubble shift rightward by 1/4 of width / 3.
        /*bubbleOneX = width/4 - (width/4/3);
        bubbleTwoX = 2 * width/4;
        bubbleThreeX = 3 * width/4 + (width/4/3);*/

        // Diameter is 1/4 of width, so radius is 1/8 of width.
        bubbleRadius = width/8;
        bubbleOneX = width/4;
        bubbleTwoX = 2 * width/4;
        bubbleThreeX = 3 * width/4;

        // Get Y by using ratio.
        // y/x = h/w, so, y = x * h/w
        bubbleOneY = slantHeight - ( width/4 * ( slantHeight/2 )/width );
        bubbleTwoY = slantHeight - ( 2 * width/4 * ( slantHeight/2 )/width );
        bubbleThreeY = slantHeight - ( 3 * width/4 * ( slantHeight/2 )/width );
    }

    public void setLargeImage(Bitmap bitmap) {
        if ( bitmap != null ) {
            imageBitmap = bitmap;
            //bitmap.recycle();
        }
    }

    public void setSmallImages(Bitmap bitmap[]) {
        smallBitmps = bitmap;
    }

    @Override
    public void onDraw(Canvas canvas) {

        // imageX centers the image
        canvas.drawBitmap(resizedBitmap, imageX, 0, null);
        canvas.drawPath(path, paint);
        canvas.drawRect(rect, paint);

        paint.setColor(Color.WHITE);
        //canvas.drawCircle((float) imageBitmap.getWidth()/2, (float) imageBitmap.getHeight(), width/4, paint);

        canvas.drawCircle(bubbleOneX, bubbleOneY, bubbleRadius, paint);
        canvas.drawCircle(bubbleTwoX, bubbleTwoY, bubbleRadius, paint);
        canvas.drawCircle(bubbleThreeX, bubbleThreeY, bubbleRadius, paint);

        canvas.drawBitmap(smallBitmps[0], bubbleOneX-bubbleRadius, bubbleOneY-bubbleRadius, null);
        canvas.drawBitmap(smallBitmps[1], bubbleTwoX-bubbleRadius, bubbleTwoY-bubbleRadius, null);
        canvas.drawBitmap(smallBitmps[2], bubbleThreeX-bubbleRadius, bubbleThreeY-bubbleRadius, null);

        //canvas.drawBitmap(smallBitmps[0], bubbleOneX, bubbleTwoY, null);
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
                setSlantShape(width, imageBitmap.getHeight());
            } else {
                // If image is smaller than the view,
                // draw image accordingly to the view size.
                imageX = 0;
                resizedBitmap = getResizedBitmap(imageBitmap, width, height);
                setSlantShape(width, height);
            }
            setBubbles();
            setSmallBitmaps();
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
