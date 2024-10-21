package org.example.tiktak.Model;

import org.example.tiktak.Piece;

public class BoardImpl implements Board {
    private Piece[][] pieces = new Piece[3][3];

    public BoardImpl() {
        initializeBoard();
    }

    @Override
    public void initializeBoard() {/////////////////////
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                pieces[i][j] = Piece.EMPTY;
            }
        }
    }

    @Override
    public boolean isLegalMove(int row, int col) {//////////////////
        return pieces[row][col] == Piece.EMPTY;
    }

    @Override
    public void updateMove(int row, int col, Piece piece) {///////////////////
        pieces[row][col] = piece;
    }

    @Override
    public Piece checkWinner() {
        // Check rows, columns, and diagonals for a winner
        for (int i = 0; i < 3; i++) {
            if (pieces[i][0] != Piece.EMPTY && pieces[i][0] == pieces[i][1] && pieces[i][1] == pieces[i][2]) {
                return pieces[i][0];
            }
            if (pieces[0][i] != Piece.EMPTY && pieces[0][i] == pieces[1][i] && pieces[1][i] == pieces[2][i]) {
                return pieces[0][i];
            }
        }
        if (pieces[0][0] != Piece.EMPTY && pieces[0][0] == pieces[1][1] && pieces[1][1] == pieces[2][2]) {
            return pieces[0][0];
        }
        if (pieces[0][2] != Piece.EMPTY && pieces[0][2] == pieces[1][1] && pieces[1][1] == pieces[2][0]) {
            return pieces[0][2];
        }

        // Check for a draw (no more moves left)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (pieces[i][j] == Piece.EMPTY) {
                    return null;  // Still moves left, no winner yet
                }
            }
        }

        return Piece.EMPTY;  // Game is a draw
    }

    public int getMoveCount() {
        int moveCount = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (pieces[i][j] != Piece.EMPTY) {
                    moveCount++;
                }
            }
        }
        return moveCount;  // Returns the number of non-empty cells (i.e., the number of moves made)
    }

    @Override
    public Piece getPieceAt(int row, int col) {
        return pieces[row][col];
    }
}
