package com.tie_vilsama.vilsamatestapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.tie_vilsama.vilsamatestapp.R;

/**
 * Created by mac on 7/3/16.
 */
public class Table extends View {

    private String tableName;
    private int shapeColor;
    private int shape;
    private int textColor;

    private float mTextHeight;
    private float mTextWidth;

    private Paint mShapePaint;
    private Paint mTableNamePaint;

    public Table(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.Table,
                0, 0);
        try{
            tableName = a.getString(R.styleable.Table_tableName);
            shape = a.getInteger(R.styleable.Table_shape, 0);
            shapeColor = a.getInteger(R.styleable.Table_shapeColor, Color.GRAY);
            textColor = a.getInteger(R.styleable.Table_textColor, Color.BLACK);
        }finally {
            a.recycle();
        }
        init();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String mTableName) {
        this.tableName = mTableName;
        // You have to invalidate the view after any change to its properties that might change its appearance,
        // so that the system knows that it needs to be redrawn.
        invalidate();
        // Likewise, you need to request a new layout if a property changes that might affect the size or shape of the view.
        requestLayout();
    }

    public int getShape() {
        return shape;
    }

    public void setShape(int mShape) {
        this.shape = mShape;
        invalidate();
        requestLayout();
    }

    public int getShapeColor() {
        return shapeColor;
    }

    public void setShapeColor(int shapeColor) {
        this.shapeColor = shapeColor;
        invalidate();
        requestLayout();
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        invalidate();
        requestLayout();
    }

    // If your view doesn't need special control over its size, you only need to override one method: onSizeChanged()
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // Account for padding
        float xpad = (float)(getPaddingLeft() + getPaddingRight());
        float ypad = (float)(getPaddingTop() + getPaddingBottom());

        // Account for the label
        xpad += mTextWidth;

        float ww = (float)w - xpad;
        float hh = (float)h - ypad;

        // Figure out how big we can make the pie.
        float diameter = Math.min(ww, hh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Try for a width based on our minimum
        int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int w = resolveSizeAndState(minw, widthMeasureSpec, 1);

        // Whatever the width ends up being, ask for a height that would let the table
        // get as big as it can
        int minh = MeasureSpec.getSize(w) - (int)mTextWidth + getPaddingBottom() + getPaddingTop();
        int h = resolveSizeAndState(MeasureSpec.getSize(w) - (int)mTextWidth, heightMeasureSpec, 0);

        setMeasuredDimension(w, h);
    }

    // So, before you draw anything, you need to create one or more Paint objects.
    private void init(){
        mTableNamePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTableNamePaint.setColor(textColor);
        if(mTextHeight == 0){
            mTextHeight = mTableNamePaint.getTextSize();
        }else{
            mTableNamePaint.setTextSize(mTextHeight);
        }
        if(mTextWidth == 0){
            mTextWidth = mTableNamePaint.getTextSize();
        }
        mShapePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mShapePaint.setStyle(Paint.Style.FILL);
        mShapePaint.setTextSize(mTextHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);



    }
}
