package board;

import static team.Team.GREEN;
import static team.Team.RED;

import java.util.ArrayList;
import java.util.List;
import piece.Chariot;
import piece.Elephant;
import piece.General;
import piece.GreenSoldier;
import piece.Guard;
import piece.Horse;
import direction.Point;
import piece.RedSoldier;
import piece.Cannon;
import piece.Piece;
import piece.Pieces;
import team.Player;
import team.Team;

public class MemoryGameBoard {

    private final List<Player> players;

    public MemoryGameBoard() {
        List<Piece> greenPieces = new ArrayList<>();
        List<Piece> redPieces = new ArrayList<>();
        greenPieces.add(new Chariot("c", new Point(1, 10)));
        greenPieces.add(new Chariot("c", new Point(9, 10)));

        greenPieces.add(new Elephant("e", new Point(2, 10)));
        greenPieces.add(new Elephant("e", new Point(7, 10)));

        greenPieces.add(new Horse("h", new Point(3, 10)));
        greenPieces.add(new Horse("h", new Point(8, 10)));

        greenPieces.add(new Guard("r", new Point(4, 10)));
        greenPieces.add(new Guard("r", new Point(6, 10)));

        greenPieces.add(new General("g", new Point(5, 9)));

        greenPieces.add(new Cannon("n", new Point(2, 8)));
        greenPieces.add(new Cannon("n", new Point(8, 8)));

        greenPieces.add(new GreenSoldier("s", new Point(1, 7)));
        greenPieces.add(new GreenSoldier("s", new Point(3, 7)));
        greenPieces.add(new GreenSoldier("s", new Point(5, 7)));
        greenPieces.add(new GreenSoldier("s", new Point(7, 7)));
        greenPieces.add(new GreenSoldier("s", new Point(9, 7)));

        // red
        redPieces.add(new Chariot("C", new Point(1, 1)));
        redPieces.add(new Chariot("C", new Point(9, 1)));

        redPieces.add(new Elephant("E", new Point(3, 1)));
        redPieces.add(new Elephant("E", new Point(7, 1)));

        redPieces.add(new Horse("H", new Point(2, 1)));
        redPieces.add(new Horse("H", new Point(8, 1)));

        redPieces.add(new Guard("R", new Point(4, 1)));
        redPieces.add(new Guard("R", new Point(6, 1)));

        redPieces.add(new General("G", new Point(5, 2)));

        redPieces.add(new Cannon("N", new Point(2, 3)));
        redPieces.add(new Cannon("N", new Point(8, 3)));

        redPieces.add(new RedSoldier("S", new Point(1, 4)));
        redPieces.add(new RedSoldier("S", new Point(3, 4)));
        redPieces.add(new RedSoldier("S", new Point(5, 4)));
        redPieces.add(new RedSoldier("S", new Point(7, 4)));
        redPieces.add(new RedSoldier("S", new Point(9, 4)));

        this.players = new ArrayList<>(List.of(
                new Player(new Pieces(greenPieces), GREEN),
                new Player(new Pieces(redPieces), RED)
        ));
    }

    public Player findPlayer(Team team) {
        return players.stream()
                .filter(player -> player.isTeam(team))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 팀이 존재하지 않습니다."));
    }

    public Pieces findAllPieces() {
        List<Piece> pieces = new ArrayList<>();

        for (Player player : players) {
            pieces.addAll(player.getPieces());
        }

        return new Pieces(pieces);
    }
}
