package sg.com.bitwave.shoppingsystem.fragments;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sg.com.bitwave.shoppingsystem.R;

public class ProductListFragment extends Fragment implements View.OnClickListener {

    private TextView tvKeyword1;
    private TextView tvKeyword2;

    private int selectedColor;
    private int unselectedColor;
    private int textSelectedColor;
    private int textUnselectedColor;

    public ProductListFragment() {
        // Empty constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_product_list, container, false);

        selectedColor = ContextCompat.getColor(getContext(), R.color.colorRed);
        unselectedColor = ContextCompat.getColor(getContext(), R.color.grey300);
        textSelectedColor = ContextCompat.getColor(getContext(), R.color.white);
        textUnselectedColor = ContextCompat.getColor(getContext(), R.color.black);

        tvKeyword1 = (TextView) rootview.findViewById(R.id.tv_keyword_1);
        tvKeyword2 = (TextView) rootview.findViewById(R.id.tv_keyword_2);
        tvKeyword1.setOnClickListener(this);
        tvKeyword2.setOnClickListener(this);
        tvKeyword1.setSelected(true);
        tvKeyword2.setSelected(true);

        setRoundRectBackground(tvKeyword1, selectedColor, textSelectedColor);
        setRoundRectBackground(tvKeyword2, selectedColor, textSelectedColor);

        return rootview;
    }
    private void setRoundRectBackground(TextView tvTarget, int bgColor, int textColor) {
        tvTarget.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED); // Must call this before getMeasured functions
        int width = tvTarget.getMeasuredWidth();
        int height = tvTarget.getMeasuredHeight();
        int startX = tvTarget.getLeft();
        int startY = tvTarget.getTop();

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.TRANSPARENT);
        canvas.drawPaint(paint);
        canvas.drawBitmap(output, 0.0f, 0.0f, paint);
        paint.setColor(bgColor);

        //Log.i("LIST FRAGMENT", "width: "+ width + " height: " + height);
        Log.i("LIST FRAGMENT", "startX: "+ startX + " startY: " + startY);
        // Draw rounded rectangle
        RectF rectF = new RectF(0, 0, width, height);
        Log.i("LIST FRAGMENT", "rectF.width: "+ rectF.width() + " rectF.height: " + rectF.height());
        canvas.drawRoundRect(rectF, 10, 10, paint);

        Drawable circle = new BitmapDrawable(getResources(), output);
        tvTarget.setBackground(circle);
        tvTarget.setTextColor(textColor);
    }

    @Override
    public void onClick(View view) {
        switch ( view.getId() ) {
            case R.id.tv_keyword_1:
                if ( tvKeyword1.isSelected() ) {
                    setRoundRectBackground(tvKeyword1, unselectedColor, textUnselectedColor);
                    tvKeyword1.setSelected(false);
                } else {
                    setRoundRectBackground(tvKeyword1, selectedColor, textSelectedColor);
                    tvKeyword1.setSelected(true);
                }
                break;
            case R.id.tv_keyword_2:
                if ( tvKeyword2.isSelected() ) {
                    setRoundRectBackground(tvKeyword2, unselectedColor, textUnselectedColor);
                    tvKeyword2.setSelected(false);
                } else {
                    setRoundRectBackground(tvKeyword2, selectedColor, textSelectedColor);
                    tvKeyword2.setSelected(true);
                }
                break;
        }
    }
}
