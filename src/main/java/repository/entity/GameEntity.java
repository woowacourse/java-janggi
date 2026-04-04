package repository.entity;

import java.util.List;

public record GameEntity(
        Long gameId,
        Long gameContextId,
        List<Long> piecesEntitiesIds
) {
}
