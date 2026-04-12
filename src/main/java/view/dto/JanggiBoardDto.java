package view.dto;

import domain.board.JanggiBoard;
import domain.intersection.Intersection;
import domain.point.Point;

import java.util.HashMap;
import java.util.Map;

public record JanggiBoardDto(
        Map<Point, PieceViewDto> boardViews
) {

    public static JanggiBoardDto from(JanggiBoard janggiBoard) {
        Map<Point, Intersection> board = janggiBoard.getJanggiBoard();
        Map<Point, PieceViewDto> boardViews = new HashMap<>();
        for (Point point : board.keySet()) {
            Intersection intersection = board.get(point);
            boardViews.put(point, PieceViewDto.from(intersection.getPiece()));
        }
        return new JanggiBoardDto(boardViews);
    }

}
