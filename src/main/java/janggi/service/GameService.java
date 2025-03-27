package janggi.service;

import janggi.domain.Coordinate;
import janggi.domain.Piece;
import janggi.domain.Team;
import java.util.Map;

public interface GameService {

    void movePiece(final Coordinate departure, final Coordinate arrival);

    boolean isGameOver();

    void clearGame();

    Team higherScoreTeam();

    Team currentTurn();

    Map<Coordinate, Piece> allPieces();

    Map<Team, Double> scoreTeams();
}
