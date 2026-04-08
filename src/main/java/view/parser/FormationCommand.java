package view.parser;

import domain.board.Formation;
import java.util.Arrays;

public enum FormationCommand {
    FIRST("1", Formation.LEFT_ELEPHANT),
    SECOND("2", Formation.RIGHT_ELEPHANT),
    THIRD("3", Formation.OUTER_ELEPHANT),
    FOURTH("4", Formation.INNER_ELEPHANT),
    ;

    private final String input;
    private final Formation formation;

    FormationCommand(String input, Formation formation) {
        this.input = input;
        this.formation = formation;
    }

    public static FormationCommand from(String input) {
        return Arrays.stream(values())
                .filter(command -> command.input.equals(input.strip()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바른 배치가 아닙니다."));
    }

    public Formation toFormation() {
        return formation;
    }
}
