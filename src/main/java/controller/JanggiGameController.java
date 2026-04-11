package controller;

import domain.board.Board;
import domain.piece.Side;
import domain.position.Position;
import dto.BoardResponseDto;
import dto.JanggiGameResultResponseDto;
import domain.janggigame.Game;
import domain.janggigame.GameStatus;
import domain.janggigame.ScoreBoard;
import domain.janggigame.result.LoadGameResult;
import domain.janggigame.result.TurnResult;
import service.JanggiGameService;
import util.Parser;
import view.InputView;
import view.OutputView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JanggiGameController {
    private final JanggiGameService janggiGameService;

    public JanggiGameController(JanggiGameService janggiGameService) {
        this.janggiGameService = janggiGameService;
    }

    public void run() {
        Game game = loadOrCreateNewGame();
        Board board = loadOrInitBoard(game);
        processByStatus(board, game);
    }

    private Game loadOrCreateNewGame() {
        OutputView.printLoadGame();
        LoadGameResult loadGameResult = janggiGameService.loadOrCreateNewGame();

        if (loadGameResult.isNewGame()) {
            OutputView.printCreateNewGame();
        }
        return loadGameResult.game();
    }

    private Board loadOrInitBoard(Game game) {
        return janggiGameService.loadOrInitBoard(game);
    }

    private void processByStatus(Board board, Game game) {
        if (game.getStatus() == GameStatus.WAITING_HAN_PLACEMENT) {
            handleWaitingHanPlacement(board, game);
            return;
        }
        if (game.getStatus() == GameStatus.WAITING_CHO_PLACEMENT) {
            handleWaitingChoPlacement(board, game);
            return;
        }
        handleInProgress(board, game);
    }

    private void handleWaitingHanPlacement(Board board, Game game) {
        selectSide();
        initPlacement(Side.HAN, board);
        janggiGameService.completePlacement(board, game, Side.HAN, GameStatus.WAITING_CHO_PLACEMENT);
        handleWaitingChoPlacement(board, game);
    }

    private void handleWaitingChoPlacement(Board board, Game game) {
        initPlacement(Side.CHO, board);
        janggiGameService.completePlacement(board, game, Side.CHO, GameStatus.IN_PROGRESS);
        handleInProgress(board, game);
    }

    private void handleInProgress(Board board, Game game) {
        playGame(board, game);
        ScoreBoard scoreBoard = board.calculateScore();
        showResult(board, scoreBoard);
        janggiGameService.updateGameStatus(game, GameStatus.FINISHED);
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

    private void playGame(Board board, Game game) {
        printBoard(board);
        while (!game.isOver(board)) {
            try {
                OutputView.printSide(game.getCurrentTurnSide());

                Position from = selectFromPosition();
                Position to = selectToPosition();
                TurnResult turnResult = janggiGameService.processTurn(from, to, board, game);
                printBoard(board);

                if (turnResult.isIncreaseJangGunCount()) {
                    OutputView.printIsJangGun();
                }
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
