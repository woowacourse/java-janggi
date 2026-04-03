package janggi.infra.entity;

import janggi.domain.piece.PieceType;
import janggi.domain.position.Position;

public class PiecePositionEntity {

    private Long id;
    private Position position;
    private PieceType pieceType;

    private GameRoomEntity gameRoomEntity;
}
