package org.example.tiktak.Model;

import org.example.tiktak.Piece;

import java.util.Random;

public class AiPlayer extends Player {
    private String difficulty;

    public AiPlayer(BoardImpl board, String difficulty) {
        super(board);//Ai player ge super cls eka wena playerge constructor ekata pass karanawa
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
                makeBestMove();
                break;
//            default://default ekk awashya wen an Easy,Medium,hard 3n ekk dificulty ekata hamawelenma update wenawaaaa
//                makeBestMove();
        }
    }

    private void makeRandomMove() {

//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                if (board.isLegalMove(i, j)) {
//                    board.updateMove(i, j, Piece.O);
//                    return;
//                }
//            }
//        }
        int i, j;
        Random random = new Random();

        do {
            i = random.nextInt(3); // Random row between 0-2
            j = random.nextInt(3); // Random column between 0-2
        } while (!board.isLegalMove(i, j)); // Repeat until a legal move is found

        board.updateMove(i, j, Piece.O); // Make the move(update piece enum )
    }

    private void makeMediumMove() {

        if (Math.random() > 0.7) {//0.7
            makeRandomMove();  // 30% chance zto make a random move
        } else {
            makeBestMoveLimitedDepth();  // Use Minimax but limit depth
        }


    }

    private void makeBestMove() {
        int bestScore = Integer.MIN_VALUE;//-2.147............
        int[] bestMove = new int[2];//length=2

        // Priority: Center > Corners > Edges (for Hard mode)
        if (board.isLegalMove(1, 1)) {//mada eka hisnm eekata tama mulinma daganne hard mode ekedi
            board.updateMove(1, 1, Piece.O);  // Center move if available
            return;
        }

        // Loop through every possible move on the board
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.isLegalMove(i, j)) {//hambena palweni Empty kotuwe idn ynne eetpasse eelaga his kotuwata
                    board.updateMove(i, j, Piece.O);  // Try this move
                    int score = minimax(board, 0, false, Integer.MIN_VALUE, Integer.MAX_VALUE);  // Full Minimax with alpha-beta pruning
                    board.updateMove(i, j, Piece.EMPTY);  // Undo the move(score eka hoyagattama aye emty karagannawa )
                    if (score > bestScore) {  // Keep track of the best score and move(score eka 0 or 1 ,-1nm)
                        bestScore = score;//score eka integer.minvalue ekata wadaa wadinm
                        bestMove[0] = i;//bestmove array eke
                        bestMove[1] = j;
                    }
                }
            }
        }
        board.updateMove(bestMove[0], bestMove[1], Piece.O);  // Make the best move
    }

    private void makeBestMoveLimitedDepth() {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[2];//length=2

        // Loop through every possible move on the board
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.isLegalMove(i, j)) {//hambena palweni Empty kotuwe idn ynne
                    board.updateMove(i, j, Piece.O);  // Try this move (atulen enum ekata dagena balanawa ui wala update karan nantuwa)
                    int score = minimax(board, 0, false, Integer.MIN_VALUE, Integer.MAX_VALUE, 3);  // Limit depth to 3 moves
                    board.updateMove(i, j, Piece.EMPTY);  // Undo the move
                    if (score > bestScore) {  // Keep track of the best score and move
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }//ena bestma score ekata adala kotuwa tama update karaganne
        board.updateMove(bestMove[0], bestMove[1], Piece.O);  // Make the best move
    }

    //minimax for hard mode
    private int minimax(BoardImpl board, int depth, boolean isMaximizing, int alpha, int beta) {//alpha=Integer.MIN_VALUE
        Piece winner = board.checkWinner();                                                     //beta=Integer.MAX_VALUE
        if (winner != null) {  //X,0 or Emptynm(drawnm)                                                                 //depth=0
                                                                                                //isMaximizing default=false
            return winner == Piece.O ? 1 : (winner == Piece.X ? -1 : 0);  // AI win: 1, Human win: -1, Draw: 0
        }
        //uda if eka run wuna kiyanne plleha ewa run wen na return eka nisa e kiyanne kawruhari dinnot hari drawnm hari

        //palleha if and else eken check karan yanne palweni turn eken dinanna bari kota eelaga future best steps calculate karala balana eka
      /*0*/  if (isMaximizing) {     // calculating best move of 0 (0 ta adinna puluwan best ma move eka balanawa)
            int bestScore = Integer.MIN_VALUE;//-2...
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board.isLegalMove(i, j)) {//hambena palweni his kotuwa gannawa(palleha else eka run wuna kiyanne meekat run wenawa is legal move eka true karala aye minimax() call karana nisa)
                        board.updateMove(i, j, Piece.O);
                        int score = minimax(board, depth + 1, false, alpha, beta);//score ekk awot tama palleha tika run wenne e kiyanne 1,0,-1 thunen ekk
                        board.updateMove(i, j, Piece.EMPTY);//aye board eka undo karagannawa
                        bestScore = Math.max(score, bestScore);//Math.max compare karanawa score ekai bestScore ekai deken wadi ekkenawa hoyan eyawa ruturn wenawa aye methanata
                        alpha = Math.max(alpha, bestScore);
                        if (beta <= alpha) break;  // Alpha-beta pruning //alpha Integer.MAX_VALUE wada wadinm ho samanm witarai break wenne
                    }                              //nathnm loop eka tawa run wenawa
                }
            }
            return bestScore;//hodama score eka tama return wenne
        }

        /*X*/  else {//KAwruth dinala nathnm or draw wela nathnm meeka run wenawa // calculating best move of X(Xta adinna puluwan best ma move eka balanawa)
            int bestScore = Integer.MAX_VALUE;//2*10^9
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board.isLegalMove(i, j)) {//hambena palweni his kotuwa gannawa
                        board.updateMove(i, j, Piece.X);
                        int score = minimax(board, depth + 1, true, alpha, beta);//kawrut dinala nati hama witama depth+1 wenawwa
                        board.updateMove(i, j, Piece.EMPTY);//aye empty karan undo karagannawa
                        bestScore = Math.min(score, bestScore);//Math.min compare karanawa score ekai bestScore ekai deken podima ekkenawa hoyan eyawa ruturn wenawa aye methanata
                        beta = Math.min(beta, bestScore);
                        if (beta <= alpha) break;  // Alpha-beta pruning  //beta Integer.MINValue(alfa) wada adunm ho samanm tama loop eka break wenne
                    }                                                     //nathnm loop eka tawa run wenawa
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
