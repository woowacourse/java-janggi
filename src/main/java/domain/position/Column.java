package domain.position;

import domain.board.BoardPolicy;

public record Column(int column) {

    public Column {
        validateColumn(column);
    }

    private void validateColumn(int column) {
        if (BoardPolicy.isOutBoundColumn(column)) {
            throw new IllegalArgumentException("[ERROR] 좌표 범위를 초과했습니다.");
        }
    }

}
