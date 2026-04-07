package database.mapper;

import domain.intersection.Intersection;

import java.sql.ResultSet;
import java.sql.SQLException;

// THINK 람다
public class IntersectionRowMapper implements RowMapper<Intersection> {

    @Override
    public Intersection map(ResultSet resultSet) throws SQLException {
        int y = resultSet.getInt("y");
        int x = resultSet.getInt("x");

        String pieceTypeName = resultSet.getString("piece_type");
        String teamName = resultSet.getString("team");
        String intersectionTypeName = resultSet.getString("intersection_type");

        return JanggiBoardMapper.toIntersection(y, x, pieceTypeName, teamName, intersectionTypeName);
    }

}
