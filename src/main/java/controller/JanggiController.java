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

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        int boardId = 1;
        JanggiService.deleteAll();
        JanggiService.insertPositions();
        JanggiService.insertPieces();
        JanggiService.readAllBoard();
        Board board = makeBoard();
        JanggiService.insertBoard(CountryType.CHO);
        JanggiService.readBoard(1);
        initBoardState(board.getPieceInfos(), boardId);
        List<CountryType> playOrders = List.of(CountryType.CHO, CountryType.HAN);
        BoardSnapshots boardSnapshots = new BoardSnapshots();

        playTurn(board, playOrders, boardSnapshots, boardId);
    }

    private Board makeBoard() {
        TableSetting choTableSetting = readTableSetting(CountryType.CHO);
        TableSetting hanTableSetting = readTableSetting(CountryType.HAN);
        BoardFactory boardFactory = new BoardFactory();
        return boardFactory.create(choTableSetting, hanTableSetting);
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

    private void initBoardState(PieceInfos pieceInfos, int boardId) {
        for (Position position : pieceInfos.getKeys()) {
            JanggiService.insertBoardState(position, pieceInfos.get(position), boardId);
        }
    }

    private void playTurn(Board board, List<CountryType> playOrders, BoardSnapshots boardSnapshots, int boardId) {
        int turnIndex = 0;
        boolean isEnd = false;
        while (!isEnd) {
            CountryType countryType = playOrders.get(turnIndex);
            JanggiService.updateBoard(countryType, 1);
            isEnd = checkEndAndMovePiece(board, countryType, boardSnapshots, boardId);

            turnIndex = (turnIndex + 1) % 2;
        }
    }

    private boolean checkEndAndMovePiece(Board board, CountryType countryType, BoardSnapshots boardSnapshots,
                                         int boardId) {
        BoardSnapshot boardSnapshot = new BoardSnapshot(board.getPieceInfos(), countryType);
        outputView.printBoard(boardSnapshot, board.getScores());

        boolean isEndWithGeneralCaught = movePiece(board, countryType, boardId);
        if (isEndWithGeneralCaught) {
            outputView.printEndWithCatchGeneral(countryType);
        }
        boolean isEndWithBoardRepeat = boardSnapshots.appearSamePositionThreeTurn(boardSnapshot);
        if (isEndWithBoardRepeat) {
            outputView.printEndWithBoardRepeat(board.getScores());
        }
        return isEndWithGeneralCaught || isEndWithBoardRepeat;
    }

    private boolean movePiece(Board board, CountryType countryType, int boardId) {
        while (true) {
            try {
                Position from = makeFromPosition();
                board.validateFromPosition(from, countryType);
                Position to = makeToPosition();

                boolean isEnd = board.checkEndAndPlay(from, to);
                JanggiService.changeBoardStateToAndFrom(from, to, board.getPieceInfos(), boardId);
                addBoardSnapshot(board.getPieceInfos(), boardId);
                return isEnd;
            } catch (IllegalArgumentException exception) {
                outputView.printErrorMessage(exception.getMessage());
            }
        }
    }

    private void addBoardSnapshot(PieceInfos pieceInfos, int boardId) {
        for (Position position : pieceInfos.getKeys()) {
            JanggiService.insertBoardSnapshot(position, pieceInfos.get(position), boardId);
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
