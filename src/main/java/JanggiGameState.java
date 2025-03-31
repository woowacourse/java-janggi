import pieceProperty.Position;
import player.Nation;
import player.Players;

public class JanggiGameState {
    private final Players players;

    public JanggiGameState(final Players players) {
        this.players = players;
    }

    public boolean isGameOver() {
        return players.isJanggunDie();
    }

    public void movePiece(final Nation attackNation, final Position presentPosition, final Position destination) {
        players.validateMovement(attackNation, presentPosition, destination);
        players.capturePiece(attackNation, presentPosition, destination);
    }

}
