package janggi.dto;

import janggi.domain.board.Board;
import janggi.domain.team.TeamType;

public record GameResultDto(
    String winnerTeam
) {

    public static GameResultDto from(final Board board) {
        final TeamType winnerTeam = board.calculateWinnerTeam();
        return new GameResultDto(winnerTeam.getName());
    }
}
