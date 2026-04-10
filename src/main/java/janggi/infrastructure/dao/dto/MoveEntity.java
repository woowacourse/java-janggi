package janggi.infrastructure.dao.dto;

public record MoveEntity(
        int sourceX,
        int sourceY,
        int targetX,
        int targetY
) {
}
