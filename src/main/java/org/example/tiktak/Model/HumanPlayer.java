package org.example.tiktak.Model;

import org.example.tiktak.Piece;

public class HumanPlayer extends Player {

    public HumanPlayer(BoardImpl board) {///////////////////
        super(board);
    }

    @Override
    public void move(int row, int col) {///////////////////
        if (board.isLegalMove(row, col)) {
            board.updateMove(row, col, Piece.X);
        }
    }
}
