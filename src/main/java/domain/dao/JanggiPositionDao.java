package domain.dao;

import domain.position.JanggiPosition;

public interface JanggiPositionDao {

    void addPosition(final JanggiPosition position);
    String findByPosition(JanggiPosition janggiPosition);
    JanggiPosition findPositionById(String positionId);
    void deleteAll();
}
