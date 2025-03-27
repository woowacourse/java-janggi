package janggi;

import janggi.board.Board;
import janggi.board.BoardFactory;
import janggi.board.Position;
import janggi.dao.JanggiGameDao;
import janggi.piece.Side;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.sql.Connection;

public class JanggiManager {

    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiGame janggiGame;

    public JanggiManager(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.janggiGame = init();
    }

    public JanggiGame init() {
        Connection connection = DBConnection.getConnection();
        JanggiGameDao dao = new JanggiGameDao();
        Board board;
        Turn turn;
        if (dao.existsPiece(connection)) {
            board = dao.findBoard(connection);
            turn = dao.find(connection);
            return new JanggiGame(board, turn);
        }
        board = BoardFactory.initBoard();
        turn = Turn.firstTurn();
        dao.addBoard(board, connection);
        dao.addTurn(turn, connection);
        return new JanggiGame(
                BoardFactory.initBoard(),
                Turn.firstTurn()
        );
    }

    public void play() {
        while (janggiGame.continueGame()) {
            displayGameStatus();
            String inputStartPosition = inputView.readStartPosition();
            if (inputStartPosition.equals("Q")) {
                break;
            }
            String inputEndPosition = inputView.readEndPosition();
            movePiece(inputStartPosition, inputEndPosition);
        }
        outputView.printResult(janggiGame.calculateWinner());
    }

    private void displayGameStatus() {
        outputView.printBoard(janggiGame.getBoard());
        outputView.printScore(janggiGame.scoreBySide(Side.RED), janggiGame.scoreBySide(Side.BLUE));
        outputView.printTurn(janggiGame.getTurn());
    }

    private void movePiece(final String inputStartPosition, final String inputEndPosition) {
        handleException(() -> {
            Position start = parsePosition(inputStartPosition);
            Position end = parsePosition(inputEndPosition);
            janggiGame.movePiece(start, end);
        });
    }

    private Position parsePosition(final String input) {
        String[] coordinate = input.split(",");
        try {
            int x = Integer.parseInt(coordinate[0]);
            int y = Integer.parseInt(coordinate[1]);
            return new Position(x, y);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("(x,y) 형태로 입력해주세요.");
        }
    }

    private void handleException(final Runnable action) {
        try {
            action.run();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e);
        }
    }
}
