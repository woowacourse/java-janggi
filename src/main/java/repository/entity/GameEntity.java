package repository.entity;

import java.util.List;

public record GameEntity(
        Long gameEntityId,
        Long gameContextEntityId,
        List<Long> piecesEntitiesIds
) {
}
