package janggi;

import janggi.domain.board.BoardSetUp;
import janggi.domain.board.JanggiBoard;
import janggi.domain.piece.Dynasty;
import janggi.domain.piece.Point;
import janggi.view.InitializeView;
import janggi.view.JanggiBoardView;
import janggi.view.JanggiBoardView.Movement;

public class JanggiGame {

    private final InitializeView initializeView;
    private final JanggiBoardView janggiBoardView;

    public JanggiGame(InitializeView initializeView, JanggiBoardView janggiBoardView) {
        this.initializeView = initializeView;
        this.janggiBoardView = janggiBoardView;
    }

    public void start() {
        try {
            JanggiBoard janggiBoard = createJanggiBoard();
            play(janggiBoard);
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private void play(JanggiBoard janggiBoard) {
        Dynasty currentTurnDynasty = Dynasty.CHU;
        Dynasty winDynasty = Dynasty.EMPTY;
        boolean gameEnded = false;

        while (!gameEnded) {
            try {
                Movement movement = janggiBoardView.readPlayerMove(currentTurnDynasty);
                janggiBoard.move(
                        currentTurnDynasty,
                        new Point(movement.startX(), movement.startY()),
                        new Point(movement.endX(), movement.endY()));
                if (janggiBoard.isDeadKing(currentTurnDynasty.opposite())) {
                    winDynasty = currentTurnDynasty;
                    gameEnded = true;
                }
                currentTurnDynasty = currentTurnDynasty.opposite();
                janggiBoardView.printBoard(janggiBoard.getPieces());
                janggiBoardView.printScore(janggiBoard);
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }
        if (winDynasty != Dynasty.EMPTY) {
            janggiBoardView.printResult(winDynasty, janggiBoard);
        }
    }

    private JanggiBoard createJanggiBoard() {
        BoardSetUp chuPlayerBoardSetUp = initializeView.readBoardSetUp(Dynasty.CHU);
        BoardSetUp hanPlayerBoardSetUp = initializeView.readBoardSetUp(Dynasty.HAN);

        janggiBoardView.printGameStartMessage();
        JanggiBoard janggiBoard = JanggiBoard.of(hanPlayerBoardSetUp, chuPlayerBoardSetUp);
        janggiBoardView.printBoard(janggiBoard.getPieces());

        return janggiBoard;
    }
}
