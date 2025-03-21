package board;

import static team.Team.*;

import java.util.ArrayList;
import java.util.List;
import move.ChariotMovement;
import move.GeneralMovement;
import move.ElephantMovement;
import move.GuardMovement;
import move.HorseMovement;
import direction.Point;
import move.SoldierMovement;
import move.CannonMovement;
import piece.Piece;
import piece.Pieces;
import team.Player;
import team.Team;

public class MemoryGameBoard implements GameBoard {

    private final List<Player> players;

    public MemoryGameBoard() {
        List<Piece> greenPieces = new ArrayList<>();
        List<Piece> redPieces = new ArrayList<>();
        greenPieces.add(new Piece("c", new Point(1, 10), new ChariotMovement()));
        greenPieces.add(new Piece("c", new Point(9, 10), new ChariotMovement()));

        greenPieces.add(new Piece("e", new Point(2, 10), new ElephantMovement(GREEN.direction())));
        greenPieces.add(new Piece("e", new Point(7, 10), new ElephantMovement(GREEN.direction())));

        greenPieces.add(new Piece("h", new Point(3, 10), new HorseMovement(GREEN.direction())));
        greenPieces.add(new Piece("h", new Point(8, 10), new HorseMovement(GREEN.direction())));

        greenPieces.add(new Piece("r", new Point(4, 10), new GuardMovement()));
        greenPieces.add(new Piece("r", new Point(6, 10), new GuardMovement()));

        greenPieces.add(new Piece("g", new Point(5, 9), new GeneralMovement()));

        greenPieces.add(new Piece("n", new Point(2, 8), new CannonMovement()));
        greenPieces.add(new Piece("n", new Point(8, 8), new CannonMovement()));

        greenPieces.add(new Piece("s", new Point(1, 7), new SoldierMovement(GREEN.direction())));
        greenPieces.add(new Piece("s", new Point(3, 7), new SoldierMovement(GREEN.direction())));
        greenPieces.add(new Piece("s", new Point(5, 7), new SoldierMovement(GREEN.direction())));
        greenPieces.add(new Piece("s", new Point(7, 7), new SoldierMovement(GREEN.direction())));
        greenPieces.add(new Piece("s", new Point(9, 7), new SoldierMovement(GREEN.direction())));

        // red
        redPieces.add(new Piece("C", new Point(1, 1), new ChariotMovement()));
        redPieces.add(new Piece("C", new Point(9, 1), new ChariotMovement()));

        redPieces.add(new Piece("E", new Point(3, 1), new ElephantMovement(RED.direction())));
        redPieces.add(new Piece("E", new Point(7, 1), new ElephantMovement(RED.direction())));

        redPieces.add(new Piece("H", new Point(2, 1), new HorseMovement(RED.direction())));
        redPieces.add(new Piece("H", new Point(8, 1), new HorseMovement(RED.direction())));

        redPieces.add(new Piece("R", new Point(4, 1), new GuardMovement()));
        redPieces.add(new Piece("R", new Point(6, 1), new GuardMovement()));

        redPieces.add(new Piece("G", new Point(5, 2), new GeneralMovement()));

        redPieces.add(new Piece("N", new Point(2, 3), new CannonMovement()));
        redPieces.add(new Piece("N", new Point(8, 3), new CannonMovement()));

        redPieces.add(new Piece("S", new Point(1, 4), new SoldierMovement(RED.direction())));
        redPieces.add(new Piece("S", new Point(3, 4), new SoldierMovement(RED.direction())));
        redPieces.add(new Piece("S", new Point(5, 4), new SoldierMovement(RED.direction())));
        redPieces.add(new Piece("S", new Point(7, 4), new SoldierMovement(RED.direction())));
        redPieces.add(new Piece("S", new Point(9, 4), new SoldierMovement(RED.direction())));

        this.players = new ArrayList<>(List.of(
                new Player(greenPieces, GREEN),
                new Player(redPieces, RED)
        ));
    }

    @Override
    public Player findPlayer(Team team) {
        return players.stream()
                .filter(player -> player.isTeam(team))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 찾으려는 팀이 없습니다."));
    }

    @Override
    public Pieces findAllPieces() {
        List<Piece> pieces = new ArrayList<>();

        for (Player player : players) {
            pieces.addAll(player.getPieces());
        }

        return new Pieces(pieces);
    }
}
