package board;

import piece.Pieces;
import team.Player;
import team.Team;

public interface Board {

    Player findPlayer(Team team);
    Pieces findAll();
}
