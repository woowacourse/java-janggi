package database.dao;

import database.dto.IntersectionDto;
import domain.intersection.Intersection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IntersectionDao {

    void saveAll(Connection connection, Long boardId, List<IntersectionDto> intersections);

    List<Intersection> readByBoardId(Connection connection, Long boardId);

    void update(Long boardId, IntersectionDto intersection) throws SQLException;

}
