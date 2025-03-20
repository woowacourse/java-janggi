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

    public void play() {
        Player chuPlayer = new Player(initializeView.readPlayerNickname(Dynasty.CHU), Dynasty.CHU);
        Player hanPlayer = new Player(initializeView.readPlayerNickname(Dynasty.HAN), Dynasty.HAN);
        Players players = new Players(List.of(hanPlayer, chuPlayer));

        BoardSetUp chuPlayerBoardSetUp = initializeView.readBoardSetUp(chuPlayer);
        BoardSetUp hanPlayerBoardSetUp = initializeView.readBoardSetUp(hanPlayer);

        janggiBoardView.printGameStartMessage();
        JanggiBoard janggiBoard = JanggiBoard.of(hanPlayerBoardSetUp, chuPlayerBoardSetUp);
        janggiBoardView.printBoard(janggiBoard.getPointPieces());

        Dynasty currentTurnDynasty = Dynasty.CHU;
        while (true) {
            Player currentTurnPlayer = players.findDynastyPlayer(currentTurnDynasty);
            Movement movement = janggiBoardView.readPlayerMove(currentTurnPlayer);
            if (movement.command().equals("end")) {
                break;
            }
            if (movement.command().equals("move")) {
                janggiBoard.move(currentTurnDynasty, new DefaultPoint(movement.startX(), movement.startY()),
                        new DefaultPoint(movement.endX(), movement.endY()));
                janggiBoardView.printBoard(janggiBoard.getPointPieces());
                if (currentTurnDynasty == Dynasty.HAN) {
                    currentTurnDynasty = Dynasty.CHU;
                    continue;
                }
                currentTurnDynasty = Dynasty.HAN;
            }
        }
    }
}
