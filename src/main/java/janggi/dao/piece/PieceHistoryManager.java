package janggi.dao.piece;

import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.piece.position.Position;
import janggi.domain.players.Players;
import janggi.domain.players.Team;
import janggi.dto.PieceDto;
import janggi.dto.PieceMove;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
                .filter(pieceDto -> pieceDto.piece().isKing())
                .count();
        return pieceDtos.isEmpty() || kingCount != KING_COUNT;
    }

    public void updatePiece(final PieceMove pieceMove) {
        pieceDao.delete(pieceMove);
        pieceDao.update(pieceMove);
    }

    private void initializeChoPieces(final Board choPieces) {
        for (final Entry<Position, Piece> entry : choPieces.getBoard().entrySet()) {
            final Position position = entry.getKey();
            final Piece piece = entry.getValue();
            final PieceDto pieceDto = new PieceDto(Team.CHO, piece, position.getY(), position.getX());
            pieceDao.insert(pieceDto);
        }
    }

    private void initializeHanPieces(final Board hanPieces) {
        for (final Entry<Position, Piece> entry : hanPieces.getBoard().entrySet()) {
            final Position position = entry.getKey();
            final Piece piece = entry.getValue();
            final PieceDto pieceDto = new PieceDto(Team.HAN, piece, position.getY(), position.getX());
            pieceDao.insert(pieceDto);
        }
    }

    public Players loadPlayers() {
        final Board hanBoard = loadPlayers(Team.HAN);
        final Board choBoard = loadPlayers(Team.CHO);
        return new Players(Map.of(Team.HAN, hanBoard, Team.CHO, choBoard));
    }

    private Board loadPlayers(final Team team) {
        final List<PieceDto> pieceDtos = pieceDao.select(team);
        final Map<Position, Piece> pieces2 = new HashMap<>();
        for (final PieceDto pieceDto : pieceDtos) {
            pieces2.put(makePosition(pieceDto.y(), pieceDto.x()), pieceDto.piece());
        }
        return new Board(pieces2);
    }

    private Position makePosition(final int y, final int x) {
        return new Position(y, x);
    }
}
