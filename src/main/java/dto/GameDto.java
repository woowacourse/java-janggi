package dto;

import model.Game;

public record GameDto(
    int id,
    String name,
    TeamDto turn
) {
    public static GameDto from(Game game) {
        return new GameDto(game.getId(), game.getName(), TeamDto.from(game.getTurn()));
    }
}
