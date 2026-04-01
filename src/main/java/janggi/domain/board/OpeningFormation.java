package janggi.domain.board;

import janggi.domain.piece.Name;
import java.util.Arrays;
import java.util.List;

public enum OpeningFormation {
    LEFT_ELEPHANT(1, List.of(Name.ELEPHANT, Name.HORSE, Name.ELEPHANT, Name.HORSE)),
    RIGHT_ELEPHANT(2, List.of(Name.HORSE, Name.ELEPHANT, Name.HORSE, Name.ELEPHANT)),
    INNER_ELEPHANT(3, List.of(Name.HORSE, Name.ELEPHANT, Name.ELEPHANT, Name.HORSE)),
    OUTER_ELEPHANT(4, List.of(Name.ELEPHANT, Name.HORSE, Name.HORSE, Name.ELEPHANT));

    private final int choice;
    private final List<Name> formation;

    OpeningFormation(int choice, List<Name> formation) {
        this.choice = choice;
        this.formation = formation;
    }

    public static OpeningFormation from(int choice) {
        return Arrays.stream(values())
                .filter(openingFormation -> openingFormation.choice == choice)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바르지 않은 차림 선택입니다."));
    }

    public List<Name> getFormation() {
        return formation;
    }
}
