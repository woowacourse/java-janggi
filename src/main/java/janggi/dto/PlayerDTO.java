package janggi.dto;

import janggi.domain.game.Player;
import janggi.domain.game.Side;
import janggi.util.SideDisplayNameMapper;

public record PlayerDTO(String name, String sideName) {

    public PlayerDTO(String name, Side side) {
        this(name, SideDisplayNameMapper.toDisplayName(side));
    }

    public static PlayerDTO from(Player currentPlayer) {
        return new PlayerDTO(currentPlayer.name(), currentPlayer.side());
    }
}
