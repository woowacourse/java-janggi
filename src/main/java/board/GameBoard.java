package board;

import piece.Pieces;
import team.Player;
import team.Team;

public interface GameBoard {

    Player findPlayer(Team team);
    Pieces findAllPieces();
}
