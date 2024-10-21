package org.example.tiktak.Model;

import org.example.tiktak.Piece;

public class AiPlayer extends Player {
    private String difficulty;

    public AiPlayer(BoardImpl board, String difficulty) {
        super(board);
        this.difficulty = difficulty;
    }

    public void makeMove() {

        switch (difficulty) {
            case "Easy":
                makeRandomMove();
                break;
            case "Medium":
                makeMediumMove();
                break;
            case "Hard":
                makeBestMove();  // Full Minimax for hard mode
                break;
            default:
                makeBestMove();  // Default to hard mode
        }
    }

    private void makeRandomMove() {
        // AI makes a random legal move
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.isLegalMove(i, j)) {
                    board.updateMove(i, j, Piece.O);
                    return;
                }
            }
        }
    }

    private void makeMediumMove() {
        // AI uses a mix of random and Minimax moves with depth limit
        if (Math.random() > 0.7) {
            makeRandomMove();  // 30% chance to make a random move
        } else {
            makeBestMoveLimitedDepth();  // Use Minimax but limit depth
        }
    }

    private void makeBestMove() {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[2];

        // Priority: Center > Corners > Edges (for Hard mode)
        if (board.isLegalMove(1, 1)) {
            board.updateMove(1, 1, Piece.O);  // Center move if available
            return;
        }

        // Loop through every possible move on the board
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.isLegalMove(i, j)) {
                    board.updateMove(i, j, Piece.O);  // Try this move
                    int score = minimax(board, 0, false, Integer.MIN_VALUE, Integer.MAX_VALUE);  // Full Minimax with alpha-beta pruning
                    board.updateMove(i, j, Piece.EMPTY);  // Undo the move
                    if (score > bestScore) {  // Keep track of the best score and move
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }
        board.updateMove(bestMove[0], bestMove[1], Piece.O);  // Make the best move
    }

    private void makeBestMoveLimitedDepth() {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[2];

        // Loop through every possible move on the board
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.isLegalMove(i, j)) {
                    board.updateMove(i, j, Piece.O);  // Try this move
                    int score = minimax(board, 0, false, Integer.MIN_VALUE, Integer.MAX_VALUE, 3);  // Limit depth to 3 moves
                    board.updateMove(i, j, Piece.EMPTY);  // Undo the move
                    if (score > bestScore) {  // Keep track of the best score and move
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }
        board.updateMove(bestMove[0], bestMove[1], Piece.O);  // Make the best move
    }

    private int minimax(BoardImpl board, int depth, boolean isMaximizing, int alpha, int beta) {
        Piece winner = board.checkWinner();
        if (winner != null) {
            return winner == Piece.O ? 1 : (winner == Piece.X ? -1 : 0);  // AI win: 1, Human win: -1, Draw: 0
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board.isLegalMove(i, j)) {
                        board.updateMove(i, j, Piece.O);
                        int score = minimax(board, depth + 1, false, alpha, beta);
                        board.updateMove(i, j, Piece.EMPTY);
                        bestScore = Math.max(score, bestScore);
                        alpha = Math.max(alpha, bestScore);
                        if (beta <= alpha) break;  // Alpha-beta pruning
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board.isLegalMove(i, j)) {
                        board.updateMove(i, j, Piece.X);
                        int score = minimax(board, depth + 1, true, alpha, beta);
                        board.updateMove(i, j, Piece.EMPTY);
                        bestScore = Math.min(score, bestScore);
                        beta = Math.min(beta, bestScore);
                        if (beta <= alpha) break;  // Alpha-beta pruning
                    }
                }
            }
            return bestScore;
        }
    }

    // Depth-limited Minimax for Medium Mode
    private int minimax(BoardImpl board, int depth, boolean isMaximizing, int alpha, int beta, int maxDepth) {
        if (depth >= maxDepth) {
            return 0;  // At max depth, return a neutral score
        }
        Piece winner = board.checkWinner();
        if (winner != null) {
            return winner == Piece.O ? 1 : (winner == Piece.X ? -1 : 0);  // AI win: 1, Human win: -1, Draw: 0
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board.isLegalMove(i, j)) {
                        board.updateMove(i, j, Piece.O);
                        int score = minimax(board, depth + 1, false, alpha, beta, maxDepth);
                        board.updateMove(i, j, Piece.EMPTY);
                        bestScore = Math.max(score, bestScore);
                        alpha = Math.max(alpha, bestScore);
                        if (beta <= alpha) break;  // Alpha-beta pruning
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board.isLegalMove(i, j)) {
                        board.updateMove(i, j, Piece.X);
                        int score = minimax(board, depth + 1, true, alpha, beta, maxDepth);
                        board.updateMove(i, j, Piece.EMPTY);
                        bestScore = Math.min(score, bestScore);
                        beta = Math.min(beta, bestScore);
                        if (beta <= alpha) break;  // Alpha-beta pruning
                    }
                }
            }
            return bestScore;
        }
    }



    @Override
    public void move(int row, int col) {

    }
}
