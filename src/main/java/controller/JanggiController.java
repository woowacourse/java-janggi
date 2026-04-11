package controller;

import domain.Position;
import domain.board.Board;
import domain.board.TableSetting;
import domain.country.CountryType;
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

        playTurn(board, gameInfoId);
    }

    private int askLoadOrCreate() {
        while (true) {
            try {
                String input = inputView.readLoadOrCreateBoard();
                if (InputParser.parseLoad(input)) {
                    return readLoadBoard();
                }
                return janggiService.makeBoard(readTableSetting(CountryType.CHO), readTableSetting(CountryType.HAN));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int readLoadBoard() {
        outputView.printBoardId(janggiService.readAllGameInfoIds());
        String input = inputView.readBoardSelect();
        return janggiService.readLoadBoard(input);
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

    private void playTurn(Board board, int gameInfoId) {
        boolean isEnd = false;
        while (!isEnd) {
            CountryType turn = board.getTurn();
            outputView.printBoard(board.getPieceInfos(), turn, board.calculateScore(CountryType.CHO),
                    board.calculateScore(CountryType.HAN));
            movePiece(board, gameInfoId);
            isEnd = isEnd(board, turn);
            board.changeTurn();
            janggiService.updateGameInfo(board.getTurn(), gameInfoId);
        }
        janggiService.deleteAllByGameInfoId(gameInfoId);
    }

    private boolean isEnd(Board board, CountryType turn) {
        boolean isEndWithGeneralCaught = board.checkEndWithGeneralCaught();
        if (board.checkEndWithGeneralCaught()) {
            outputView.printEndWithCatchGeneral(turn);
        }
        boolean isEndWithBoardRepeat = board.checkEndWithBoardRepeat();
        if (isEndWithBoardRepeat) {
            outputView.printEndWithBoardRepeat(board.calculateScore(CountryType.CHO),
                    board.calculateScore(CountryType.HAN));
        }
        return isEndWithGeneralCaught || isEndWithBoardRepeat;
    }

    private void movePiece(Board board, int gameInfoId) {
        while (true) {
            try {
                Position from = janggiService.makePosition(inputView.readFromPosition());
                board.validateFromPosition(from);
                Position to = janggiService.makePosition(inputView.readToPosition());

                janggiService.movePiece(board, from, to, gameInfoId);
                return;
            } catch (IllegalArgumentException exception) {
                outputView.printErrorMessage(exception.getMessage());
            }
        }
    }
}
