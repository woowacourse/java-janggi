package database.dao;

import domain.intersection.Intersection;

import java.util.List;

public interface IntersectionDao {

    void saveAll(Long boardId, List<Intersection> intersections);

    List<Intersection> selectIntersections(Long boardId);

    void update(Long boardId, Intersection intersection);

}
