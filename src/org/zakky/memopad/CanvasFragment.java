
package org.zakky.memopad;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CanvasFragment extends Fragment {

    /**
     * お絵かき用の {@link View} です。
     */
    private PaintView mPaintView;

    private CanvasListener mCanvasListener;

    /**
     * 現在のペンカラーのインデックス。{@code mPecColorLabels} と {@code mPenColorValues}用。
     */
    private int mPenColorIndex;

    /**
     * 現在のペンサイズのインデックス。{@code mPenSizeImages} と {@code mPenSizeValues}用。
     */
    private int mPenSizeIndex;

    /**
     * 現在の背景色のインデックス。{@code mBgColorLabels} と {@code mBgColorValues}用。
     */
    private int mBgColorIndex;

    public CanvasFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.main, null);
        mPaintView = (PaintView) view.findViewById(R.id.canvas);

        /*
         * １つ目の色をデフォルトの色として選択。 メニューラベル更新の都合があるので、反映は #onStart() で行います。
         */
        mPenColorIndex = 0;
        mPenSizeIndex = 0;
        mBgColorIndex = 0;

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCanvasListener = (CanvasListener) activity;
    }

    /**
     * ペンの色を、次の色に変更します。
     */
    public void setNextPenColor(int penColorCount) {
        mPenColorIndex++;
        mPenColorIndex %= penColorCount;
        applyPenColor();
    }

    /**
     * ペンの色を、反映させます。
     */
    public void applyPenColor() {
        if (mCanvasListener == null) {
            return;
        }
        final int argb = mCanvasListener.penColorChanged(mPenColorIndex);
        mPaintView.setPenColor(argb);
    }

    /**
     * ペンのサイズを、次のサイズに変更します。
     */
    public void setNextPenSize(int penSizeCount) {
        mPenSizeIndex++;
        mPenSizeIndex %= penSizeCount;
        applyPenSize();
    }

    /**
     * ペンのサイズを、反映させます。
     */
    public void applyPenSize() {
        if (mCanvasListener == null) {
            return;
        }
        final float penSize = mCanvasListener.penSizeChanged(mPenSizeIndex);
        mPaintView.setPenSize(penSize);
    }

    /**
     * 背景の色を次の色に変更します。
     */
    public void setNextBgColor(int bgColorCount) {
        mBgColorIndex++;
        mBgColorIndex %= bgColorCount;
        applyBgColor();
    }

    /**
     * 背景の色を反映させます。
     */
    public void applyBgColor() {
        if (mCanvasListener == null) {
            return;
        }
        final int bgArgb = mCanvasListener.bgColorChanged(mBgColorIndex);
        mPaintView.setBackgroundColor(bgArgb);
    }

    public Uri saveImageAsPng() {
        final Uri png = mPaintView.saveImageAsPng();
        return png;
    }

    public void clearCanvas() {
        mPaintView.clearCanvas();
    }
}

/**
 * canvas に対するイベントを通知するためのインタフェースです。
 */
interface CanvasListener {

    /**
     * ペンの色が変更されたことを通知し、インデックスから次の色を取得します。
     * @param penColorIndex 次の色のインデックス。
     * @return 次の色の値。
     */
    public int penColorChanged(int penColorIndex);

    /**
     * ペンのサイズが変更されたことを通知し、インデックスから次のサイズを取得します。
     * @param penSizeIndex 次のサイズのインデックス。
     * @return 次のペンサイズ。
     */
    public float penSizeChanged(int penSizeIndex);

    /**
     * 背景色が変更されたことを通知し、インデックスから次の背景色を取得します。
     * @param bgColorIndex 次の背景色のインデックス。
     * @return 次の背景色。
     */
    public int bgColorChanged(int bgColorIndex);
}
