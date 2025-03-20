package domain.piece.strategy;

import domain.TeamType;
import domain.piece.Piece;
import java.util.ArrayList;
import java.util.List;

public abstract class HorseElephantSetupStrategy {

    protected final List<Piece> HAN_ELEPHANT_HORSE;
    protected final List<Piece> CHO_ELEPHANT_HORSE;

    protected HorseElephantSetupStrategy(List<Piece> hanElephantHorse, List<Piece> choElephantHorse) {
        HAN_ELEPHANT_HORSE = hanElephantHorse;
        CHO_ELEPHANT_HORSE = choElephantHorse;
    }

    public List<Piece> createElephantHorse(TeamType teamType) {
        if (teamType == TeamType.CHO) {
            return new ArrayList<>(CHO_ELEPHANT_HORSE);
        }
        return new ArrayList<>(HAN_ELEPHANT_HORSE);
    }
}
