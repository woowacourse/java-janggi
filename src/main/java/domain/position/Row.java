package domain.position;

import static domain.board.BoardPolicy.MAX_ROW;
import static domain.board.BoardPolicy.MIN_ROW;

public record Row(int row) {

    public Row {
        validateRange(row);
    }

    private void validateRange(int row) {
        if (row < MIN_ROW || row > MAX_ROW) {
            throw new IllegalArgumentException("[ERROR] 좌표 범위를 초과했습니다.");
        }
    }

}
