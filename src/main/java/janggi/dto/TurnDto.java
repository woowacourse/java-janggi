package janggi.dto;

import janggi.domain.board.Turn;

public record TurnDto(String teamName) {
    public static TurnDto from(Turn turn) {
        return new TurnDto(turn.getTeam().name());
    }
}

