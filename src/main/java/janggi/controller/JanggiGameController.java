package janggi.controller;

import janggi.domain.board.Board;
import janggi.exception.ErrorException;
import janggi.domain.piece.Camp;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import janggi.service.BoardService;
import janggi.view.Command;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class JanggiGameController {

    public static final Camp FIRST_TURN = Camp.CHO;

    private final InputView inputView;
    private final OutputView outputView;
    private final BoardService boardService;

    public JanggiGameController(InputView inputView, OutputView outputView, BoardService boardService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.boardService = boardService;
    }

    public void runGame() {
        outputView.displayGameBanner();
        GameMode gameMode = new GameMode();
        Command command = repeatUntilSuccess(inputView::askStartOrRecordCommand);

        if (command == Command.START) {
            Board board = boardService.createNewBoard();
            Long boardId = boardService.getLastCreatedBoardId();
            startGame(gameMode, board, boardId);
        }
        if (command == Command.RESUME) {
            Long boardId = requestBoardId();
            Board board = boardService.getBoardRecordWithTransaction(boardId);
            startGame(gameMode, board, boardId);
        }
    }

    private Long requestBoardId() {
        List<Long> boardIds = boardService.getAllBoardIds();
        outputView.displayBoardIds(boardIds);
        return Long.valueOf(repeatUntilSuccess(inputView::askBoardId));
    }

    private void startGame(GameMode gameMode, Board board, Long boardId) {
        gameMode.startPlaying();
        Camp currentTurn = board.getCurrentCamp();
        while (gameMode.isPlaying()) {
            showBoard(board);
            requestPlayGameUntilEnd(currentTurn, board, gameMode, boardId);
            currentTurn = currentTurn.switchTurn();
        }
    }

    private void requestPlayGameUntilEnd(Camp baseCamp, Board board, GameMode gameMode, Long boardId) {
        repeatUntilSuccess(() -> playGame(baseCamp, board, gameMode, boardId));
    }

    private void playGame(Camp baseCamp, Board board, GameMode gameMode, Long boardId) {
        Command command = repeatUntilSuccess(inputView::askPlayCommand);
        if (command == Command.END) {
            gameMode.stopPlaying();
        }
        if (command == Command.MOVE) {
            processAndShowGame(inputView.readMovement(baseCamp), board, gameMode, boardId);
        }
    }

    private void processAndShowGame(List<List<Integer>> input, Board board, GameMode gameMode, Long boardId) {
        processBoard(input, board, boardId);
        processAndShowResult(board, gameMode);
    }

    private void processAndShowResult(Board board, GameMode gameMode) {
        showBoard(board);
        showScore(board);
        Camp winner = board.determineWinner();
        if (winner != Camp.NONE) {
            gameMode.stopPlaying();
            showWinner(winner);
        }
    }

    private void processBoard(List<List<Integer>> input, Board board, Long boardId) {
        Position origin = parsePositionOf(input.getFirst());
        Position target = parsePositionOf(input.getLast());
        Movement movement = new Movement(origin, target);
        boardService.movePiecesWithTransaction(board, movement, boardId);
    }

    private void showBoard(Board board) {
        outputView.displayBoard(board.getCells());
    }

    private void showScore(Board board) {
        Map<Camp, Double> scores = board.determineScores(FIRST_TURN);
        outputView.displayScores(scores);
    }

    private void showWinner(Camp winner) {
        outputView.displayWinner(winner);
    }

    private Position parsePositionOf(List<Integer> input) {
        return Position.of(input.getFirst(), input.getLast());
    }

    private void repeatUntilSuccess(Runnable runner) {
        boolean success = false;
        while (!success) {
            success = tryRunWithErrorHandling(runner);
        }
    }

    private boolean tryRunWithErrorHandling(Runnable runner) {
        try {
            runner.run();
            return true;
        } catch (ErrorException e) {
            outputView.displayErrorMessage(e.getMessage());
            return false;
        }
    }

    private <T> T repeatUntilSuccess(Supplier<T> supplier) {
        Optional<T> result = Optional.empty();
        while (result.isEmpty()) {
            result = tryGetWithErrorHandling(supplier);
        }
        return result.get();
    }

    private <T> Optional<T> tryGetWithErrorHandling(Supplier<T> supplier) {
        try {
            return Optional.ofNullable(supplier.get());
        } catch (IllegalArgumentException e) {
            outputView.displayErrorMessage(e.getMessage());
            return Optional.empty();
        }
    }
}
