package repository.game_record.dto;

import domain.board.Formation;

public record GameRecord(
    Formation choFormation,
    Formation hanFormation
) {

}
