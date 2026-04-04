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
import java.util.Scanner;
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
        Board board = new Board(JanggiService.loadBoardState(boardId));
        BoardSnapshots boardSnapshots = new BoardSnapshots();

        playTurn(board, boardSnapshots, boardId);
    }

    private int askLoadOrCreate() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("보드를 불러오시겠습니까? 예: 1번, 아니오: 2번");
                String answer = scanner.nextLine();
                if (answer.equals("1")) {
                    return loadBoard(scanner);
                }
                return makeBoard();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int loadBoard(Scanner scanner) {
        JanggiService.readAllBoard();
        System.out.println("불러올 보드를 선택해 주세요.");
        int boardId = Integer.parseInt(scanner.nextLine());
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
            JanggiService.updateBoard(countryType.anotherCountryType(), boardId);
        }
        JanggiService.deleteAllBoardStateInBoard(boardId);
        JanggiService.deleteAllBoardSnapshotInBoard(boardId);
        JanggiService.deleteBoard(boardId);
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
