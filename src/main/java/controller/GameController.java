package controller;

import dao.JanggiGameDao;
import dao.converter.BoardConverter;
import database.MysqlConnectionManager;
import dto.GameStatus;
import dto.PieceDto;
import java.util.List;
import java.util.function.Supplier;
import model.board.Army;
import model.board.Board;
import model.board.Country;
import model.board.HorseElephantStrategy;
import model.board.strategy.ElephantSetup;
import model.move.Move;
import model.position.Position;
import view.InputView;
import view.OutputView;

public class GameController {
    private final MysqlConnectionManager manager;

    public GameController(MysqlConnectionManager manager) {
        this.manager = manager;
    }

    public void start() {
        OutputView.printStartMode();
        int mode = retry(() -> {
            int num = InputView.readGameMode();
            validateMode(num);
            return num;
        });

        GameStatus status = findInitialStatus(mode);
        Board board = createBoardByMode(mode, status);
        int gameId = status.id();
        Country currentTurn = status.turn();

        if (mode == 1) {
            JanggiGameDao janggiGameDao = new JanggiGameDao(manager);
            gameId = janggiGameDao.createGame(currentTurn);
            janggiGameDao.saveGame(gameId, currentTurn, BoardConverter.convertToPieceDtos(board));
        }

        OutputView.printBoard(board);

        while (board.endCondition()) {
            playTurn(gameId, board, currentTurn);
            currentTurn = convertCountry(currentTurn);
        }

        endGamePhase(board);
    }

    private GameStatus findInitialStatus(int mode) {
        if (mode == 1) {
            return new GameStatus(0, Country.CHO);
        }
        JanggiGameDao janggiGameDao = new JanggiGameDao(manager);
        GameStatus status = janggiGameDao.findLatestStatus();
        if (status == null) {
            OutputView.printError("저장된 게임이 없습니다. 새 게임을 시작합니다.");
            return new GameStatus(0, Country.CHO);
        }
        return status;
    }

    private Board createBoardByMode(int mode, GameStatus gameStatus) {
        if (mode == 1 || gameStatus.id() == 0) {
            Board board = new Board();
            initBoard(board);
            return board;
        }
        JanggiGameDao janggiGameDao = new JanggiGameDao(manager);
        List<PieceDto> pieces = janggiGameDao.loadPiecesByGameId(gameStatus.id());
        return BoardConverter.convertToBoard(pieces);
    }

    private void playTurn(int gameId, Board board, Country country) {
        OutputView.printPositionCountry(country);

        retry(() -> {
            gamePhaseRetry(board, country);

            Country nextCountry = Country.HAN;
            if (country == Country.HAN) {
                nextCountry = Country.CHO;
            }

            JanggiGameDao janggiGameDao = new JanggiGameDao(manager);
            janggiGameDao.saveGame(gameId, nextCountry, BoardConverter.convertToPieceDtos(board));
        });
        OutputView.printBoard(board);
    }

    private Country convertCountry(Country currentTurn) {
        if (currentTurn == Country.CHO) {
            return Country.HAN;
        }
        return Country.CHO;
    }

    private void initBoard(Board board) {
        OutputView.printArrangeCountry(Country.CHO);
        Army cho = initArmy(Country.CHO);
        cho.deployTo(board, Country.CHO);
        OutputView.printLine();
        OutputView.printArrangeCountry(Country.HAN);
        Army han = initArmy(Country.HAN);
        han.deployTo(board, Country.HAN);
    }

    private Army initArmy(Country country) {
        OutputView.printArrangeList(ElephantSetup.arrangementList(), country);
        HorseElephantStrategy strategy = retry(() -> {
            int number = InputView.readArrangement();
            return ElephantSetup.init(number);
        });
        return new Army(strategy);
    }

    private void gamePhaseRetry(Board board, Country country) {
        Position from = selectStartPosition();
        board.checkTurn(from, country);
        Position to = selectEndPosition();
        Move move = new Move(from, to);
        board.move(move);
    }

    private Position selectStartPosition() {
        List<Integer> startList = InputView.readStartPosition();
        return Position.of(startList.get(0), startList.get(1));
    }

    private Position selectEndPosition() {
        List<Integer> startList = InputView.readEndPosition();
        return Position.of(startList.get(0), startList.get(1));
    }

    private void endGamePhase(Board board) {
        board.winnerCountry().ifPresent(OutputView::printWinner);
        OutputView.printScore(Country.CHO, board.sumScore(Country.CHO));
        OutputView.printScore(Country.HAN, board.sumScore(Country.HAN));
    }

    private void validateMode(int mode) {
        if (mode != 1 && mode != 2) {
            throw new IllegalArgumentException("[ERROR] 올바른 번호를 입력해 주세요.");
        }
    }

    private <T> T retry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                OutputView.printError(e.getMessage());
            }
        }
    }

    private void retry(Runnable callback) {
        while (true) {
            try {
                callback.run();
                return;
            } catch (IllegalArgumentException e) {
                OutputView.printError(e.getMessage());
            }
        }
    }
}
