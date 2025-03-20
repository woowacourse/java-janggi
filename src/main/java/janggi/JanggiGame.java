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
        // 1. 이름입력
        Player hanPlayer = new Player(initializeView.readPlayerNickname(Dynasty.HAN), Dynasty.HAN);
        Player chuPlayer = new Player(initializeView.readPlayerNickname(Dynasty.CHU), Dynasty.CHU);
        Players players = new Players(List.of(hanPlayer, chuPlayer));

        // 2. 상차림선택
        BoardSetUp hanPlayerBoardSetUp = initializeView.readBoardSetUp(hanPlayer);
        BoardSetUp chuPlayerBoardSetUp = initializeView.readBoardSetUp(chuPlayer);

        // 3. 게임 시작 -> 초기 보드판 출력
        janggiBoardView.printGameStartMessage();
        JanggiBoard janggiBoard = JanggiBoard.of(hanPlayerBoardSetUp, chuPlayerBoardSetUp);
        janggiBoardView.printBoard(janggiBoard.getPointPieces());

        // 4. 번갈아가면서움직임 입력 -> 움직임에 따른 보드판 출력
        Dynasty currentTurnDynasty = Dynasty.HAN;
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

        // 예외 발생 시 message 출력 -> 해당 부분부터 재입력
        //리팩토링
        //테스트 코드 더 작성
    }
}
