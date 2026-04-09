package database.dto;

import domain.intersection.Intersection;

public record Moved(
        Intersection origin,
        Intersection destination
) {
}
