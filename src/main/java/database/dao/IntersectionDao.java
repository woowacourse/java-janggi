package database.dao;

import database.dto.IntersectionDto;
import domain.intersection.Intersection;

import java.util.List;

public interface IntersectionDao {

    void saveAll(Long boardId, List<IntersectionDto> intersections);

    List<Intersection> readByBoardId(Long boardId);

    void update(Long boardId, IntersectionDto intersection);

}
