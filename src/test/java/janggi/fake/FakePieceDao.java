package janggi.fake;

import janggi.dao.entity.PieceEntity;
import janggi.dao.piece.PieceDao;
import janggi.position.Position;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FakePieceDao implements PieceDao {

    private long id = 0L;
    private final List<PieceEntity> pieceEntities;

    public FakePieceDao(final PieceEntity... pieceEntities) {
        this.pieceEntities = new ArrayList<>(List.of(pieceEntities));
    }

    @Override
    public void addPieces(final List<PieceEntity> pieces) {
        for (final PieceEntity piece : pieces) {
            final PieceEntity pieceEntity = new PieceEntity(++id,
                    piece.getPieceType(),
                    piece.getTeam(),
                    piece.getRowIndex(),
                    piece.getColIndex(),
                    piece.getGameId());
            pieceEntities.add(pieceEntity);
        }
    }

    @Override
    public List<PieceEntity> findPiecesById(final Long gameId) {
        return pieceEntities.stream()
                .filter(pieceEntity -> pieceEntity.getGameId().equals(gameId))
                .collect(Collectors.toList());
    }

    @Override
    public void removePieceByPosition(final Long gameId, final Position position) {

    }

    @Override
    public void updatePiece(final PieceEntity pieceEntity) {

    }

    @Override
    public void deletePiecesBy(final Long gameId) {

    }

    public List<PieceEntity> getPieceEntities() {
        return Collections.unmodifiableList(this.pieceEntities);
    }
}
