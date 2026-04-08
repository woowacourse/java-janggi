package controller;

import dto.GameRecordDto;
import dto.SavedGameDto;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import domain.Board;
import domain.constant.Country;
import domain.JanggiGame;
import domain.constant.PieceType;
import domain.Position;
import service.JanggiService;
import dto.BoardDto;
import dto.PositionDto;
import view.InputView;
import view.OutputView;

public class JanggiController {
    private static final int MAX_RETRY = 10;

    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiService janggiService;

    public JanggiController(InputView inputView, OutputView outputView, JanggiService janggiService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.janggiService = janggiService;
    }

    public void run() {
        int gameMenuInput = inputView.requestGameMenu();
        if (gameMenuInput == 1) {
            startNewGame();
            return;
        }
        if (gameMenuInput == 2) {
            loadSavedGame();
            return;
        }
        selectGameRecord();
    }

    private void startNewGame() {
        Board board = initBoard();
        JanggiGame janggiGame = initJanggiGame(board);
        int gameId = janggiService.createNewGame(janggiGame.getCountry().name());
        janggiService.saveInitBoard(gameId, board);

        playGame(gameId, board, janggiGame);
    }

    private void loadSavedGame() {
        List<SavedGameDto> savedGameDtos = janggiService.getSavedGames();
        if (savedGameDtos.isEmpty()) {
            outputView.printNewGameStart();
            startNewGame();
            return;
        }
        startSavedGame(savedGameDtos);
    }

    private void selectGameRecord() {
        List<GameRecordDto> gameRecordDtos = janggiService.getGameRecords();
        outputView.printGameRecords(gameRecordDtos);
        run();
    }

    private void startSavedGame(List<SavedGameDto> savedGames) {
        doRetry(() -> {
            int gameId = inputView.requestGameId(savedGames);
            Board board = janggiService.getSavedBoard(gameId);

            outputView.printBoard(janggiService.createBoardDto(board));
            JanggiGame janggiGame = janggiService.loadGame(gameId, board);

            playGame(gameId, board, janggiGame);
            return Optional.empty();
        });
    }

    private void playGame(int gameId, Board board, JanggiGame janggiGame) {
        while (!janggiGame.isFinished()) {
            outputView.printChangeTurnMessage(janggiGame.getCountry().getName(), janggiGame.calculateScore());
            List<PositionDto> positionDtos = requestMovePiece(janggiGame);
            playTurn(positionDtos, gameId, janggiGame, board);
        }
        outputView.printGameResult(janggiService.getGameResult(janggiGame));
        janggiService.finishGame(gameId, janggiGame);
    }

    private void playTurn(List<PositionDto> positionDtos, int gameId, JanggiGame janggiGame, Board board) {
        Position start = requestStartPiecePosition(positionDtos);
        requestEndPosition(gameId, start, janggiGame);
        outputView.printBoard(janggiService.createBoardDto(board));
    }

    private void requestEndPosition(int gameId, Position start, JanggiGame janggiGame) {
        doRetry(() -> {
                    List<Integer> destination = inputView.requestMovePosition();
                    Position end = Position.create(destination.getFirst(), destination.getLast());
                    janggiService.applyMove(gameId, start, end, janggiGame);
                    return Optional.empty();
                }
        );
    }

    private Board initBoard() {
        outputView.printGameStartMessage();

        List<PieceType> choMasangChoose = initChoMaSang();
        List<PieceType> hanMasangChoose = initHanMaSang();

        return janggiService.createBoard(choMasangChoose, hanMasangChoose);
    }

    private List<PieceType> initChoMaSang() {
        return doRetry(() -> {
            outputView.printCountry(Country.CHO);
            int choMaSangChoice = inputView.requestMaSangPosition();
            return janggiService.createMaSang(choMaSangChoice);
        });
    }

    private List<PieceType> initHanMaSang() {
        return doRetry(() -> {
            outputView.printCountry(Country.HAN);
            int hanMaSangChoice = inputView.requestMaSangPosition();
            return janggiService.createMaSang(hanMaSangChoice);
        });
    }

    private JanggiGame initJanggiGame(Board board) {
        outputView.printTurnStartMessage();

        BoardDto boardDto = janggiService.createBoardDto(board);
        outputView.printBoard(boardDto);

        return janggiService.createJanggiGame(board);
    }

    private List<PositionDto> requestMovePiece(JanggiGame janggiGame) {
        return doRetry(() -> {
            PieceType pt = PieceType.from(inputView.requestPiece());
            List<PositionDto> dtos = janggiService.getPiecePositions(janggiGame, pt);
            outputView.printPiecePossiblePosition(pt, dtos);
            return dtos;
        });
    }

    private Position requestStartPiecePosition(List<PositionDto> positionDtos) {
        return doRetry(() -> {
            int choiceStart = inputView.requestStartPiecePosition(positionDtos.size()) - 1;
            return Position.create(positionDtos.get(choiceStart).row(), positionDtos.get(choiceStart).col());
        });
    }

    private <T> T doRetry(Supplier<T> supplier) {
        int retry = 0;
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
                retry++;
                checkRetryLimit(retry);
            }
        }
    }

    private static void checkRetryLimit(int retry) {
        if (retry > MAX_RETRY) {
            throw new IllegalStateException("입력횟수를 초과했습니다.");
        }
    }
}
