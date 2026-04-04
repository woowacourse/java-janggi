package repository.entity;

import java.util.List;

public record Game(
        Long gameId,
        Long gameContextId,
        List<Long> piecesEntitiesIds
) {
}
