package validator;

import domain.board.Palace;
import domain.piece.Country;
import domain.position.Position;

public interface PalaceCheckable {

    default void validateBound(Position dest, Country country) {
        int destX = dest.x();
        int destY = dest.y();

        Palace palace = Palace.from(country.getDirection());
        if (!palace.isBound(destX, destY)) {
            throw new IllegalArgumentException("dest는 궁성 밖으로 나갈 수 없습니다.");
        }
    }
}
