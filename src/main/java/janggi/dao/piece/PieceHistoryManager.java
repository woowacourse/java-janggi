package janggi.dao.piece;

import janggi.direction.PieceMoveRule;
import janggi.direction.PieceType;
import janggi.direction.obstacle.ObstacleBlockStrategy;
import janggi.direction.obstacle.ObstacleJumpingObstacle;
import janggi.dto.PieceDto;
import janggi.dto.PieceMove;
import janggi.piece.Piece;
import janggi.piece.board.Board;
import janggi.piece.players.Players;
import janggi.piece.players.Team;
import janggi.position.Position;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PieceHistoryManager {

    private static final int KING_COUNT = 2;

    private final PieceDao pieceDao;

    public PieceHistoryManager(final PieceDao pieceDao) {
        this.pieceDao = pieceDao;
    }

    public void initialize(final Players players) {
        pieceDao.deleteAll();
        initializeChoPieces(players.getChoPieces());
        initializeHanPieces(players.getHanPieces());
    }

    public boolean mustBeInitialize() {
        final List<PieceDto> pieceDtos = pieceDao.selectAll();
        final int kingCount = (int) pieceDtos.stream()
                .filter(pieceDto -> pieceDto.pieceType().isKing())
                .count();
        return pieceDtos.isEmpty() || kingCount != KING_COUNT;
    }

    public void updatePiece(final PieceMove pieceMove) {
        if (pieceMove.isCaught()) {
            pieceDao.delete(pieceMove);
        }
        pieceDao.update(pieceMove);
    }

    private void initializeChoPieces(final Board choPieces) {
        for (final Piece piece : choPieces.getPieces()) {
            final Position position = piece.getPosition();
            pieceDao.insert(new PieceDto(Team.CHO, piece.getPieceType(), position.getY(), position.getX()));
        }
    }

    private void initializeHanPieces(final Board hanPieces) {
        for (final Piece piece : hanPieces.getPieces()) {
            final Position position = piece.getPosition();
            pieceDao.insert(new PieceDto(Team.HAN, piece.getPieceType(), position.getY(), position.getX()));
        }
    }

    public Players loadPlayers() {
        final Board hanBoard = loadPlayers(Team.HAN);
        final Board choBoard = loadPlayers(Team.CHO);
        return new Players(Map.of(Team.HAN, hanBoard, Team.CHO, choBoard));
    }

    private Board loadPlayers(final Team team) {
        final List<PieceDto> pieceDtos = pieceDao.select(team);
        final Set<Piece> pieces = new HashSet<>();
        for (final PieceDto pieceDto : pieceDtos) {
            final Piece piece = new Piece(makePieceMoveRule(pieceDto.pieceType()),
                    makePosition(pieceDto.y(), pieceDto.x()));
            pieces.add(piece);
        }
        return Board.from(pieces);
    }

    private PieceMoveRule makePieceMoveRule(final PieceType pieceType) {
        if (pieceType == PieceType.CANNON) {
            return new PieceMoveRule(pieceType, new ObstacleJumpingObstacle());
        }
        return new PieceMoveRule(pieceType, new ObstacleBlockStrategy());
    }

    private Position makePosition(final int y, final int x) {
        return new Position(y, x);
    }
}
