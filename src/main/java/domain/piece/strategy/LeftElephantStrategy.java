package domain.piece.strategy;

import domain.Position;
import domain.TeamType;
import domain.piece.Elephant;
import domain.piece.Horse;
import domain.piece.Piece;
import java.util.ArrayList;
import java.util.List;

public class LeftElephantStrategy implements HorseElephantSetupStrategy{

    private static final List<Piece> HAN_LEFT_ELEPHANT_HORSE;
    private static final List<Piece> CHO_LEFT_ELEPHANT_HORSE;

    static {
        CHO_LEFT_ELEPHANT_HORSE = List.of(
                new Horse(Position.of(0, 2), TeamType.CHO),
                new Elephant(Position.of(0, 1), TeamType.CHO),
                new Elephant(Position.of(0, 6), TeamType.CHO),
                new Horse(Position.of(0, 7), TeamType.CHO)
        );

        HAN_LEFT_ELEPHANT_HORSE = List.of(
                new Horse(Position.of(9, 2), TeamType.HAN),
                new Elephant(Position.of(9, 1), TeamType.HAN),
                new Elephant(Position.of(9, 6), TeamType.HAN),
                new Horse(Position.of(9, 7), TeamType.HAN)
        );
    }

    @Override
    public List<Piece> createElephantHorse(TeamType teamType) {
        if (teamType == TeamType.CHO) {
            return new ArrayList<>(CHO_LEFT_ELEPHANT_HORSE);
        }
        return new ArrayList<>(HAN_LEFT_ELEPHANT_HORSE);
    }
}
