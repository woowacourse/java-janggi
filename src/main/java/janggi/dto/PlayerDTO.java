package janggi.dto;

import janggi.domain.game.Side;
import janggi.util.SideDisplayNameMapper;

public record PlayerDTO(String name, String sideName) {
    public PlayerDTO(String name, Side side) {
        this(name, SideDisplayNameMapper.toDisplayName(side));
    }
}
