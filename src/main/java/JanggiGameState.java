import static player.Nation.CHO;

import pieceProperty.Position;
import player.Nation;
import player.Players;

public class JanggiGameState {
    private final Players players;
    private Nation attackNation = CHO;

    public JanggiGameState(final Players players) {
        this.players = players;
    }

    public boolean isGameOver() {
        return players.isKingDie();
    }

    public void movePiece(final Position presentPosition, final Position destination) {
        players.validateMovement(attackNation, presentPosition, destination);
        players.movePiece(attackNation, presentPosition, destination);
        players.removePiece(attackNation.getDefenseNation(), destination);
        attackNation = attackNation.getDefenseNation();
    }

    public Nation getAttackNation() {
        return attackNation;
    }

}
