package domain.piece.strategy;

import domain.piece.Elephant;
import domain.piece.Horse;
import domain.piece.TeamType;
import domain.position.Position;
import java.util.Map;

public class OuterElephantStrategy extends HorseElephantSetupStrategy {

    public OuterElephantStrategy() {
        super(
                Map.of(Position.of(9, 1), new Elephant(TeamType.HAN),
                        Position.of(9, 2), new Horse(TeamType.HAN),
                        Position.of(9, 6), new Horse(TeamType.HAN),
                        Position.of(9, 7), new Elephant(TeamType.HAN)),
                Map.of(Position.of(0, 1), new Elephant(TeamType.CHO),
                        Position.of(0, 2), new Horse(TeamType.CHO),
                        Position.of(0, 6), new Horse(TeamType.CHO),
                        Position.of(0, 7), new Elephant(TeamType.CHO))
        );
    }
}
