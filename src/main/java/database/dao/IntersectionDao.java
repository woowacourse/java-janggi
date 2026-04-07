package database.dao;

import domain.intersection.Intersection;

import java.sql.Connection;
import java.util.List;

public interface IntersectionDao {

    void saveAllIntersection(Connection connection, Long boardId, List<Intersection> intersections);

    List<Intersection> readIntersectionByBoardId(Connection connection, Long boardId);

    void update(Connection connection, Long boardId, Intersection intersection);

}
