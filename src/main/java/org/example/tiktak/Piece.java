package org.example.tiktak;

public enum Piece {
    X, O, EMPTY;

    @Override
    public String toString() {
        switch (this) {
            case X: return "X";
            case O: return "O";
            default: return " ";
        }
    }
}
