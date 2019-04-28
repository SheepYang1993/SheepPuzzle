package com.sheepyang1993.sheeppuzzle.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.sheepyang1993.sheeppuzzle.utils.LogUtil;

/**
 * @author SheepYang
 * @email 332594623@qq.com
 * @date 2019/4/28 8:41
 * @describe 游戏容器
 */
public class GameContainer extends LinearLayout {
    private static final String TAG = "左右滑动";
    /**
     * 游戏区域-宽高最小显示比例
     */
    private static final float MIN_HEIGHT_WIDTH_RATE = 0.7f;
    private ViewDragHelper mViewDragHelper;
    private View mGamePanel;
    private int mScreenWidth;
    private int mScreenHeight;

    public GameContainer(Context context) {
        this(context, null);
    }

    public GameContainer(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GameContainer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onFinishInflate() {
        mGamePanel = getChildAt(0);
        super.onFinishInflate();
    }

    private void init() {
        mScreenWidth = ScreenUtils.getScreenWidth();
        mScreenHeight = ScreenUtils.getScreenHeight();
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(@NonNull View view, int i) {
                if (mGamePanel != null && mGamePanel == view) {
                    return true;
                }
                return false;
            }

            @Override
            public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
                int currentLeft = limitHorizontalScroll(child, left, dx);
                return currentLeft;
            }

            @Override
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
                int currentTop = limitVerticalScroll(child, top, dy);
                return currentTop;
            }
        });
    }

    /**
     * 限制垂直滑动范围
     *
     * @param child
     * @param top
     * @param dy
     * @return
     */
    private int limitVerticalScroll(View child, int top, int dy) {
        int childHeight = child.getHeight();
        int childBottom = top + childHeight;
        int exceedTop = 0;
        int exceedBottom = 0;
        int bottom = mScreenHeight - childBottom - BarUtils.getActionBarHeight() - BarUtils.getStatusBarHeight();
        if (bottom < 0) {
            exceedBottom = bottom;
        }
        if (top < 0) {
            exceedTop = top;
        }
        int childShowHeight = childHeight + exceedTop + exceedBottom;
        float childMinShowHeight = childHeight * MIN_HEIGHT_WIDTH_RATE;
        LogUtil.v(TAG, "top:" + top + ", dy:" + dy + ", childBottom:" + childBottom + ", exceedBottom:" + exceedBottom + ", childShowHeight:" + childShowHeight + ", childMinShowHeight:" + childMinShowHeight + ", childHeight:" + childHeight);
        if (dy < 0) {
            //上滑
            if (exceedTop < 0 && childShowHeight <= childMinShowHeight) {
                return (int) (childMinShowHeight - childHeight);
            }
        } else {
            //下滑
            if (exceedBottom < 0 && childShowHeight <= childMinShowHeight) {
                return (int) (mScreenHeight - childMinShowHeight - BarUtils.getActionBarHeight() - BarUtils.getStatusBarHeight());
            }
        }

        return top;
    }

    /**
     * 限制水平滑动范围
     *
     * @param child
     * @param left
     * @param dx
     * @return
     */
    private int limitHorizontalScroll(@NonNull View child, int left, int dx) {
        int childWidth = child.getWidth();
        int childRight = left + childWidth;
        int exceedLeft = 0;
        int exceedRight = 0;
        int right = mScreenWidth - childRight;
        if (right < 0) {
            exceedRight = right;
        }
        if (left < 0) {
            exceedLeft = left;
        }
        int childShowWidth = childWidth + exceedLeft + exceedRight;
        float childMinShowWidth = childWidth * MIN_HEIGHT_WIDTH_RATE;
//                LogUtil.v(TAG, "left:" + left + ", dx:" + dx + ", childRight:" + childRight + ", exceedRight:" + exceedRight + ", childShowWidth:" + childShowWidth + ", childMinShowWidth:" + childMinShowWidth + ", childWidth:" + childWidth);
        if (dx < 0) {
            //左滑
            if (exceedLeft < 0 && childShowWidth <= childMinShowWidth) {
                return (int) (childMinShowWidth - childWidth);
            }
        } else {
            //右滑
            if (exceedRight < 0 && childShowWidth <= childMinShowWidth) {
                return (int) (mScreenWidth - childMinShowWidth);
            }
        }

        return left;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }
}
