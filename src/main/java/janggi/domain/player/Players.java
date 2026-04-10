package janggi.domain.player;

import janggi.domain.Side;
import janggi.domain.board.Formation;
import java.util.Map;

public class Players {
    private final Map<Side, Player> players;

    public Players(Player choPlayer, Player hanPlayer) {
        validate(choPlayer, hanPlayer);
        this.players = Map.of(
                Side.CHO, choPlayer,
                Side.HAN, hanPlayer
        );
    }

    private void validate(Player choPlayer, Player hanPlayer) {
        if (choPlayer.side() != Side.CHO || hanPlayer.side() != Side.HAN) {
            throw new IllegalArgumentException("초나라와 한나라 플레이어가 각각 1명씩 필요합니다.");
        }
        validateDuplicateName(choPlayer.name(), hanPlayer.name());
    }

    private static void validateDuplicateName(Name choName, Name hanName) {
        if (choName.equals(hanName)) {
            throw new IllegalArgumentException("동일한 플레이어 이름을 사용할 수 없습니다.");
        }
    }

    public Formation getFormation(Side side) {
        return players.get(side).formation();
    }

    public Name getNameBySide(Side side) {
        return players.get(side).name();
    }
}
