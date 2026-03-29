package dto;

import domain.intersection.Intersection;
import domain.point.Point;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class BoardStatusDTO {

    private final static int MIN_INDEX = 0;
    private final static int MAX_ROW = 10;
    private final static int MAX_FILE = 9;

    private final List<PointInfoDTO> boardStatus;

    public BoardStatusDTO(final Map<Point, Intersection> intersections) {
        List<PointInfoDTO> pointInfos = IntStream.range(0, MAX_ROW)
                .boxed()
                .flatMap(row -> IntStream.range(MIN_INDEX, MAX_FILE)
                        .mapToObj(file -> {
                            final Point point = new Point(row, file);
                            final String pointInfo = intersections.get(point).getChineseCharacter();
                            return new PointInfoDTO(pointInfo);
                        }))
                .toList();
        this.boardStatus = List.copyOf(pointInfos);
    }

    public List<PointInfoDTO> boardStatus() {
        return List.copyOf(boardStatus);
    }
}
