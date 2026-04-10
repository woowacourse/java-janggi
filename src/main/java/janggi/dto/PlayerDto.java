package janggi.dto;

import janggi.domain.game.Player;
import janggi.domain.game.Side;
import janggi.util.SideDisplayNameMapper;

public record PlayerDto(String name, String sideName) {

    public PlayerDto(String name, Side side) {
        this(name, SideDisplayNameMapper.toDisplayName(side));
    }

    public static PlayerDto from(Player currentPlayer) {
        return new PlayerDto(currentPlayer.name(), currentPlayer.side());
    }
}
