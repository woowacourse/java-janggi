package janggi.model.board.palace;

import janggi.model.position.absolute.Position;

public record UndirectedLine(
        Position endPoint1,
        Position endPoint2
) {
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UndirectedLine that = (UndirectedLine) o;

        return (endPoint1.equals(that.endPoint1) && endPoint2.equals(that.endPoint2))
                || (endPoint1.equals(that.endPoint2) && endPoint2.equals(that.endPoint1));
    }

    @Override
    public int hashCode() {
        return endPoint1().hashCode() ^ endPoint2().hashCode();
    }
}
