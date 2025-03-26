package repository;

import domain.Coordinate;
import domain.Piece;
import domain.Team;
import java.util.Set;

public interface JanggiRepository {

    void save(Piece piece);

    void update(Coordinate from, Coordinate to);

    Set<Piece> findAll();

    void deleteByCoordinate(Coordinate coordinate);

    void clear();

    void setTurn(Team team);

    Team getTurn();
}
