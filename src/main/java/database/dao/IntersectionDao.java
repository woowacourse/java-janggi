package database.dao;

import domain.intersection.Intersection;

import java.sql.Connection;
import java.util.List;

public interface IntersectionDao {

    void saveAll(Connection connection, Long boardId, List<Intersection> intersections);

    List<Intersection> readByBoardId(Connection connection, Long boardId);

    void update(Connection connection, Long boardId, Intersection intersection);

}
