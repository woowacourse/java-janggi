package manager;

import dao.ConnectionProvider;
import dao.JanggiDao;
import dao.PiecePositionDao;
import domain.Janggi;
import domain.Score;
import domain.Team;
import domain.Turn;
import domain.board.Board;
import domain.board.BoardPosition;
import domain.piece.Piece;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class JanggiManager {

    private final PiecePositionDao piecePositionDao;
    private final JanggiDao janggiDao;

    public JanggiManager(
            final PiecePositionDao piecePositionDao,
            final JanggiDao janggiDao
    ) {
        this.piecePositionDao = piecePositionDao;
        this.janggiDao = janggiDao;
    }

    public Janggi processTurn(
            final Janggi janggi,
            final BoardPosition selectPosition,
            final BoardPosition destinationPosition
    ) {
        final Janggi janggiSnapshot = janggi.takeSnapshot();
        final Connection connection = ConnectionProvider.getConnection();

        try {
            connection.setAutoCommit(false);
            janggi.processTurn(selectPosition, destinationPosition);
            piecePositionDao.deleteByBoardPosition(connection, destinationPosition);
            piecePositionDao.updateByBoardPosition(
                    connection,
                    selectPosition,
                    destinationPosition
            );
            janggiDao.updateAnyTurn(connection, getCurrentTeam(janggi));
            connection.commit();
            connection.setAutoCommit(true);
            return janggi;
        } catch (Exception e) {
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
            return janggiSnapshot;
        }
    }

    public Janggi loadOrCreateJanggi() {
        final Connection connection = ConnectionProvider.getConnection();
        final Turn turn = janggiDao.findAnyTurn(connection);
        if (turn == null) {
            final Janggi initaialJanggi = Janggi.initialize();
            piecePositionDao.addAll(connection, initaialJanggi.getPieces());
            janggiDao.saveTurn(connection, getCurrentTeam(initaialJanggi));
            return initaialJanggi;
        }
        final Map<BoardPosition, Piece> pieces = piecePositionDao.findAll(connection);
        return new Janggi(new Board(pieces), turn);
    }

    public Score findScore(
            final Janggi janggi,
            final Team team
    ) {
        return janggi.findScore(team);
    }

    public boolean isGameFinish(final Janggi janggi) {
        return janggi.isGameFinish();
    }

    public Team findWinnerTeam(final Janggi janggi) {
        return janggi.findWinnerTeam();
    }

    public Map<BoardPosition, Piece> getPieces(final Janggi janggi) {
        return janggi.getPieces();
    }

    public Team getCurrentTeam(final Janggi janggi) {
        return janggi.getCurrentTeam();
    }
}
