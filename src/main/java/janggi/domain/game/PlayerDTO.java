package janggi.domain.game;

public record PlayerDTO(String name, Side side) {
    public static PlayerDTO from(Player player) {
        return new PlayerDTO(player.getName(), player.getSide());
    }
}
