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
import janggi.service.util.DBConnectionUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class JanggiService {

    private final PieceDao pieceDao;
    private final TurnDao turnDao;
    private final JanggiGame janggiGame;

    public JanggiService(PieceDao pieceDao, TurnDao turnDao) {
        this.pieceDao = pieceDao;
        this.turnDao = turnDao;
        this.janggiGame = init();
    }

    public void movePiece(final Position start, final Position end) {
        Connection connection = DBConnectionUtil.getConnection();
        janggiGame.movePiece(start, end);
        pieceDao.deleteByPosition(end, connection);
        Piece piece = pieceDao.findByPosition(start, connection);
        pieceDao.updateByPosition(piece, start, end, connection);
        turnDao.update(janggiGame.getTurn(), connection);
        commit(connection);
    }

    public boolean continueGame() {
        return janggiGame.continueGame();
    }

    public void clearGame() {
        Connection connection = DBConnectionUtil.getConnection();
        pieceDao.clear(connection);
        turnDao.clear(connection);
        commit(connection);
    }

    public Map<Position, Piece> findPiecesByPosition() {
        Connection connection = DBConnectionUtil.getConnection();
        return pieceDao.findAll(connection);
    }

    public Side calculateWinner() {
        return janggiGame.calculateWinner();
    }

    public Score scoreBySide(final Side side) {
        return janggiGame.scoreBySide(side);
    }

    private JanggiGame init() {
        Connection connection = DBConnectionUtil.getConnection();
        pieceDao.createTableIfAbsent(connection);
        turnDao.createTableIfAbsent(connection);
        if (pieceDao.existsPieces(connection)) {
            return loadJanggiGame();
        }
        return createJanggiGame();
    }

    private JanggiGame loadJanggiGame() {
        Connection connection = DBConnectionUtil.getConnection();
        Map<Position, Piece> piecesByPosition = pieceDao.findAll(connection);
        Turn turn = turnDao.find(connection);
        return new JanggiGame(new Board(piecesByPosition), turn);
    }

    private JanggiGame createJanggiGame() {
        Connection connection = DBConnectionUtil.getConnection();
        Board board = BoardFactory.initBoard();
        Turn turn = Turn.firstTurn();
        pieceDao.save(board, connection);
        turnDao.save(turn, connection);
        commit(connection);
        return new JanggiGame(
                board,
                turn
        );
    }

    private void commit(final Connection connection) {
        try {
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public Turn getTurn() {
        return janggiGame.getTurn();
    }
}
