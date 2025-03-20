package janggi;

import janggi.domain.Dynasty;
import janggi.domain.Player;
import janggi.domain.Players;
import janggi.domain.board.BoardSetUp;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.point.DefaultPoint;
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
            play();
        } catch (RuntimeException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private void play() {
        Players players = createPlayers();
        JanggiBoard janggiBoard = createJanggiBoard(players);
        playJanggi(players, janggiBoard);
    }

    private void playJanggi(Players players, JanggiBoard janggiBoard) {
        Dynasty currentTurnDynasty = Dynasty.CHU;
        while (true) {
            Player currentTurnPlayer = players.findDynastyPlayer(currentTurnDynasty);
            try {
                Movement movement = janggiBoardView.readPlayerMove(currentTurnPlayer);
                if (movement.isEnd()) {
                    break;
                }
                if (movement.isMove()) {
                    janggiBoard.move(currentTurnDynasty, new DefaultPoint(movement.startX(), movement.startY()),
                            new DefaultPoint(movement.endX(), movement.endY()));
                    janggiBoardView.printBoard(janggiBoard.getPointPieces());
                    currentTurnDynasty = changePlayerTurn(currentTurnDynasty);
                }
            } catch (RuntimeException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }
    }

    private Dynasty changePlayerTurn(Dynasty currentTurnDynasty) {
        if (currentTurnDynasty == Dynasty.HAN) {
            return Dynasty.CHU;
        }
        return Dynasty.HAN;
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
        janggiBoardView.printBoard(janggiBoard.getPointPieces());

        return janggiBoard;
    }
}
