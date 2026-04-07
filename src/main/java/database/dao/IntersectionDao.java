package database.dao;

import database.dto.IntersectionDto;
import domain.intersection.Intersection;

import java.sql.SQLException;
import java.util.List;

public interface IntersectionDao {

    void saveAll(Long boardId, List<IntersectionDto> intersections) throws SQLException;

    List<Intersection> readByBoardId(Long boardId) throws SQLException;

    void update(Long boardId, IntersectionDto intersection) throws SQLException;

}
