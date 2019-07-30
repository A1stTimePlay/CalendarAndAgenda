package bss.intern.calendarandagenda.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.ViewCompat;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

import bss.intern.calendarandagenda.Util.CalendarUtil;

public class MyView extends View {

    private enum Direction {
        NONE, LEFT, RIGHT, UP, DOWN
    }

    private Direction mCurrentScrollDirection = Direction.NONE;

    int WeekDifferentFromCurrent = 0;   // number of week different compare to current time

    Paint paint;
    float startX;
    float startY;

    Paint mHeaderTextPaint;
    float mHeaderTextSize = 42f;
    float mHeaderPadding = 24f;
    float mCollumnWidth = 132f;

    float mTimeColumnWidth = 156f;
    float mTimeRowHeight = 168f;

    Paint mToolbarPaint;
    float mToolbarHeight = 168f;
    float mToolbarWidth = 1080f;

    Paint mTitlePaint;
    float mTitleTextSize = 54f;

    private final Context mContext;

    private GestureDetectorCompat mGestureDetector;

    private final GestureDetector.SimpleOnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            switch (mCurrentScrollDirection) {
                case NONE: {
                    // Allow scrolling only in one direction.
                    if (Math.abs(distanceX) > Math.abs(distanceY)) {
                        if (distanceX > 0) {
                            mCurrentScrollDirection = Direction.LEFT;
                        } else {
                            mCurrentScrollDirection = Direction.RIGHT;
                        }
                    } else {
                        if (distanceY > 0) {
                            mCurrentScrollDirection = Direction.DOWN;
                        } else {
                            mCurrentScrollDirection = Direction.UP;
                        }
                    }
                    break;
                }
                case LEFT: {
                    // Change direction if there was enough change.
                    if (Math.abs(distanceX) > Math.abs(distanceY)) {
                        mCurrentScrollDirection = Direction.RIGHT;
                    }
                    break;
                }
                case RIGHT: {
                    // Change direction if there was enough change.
                    if (Math.abs(distanceX) > Math.abs(distanceY)) {
                        mCurrentScrollDirection = Direction.LEFT;
                    }
                    break;
                }
            }

            switch (mCurrentScrollDirection) {
                case LEFT:
                    WeekDifferentFromCurrent += +1;
                    ViewCompat.postInvalidateOnAnimation(MyView.this);
                    break;
                case RIGHT:
                    WeekDifferentFromCurrent += -1;
                    ViewCompat.postInvalidateOnAnimation(MyView.this);
                    break;
            }
            return true;
        }
    };

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        mGestureDetector = new GestureDetectorCompat(mContext, mGestureListener);
        paint = new Paint();
        paint.setTextSize(42);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(2);
        paint.setAntiAlias(true);

        mToolbarPaint = new Paint();
        mToolbarPaint.setColor(Color.BLUE);

        mHeaderTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHeaderTextPaint.setColor(Color.BLUE);
        mHeaderTextPaint.setTextAlign(Paint.Align.CENTER);
        mHeaderTextPaint.setTextSize(mHeaderTextSize);
        mHeaderTextPaint.setTypeface(Typeface.SANS_SERIF);

        mTitlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTitlePaint.setColor(Color.WHITE);
        mTitlePaint.setTextSize(mTitleTextSize);
        mTitlePaint.setTypeface(Typeface.SANS_SERIF);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawToolbar(canvas);
        drawHeader(canvas);
        drawTimeStamp(canvas);
        drawSeperate(canvas);
    }

    private void drawToolbar(Canvas canvas) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, WeekDifferentFromCurrent);

        canvas.drawRect(0, 0, mToolbarWidth, mToolbarHeight, mToolbarPaint);

        Rect rect = new Rect();
        mTitlePaint.getTextBounds(CalendarUtil.stringMonthInYear(calendar), 0, CalendarUtil.stringMonthInYear(calendar).length(), rect);

        float textWidth = rect.width();
        float textHeigth = rect.height();

        float startX = mTimeColumnWidth;
        float startY = (mToolbarHeight + textHeigth) / 2;
        canvas.drawText(CalendarUtil.stringMonthInYear(calendar), startX, startY, mTitlePaint);
    }

    private void drawHeader(Canvas canvas) {
        startX = mTimeColumnWidth;
        startY = mHeaderPadding + mHeaderTextSize + mToolbarHeight;

        int temp = 1 - CalendarUtil.today().get(Calendar.DAY_OF_WEEK);
        System.out.println(temp);
        for (int i = 0; i < 7; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, temp + i);
            canvas.drawText(CalendarUtil.stringDayInWeek(calendar), startX + mCollumnWidth * i + mCollumnWidth / 2, startY, mHeaderTextPaint);  // dòng 1
            canvas.drawText(CalendarUtil.stringDayInMonth(calendar), startX + mCollumnWidth * i + mCollumnWidth / 2, startY + mHeaderPadding + mHeaderTextSize, mHeaderTextPaint);  // dòng 2

        }
    }

    private void drawTimeStamp(Canvas canvas) {
        Rect rect = new Rect();
        mHeaderTextPaint.getTextBounds("00:00", 0, "00:00".length(), rect);

        float textWidth = rect.width();
        float textHeight = rect.height();

        startX = textWidth / 2 + mHeaderPadding;
        startY = mToolbarHeight + mHeaderPadding * 4 + mHeaderTextSize * 2;
        for (int i = 0; i < 24; i++) {
            NumberFormat formatter = new DecimalFormat("00");
            String s = formatter.format(i);
            String timeStamp = s + ":00";
            canvas.drawText(timeStamp, startX, startY + mTimeRowHeight * i + textHeight / 2, mHeaderTextPaint);
        }
    }

    private void drawSeperate(Canvas canvas) {

        // day seperate
        float[] dayLines = new float[7 * 4];
        startX = mTimeColumnWidth;
        startY = mHeaderPadding * 3 + mHeaderTextSize * 2 + mToolbarHeight;
        for (int i = 0; i < 7; i++) {
            dayLines[i * 4] = startX + mCollumnWidth * i;
            dayLines[i * 4 + 1] = startY;
            dayLines[i * 4 + 2] = startX + mCollumnWidth * i;
            dayLines[i * 4 + 3] = 1920f;
        }
        canvas.drawLines(dayLines, paint);

        // time seperate
        float[] timeLines = new float[9 * 4];
        startX = mTimeColumnWidth - 24;
        startY = mToolbarHeight + mHeaderPadding * 4 + mHeaderTextSize * 2;
        for (int i = 0; i < 9; i++) {
            timeLines[i * 4] = startX;
            timeLines[i * 4 + 1] = startY + mTimeRowHeight * i;
            timeLines[i * 4 + 2] = 1080f;
            timeLines[i * 4 + 3] = startY + mTimeRowHeight * i;
        }
        canvas.drawLines(timeLines, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean val = mGestureDetector.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_UP){
            mCurrentScrollDirection = Direction.NONE;
        }
        return val;
    }
}
