package domain.board.dto;

import domain.board.JanggiBoard;
import domain.intersection.Intersection;
import domain.point.Point;
import view.PieceView;
import java.util.HashMap;
import java.util.Map;

public record JanggiBoardDto(
        Map<Point, PieceView> boardViews
) {

    public static JanggiBoardDto from(JanggiBoard janggiBoard) {
        Map<Point, Intersection> board = janggiBoard.getJanggiBoard();
        Map<Point, PieceView> boardViews = new HashMap<>();
        for (Point point : board.keySet()) {
            Intersection intersection = board.get(point);
            boardViews.put(point, PieceView.valueOf(intersection.readPiece()));
        }
        return new JanggiBoardDto(boardViews);
    }

}
