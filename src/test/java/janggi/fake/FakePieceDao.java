package janggi.fake;

import janggi.dao.PieceDao;
import janggi.dao.entity.PieceEntity;
import janggi.domain.piece.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FakePieceDao implements PieceDao {

    private long id;
    private List<PieceEntity> pieceEntities;

    public FakePieceDao(PieceEntity... pieceEntities) {
        this.pieceEntities = new ArrayList<>(List.of(pieceEntities));
        id = pieceEntities.length;
    }

    @Override
    public void addPieces(List<PieceEntity> pieces) {
        for (PieceEntity piece : pieces) {
            piece.setId(++id);
            pieceEntities.add(piece);
        }
    }

    @Override
    public List<PieceEntity> findAllByGameId(Long gameId) {
        return pieceEntities.stream()
                .filter(pieceEntity -> pieceEntity.getGameId().equals(gameId))
                .collect(Collectors.toList());
    }

    @Override
    public void updatePiece(Long gameId, Point from, Point to) {
        PieceEntity pieceEntity = findPieceByGameIdAndPoint(gameId, from);
        pieceEntity.setPoint(to);
    }

    @Override
    public void deletePiece(Long gameId, Point point) {
        pieceEntities = pieceEntities.stream()
                .filter(each -> !each.getPoint().equals(point))
                .collect(Collectors.toList());
    }

    private PieceEntity findPieceByGameIdAndPoint(Long gameId, Point point) {
        return pieceEntities.stream()
                .filter(each -> each.getGameId().equals(gameId))
                .filter(each -> each.getPoint().equals(point))
                .findFirst()
                .orElse(null);
    }
}
