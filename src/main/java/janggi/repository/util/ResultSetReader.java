package janggi.repository.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultSetReader {

    public interface ResultSetMapper<T> {
        T map(ResultSet rs) throws SQLException;
    }

    public static <T> List<T> toList(final ResultSet resultSet, final ResultSetMapper<T> mapper) throws SQLException {
        final List<T> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(mapper.map(resultSet));
        }
        return result;
    }
}
