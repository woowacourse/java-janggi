package janggi.entity;

import janggi.domain.position.Position;

public record MovementEntity(
        Long id,
        Long gameId,
        Position from,
        Position to,
        String destTeam,
        String destType
) {

    public boolean hasCapturedPiece() {
        return destTeam != null && destType != null;
    }
    
}
