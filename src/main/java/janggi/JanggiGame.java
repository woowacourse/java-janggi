package janggi;

import janggi.domain.Dynasty;
import janggi.domain.Player;
import janggi.domain.Players;
import janggi.domain.board.BoardSetUp;
import janggi.domain.board.JanggiBoard;
import janggi.domain.piece.Point;
import janggi.view.InitializeView;
import janggi.view.JanggiBoardView;
import janggi.view.JanggiBoardView.Movement;
import java.util.List;

public class JanggiGame {

    private final InitializeView initializeView;
    private final JanggiBoardView janggiBoardView;

    public JanggiGame(InitializeView initializeView, JanggiBoardView janggiBoardView) {
        this.initializeView = initializeView;
        this.janggiBoardView = janggiBoardView;
    }

    public void start() {
        try {
            Players players = createPlayers();
            JanggiBoard janggiBoard = createJanggiBoard(players);
            play(players, janggiBoard);
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private void play(Players players, JanggiBoard janggiBoard) {
        Dynasty currentTurnDynasty = Dynasty.CHU;
        Player winPlayer = null;
        boolean gameEnded = false;

        while (!gameEnded) {
            Player currentTurnPlayer = players.findDynastyPlayer(currentTurnDynasty);
            try {
                Movement movement = janggiBoardView.readPlayerMove(currentTurnPlayer);
                janggiBoard.move(
                        currentTurnDynasty,
                        new Point(movement.startX(), movement.startY()),
                        new Point(movement.endX(), movement.endY()));
                if (janggiBoard.isDeadKing(currentTurnDynasty.opposite())) {
                    winPlayer = currentTurnPlayer;
                    gameEnded = true;
                }
                currentTurnDynasty = currentTurnDynasty.opposite();
                janggiBoardView.printBoard(janggiBoard.getPieces());
                janggiBoardView.printScore(janggiBoard);
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }
        if (winPlayer != null) {
            janggiBoardView.printResult(winPlayer, janggiBoard);
        }
    }

    private Players createPlayers() {
        Player chuPlayer = new Player(initializeView.readPlayerNickname(Dynasty.CHU), Dynasty.CHU);
        Player hanPlayer = new Player(initializeView.readPlayerNickname(Dynasty.HAN), Dynasty.HAN);
        return new Players(List.of(hanPlayer, chuPlayer));
    }

    private JanggiBoard createJanggiBoard(Players players) {
        BoardSetUp chuPlayerBoardSetUp = initializeView.readBoardSetUp(players.findDynastyPlayer(Dynasty.CHU));
        BoardSetUp hanPlayerBoardSetUp = initializeView.readBoardSetUp(players.findDynastyPlayer(Dynasty.HAN));

        janggiBoardView.printGameStartMessage();
        JanggiBoard janggiBoard = JanggiBoard.of(hanPlayerBoardSetUp, chuPlayerBoardSetUp);
        janggiBoardView.printBoard(janggiBoard.getPieces());

        return janggiBoard;
    }
}
