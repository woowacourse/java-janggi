package board;

import direction.Point;
import piece.Pieces;
import team.Player;
import team.Team;

public interface GameBoard {

    void loadGame();

    Team loadCurrentTurn();

    boolean isGameExist();

    void startNewGame(Team turn);

    void saveGame(Point start, Point end, Team turn);

    Player findPlayer(Team team);

    Pieces findAllPieces();

    Pieces findTeamPieces(Team team);
}
