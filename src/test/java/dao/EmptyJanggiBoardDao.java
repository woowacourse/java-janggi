package dao;

import domain.janggiPiece.JanggiChessPiece;
import domain.position.JanggiPosition;

import java.util.HashMap;
import java.util.Map;

public class EmptyJanggiBoardDao implements BoardDao {
    @Override
    public Map<JanggiPosition, JanggiChessPiece> findAll() {
        return new HashMap<>();
    }

    @Override
    public JanggiChessPiece findByPosition(JanggiPosition position) {
        return null;
    }

    @Override
    public void save(JanggiPosition position, JanggiChessPiece piece) {

    }

    @Override
    public void delete(JanggiPosition position) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void updatePosition(JanggiPosition before, JanggiPosition after) {

    }
}
