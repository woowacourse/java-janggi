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

    private final Connection connection;
    private final PieceDao pieceDao;
    private final TurnDao turnDao;
    private final JanggiGame janggiGame;

    public JanggiService(PieceDao pieceDao, TurnDao turnDao) {
        this.connection = DBConnectionUtil.getConnection();
        this.pieceDao = pieceDao;
        this.turnDao = turnDao;
        this.janggiGame = init();
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

    public Map<Position, Piece> findPiecesByPosition() {
        return pieceDao.findAll(connection);
    }

    public Side calculateWinner() {
        return janggiGame.calculateWinner();
    }

    public Score scoreBySide(final Side side) {
        return janggiGame.scoreBySide(side);
    }

    private JanggiGame init() {
        pieceDao.createTableIfAbsent(connection);
        turnDao.createTableIfAbsent(connection);
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

    private void commit() {
        try {
            this.connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public Turn getTurn() {
        return janggiGame.getTurn();
    }
}
