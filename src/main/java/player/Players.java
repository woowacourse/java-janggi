package player;

import java.util.List;
import pieceProperty.Position;
import pieceProperty.Positions;
import view.ErrorMessage;

public class Players {
    private final List<Player> players;

    public Players(final List<Player> players) {
        this.players = players;
    }

    public boolean isKingDie() {
        return players.stream()
                .anyMatch(Player::isKingDie);
    }

    public void validateMovement(final Nation attackNation, final Position presentPosition, final Position destination) {
        validateAllyPieceAtStart(attackNation, presentPosition);
        validateAllyPieceAtDestination(attackNation, destination);
        canPieceMoveTo(attackNation, presentPosition, destination);
        validateRoute(attackNation, presentPosition, destination);
    }

    public void movePiece(final Nation attackNation, final Position presentPosition, final Position destination) {
        players.stream()
                .filter(player -> player.isSameNation(attackNation))
                .findFirst()
                .ifPresent(player -> player.movePiece(presentPosition, destination));
    }

    public void removePiece(final Nation defenseNation, final Position destination) {
        players.stream()
                .filter(player -> player.isSameNation(defenseNation))
                .findFirst()
                .ifPresent(player -> player.removePiece(destination));
    }

    private void validateAllyPieceAtStart(final Nation attackNation, final Position presentPosition) {
        players.stream()
                .filter(player -> player.isSameNation(attackNation))
                .findFirst()
                .ifPresent(player -> player.validateAllyPieceAtStart(presentPosition));
    }

    private void validateAllyPieceAtDestination(final Nation attackNation, final Position destination) {
        players.stream()
                .filter(player -> player.isSameNation(attackNation))
                .findFirst()
                .ifPresent(player -> player.validateAllyPieceAtDestination(destination));
    }

    private void canPieceMoveTo(final Nation attackNation, final Position presentPosition, final Position destination){
        players.stream()
                .filter(player -> player.isSameNation(attackNation))
                .findFirst()
                .ifPresent(player -> player.canPieceMoveTo(presentPosition, destination));
    }

    private void validateRoute(final Nation attackNation, final Position presentPosition, final Position destination) {
        Positions route = makeRoute(attackNation, presentPosition, destination);
        int count = players.stream()
                .mapToInt(player -> player.countObstacle(route))
                .sum();

        if (isPoAt(attackNation, presentPosition)) {
            if (count >= 2 || isExistPoInRoute(route)) {
                throw new IllegalArgumentException(ErrorMessage.formatMessage("포가 가는 경로에 장애물이 2개 이상 존재하거나 포가 존재하여 이동할 수 없습니다."));
            }
            return;
        }

        if (count >= 1) {
            throw new IllegalArgumentException(ErrorMessage.formatMessage("장애물이 존재하여 이동할 수 없습니다."));
        }

    }

    private Boolean isPoAt(final Nation attackNation, final Position presentPosition) {
        return players.stream()
                .filter(player -> player.isSameNation(attackNation))
                .findFirst()
                .map(player -> player.isPoAt(presentPosition))
                .orElseThrow();
    }

    private Boolean isExistPoInRoute(final Positions route) {
        return players.stream()
                .anyMatch(player -> player.isExistPoInRoute(route));
    }

    private Positions makeRoute(final Nation attackNation, final Position presentPosition, final Position destination) {
        return players.stream()
                .filter(player -> player.isSameNation(attackNation))
                .map(player -> player.makeRoute(presentPosition, destination))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.formatMessage("경로가 존재하지 않습니다.")));
    }

}
