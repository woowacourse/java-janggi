package player;

import java.util.List;
import pieceProperty.Position;
import pieceProperty.Positions;

public class Players {
    private final List<Player> players;

    public Players(List<Player> players) {
        this.players = players;
    }

    public boolean isKingDie() {
        return players.stream()
                .anyMatch(Player::isKingDie);
    }

    public void validateMovement(Nation attackNation, Position presentPosition, Position destination) {
        validateAllyPieceAtStart(attackNation, presentPosition);
        validateAllyPieceAtDestination(attackNation, destination);
        canPieceMoveTo(attackNation, presentPosition, destination);
        validateRoute(attackNation, presentPosition, destination);
    }

    public void movePiece(Nation attackNation, Position presentPosition, Position destination) {
        players.stream()
                .filter(player -> player.isSameNation(attackNation))
                .findFirst()
                .ifPresent(player -> player.movePiece(presentPosition, destination));
    }

    private void validateAllyPieceAtStart(Nation attackNation, Position presentPosition) {
        players.stream()
                .filter(player -> player.isSameNation(attackNation))
                .findFirst()
                .ifPresent(player -> player.validateAllyPieceAtStart(presentPosition));
    }

    private void validateAllyPieceAtDestination(Nation attackNation, Position destination) {
        players.stream()
                .filter(player -> player.isSameNation(attackNation))
                .findFirst()
                .ifPresent(player -> player.validateAllyPieceAtDestination(destination));
    }

    private void canPieceMoveTo(Nation attackNation, Position presentPosition, Position destination){
        players.stream()
                .filter(player -> player.isSameNation(attackNation))
                .findFirst()
                .ifPresent(player -> player.canPieceMoveTo(presentPosition, destination));
    }

    private void validateRoute(Nation attackNation, Position presentPosition, Position destination) {
        Positions route = makeRoute(attackNation, presentPosition, destination);
        int count = players.stream()
                .mapToInt(player -> player.countObstacle(route))
                .sum();

        if (isPoAt(attackNation, presentPosition)) {
            if (count >= 2 || isExistPoInRoute(route)) {
                throw new IllegalArgumentException("[ERROR] 포가 가는 경로에 장애물이 2개 이상 존재하거나 포가 존재하여 이동할 수 없습니다.");
            }
            return;
        }

        if (count >= 1) {
            throw new IllegalArgumentException("[ERROR] 장애물이 존재하여 이동할 수 없습니다.");
        }

    }

    private Boolean isPoAt(Nation attackNation, Position presentPosition) {
        return players.stream()
                .filter(player -> player.isSameNation(attackNation))
                .findFirst()
                .map(player -> player.isPoAt(presentPosition))
                .orElseThrow();
    }

    private Boolean isExistPoInRoute(Positions route) {
        return players.stream()
                .anyMatch(player -> player.isExistPoInRoute(route));
    }

    private Positions makeRoute(Nation attackNation, Position presentPosition, Position destination) {
        return players.stream()
                .filter(player -> player.isSameNation(attackNation))
                .map(player -> player.makeRoute(presentPosition, destination))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 경로가 존재하지 않습니다."));
    }

    public void removePiece(Nation defenseNation, Position destination) {
        players.stream()
                .filter(player -> player.isSameNation(defenseNation))
                .findFirst()
                .ifPresent(player -> player.removePiece(destination));
    }
}
