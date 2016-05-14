package com.example.zeinaamhaz.minesweeper.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import com.example.zeinaamhaz.minesweeper.MainActivity;
import com.example.zeinaamhaz.minesweeper.R;
import com.example.zeinaamhaz.minesweeper.model.MinesweeperModel;

public class MinesweeperView extends View {
    //draws char
    private Paint paintBackground;
    private Paint paintLine;
    private Paint paintNumber;


    public MinesweeperView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintBackground = new Paint();
        paintBackground.setColor(Color.GRAY);
        paintBackground.setStyle(Paint.Style.FILL);

        paintNumber = new Paint();
        paintNumber.setColor(Color.WHITE);
        paintNumber.setTextSize(60);

        paintLine = new Paint();
        paintLine.setColor(Color.WHITE);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(5);

    }

    // #2matrices, one for mines, one for what is being drawn

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0, 0,
                getWidth(), getHeight(),
                paintBackground);

        drawGameArea(canvas);

        drawToggle(canvas);
    }

    private void drawGameArea(Canvas canvas) {

        // border
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintLine);
        // four horizontal lines
        canvas.drawLine(0, getHeight() / 5, getWidth(), getHeight() / 5,
                paintLine);
        canvas.drawLine(0, 2 * getHeight() / 5, getWidth(),
                2 * getHeight() / 5, paintLine);
        canvas.drawLine(0, 3 * getHeight() / 5, getWidth(),
                3 * getHeight() / 5, paintLine);
        canvas.drawLine(0, 4 * getHeight() / 5, getWidth(),
                4 * getHeight() / 5, paintLine);

        // four vertical lines
        canvas.drawLine(getWidth() / 5, 0, getWidth() / 5, getHeight(),
                paintLine);
        canvas.drawLine(2 * getWidth() / 5, 0, 2 * getWidth() / 5,
                getHeight(), paintLine);
        canvas.drawLine(3 * getWidth() / 5, 0, 3 * getWidth() / 5,
                getHeight(), paintLine);
        canvas.drawLine(4 * getWidth() / 5, 0, 4 * getWidth() / 5,
                getHeight(), paintLine);
    }

    //flag, ->x, number of adjacent bombs, draw string
    private void drawToggle(Canvas canvas) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {

                if (MinesweeperModel.getInstance().getGameContent(i, j)
                        == MinesweeperModel.FLAG) {

                    float centerX = i * getWidth() / 5 + getWidth() / 10;
                    float centerY = j * getHeight() / 5 + getHeight() / 10;
                    int radius = getHeight() / 10 - 2;
                    canvas.drawCircle(centerX, centerY, radius, paintLine);

                } else if (MinesweeperModel.getInstance().getGameContent(i, j) == MinesweeperModel.getInstance().getMinesContent(i, j)) {
                    canvas.drawText(Short.toString(MinesweeperModel.getInstance().getGameContent(i, j)),
                            (i * getWidth() / 5 + getWidth() / 20), (j * getHeight() / 5 + getHeight() / 7), paintNumber);

                }
            }
        }
    }

    //what happens when u click each cell
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            int tX = ((int) event.getX()) / (getWidth() / 5);
            int tY = ((int) event.getY()) / (getHeight() / 5);

            if (tX < 5 && tY < 5 &&
                    MinesweeperModel.getInstance().getGameContent(tX, tY) ==
                            MinesweeperModel.CELL) {                      //MinesweeperModel.getInstance().didWin();


                if (MinesweeperModel.getInstance().TOGGLE == MinesweeperModel.FLAG) {
                    if (MinesweeperModel.getInstance().getMinesContent(tX, tY) != MinesweeperModel.MINE) {
                        restartGame();
                        ((MainActivity) getContext()).showSnackBarMessage(
                                getContext().getString(R.string.text_lose));
                    } else if (MinesweeperModel.getInstance().getMinesContent(tX, tY) == MinesweeperModel.MINE) {
                        MinesweeperModel.getInstance().setGameContent(tX, tY, MinesweeperModel.getInstance().FLAG);
                        if (MinesweeperModel.getInstance().didWin() == true) {
                            ((MainActivity) getContext()).showSnackBarMessage(
                                    getContext().getString(R.string.text_win));
                        }

                    }
                } else if (MinesweeperModel.getInstance().TOGGLE == MinesweeperModel.TRY) {
                    if (MinesweeperModel.getInstance().getMinesContent(tX, tY) != MinesweeperModel.MINE) {
                        MinesweeperModel.getInstance().setGameContent(tX, tY, MinesweeperModel.getInstance().getMinesContent(tX, tY));

                    } else if (MinesweeperModel.getInstance().getMinesContent(tX, tY) == MinesweeperModel.MINE) {
                        restartGame();
                        ((MainActivity) getContext()).showSnackBarMessage(
                                getContext().getString(R.string.text_lose));
                    }
                }
                invalidate();
            }
        }

        return super.onTouchEvent(event);
    }


    //reset game
    public void restartGame() {

        MinesweeperModel.getInstance().addMines();
        MinesweeperModel.getInstance().clearGameContent();
        invalidate();
    }

    //this is click
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int d = w == 0 ? h : h == 0 ? w : w < h ? w : h;
        setMeasuredDimension(d, d);

    }

    public void whichToggle(short toggle) {
        MinesweeperModel.getInstance().whichToggle(toggle);
    }
}