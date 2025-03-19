package domain.piece.strategy;

import domain.TeamType;
import domain.piece.Piece;
import java.util.List;

public interface HorseElephantSetupStrategy {
    List<Piece> createElephantHorse(TeamType teamType);
}
