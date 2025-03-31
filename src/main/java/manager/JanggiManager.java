package manager;

import dao.ConnectionProvider;
import dao.JanggiDao;
import dao.PiecePositionDao;
import domain.board.Board;
import domain.board.BoardPosition;
import domain.janggi.Janggi;
import domain.janggi.JanggiStatus;
import domain.janggi.Score;
import domain.janggi.Team;
import domain.janggi.Turn;
import domain.piece.Piece;
import dto.JanggiDto;
import entity.JanggiEntity;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
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
            piecePositionDao.deleteByJanggiIdAndPosition(connection, janggi.getId(), destinationPosition);
            piecePositionDao.updateByJanggiIdAndPosition(
                    connection,
                    janggi.getId(),
                    selectPosition,
                    destinationPosition
            );
            janggiDao.updateTurnByJanggiId(connection, janggi.getId(), getCurrentTeam(janggi));
            connection.commit();
            connection.setAutoCommit(true);
            return janggi;
        } catch (Exception e) {
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException sqlException) {
                return janggiSnapshot;
            }
            return janggiSnapshot;
        }
    }

    public List<JanggiDto> findAllJanggiDtos() {
        try {
            final Connection connection = ConnectionProvider.getConnection();
            return janggiDao.findAllJanggiEntities(connection).stream()
                    .map(entity -> new JanggiDto(
                            entity.id(), entity.title(), new Turn(entity.team()), entity.status()))
                    .toList();
        } catch (SQLException e) {
            throw new IllegalStateException("장기 게임을 불러오는 중 문제 발생");
        }
    }

    public Janggi createJanggi(
            final String title
    ) {
        final Connection connection = ConnectionProvider.getConnection();
        try {
            connection.setAutoCommit(false);
            final int janggiId = janggiDao.create(
                    connection, title, JanggiStatus.PROCESS, new Turn(Team.GREEN)
            );
            final Janggi initaialJanggi = Janggi.initialize(janggiId, title);
            piecePositionDao.createAllByJanggiId(connection, janggiId, initaialJanggi.getPieces());
            connection.commit();
            connection.setAutoCommit(true);
            return initaialJanggi;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                throw new IllegalStateException("장기 게임 생성 중 문제 후 롤백 실패");
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new IllegalStateException("AutoCommit 설정 복구 실패");
            }
        }
        throw new IllegalStateException("장기 게임 생성 중 문제 발생");
    }

    public Janggi loadJanggi(final int janggiId) {
        final Connection connection = ConnectionProvider.getConnection();
        try {
            final JanggiEntity janggiEntity = janggiDao.findJanggiEntityById(connection, janggiId);

            if (janggiEntity.id() == 0) {
                throw new IllegalArgumentException("해당하는 게임이 없습니다.");
            }
            final Board board = new Board(piecePositionDao.findAllByJanggiId(connection, janggiId));

            return janggiEntity.toJanggi(board);
        } catch (SQLException e) {
            throw new IllegalStateException("장기 게임 로드 중 문제 발생");
        }
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
