package janggi.domain.game;

public record PlayerResultDTO(String name, Side side, double score) {
    public static PlayerResultDTO of(Player player, double score) {
        return new PlayerResultDTO(player.getName(), player.getSide(), score);
    }
}
