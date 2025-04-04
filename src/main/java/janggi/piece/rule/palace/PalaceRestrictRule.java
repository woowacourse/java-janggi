package janggi.piece.rule.palace;

import janggi.board.Board;
import janggi.coordinate.Position;

public class PalaceRestrictRule {

    public void validate(final Board board, final Position destination) {
        if (board.isPalace(destination)) {
            return;
        }

        throw new IllegalArgumentException("해당 기물은 궁성 밖으로 움직일 수 없습니다");
    }
}
