package domain;

import domain.piece.Pieces;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class GameResult {

    private final Map<Player, Result> resultMap = new HashMap<>();

    public GameResult(final Map<Player, Pieces> gamePlayers) {
        for (Entry<Player, Pieces> entry : gamePlayers.entrySet()) {
            Player current = entry.getKey();
            Player opposite = getOpposite(gamePlayers, current);
            Result result = determineResult(entry.getValue(), gamePlayers.get(opposite), current, opposite);
            resultMap.put(current, result);
        }
    }

    private Player getOpposite(final Map<Player, Pieces> gamePlayers, final Player current) {
        return gamePlayers.keySet()
                .stream()
                .filter(player -> !player.equals(current))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("서버에 문제가 발생했습니다. - 상대 플레이어가 없습니다."));
    }

    private Result determineResult(final Pieces currentPieces, final Pieces oppositePieces, final Player current,
                                   final Player opposite) {
        if (!currentPieces.existKing()) {
            return Result.LOSE;
        }
        if (!oppositePieces.existKing()) {
            return Result.WIN;
        }

        return compareScores(current, opposite);
    }

    private Result compareScores(final Player current, final Player opposite) {
        int compared = current.compareScoreTo(opposite);
        if (compared == 0) {
            return Result.DRAW;
        }
        if (compared > 0) {
            return Result.WIN;
        }
        return Result.LOSE;
    }

    public Map<Player, Result> getResultMap() {
        return resultMap;
    }
}
