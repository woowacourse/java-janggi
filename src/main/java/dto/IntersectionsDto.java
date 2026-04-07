package dto;

import java.util.List;

public class IntersectionsDto {
    private final List<IntersectionDto> intersections;

    public IntersectionsDto(List<IntersectionDto> intersections) {
        this.intersections = List.copyOf(intersections);
    }

    public List<IntersectionDto> getIntersections() {
        return List.copyOf(intersections);
    }
}
