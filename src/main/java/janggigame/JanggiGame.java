package janggigame;

import domain.board.Board;
import domain.piece.Side;
import domain.position.Position;
import dto.BoardResponseDto;
import dto.JanggiGameResultResponseDto;
import janggigame.result.LoadGameResult;
import janggigame.result.TurnResult;
import service.JanggiGameService;
import util.Parser;
import view.InputView;
import view.OutputView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JanggiGame {
    private final JanggiGameService janggiGameService;

    public JanggiGame(JanggiGameService janggiGameService) {
        this.janggiGameService = janggiGameService;
    }

    public void run() {
        GameMetaData gameMetaData = loadOrCreateNewGame();
        Board board = loadOrInitBoard(gameMetaData);
        processByStatus(board, gameMetaData);
    }

    private GameMetaData loadOrCreateNewGame() {
        OutputView.printLoadGame();
        LoadGameResult loadGameResult = janggiGameService.loadOrCreateNewGame();

        if (loadGameResult.isNewGame()) {
            OutputView.printCreateNewGame();
        }
        return loadGameResult.gameMetaData();
    }

    private Board loadOrInitBoard(GameMetaData gameMetaData) {
        return janggiGameService.loadOrInitBoard(gameMetaData);
    }

    private void processByStatus(Board board, GameMetaData gameMetaData) {
        if (gameMetaData.getStatus() == JanggiGameStatus.WAITING_HAN_PLACEMENT) {
            handleWaitingHanPlacement(board, gameMetaData);
            return;
        }
        if (gameMetaData.getStatus() == JanggiGameStatus.WAITING_CHO_PLACEMENT) {
            handleWaitingChoPlacement(board, gameMetaData);
            return;
        }
        handleInProgress(board, gameMetaData);
    }

    private void handleWaitingHanPlacement(Board board, GameMetaData gameMetaData) {
        selectSide();
        initPlacement(Side.HAN, board);
        janggiGameService.completePlacement(board, gameMetaData, Side.HAN, JanggiGameStatus.WAITING_CHO_PLACEMENT);
        handleWaitingChoPlacement(board, gameMetaData);
    }

    private void handleWaitingChoPlacement(Board board, GameMetaData gameMetaData) {
        initPlacement(Side.CHO, board);
        janggiGameService.completePlacement(board, gameMetaData, Side.CHO, JanggiGameStatus.IN_PROGRESS);
        handleInProgress(board, gameMetaData);
    }

    private void handleInProgress(Board board, GameMetaData gameMetaData) {
        playGame(board, gameMetaData);
        ScoreBoard scoreBoard = board.calculateScore();
        showResult(board, scoreBoard);
        janggiGameService.updateGameStatus(gameMetaData, JanggiGameStatus.FINISHED);
    }

    private void selectSide() {
        while (true) {
            try {
                String input = InputView.inputSideChoice();
                int sideCode = Parser.parseToSideCode(input);
                Side side = generateSide(sideCode);
                OutputView.printSideChoiceResult(side);
                return;
            } catch (Exception e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Side generateSide(int sideCode) {
        List<Side> sides = Arrays.asList(Side.values());
        Collections.shuffle(sides);

        return sides.get(sideCode - 1);
    }

    private void initPlacement(Side side, Board board) {
        while (true) {
            try {
                String input = inputPlacementCode(side);
                int code = Parser.parseToPlacementCode(input);
                janggiGameService.placePiece(board, side, code);
                printBoard(board);
                return;
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private String inputPlacementCode(Side side) {
        if (side == Side.HAN) return InputView.inputHanPlacementCode();
        return InputView.inputChoPlacementCode();
    }

    private void playGame(Board board, GameMetaData gameMetaData) {
        printBoard(board);
        while (!gameMetaData.isGameOver(board)) {
            try {
                OutputView.printSide(gameMetaData.getCurrentTurnSide());

                Position from = selectFromPosition();
                Position to = selectToPosition();

                TurnResult turnResult = janggiGameService.processTurn(from, to, board, gameMetaData);
                printBoard(board);

                if (turnResult.isIncreaseJangGunCount()) {
                    OutputView.printIsJangGun();
                }
                gameMetaData.changeCurrentTurn(gameMetaData.getCurrentTurnSide());
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Position selectFromPosition() {
        String input = InputView.inputFromPosition();
        return Parser.parseToPosition(input);
    }

    private Position selectToPosition() {
        String input = InputView.inputToPosition();
        return Parser.parseToPosition(input);
    }

    private void printBoard(Board board) {
        BoardResponseDto nowBoardState = BoardResponseDto.from(board);
        OutputView.printBoard(nowBoardState);
    }

    private void showResult(Board board, ScoreBoard scoreBoard) {
        if (board.isEmptyGeneral(Side.CHO)) {
            OutputView.printWinSide(Side.HAN);
            return;
        }
        if (board.isEmptyGeneral(Side.HAN)) {
            OutputView.printWinSide(Side.CHO);
            return;
        }
        OutputView.printScoreBothSide(JanggiGameResultResponseDto.from(scoreBoard));
    }
}
