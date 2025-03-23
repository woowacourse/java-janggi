package janggi.position;

import janggi.board.Board;
import java.util.List;

public class Route {
    private final List<Direction> directions;

    public Route(List<Direction> directions) {
        this.directions = directions;
    }

    public boolean canMove(Position position) {
        int row = rowSum();
        int column = columnSum();

        System.out.println();
        return position.canMove(row, column);
    }

    private int columnSum() {
        return directions.stream()
                .mapToInt(Direction::column)
                .sum();
    }

    private int rowSum() {
        return directions.stream()
                .mapToInt(Direction::row)
                .sum();
    }

    public Position validateInterrupt(Board board, Position position) {
        int moveCount;
        for (moveCount = 0; moveCount < directions.size()-1; moveCount++) {
            position = position.move(directions.get(moveCount));
            hasPiece(board, position);
        }

        return position.move(directions.get(moveCount));
    }

    private static void hasPiece(Board board, Position position) {
        if (board.hasPieceOn(position)) {
            throw new IllegalArgumentException("[ERROR] 이동 경로에 다른 기물이 존재합니다.");
        }
    }
}

