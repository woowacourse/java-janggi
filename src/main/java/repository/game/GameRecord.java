package repository.game;

import domain.Formation;

public record GameRecord(
    Formation choFormation,
    Formation hanFormation
) {

}
