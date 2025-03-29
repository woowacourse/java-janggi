package dao;

import domain.janggiPiece.JanggiChessPiece;
import domain.position.JanggiPosition;

public interface BoardDao {
    JanggiChessPiece findByPosition(JanggiPosition position);

    void save(JanggiPosition position, JanggiChessPiece piece);

    void delete(JanggiPosition position);

    void updatePosition(JanggiPosition before, JanggiPosition after);
}
