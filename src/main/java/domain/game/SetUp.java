package domain.game;

import domain.direction.PieceDirections;
import domain.piece.Piece;
import domain.piece.Position;
import domain.piece.category.Elephant;
import domain.piece.category.Horse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public enum SetUp {

    INNER_ELEPHANT("1",
            Position.of(2, 1),
            Position.of(3, 1)
    ),
    OUTER_ELEPHANT("2",
            Position.of(3, 1),
            Position.of(2, 1)
    ),
    RIGHT_ELEPHANT("3",
            Position.of(2, 1),
            Position.of(3, 1)
    ),
    LEFT_ELEPHANT("4",
            Position.of(3, 1),
            Position.of(2, 1)
    ),
    ;

    private final String command;
    private final Position hanHorsePosition;
    private final Position hanElephantPosition;

    SetUp(String command, Position hanHorsePosition, Position hanElephantPosition) {
        this.command = command;
        this.hanHorsePosition = hanHorsePosition;
        this.hanElephantPosition = hanElephantPosition;
    }

    public static SetUp getValue(String input) {
        return Arrays.stream(SetUp.values())
                .filter(setUp -> setUp.command.equals(input))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 잘못된 선택입니다."));
    }

    public List<Piece> initializeHorseAndElephant(Function<Position, Position> teamSide) {
        if (this == INNER_ELEPHANT || this == OUTER_ELEPHANT) {
            return innerOuterPieces(teamSide);
        }
        if (this == RIGHT_ELEPHANT) {
            return rightElephant(teamSide);
        }
        if (this == LEFT_ELEPHANT) {
            return leftElephant(teamSide);
        }
        throw new IllegalArgumentException("[ERROR] 잘못된 선택입니다.");
    }

    public List<Piece> innerOuterPieces(Function<Position, Position> teamSide) {
        return getPieces(teamSide, hanHorsePosition.flipLeftRight(), hanElephantPosition.flipLeftRight());
    }

    public List<Piece> rightElephant(Function<Position, Position> teamSide) {
        return getPieces(teamSide, hanHorsePosition.flipLeftRight().moveRow(-1),
                hanElephantPosition.flipLeftRight().moveRow(1));
    }

    public List<Piece> leftElephant(Function<Position, Position> teamSide) {
        return getPieces(teamSide, hanHorsePosition.flipLeftRight().moveRow(1),
                hanElephantPosition.flipLeftRight().moveRow(-1));
    }

    private List<Piece> getPieces(Function<Position, Position> teamSide, Position hanHorsePosition,
                                  Position hanElephantPosition) {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Horse(teamSide.apply(this.hanHorsePosition), PieceDirections.HORSE.get()));
        pieces.add(new Horse(teamSide.apply(hanHorsePosition), PieceDirections.HORSE.get()));
        pieces.add(new Elephant(teamSide.apply(this.hanElephantPosition), PieceDirections.ELEPHANT.get()));
        pieces.add(new Elephant(teamSide.apply(hanElephantPosition),
                PieceDirections.ELEPHANT.get()));
        return pieces;
    }
}
