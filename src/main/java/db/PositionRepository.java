package db;

import domain.position.PointValue;
import domain.position.Position;
import java.sql.Connection;
import java.util.List;

public interface PositionRepository {

    void savePosition(final Connection connection, final Position position);

    List<Position> getPositions(final Connection connection);

    void updatePosition(final Connection connection, final PointValue fromPoint, final PointValue toPoint);

    void deletePosition(final Connection connection, final PointValue pointValue);

    void deleteAllPosition(final Connection connection);

    void updatePoint(final Connection connection, final PointValue fromPoint, final PointValue toPoint);
}
