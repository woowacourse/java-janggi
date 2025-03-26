package player;

import java.util.HashMap;
import java.util.Map;
import pieceProperty.Position;
import pieceProperty.Positions;
import view.ErrorMessage;

public class Players {
    private final Map<Nation, Player> players;

    public Players(Map<Nation, Player> players) {
        this.players = new HashMap<>(players);
    }

    public Boolean isJanggunDie() {
        return players.values().stream()
                .anyMatch(Player::isJanggunDie);
    }

    public void capturePiece(final Nation attackNation, final Position presentPosition, final Position destination) {
        movePiece(attackNation, presentPosition, destination);
        Nation defenseNation = attackNation.getDefenseNation();
        removePiece(defenseNation, destination);
    }

    public void validateMovement(final Nation attackNation, final Position presentPosition, final Position destination) {
        validateAllyPieceAtStart(attackNation, presentPosition);
        validateAllyPieceAtDestination(attackNation, destination);
        canPieceMoveTo(attackNation, presentPosition, destination);
        validateRoute(attackNation, presentPosition, destination);
    }

    private void movePiece(final Nation attackNation, final Position presentPosition, final Position destination) {
        players.get(attackNation).movePiece(presentPosition, destination);
    }

    private void removePiece(final Nation defenseNation, final Position destination) {
        players.get(defenseNation).removePiece(destination);
    }

    private void validateAllyPieceAtStart(final Nation attackNation, final Position presentPosition) {
        players.get(attackNation).validateAllyPieceAtStart(presentPosition);
    }

    private void validateAllyPieceAtDestination(final Nation attackNation, final Position destination) {
        players.get(attackNation).validateAllyPieceAtDestination(destination);
    }

    private void canPieceMoveTo(final Nation attackNation, final Position presentPosition, final Position destination) {
        players.get(attackNation).canPieceMoveTo(presentPosition, destination);
    }

    private void validateRoute(final Nation attackNation, final Position presentPosition, final Position destination) {
        Positions route = makeRoute(attackNation, presentPosition, destination);
        int count = players.values().stream()
                .mapToInt(player -> player.countObstacle(route))
                .sum();

        if (isPoAt(attackNation, presentPosition)) {
            validatePoRoute(count, route);
            return;
        }

        if (count >= 1) {
            throw new IllegalArgumentException(ErrorMessage.formatMessage("장애물이 존재하여 이동할 수 없습니다."));
        }
    }

    private void validatePoRoute(int count, Positions route) {
        if (count == 0 || count >= 2 || isExistPoInRoute(route)) {
            throw new IllegalArgumentException(ErrorMessage.formatMessage("포가 가는 경로에 장애물이 2개 이상 존재하거나 포가 존재하여 이동할 수 없습니다."));
        }
    }

    private Boolean isPoAt(final Nation attackNation, final Position presentPosition) {
        return players.get(attackNation).isPoAt(presentPosition);
    }

    private Boolean isExistPoInRoute(final Positions route) {
        return players.values().stream()
                .anyMatch(player -> player.isExistPoInRoute(route));
    }

    private Positions makeRoute(final Nation attackNation, final Position presentPosition, final Position destination) {
        return players.get(attackNation).makeRoute(presentPosition, destination);
    }

}
