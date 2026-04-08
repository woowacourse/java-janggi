package janggi.service;

import janggi.dao.piece.PieceDao;
import janggi.dao.piece.PieceEntity;
import janggi.model.Team;
import janggi.model.piece.Piece;
import janggi.model.position.absolute.Position;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

public class TestPieceDao implements PieceDao {

    private final List<PieceEntity> pieceEntities = new ArrayList<>();
    private long lastPieceId = 0L;

    @Override
    public void saveBoard(
            Connection connection,
            Map<Position, Piece> boardInfo,
            Long gameId
    ) {
        for (Entry<Position, Piece> entry : boardInfo.entrySet()) {
            Position position = entry.getKey();
            Piece piece = entry.getValue();

            lastPieceId++;
            pieceEntities.add(
                    new PieceEntity(
                            lastPieceId,
                            gameId,
                            piece.getPieceType().name(),
                            position.row().getValue(),
                            position.column().getValue(),
                            extractTeam(piece)
                    )
            );
        }
    }

    @Override
    public List<PieceEntity> findAllPiecesByGameId(Connection connection, long gameId) {
        return pieceEntities.stream()
                .filter(e -> e.gameId() == gameId)
                .toList();
    }

    @Override
    public Optional<PieceEntity> findPieceByPosition(Connection connection, Position position) {
        return pieceEntities.stream()
                .filter(e ->
                        e.positionRow() == position.row().getValue()
                                && e.positionColumn() == position.column().getValue()
                )
                .findFirst();
    }

    @Override
    public void updatePosition(Connection connection, Long pieceId, Position to) {
        PieceEntity piece = pieceEntities.stream()
                .filter(e -> e.id() == pieceId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 기물이 없습니다."));

        pieceEntities.remove(piece);
        pieceEntities.add(
                new PieceEntity(
                        piece.id(),
                        piece.gameId(),
                        piece.pieceType(),
                        to.row().getValue(),
                        to.column().getValue(),
                        piece.team()
                )
        );
    }

    @Override
    public void deletePieceByPosition(Connection connection, Position position) {
        PieceEntity piece = pieceEntities.stream()
                .filter(e ->
                        e.positionRow() == position.row().getValue()
                                && e.positionColumn() == position.column().getValue()
                )
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 기물이 없습니다."));

        pieceEntities.remove(piece);
    }

    private String extractTeam(Piece piece) {
        if (piece.isSameTeam(Team.HAN)) {
            return Team.HAN.name();
        }
        return Team.CHO.name();
    }
}