import player.Player;

public class JanggiGameState {
    private final Player hanPlayer;
    private final Player choPlayer;
    private final Boolean isChoTurn = true;

    public JanggiGameState(Player hanPlayer, Player choPlayer) {
        this.hanPlayer = hanPlayer;
        this.choPlayer = choPlayer;
    }

    public Player getHanPlayer() {
        return hanPlayer;
    }

    public Player getChoPlayer() {
        return choPlayer;
    }

    public Boolean getChoTurn() {
        return isChoTurn;
    }
}
