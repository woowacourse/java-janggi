package manager;

import dao.ConnectionProvider;
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

    public JanggiManager(final PiecePositionDao piecePositionDao) {
        this.piecePositionDao = piecePositionDao;
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
            // TODO : JanggiGameDao에 해당 게임 턴 업데이트
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
        final Map<BoardPosition, Piece> pieces = piecePositionDao.findAll(connection);
        // TODO : JanggiGameDao에 튜플이 존재한다면으로 변경
        if (pieces.isEmpty()) {
            final Janggi initaialJanggi = Janggi.initialize();
            // TODO : JanggiGame 저장
            piecePositionDao.addAll(connection, initaialJanggi.getPieces());
            return initaialJanggi;
        }

        return new Janggi(
                new Board(pieces),
                // TODO : JanggiGameDao에서 현재 턴을 조회하여 장기 객체 생성하도록 변경
                new Turn(Team.GREEN)
        );
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
