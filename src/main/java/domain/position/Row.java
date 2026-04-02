package domain.position;

import domain.board.BoardPolicy;

public record Row(int row) {

    public Row {
        validateRange(row);
    }

    private void validateRange(int row) {
        if (BoardPolicy.isOutBoundRow(row)) {
            throw new IllegalArgumentException("[ERROR] 좌표 범위를 초과했습니다.");
        }
    }

}
