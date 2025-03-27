package janggi;

import static janggi.piece.Side.BLUE;
import static janggi.piece.Side.RED;

import janggi.board.Board;
import janggi.board.Position;
import janggi.dao.JanggiGameDao;
import janggi.piece.Piece;
import janggi.piece.Side;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;

public class JanggiGame {

    private final Board board;
    private final Turn turn;

    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "chess"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root"; // MySQL 서버 비밀번호

    private final Connection connection;

    public Connection getConnection() {
        // 드라이버 연결
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public JanggiGame(final Board board, final Turn turn) {
        this.board = board;
        this.turn = turn;
        this.connection = getConnection();
    }

    public void movePiece(final Position start, final Position end) {
        try {
            JanggiGameDao dao = new JanggiGameDao();
            board.move(start, end, turn);
            dao.deleteByPosition(end, connection);
            dao.updatePiece(board.getBoard().get(end), start, end, connection);
            turn.nextTurn();
            dao.updateTurn(turn, connection);
        } catch (IllegalArgumentException e) {
            rollback(connection);
            throw e;
        }
    }

    public Side calculateWinner() {
        Score redSideScore = scoreBySide(RED);
        Score blueSideScore = scoreBySide(BLUE);
        if (redSideScore.isGreaterThan(blueSideScore)) {
            return RED;
        }
        return BLUE;
    }

    public Score scoreBySide(final Side side) {
        Score scoreBySide = board.calculatePiecesScoreBySide(side);
        return Score.initBySide(side).plus(scoreBySide);
    }

    public boolean continueGame() {
        return scoreBySide(RED).isGreaterThanZero()
                && scoreBySide(BLUE).isGreaterThanZero();
    }

    public void rollback(final Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException ignored) {
        }
    }

    public Map<Position, Piece> getBoard() {
        return Collections.unmodifiableMap(board.getBoard());
    }

    public Turn getTurn() {
        return turn;
    }
}
