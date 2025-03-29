package dao;

import domain.janggiPiece.JanggiChessPiece;
import domain.position.JanggiPosition;

import java.util.Map;

public interface BoardDao {
    Map<JanggiPosition, JanggiChessPiece> findAll();

    JanggiChessPiece findByPosition(JanggiPosition position);

    void save(JanggiPosition position, JanggiChessPiece piece);

    void delete(JanggiPosition position);

    void deleteAll();

    void updatePosition(JanggiPosition before, JanggiPosition after);
}
