package repository;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class TransactionTemplate<T> {

    public T execute() {
        try (Connection con = DBConnectionUtil.getConnection()) {
            con.setAutoCommit(false);

            try {
                T result = doInTransaction(con);
                con.commit();
                return result;
            } catch (Exception e) {
                con.rollback();
                throw new RuntimeException(e.getMessage());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    protected abstract T doInTransaction(Connection con);
}
