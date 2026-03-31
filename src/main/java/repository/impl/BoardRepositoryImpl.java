package repository.impl;

import config.H2ConnectionManager;
import domain.place.Place;
import domain.position.Position;
import java.util.HashMap;
import java.util.Map;
import repository.BoardRepository;

public class BoardRepositoryImpl implements BoardRepository {

    private final H2ConnectionManager connectionManager;

    public BoardRepositoryImpl(H2ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public void saveBoard(Map<Position, Place> board) {
    }

    @Override
    public Map<Position, Place> findBoard() {
        Map<Position, Place> board = new HashMap<>();
        return board;
    }
}
