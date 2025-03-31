package janggi.service;

import janggi.domain.board.JanggiBoard;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Pieces;
import janggi.domain.piece.Side;
import janggi.domain.position.Position;
import janggi.repository.JanggiConnection;
import janggi.repository.JanggiDao;
import janggi.repository.JanggiTableCreator;

import java.sql.Connection;
import java.util.List;

public class JanggiBoardService {

    private final JanggiDao janggiDao;

    public JanggiBoardService(JanggiDao janggiDao) {
        this.janggiDao = janggiDao;
    }

    public void createJanggiTables() {
        Connection connection = JanggiConnection.getConnection();
        JanggiTableCreator.createPieceTable(connection);
        JanggiTableCreator.createTurnTable(connection);
        JanggiConnection.commit(connection);
    }

    public void saveInitialBoard(JanggiBoard janggiBoard) {
        Connection connection = JanggiConnection.getConnection();
        Pieces pieces = janggiBoard.getPieces();
        List<Piece> allPieces = pieces.getPieces();
        for (Piece piece : allPieces) {
            janggiDao.insertPiece(piece, connection);
        }
        janggiDao.insertTurn(janggiBoard.getTurn(), connection);
        JanggiConnection.commit(connection);
    }

    public boolean hasGameData() {
        Connection connection = JanggiConnection.getConnection();
        boolean hasGameData = janggiDao.hasGamePiece(connection);
        JanggiConnection.commit(connection);
        return hasGameData;
    }

    public JanggiBoard loadGame() {
        Connection connection = JanggiConnection.getConnection();
        List<Piece> pieces = janggiDao.loadPieces(connection);
        Side turn = janggiDao.loadTurn(connection);
        JanggiConnection.commit(connection);

        return new JanggiBoard(new Pieces(pieces), turn);
    }

    public void updateGame(Position start, Position destination, Side turn) {
        Connection connection = JanggiConnection.getConnection();
        janggiDao.removeDestinationPiece(destination, connection);
        janggiDao.updateMovingPiece(start, destination, connection);
        janggiDao.updateTurn(turn, connection);
        JanggiConnection.commit(connection);
    }

    public void resetGame() {
        Connection connection = JanggiConnection.getConnection();
        janggiDao.removePieces(connection);
        janggiDao.removeTurn(connection);
        JanggiConnection.commit(connection);
    }
}
