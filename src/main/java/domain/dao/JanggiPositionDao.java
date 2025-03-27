package domain.dao;

import domain.position.JanggiPosition;

public interface JanggiPositionDao {

    void addPosition(final JanggiPosition position);
    int findByPosition(JanggiPosition janggiPosition);
    JanggiPosition findPositionById(int positionId);
    void deleteAll();
}
