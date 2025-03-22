package domain.piece.strategy;

import domain.TeamType;
import domain.piece.Piece;
import domain.position.Position;
import java.util.Map;

public abstract class HorseElephantSetupStrategy {

    protected final Map<Position, Piece> HAN_ELEPHANT_HORSE;
    protected final Map<Position, Piece> CHO_ELEPHANT_HORSE;

    protected HorseElephantSetupStrategy(Map<Position, Piece> hanElephantHorse, Map<Position, Piece> choElephantHorse) {
        HAN_ELEPHANT_HORSE = hanElephantHorse;
        CHO_ELEPHANT_HORSE = choElephantHorse;
    }

    public Map<Position, Piece> createElephantHorse(TeamType teamType) {
        if (teamType == TeamType.CHO) {
            return CHO_ELEPHANT_HORSE;
        }
        return HAN_ELEPHANT_HORSE;
    }
}
