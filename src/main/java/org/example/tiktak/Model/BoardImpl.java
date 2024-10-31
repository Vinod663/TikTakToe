package org.example.tiktak.Model;

import org.example.tiktak.Piece;

public class BoardImpl implements Board {
    private Piece[][] pieces = new Piece[3][3];

    public BoardImpl() {
        initializeBoard();
    }

    @Override
    public void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                pieces[i][j] = Piece.EMPTY;
            }
        }
    }

    @Override
    public boolean isLegalMove(int row, int col) {//Element eka empty walata samana nm return wenawa true nathnm false
        return pieces[row][col] == Piece.EMPTY;
    }

    @Override
    public void updateMove(int row, int col, Piece piece) {
        pieces[row][col] = piece;
    }

    @Override
    public Piece checkWinner() {

        for (int i = 0; i < 3; i++) {
            if (pieces[i][0] != Piece.EMPTY && pieces[i][0] == pieces[i][1] && pieces[i][1] == pieces[i][2]) {
                return pieces[i][0];//X or 0 return (thiras plei 3 check karannawa)
            }
            if (pieces[0][i] != Piece.EMPTY && pieces[0][i] == pieces[1][i] && pieces[1][i] == pieces[2][i]) {
                return pieces[0][i];//X or 0 return (siras plei 3 check karannawa)
            }
        }
        if (pieces[0][0] != Piece.EMPTY && pieces[0][0] == pieces[1][1] && pieces[1][1] == pieces[2][2]) {
            return pieces[0][0];// \-mee atata check karanawa X or 0 return
        }
        if (pieces[0][2] != Piece.EMPTY && pieces[0][2] == pieces[1][1] && pieces[1][1] == pieces[2][0]) {
            return pieces[0][2];// /-mee atata check karanawa X or 0 return
        }


        for (int i = 0; i < 3; i++) { // x or 0 return wunot meeka raun wen naa
            for (int j = 0; j < 3; j++) { //Empty cell ekk tiyenawada kiyala check karala tiyenawanm null return wenawa(kawrut dinala naa hambena palaweni his kotuwa yawanne)
                if (pieces[i][j] == Piece.EMPTY) {
                    return null;
                }
            }
        }

        return Piece.EMPTY;  // (Game is a draw) samana peliyak habela nathnm saha empty cell ekakut nathnm return wenswa empty
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
        return moveCount;
    }

    @Override
    public Piece getPieceAt(int row, int col) {
        return pieces[row][col];//pieces array ekata adala elemet eka pass karanna
    }

    public void printBoard() {
        System.out.println("Current Board State:");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (pieces[i][j] == Piece.EMPTY) {
                    System.out.print("-");  // Print "-" for empty spaces
                } else {
                    System.out.print(pieces[i][j]);  // Print "X" or "O"
                }
                if (j < 2) System.out.print(" | ");  // Add separator between cells
            }
            System.out.println();  // Newline after each row
            if (i < 2) System.out.println("---------");  // Separator between rows
        }
        System.out.println();
    }

}
