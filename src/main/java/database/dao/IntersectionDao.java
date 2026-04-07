package database.dao;

import database.dto.IntersectionDto;
import domain.intersection.Intersection;

import java.sql.Connection;
import java.util.List;

public interface IntersectionDao {

    void saveAll(Connection connection, Long boardId, List<IntersectionDto> intersections);

    List<Intersection> readByBoardId(Connection connection, Long boardId);

    void update(Connection connection, Long boardId, IntersectionDto intersection);

}
