package janggi;

import janggi.data.BoardDao;
import janggi.domain.board.Dynasty;
import janggi.domain.board.BoardSetUp;
import janggi.domain.board.GameState;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Point;
import janggi.domain.piece.Piece;
import janggi.domain.player.Player;
import janggi.domain.player.Players;
import janggi.view.InitializeView;
import janggi.view.JanggiBoardView;
import janggi.view.JanggiBoardView.Movement;
import java.util.List;
import java.util.Map;

public class JanggiGame {

    private final InitializeView initializeView;
    private final JanggiBoardView janggiBoardView;
    private final BoardDao boardDao;

    public JanggiGame(InitializeView initializeView, JanggiBoardView janggiBoardView, BoardDao boardDao) {
        this.initializeView = initializeView;
        this.janggiBoardView = janggiBoardView;
        this.boardDao = boardDao;
    }

    public void start() {
        try {
            play();
        } catch (RuntimeException e) {
            janggiBoardView.printException(e.getMessage());
        }
    }

    private void play() {
        Players players = createPlayers();
        JanggiBoard janggiBoard = createJanggiBoard(players);
        playJanggi(players, janggiBoard);
    }

    private void playJanggi(Players players, JanggiBoard janggiBoard) {
        Dynasty currentTurnDynasty = Dynasty.CHU;
        GameState gameState = GameState.RUN;
        while (gameState.isRun()) {
            Player currentTurnPlayer = players.findDynastyPlayer(currentTurnDynasty);
            try {
                Movement movement = janggiBoardView.readPlayerMove(currentTurnPlayer);
                if (movement.isEnd()) {
                    gameState = GameState.USER_END;
                }
                if (movement.isMove()) {
                    gameState = janggiBoard.move(currentTurnDynasty,
                            new Point(movement.startX(), movement.startY()),
                            new Point(movement.endX(), movement.endY()));
                    janggiBoardView.printBoard(janggiBoard.getBoardPieces());
                    currentTurnDynasty = changePlayerTurn(currentTurnDynasty);
                }
            } catch (RuntimeException e) {
                janggiBoardView.printException(e.getMessage());
            }
        }
        boardDao.deleteAll();
        if(gameState == GameState.USER_END) {
            boardDao.updatePiecePoints(janggiBoard.getBoardPieces());
        }
        if(gameState == GameState.GAME_END) {
            int hanScore = janggiBoard.calculateScore(Dynasty.HAN);
            int chuScore = janggiBoard.calculateScore(Dynasty.CHU);
            janggiBoardView.printScore(hanScore, chuScore);
            janggiBoardView.printWinDynasty(janggiBoard.getWinnerDynasty());
            boardDao.initializePiecePoints();
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
        Map<Point, Piece> pieces = boardDao.getPiecePoints();
        BoardSetUp chuPlayerBoardSetUp = initializeView.readBoardSetUp(players.findDynastyPlayer(Dynasty.CHU));
        BoardSetUp hanPlayerBoardSetUp = initializeView.readBoardSetUp(players.findDynastyPlayer(Dynasty.HAN));
        janggiBoardView.printGameStartMessage();
        JanggiBoard janggiBoard = JanggiBoard.of(pieces, hanPlayerBoardSetUp, chuPlayerBoardSetUp);
        janggiBoardView.printBoard(janggiBoard.getBoardPieces());
        return janggiBoard;
    }
}
