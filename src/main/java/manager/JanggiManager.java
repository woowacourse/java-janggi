package manager;

import dao.PiecePositionDao;
import domain.Janggi;
import domain.Score;
import domain.Team;
import domain.Turn;
import domain.board.Board;
import domain.board.BoardPosition;
import domain.piece.Piece;
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

        try {
            janggi.processTurn(selectPosition, destinationPosition);
            piecePositionDao.processTurnTransaction(selectPosition, destinationPosition);
            return janggi;
        } catch (Exception e) {
            return janggiSnapshot;
        }
    }

    public Janggi loadOrCreateJanggi() {
        final Map<BoardPosition, Piece> pieces = piecePositionDao.findAll();
        // TODO : JanggiGameDao에 튜플이 존재한다면으로 변경
        if (pieces.isEmpty()) {
            // TODO : JanggiGameDao를 통해 장기 정보 저장
            return Janggi.initialize();
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
