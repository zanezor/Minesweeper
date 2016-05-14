package com.example.zeinaamhaz.minesweeper.model;

import java.util.Random;

public class MinesweeperModel {
    //main calls view, draws, then model
    private static MinesweeperModel instance = null;

    private MinesweeperModel() {
        addMines();
    }

    //only want one instance going on, checks if only one instance
    public static MinesweeperModel getInstance() {
        if (instance == null) {
            instance = new MinesweeperModel();
        }
        return instance;
    }


    public static short EMPTY = 0; // empty-- increments
    public static final short MINE = -2; // mine
    public static short CELL = -4;

    public static short TOGGLE = -3;
    public static final short FLAG = -1;
    public static final short TRY = -3;

    private short[][] mines = {
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY}
    };

    private short[][] game = {
            {CELL, CELL, CELL, CELL, CELL},
            {CELL, CELL, CELL, CELL, CELL},
            {CELL, CELL, CELL, CELL, CELL},
            {CELL, CELL, CELL, CELL, CELL},
            {CELL, CELL, CELL, CELL, CELL}
    };


    //sets toggle to either Try or Flag button
    public void whichToggle(short t) {
        TOGGLE = t;
    }

    public void clearGameContent() {
        TOGGLE = -3;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                game[i][j] = CELL;
            }
        }
    }

    public boolean didWin() {
        int minesFound = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (game[i][j] == FLAG) {
                    minesFound++;
                }
            }
        }
        if (minesFound == 3) {
            addMines();
            clearGameContent();
            return true;
        } else return false;
    }

    public void addMines() {
        //clear mine board
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                mines[i][j] = EMPTY;
            }
        }
//pick random cell
        Random random = new Random();
        int mineRow;
        int mineCol;

        for (int i = 0; i < 3; i++) {
            mineRow = random.nextInt(5);
            mineCol = random.nextInt(5);
//put mine in random cell
            if (mines[mineRow][mineCol] == EMPTY) {
                mines[mineRow][mineCol] = MINE;
                updateSurr(mineRow, mineCol);
            } else
                i--;
        }
    }

    public void updateSurr(int mineRow, int mineCol) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if ((mineRow + i >= 0) && (mineRow + i < 5) && (mineCol + j >= 0) && (mineCol + j < 5)) {
                    if (mines[mineRow + i][mineCol + j] != MINE) {
                        mines[mineRow + i][mineCol + j]++;
                    }
                }
            }
        }

    }


    public short getMinesContent(int x, int y) {
        return mines[x][y];
    }

    public short getGameContent(int x, int y) {
        return game[x][y];
    }

    public short setGameContent(int x, int y, short content) {
        return game[x][y] = content;
    }

}