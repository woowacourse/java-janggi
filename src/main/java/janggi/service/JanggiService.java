package janggi.service;

import janggi.dao.PieceDao;
import janggi.dao.TurnDao;
import janggi.domain.board.Board;
import janggi.domain.board.BoardFactory;
import janggi.domain.board.Position;
import janggi.domain.game.JanggiGame;
import janggi.domain.game.Score;
import janggi.domain.game.Turn;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Side;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class JanggiService {

    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "chess"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root"; // MySQL 서버 비밀번호

    private final Connection connection;
    private final PieceDao pieceDao;
    private final TurnDao turnDao;
    private final JanggiGame janggiGame;

    public JanggiService(PieceDao pieceDao, TurnDao turnDao) {
        this.connection = getConnection();
        this.pieceDao = pieceDao;
        this.turnDao = turnDao;
        this.janggiGame = init();
    }

    private JanggiGame init() {
        if (pieceDao.existsPieces(connection)) {
            return loadJanggiGame();
        }
        return createJanggiGame();
    }

    private JanggiGame loadJanggiGame() {
        Map<Position, Piece> piecesByPosition = pieceDao.findAll(connection);
        Turn turn = turnDao.find(connection);
        return new JanggiGame(new Board(piecesByPosition), turn);
    }

    private JanggiGame createJanggiGame() {
        Board board = BoardFactory.initBoard();
        Turn turn = Turn.firstTurn();
        pieceDao.save(board, connection);
        turnDao.save(turn, connection);
        return new JanggiGame(
                board,
                turn
        );
    }

    private Connection getConnection() {
        // 드라이버 연결
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION,
                    USERNAME, PASSWORD);
            connection.setAutoCommit(false);
            return connection;
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void movePiece(final Position start, final Position end) {
        janggiGame.movePiece(start, end);
        pieceDao.deleteByPosition(end, connection);
        Piece piece = pieceDao.findByPosition(start, connection);
        pieceDao.updateByPosition(piece, start, end, connection);
        turnDao.update(janggiGame.getTurn(), connection);
        commit();
    }

    public boolean continueGame() {
        return janggiGame.continueGame();
    }

    public void clearGame() {
        pieceDao.clear(connection);
        turnDao.clear(connection);
        commit();
    }

    private void commit() {
        try {
            connection.commit();
        } catch (SQLException ignored) {
        }
    }

    public Map<Position, Piece> findPiecesByPosition() {
        return pieceDao.findAll(connection);
    }

    public Side calculateWinner() {
        return janggiGame.calculateWinner();
    }

    public Score scoreBySide(final Side side) {
        return janggiGame.scoreBySide(side);
    }

    public Turn getTurn() {
        return janggiGame.getTurn();
    }
}
