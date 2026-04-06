package database.dao;

import java.sql.Connection;

public interface BoardDao {

    // TODO JanggiBoard를 받도록 수정.
    Long save(Connection connection);

}
