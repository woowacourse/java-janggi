package janggi.infra.dao;

import janggi.domain.position.Position;
import janggi.infra.entity.PiecePositionEntity;

import java.util.List;

public interface BoardDAO {

    void saveAll(List<PiecePositionEntity> piecePositionEntities);

    List<PiecePositionEntity> findAllPieceByGameRoomId(Long gameRoomId);

    void updatePosition(Long gameRoomId, Position from, Position to);
}
