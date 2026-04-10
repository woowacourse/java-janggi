package janggi.infra.dao;

import janggi.domain.position.Position;
import janggi.infra.entity.PiecePositionEntity;

import java.util.List;

public interface PiecePositionDAO {

    List<Long> saveAll(List<PiecePositionEntity> piecePositionEntities);

    List<PiecePositionEntity> findAllPiecesByGameId(Long gameId);

    void deleteByGameIdAndPosition(Long gameId, Position to);

    void updatePosition(Long gameRoomId, Position from, Position to);
}
