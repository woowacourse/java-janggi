package janggi.dto;

import janggi.domain.PieceInitInfo;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.piece.PieceType;

public record PieceDto(int x, int y, String pieceType, String side) {
    public static PieceDto from(PieceInitInfo pieceInitInfo) {
        return new PieceDto(pieceInitInfo.position().getX(), pieceInitInfo.position().getY(), pieceInitInfo.pieceType().getName(), pieceInitInfo.side().getName());
    }

    public PieceInitInfo toPieceInitInfo() {
        return new PieceInitInfo(new Position(x, y), Side.from(side), PieceType.from(pieceType));
    }
}
