package janggi.value;

import java.util.List;
import java.util.stream.IntStream;

public record Position(int x, int y) {

    public Position calculateSum(Position other) {
        int xSum = x + other.x();
        int ySum = y + other.y();
        return new Position(xSum, ySum);
    }

    public List<Position> makeInXLine(Position end) {
        validateInXLine(this, end);
        return makeXValue(this.x, end.x).stream().map(newX -> new Position(newX, this.y)).toList();
    }

    public List<Position> makeInYLine(Position end) {
        validateInYLine(this, end);
        return makeYValue(this.y, end.y).stream().map(newY -> new Position(this.x, newY)).toList();
    }

    public List<Position> makeInDiagonalWithPlusOneSlop(Position end) {
        validateDiagonalWithPlusOneSlop(this, end);
        List<Integer> xValues = makeXValue(this.x, end.x);
        List<Integer> yValues = makeYValue(this.y, end.y);
        return IntStream.range(0, xValues.size())
                .mapToObj(i -> new Position(xValues.get(i), yValues.get(i)))
                .toList();
    }

    public List<Position> makeInDiagonalWithMinusOneSlop(Position end) {
        validateDiagonalWithMinusOneSlop(this, end);
        List<Integer> xValues = makeXValue(this.x, end.x);
        List<Integer> yValues = makeYValue(this.y, end.y);
        return IntStream.range(0, xValues.size())
                .mapToObj(i -> new Position(xValues.get(i), yValues.get(i)))
                .toList();
    }

    private List<Integer> makeXValue(int startX, int endX) {
        int max = Math.max(startX, endX);
        int min = Math.min(startX, endX);
        List<Integer> xValues = IntStream.rangeClosed(min, max).boxed().toList();
        if (startX <= endX) {
            return xValues;
        }
        return xValues.reversed();
    }

    private List<Integer> makeYValue(int startY, int endY) {
        int max = Math.max(startY, endY);
        int min = Math.min(startY, endY);
        List<Integer> yValues = IntStream.rangeClosed(min, max).boxed().toList();
        if (startY <= endY) {
            return yValues;
        }
        return yValues.reversed();
    }

    private void validateInXLine(Position start, Position end) {
        if (start.y != end.y) {
            throw new IllegalArgumentException("동일한 X축 선에 시작점과 도착점이 존재하지 않습니다.");
        }
    }

    private void validateInYLine(Position start, Position end) {
        if (start.x != end.x) {
            throw new IllegalArgumentException("동일한 y축 선에 시작점과 도착점이 존재하지 않습니다.");
        }
    }

    private void validateDiagonalWithPlusOneSlop(Position start, Position end) {
        if (start.equals(end)) {
            return;
        }
        if ((end.y - start.y) == 0 || (end.x - start.x) / (end.y - start.y) != 1) {
            throw new IllegalArgumentException("시작점과 도착점이 기울기 1 대각선을 이루지 않습니다.");
        }
    }

    private void validateDiagonalWithMinusOneSlop(Position start, Position end) {
        if (start.equals(end)) {
            return;
        }
        if ((end.y - start.y) == 0 || (end.x - start.x) / (end.y - start.y) != -1) {
            throw new IllegalArgumentException("시작점과 도착점이 기울기 -1 대각선을 이루지 않습니다.");
        }
    }
}
