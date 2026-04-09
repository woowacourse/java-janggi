package janggi.repository.piece;

import janggi.domain.position.Position;
import janggi.entity.MovementEntity;
import janggi.entity.PieceEntity;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakePieceRepository implements PieceRepository {

    private final Map<Long, Map<Position, PieceEntity>> storage = new HashMap<>();

    @Override
    public void saveAll(Connection connection, Long gameId, List<PieceEntity> pieces) {
        Map<Position, PieceEntity> board = storage.computeIfAbsent(gameId, id -> new HashMap<>());

        for (PieceEntity piece : pieces) {
            Position position = Position.from(piece.row(), piece.column());
            board.put(position, piece);
        }
    }

    @Override
    public void update(Connection connection, Long gameId, Position from, Position to) {
        Map<Position, PieceEntity> board = storage.computeIfAbsent(gameId, id -> new HashMap<>());

        PieceEntity sourcePiece = board.remove(from);
        if (sourcePiece == null) {
            throw new IllegalArgumentException("이동할 기물이 존재하지 않습니다.");
        }

        board.remove(to);

        PieceEntity movedPiece = PieceEntity.toEntity(
                to.row().row(),
                to.column().column(),
                sourcePiece.dynasty(),
                sourcePiece.type()
        );
        board.put(to, movedPiece);
    }

    @Override
    public void revert(Connection connection, Long gameId, MovementEntity movement) {
        Map<Position, PieceEntity> board = storage.computeIfAbsent(gameId, id -> new HashMap<>());

        PieceEntity movedPiece = board.remove(movement.to());
        if (movedPiece == null) {
            throw new IllegalArgumentException("되돌릴 기물이 존재하지 않습니다.");
        }

        PieceEntity restoredSourcePiece = PieceEntity.toEntity(
                movement.from().row().row(),
                movement.from().column().column(),
                movedPiece.dynasty(),
                movedPiece.type()
        );
        board.put(movement.from(), restoredSourcePiece);

        if (movement.hasCapturedPiece()) {
            PieceEntity restoredCapturedPiece = PieceEntity.toEntity(
                    movement.to().row().row(),
                    movement.to().column().column(),
                    movement.destTeam(),
                    movement.destType()
            );
            board.put(movement.to(), restoredCapturedPiece);
        }
    }

    @Override
    public List<PieceEntity> findAllByGameId(Long gameId) {
        Map<Position, PieceEntity> board = storage.getOrDefault(gameId, Map.of());
        return List.copyOf(board.values());
    }

}
