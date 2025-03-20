package domain.piece.strategy;

import domain.Position;
import domain.TeamType;
import domain.piece.Elephant;
import domain.piece.Horse;
import java.util.List;

public class OuterElephantStrategy extends HorseElephantSetupStrategy {

    public OuterElephantStrategy() {
        super(
            List.of(
                new Elephant(Position.of(9, 1), TeamType.HAN),
                new Horse(Position.of(9, 2), TeamType.HAN),
                new Horse(Position.of(9, 6), TeamType.HAN),
                new Elephant(Position.of(9, 7), TeamType.HAN)
            ),
            List.of(
                new Elephant(Position.of(0, 1), TeamType.CHO),
                new Horse(Position.of(0, 2), TeamType.CHO),
                new Horse(Position.of(0, 6), TeamType.CHO),
                new Elephant(Position.of(0, 7), TeamType.CHO)
            )
        );
    }
}
