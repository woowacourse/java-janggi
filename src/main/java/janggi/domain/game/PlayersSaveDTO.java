package janggi.domain.game;

public record PlayersSaveDTO(String choPlayerName, String hanPlayerName, String turn) {
    public static PlayersSaveDTO from(Players players) {
        return new PlayersSaveDTO(
                players.getChoPlayerName(),
                players.getHanPlayerName(),
                players.getTurn().getSide().name()
        );
    }
}
