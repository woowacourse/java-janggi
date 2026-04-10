import domain.GameDeadline;
import domain.JanggiGame;

public record InitializedGame(JanggiGame game, GameDeadline deadline) {}
