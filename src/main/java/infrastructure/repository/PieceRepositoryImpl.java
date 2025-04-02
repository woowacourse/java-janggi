package infrastructure.repository;

import application.persistence.PieceRepository;
import domain.piece.coordiante.Coordinate;
import domain.game.Game;
import domain.piece.Piece;
import infrastructure.dao.PieceDao;
import infrastructure.entity.GameEntity;
import infrastructure.entity.PieceEntity;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PieceRepositoryImpl implements PieceRepository {

    private final PieceDao pieceDao;

    public PieceRepositoryImpl(PieceDao pieceDao) {
        this.pieceDao = pieceDao;
    }

    @Override
    public void savePieces(Map<Coordinate, Piece> pieces, Game game) {
        List<PieceEntity> pieceEntities = PieceEntity.from(pieces);
        GameEntity gameEntity = GameEntity.from(game);

        pieceDao.save(pieceEntities, gameEntity);
    }

    @Override
    public void deleteByGame(Game game) {
        GameEntity gameEntity = GameEntity.from(game);
        pieceDao.deleteByGame(gameEntity);
    }

    @Override
    public Map<Coordinate, Piece> findByGame(Game game) {
        GameEntity gameEntity = GameEntity.from(game);
        List<PieceEntity> pieceEntities = pieceDao.findByGame(gameEntity);

        return pieceEntities.stream()
                .map(PieceEntity::toDomain)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
