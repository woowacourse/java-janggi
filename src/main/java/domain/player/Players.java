package domain.player;

import domain.common.Side;
import domain.turn.ActiveTurn;
import domain.turn.InactiveTurn;
import java.util.List;

public class Players {
    private final List<Player> players;

    private Players(Player cho, Player han) {
        this.players = List.of(cho, han);
    }

    public static Players createInitial(Name choName, Name hanName) {
        validateDuplicateName(choName, hanName);
        return new Players(
                new Player(choName, Side.CHO, new ActiveTurn()),
                new Player(hanName, Side.HAN, new InactiveTurn())
        );
    }

    private static void validateDuplicateName(Name choName, Name hanName) {
        if (choName.equals(hanName)) {
            throw new IllegalArgumentException("동일한 플레이어 이름을 사용할 수 없습니다.");
        }
    }

    public Player getActiveTurnPlayer() {
        return players.stream()
                .filter(Player::isCurrentTurn)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("현재 턴인 플레이어가 없습니다."));
    }

    public Player getInActiveTurnPlayer() {
        return players.stream()
                .filter(player -> !player.isCurrentTurn())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("현재 턴인 플레이어가 없습니다."));
    }

    public Side getCurrentSide() {
        return players.stream()
                .filter(Player::isCurrentTurn)
                .map(Player::getSide)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("현재 턴인 플레이어의 진영이 존재하지 않습니다."));
    }

    public void switchPlayer() {
        players.forEach(Player::toggleTurn);
    }
}
