package domain.board;

import domain.board.cho.DefaultChoPlacement;
import domain.board.cho.InnerHorseChoPlacement;
import domain.board.cho.LeftInnerHorseChoPlacement;
import domain.board.cho.RightInnerHorseChoPlacement;
import domain.board.han.DefaultHanPlacement;
import domain.board.han.InnerHorseHanPlacement;
import domain.board.han.LeftInnerHorseHanPlacement;
import domain.board.han.RightInnerHorseHanPlacement;
import domain.piece.Team;
import util.ErrorMessage;

import java.util.Arrays;

public enum PlacementOption {

    HAN_DEFAULT(Team.HAN, "1", new DefaultHanPlacement()),
    HAN_INNER_HORSE(Team.HAN, "2", new InnerHorseHanPlacement()),
    HAN_LEFT_INNER_HORSE(Team.HAN, "3", new LeftInnerHorseHanPlacement()),
    HAN_RIGHT_INNER_HORSE(Team.HAN, "4", new RightInnerHorseHanPlacement()),

    CHO_DEFAULT(Team.CHO, "1", new DefaultChoPlacement()),
    CHO_INNER_HORSE(Team.CHO, "2", new InnerHorseChoPlacement()),
    CHO_LEFT_INNER_HORSE(Team.CHO, "3", new LeftInnerHorseChoPlacement()),
    CHO_RIGHT_INNER_HORSE(Team.CHO, "4", new RightInnerHorseChoPlacement());

    private final Team team;
    private final String input;
    private final PlacementStrategy strategy;

    PlacementOption(Team team, String input, PlacementStrategy strategy) {
        this.team = team;
        this.input = input;
        this.strategy = strategy;
    }

    public static PlacementStrategy hanFrom(String input) {
        return Arrays.stream(values())
                .filter(option -> option.team == Team.HAN)
                .filter(option -> option.input.equals(input))
                .map(option -> option.strategy)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_PLACEMENT_OPTION.getMessage()));
    }

    public static PlacementStrategy choFrom(String input) {
        return Arrays.stream(values())
                .filter(option -> option.team == Team.CHO)
                .filter(option -> option.input.equals(input))
                .map(option -> option.strategy)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_PLACEMENT_OPTION.getMessage()));
    }
}
