package database.repository.game;

import object.team.Country;

public record GameDto(
        Country turnTeam,
        boolean isEnd,
        int scoreHan,
        int scoreCho
) {
}
