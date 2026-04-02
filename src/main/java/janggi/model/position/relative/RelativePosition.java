package janggi.model.position.relative;

import janggi.model.position.absolute.Column;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.Row;

public record RelativePosition(
        int rowOffset,
        int columnOffset
) {
    public Position moved(Position from) {
        int nextRowNumber = from.row().getValue() + rowOffset;
        int nextColumnNumber = from.column().getValue() + columnOffset;

        if (nextRowNumber < Row.START || nextRowNumber > Row.END
        || nextColumnNumber < Column.START || nextColumnNumber > Column.END) {
            throw new IllegalArgumentException("보드 밖으로 이동할 수 없습니다.");
        }

        return new Position(
                Row.of(nextRowNumber),
                Column.of(nextColumnNumber)
        );
    }
}
