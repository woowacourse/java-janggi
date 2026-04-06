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
//        JanggiService.deleteAll();
//        JanggiService.insertPositions();
//        JanggiService.insertPieces();
        int boardId = askLoadOrCreate();
        Board board = JanggiService.readBoard(boardId);
        BoardSnapshots boardSnapshots = JanggiService.loadBoardSnapshot(boardId);

        playTurn(board, boardSnapshots, boardId);
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
        outputView.printBoardId(JanggiService.readAllBoardId());
        String input = inputView.readBoardSelect();
        int boardId = InputParser.parseBoardId(input);
        JanggiService.readBoard(boardId);
        return boardId;
    }

    private int makeBoard() {
        TableSetting choTableSetting = readTableSetting(CountryType.CHO);
        TableSetting hanTableSetting = readTableSetting(CountryType.HAN);
        BoardFactory boardFactory = new BoardFactory();
        Board board = boardFactory.create(choTableSetting, hanTableSetting);
        int boardId = JanggiService.insertBoard();
        initBoardState(board.getPieceInfos(), boardId);
        return boardId;
    }

    private void initBoardState(PieceInfos pieceInfos, int boardId) {
        for (Position position : pieceInfos.getKeys()) {
            JanggiService.insertBoardState(position, pieceInfos.get(position), boardId);
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

    private void playTurn(Board board, BoardSnapshots boardSnapshots, int boardId) {
        boolean isEnd = false;
        while (!isEnd) {
            CountryType countryType = JanggiService.readCountryTurn(boardId);
            isEnd = checkEndAndMovePiece(board, countryType, boardSnapshots, boardId);
            JanggiService.updateBoard(countryType.anotherCountryType(), board.getScores(), boardId);
        }
        JanggiService.deleteAllBoardStateInBoard(boardId);
        JanggiService.deleteAllBoardSnapshotInBoard(boardId);
        JanggiService.deleteBoard(boardId);
    }

    private boolean checkEndAndMovePiece(Board board, CountryType turn, BoardSnapshots boardSnapshots,
                                         int boardId) {
        outputView.printBoard(board.getPieceInfos(), turn, board.getScores());

        boolean isEndWithGeneralCaught = movePiece(board, turn, boardId, boardSnapshots);
        if (isEndWithGeneralCaught) {
            outputView.printEndWithCatchGeneral(turn);
        }
        boolean isEndWithBoardRepeat = boardSnapshots.appearSamePositionThreeTurn();
        if (isEndWithBoardRepeat) {
            outputView.printEndWithBoardRepeat(board.getScores());
        }
        return isEndWithGeneralCaught || isEndWithBoardRepeat;
    }

    private boolean movePiece(Board board, CountryType turn, int boardId, BoardSnapshots boardSnapshots) {
        while (true) {
            try {
                Position from = makeFromPosition();
                board.validateFromPosition(from, turn);
                Position to = makeToPosition();

                boolean isEnd = board.checkEndAndPlay(from, to);
                BoardSnapshot boardSnapshot = new BoardSnapshot(board.getPieceInfos(), turn);
                boardSnapshots.addBoardSnapshot(boardSnapshot);
                JanggiService.insertBoardSnapshot(board.getPieceInfos(), boardId, turn);
                JanggiService.changeBoardStateToAndFrom(from, to, board.getPieceInfos(), boardId);
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
