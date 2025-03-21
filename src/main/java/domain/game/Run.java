package domain.game;

import domain.JanggiBoard;
import domain.JanggiPosition;

public class Run extends Start {
    private final JanggiBoard janggiBoard;
    private final Player player;

    public Run(JanggiBoard janggiBoard, Player player) {
        this.janggiBoard = janggiBoard;
        this.player = player;
    }

    @Override
    public GameState move(JanggiPosition beforePosition, JanggiPosition afterPosition) {
        janggiBoard.move(beforePosition, afterPosition);
        player.change();
        return new Run(janggiBoard, player);
    }
}
