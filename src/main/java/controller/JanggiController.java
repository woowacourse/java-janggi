package controller;

import domain.Position;
import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.BoardSnapshot;
import domain.board.BoardSnapshots;
import domain.board.TableSetting;
import domain.country.CountryType;
import domain.piece.PieceInfos;
import java.util.List;
import service.JanggiService;
import view.CountryFormatter;
import view.InputParser;
import view.InputView;
import view.OutputView;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiService janggiService;

    public JanggiController(InputView inputView, OutputView outputView, JanggiService janggiService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.janggiService = janggiService;
    }

    public void run() {
        int gameInfoId = askLoadOrCreate();
        Board board = janggiService.readBoard(gameInfoId);
        BoardSnapshots boardSnapshots = janggiService.loadBoardSnapshots(gameInfoId);

        playTurn(board, boardSnapshots, gameInfoId);
    }

    private int askLoadOrCreate() {
        while (true) {
            try {
                String input = inputView.readLoadOrCreateBoard();
                if (InputParser.parseLoad(input)) {
                    return loadBoard();
                }
                return makeBoard();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int loadBoard() {
        outputView.printBoardId(janggiService.readAllGameInfoIds());
        String input = inputView.readBoardSelect();
        return InputParser.parseBoardId(input);
    }

    private int makeBoard() {
        TableSetting choTableSetting = readTableSetting(CountryType.CHO);
        TableSetting hanTableSetting = readTableSetting(CountryType.HAN);
        BoardFactory boardFactory = new BoardFactory();
        Board board = boardFactory.create(choTableSetting, hanTableSetting);
        int gameInfoId = janggiService.insertGameInfo();
        initBoardState(board.getPieceInfos(), gameInfoId);
        return gameInfoId;
    }

    private void initBoardState(PieceInfos pieceInfos, int gameInfoId) {
        for (Position position : pieceInfos.getKeys()) {
            janggiService.insertPositionState(position, pieceInfos.get(position), gameInfoId);
        }
    }

    private TableSetting readTableSetting(CountryType countryType) {
        while (true) {
            try {
                String input = inputView.readTableSetting(CountryFormatter.from(countryType));
                String tableNames = InputParser.parseTableSetting(input);

                return TableSetting.from(tableNames);
            } catch (IllegalArgumentException exception) {
                outputView.printErrorMessage(exception.getMessage());
            }
        }
    }

    private void playTurn(Board board, BoardSnapshots boardSnapshots, int gameInfoId) {
        boolean isEnd = false;
        while (!isEnd) {
            CountryType countryType = janggiService.readCountryTurn(gameInfoId);
            isEnd = checkEndAndMovePiece(board, countryType, boardSnapshots, gameInfoId);
            janggiService.updateGameInfo(countryType.anotherCountryType(), gameInfoId);
        }
        janggiService.deleteAllPositionStates(gameInfoId);
        janggiService.deleteBoardSnapshots(gameInfoId);
        janggiService.deleteGameInfo(gameInfoId);
    }

    private boolean checkEndAndMovePiece(Board board, CountryType turn, BoardSnapshots boardSnapshots,
                                         int gameInfoId) {
        outputView.printBoard(board.getPieceInfos(), turn, board.calculateScore(CountryType.CHO),
                board.calculateScore(CountryType.HAN));

        boolean isEndWithGeneralCaught = movePiece(board, turn, gameInfoId, boardSnapshots);
        if (isEndWithGeneralCaught) {
            outputView.printEndWithCatchGeneral(turn);
        }
        boolean isEndWithBoardRepeat = boardSnapshots.appearSamePositionThreeTurn();
        if (isEndWithBoardRepeat) {
            outputView.printEndWithBoardRepeat(board.calculateScore(CountryType.CHO),
                    board.calculateScore(CountryType.HAN));
        }
        return isEndWithGeneralCaught || isEndWithBoardRepeat;
    }

    private boolean movePiece(Board board, CountryType turn, int gameInfoId, BoardSnapshots boardSnapshots) {
        while (true) {
            try {
                Position from = makeFromPosition();
                board.validateFromPosition(from, turn);
                Position to = makeToPosition();

                boolean isEnd = board.checkEndAndPlay(from, to);
                BoardSnapshot boardSnapshot = new BoardSnapshot(board.getPieceInfos(), turn);
                boardSnapshots.addBoardSnapshot(boardSnapshot);
                int turnHistoryId = janggiService.insertTurnHistory(gameInfoId, turn);
                janggiService.insertPositionHistory(board.getPieceInfos(), turnHistoryId);
                janggiService.changePositionStateToAndFrom(from, to, board.getPieceInfos(), gameInfoId);
                return isEnd;
            } catch (IllegalArgumentException exception) {
                outputView.printErrorMessage(exception.getMessage());
            }
        }
    }

    private Position makeFromPosition() {
        String input = inputView.readFromPosition();
        List<Integer> positions = InputParser.parsePosition(input);
        return new Position(positions.get(0), positions.get(1));
    }

    private Position makeToPosition() {
        String input = inputView.readToPosition();
        List<Integer> positions = InputParser.parsePosition(input);
        return new Position(positions.get(0), positions.get(1));
    }
}
