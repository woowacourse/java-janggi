package janggi.game;

import janggi.board.Board;
import janggi.board.Pieces;
import janggi.dao.BoardDao;
import janggi.dao.PieceDao;
import janggi.dao.connection.MysqlConnection;
import janggi.piece.Team;
import janggi.piece.pieces.Piece;
import janggi.position.Position;
import janggi.position.Route;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class Game {
    private static final int POSITION_INPUT_SIZE = 2;
    private static final int INPUT_COLUMN_INDEX = 0;
    private static final int INPUT_ROW_INDEX = 1;

    private final InputView inputView;
    private final OutputView outputView;
    private final BoardDao boardDao;
    private final PieceDao pieceDao;

    public Game(InputView inputView, OutputView outputView, BoardDao boardDao, PieceDao pieceDao) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.boardDao = boardDao;
        this.pieceDao = pieceDao;
    }

    public void play() {
        // todo: 만약 DB에 board가 존재한다면 해당 board의 정보로 초기화한다.
        GameState gameState = GameState.PLAY;
        Board board = new Board(new Pieces(), Team.CHO);
        Map<String, Function<Board, GameState>> command = initCommand();

        while (gameState == GameState.PLAY) {
            String inputChoice = inputView.readChoice();
            gameState = command.get(inputChoice).apply(board);
        }
        determineWinner(board);
        inputView.close();
    }

    private Map<String, Function<Board, GameState>> initCommand() {
        HashMap<String, Function<Board, GameState>> command = new HashMap<>();
        command.put("1", this::movePiece);
        command.put("2", this::saveGame);
        command.put("3", this::gameOver);
        return command;
    }

    private GameState movePiece(Board board) {
        outputView.printPieces(board.getPieces());

        Position position = getPosition(board);
        if (position == null) {
            return GameState.QUIT;
        }
        board.judgeUnitTurn(position);

        List<Route> routes = board.searchAvailableRoutes(position);
        outputView.printAvailableRoute(routes, position);

        moveAndCaptureIfEnemyExists(board, routes, position);
        if (board.isNoneEnemyGeneralUnit()) {
            return GameState.QUIT;
        }
        board.changeTurn();
        return GameState.PLAY;
    }

    private GameState saveGame(Board board) {
        outputView.printPieces(board.getPieces());

        pieceDao.deleteAllPieces();
        boardDao.deleteAllBoards();
        String boardId = boardDao.addBoard(board);
        for (Entry<Position, Piece> entry : board.getPieces().entrySet()) {
            pieceDao.addPiece(entry.getValue(), entry.getKey().getColumn(), entry.getKey().getRow(), boardId);
        }
        outputView.printSuccessSave();
        return GameState.PLAY;
    }

    private GameState gameOver(Board board) {
        outputView.printPieces(board.getPieces());

        return GameState.QUIT;
    }

    private void determineWinner(Board board) {
        Optional<Team> winner = board.determineWinner();
        double choScore = board.calculateScore(Team.CHO);
        double hanScore = board.calculateScore(Team.HAN);

        winner.ifPresentOrElse(
                team -> outputView.printWinner(team, choScore, hanScore),
                () -> outputView.printDraw(choScore, hanScore));
    }

    private Position getPosition(Board board) {
        List<Integer> positionValue = handleInputException(() ->
                inputView.readPosition(board.getTurn()), Game::getPosition);
        if (positionValue == null) {
            return null;
        }
        if (positionValue.size() != POSITION_INPUT_SIZE) {
            throw new IllegalArgumentException("column, row 형태로 입력해주세요.");
        }
        return new Position(positionValue.get(INPUT_COLUMN_INDEX), positionValue.get(INPUT_ROW_INDEX));
    }

    private static List<Integer> getPosition(String rawPosition) {
        try {
            if (rawPosition.equalsIgnoreCase("end")) {
                return null;
            }
            return Arrays.stream(rawPosition.split(","))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .toList();
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("좌표 내의 정수 숫자를 입력해주세요.");
        }
    }

    private void moveAndCaptureIfEnemyExists(Board board, List<Route> routes, Position startPoint) {
        int selectedRouteNumber = handleInputException(inputView::readRoute,
                (inputValue) -> parseSelectNumber(inputValue, routes.size()));
        Route route = routes.get(selectedRouteNumber - 1);
        board.moveAndCaptureIfEnemyExists(route, startPoint);
    }

    private int parseSelectNumber(String input, int selectBoxMaxSize) {
        try {
            return parseAndValidateNumber(input, selectBoxMaxSize);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("올바른 번호를 입력해주세요.");
        }
    }

    private int parseAndValidateNumber(String input, int selectBoxMaxSize) {
        int selectedNumber = Integer.parseInt(input);
        if (selectedNumber < 1 || selectedNumber > selectBoxMaxSize) {
            throw new IllegalArgumentException("범위 내의 번호를 입력해주세요.");
        }
        return selectedNumber;
    }

    private <T> T handleInputException(Supplier<String> input, Function<String, T> converter) {
        try {
            String inputValue = input.get();
            return converter.apply(inputValue);
        } catch (IllegalArgumentException exception) {
            outputView.printError(exception.getMessage());
            return handleInputException(input, converter);
        }
    }
}
