package domain.piece.rule;

import domain.Moves;
import domain.piece.Position;

public class PalaceRule implements Rule {

    @Override
    public void validate(Moves moves, Position src, Position dest) {
        if (!moves.isPossibleInPalace(src)) {
            throw new IllegalArgumentException("궁 내부에서만 이동할 수 있습니다.");
        }
    }
}
