package org.example.tiktak.Model;

import org.example.tiktak.Piece;

public interface Board {
    void initializeBoard();
    boolean isLegalMove(int row, int col);
    void updateMove(int row, int col, Piece piece);
    Piece checkWinner();
    Piece getPieceAt(int row, int col);
}
