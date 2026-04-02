package janggi;

import janggi.domain.Arrangement;
import janggi.domain.Game;
import janggi.domain.GameInfo;
import janggi.domain.GameName;
import janggi.domain.PieceInitInfo;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.SideScore;
import janggi.domain.piece.PieceAttribute;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Runner {
    public static final String END_TEXT = "종료";

    private static final String UNEXPECTED_SERVER_ERROR_LOG_MESSAGE = "예측하지 못한 시스템 오류 발생";
    private static final String SYSTEM_ERROR_MESSAGE = "시스템 오류가 발생하여 게임을 종료합니다.";

    private static final Logger logger = Logger.getLogger(Runner.class.getName());

    private final JanggiService janggiService;
    private final Game game;

    public Runner(JanggiService janggiService, Game game) {
        this.janggiService = janggiService;
        this.game = game;
    }

    public void run() {
        int gameId = manageGameRoom();
        playGame(gameId);
    }

    public int manageGameRoom() {
        List<GameInfo> gameInfos = janggiService.getEntireGame();
        if(gameInfos.isEmpty()) {
            return initArrangeGame();
        }
        OutputView.printGameRoom(gameInfos);

        Optional<Integer> input = InputView.askLoadGame();

        if(input.isEmpty()) {
            return initArrangeGame();
        }

        GameInfo selectedGame = gameInfos.get(input.get() - 1);

        if(selectedGame == null) {
            throw new IllegalArgumentException("잘못된 값을 입력하셨습니다.");
        }

        List<PieceInitInfo> pieceInitInfos = janggiService.getPieceInitInfos(selectedGame.id());
        game.init(pieceInitInfos);
        return selectedGame.id();
    }

    private int initArrangeGame() {
        GameName gameName = new GameName(InputView.askGameName());

        String hanArrangementInput = InputView.askHanArrangement();
        Arrangement hanArrangement = Arrangement.from(hanArrangementInput);

        String choArrangementInput = InputView.askChoArrangement();
        Arrangement choArrangement = Arrangement.from(choArrangementInput);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedNow = LocalDateTime.now().format(formatter);

        List<PieceInitInfo> pieceInitInfos = game.init(hanArrangement, choArrangement);

        return janggiService.addGameData(gameName.name(), formattedNow, formattedNow, pieceInitInfos);
    }

    private void playGame(int gameId) {
        while (playTurnGame(gameId)) {
        }

        Side winnerSide = game.getWinnerSide();

        if(winnerSide == null) {
            printCurrentScore();
            printScoreWinner();
            return;
        }
        OutputView.printWinner(winnerSide);
    }

    private boolean playTurnGame(int gameId) {
        try {
            printCurrentStatus();
            return executeTurn(gameId);
        } catch (IllegalArgumentException e) {
            OutputView.printErrorMessage(e.getMessage());
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, UNEXPECTED_SERVER_ERROR_LOG_MESSAGE, e);
            OutputView.printErrorMessage(SYSTEM_ERROR_MESSAGE);
            return false;
        }
    }

    private void printCurrentStatus() {
        OutputView.printLine();
        OutputView.printBoard(game.getCurrentBoardDto());
        OutputView.printTurn(game.getCurrentSide());
    }

    private boolean executeTurn(int gameId) {
        Optional<List<Integer>> startPositionInput = InputView.askStartPosition();

        if(startPositionInput.isEmpty()) {
            return consentEndGame(gameId);
        }

        Position startPosition = Position.from(startPositionInput.get());

        List<Integer> endPositionInput = InputView.askEndPosition();
        Position endPosition = Position.from(endPositionInput);
        OutputView.printLine();

        PieceAttribute pieceAttribute = game.move(startPosition, endPosition);
        janggiService.movePiece(gameId, startPosition, endPosition, pieceAttribute.side(), pieceAttribute.pieceType());

        printCurrentScore();
        return game.isFinished();
    }

    private boolean consentEndGame(int gameId) {
        String consentInput = InputView.consentEnd();

        if(consentInput.equals(END_TEXT)) {
            return false;
        }

        return executeTurn(gameId);
    }

    private void printScoreWinner() {
        SideScore score = game.getCurrentSideScore();
        if(score.cho() > score.han()) {
            OutputView.printWinner(Side.CHO);
            return;
        }
        OutputView.printWinner(Side.HAN);
    }


    private void printCurrentScore() {
        OutputView.printScore(game.getCurrentSideScore());
    }
}
