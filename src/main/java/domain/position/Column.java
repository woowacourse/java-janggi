package domain.position;

import static domain.board.BoardPolicy.MAX_COLUMN;
import static domain.board.BoardPolicy.MIN_COLUMN;

public record Column(int column) {

    public Column {
        validateColumn(column);
    }

    private void validateColumn(int column) {
        if (column < MIN_COLUMN || column > MAX_COLUMN) {
            throw new IllegalArgumentException("[ERROR] 좌표 범위를 초과했습니다.");
        }
    }

}
