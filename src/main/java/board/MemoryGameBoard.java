package board;

import java.util.ArrayList;
import java.util.List;
import piece.Chariot;
import piece.General;
import piece.Elephant;
import piece.Guard;
import piece.Horse;
import direction.Point;
import piece.Soldier;
import piece.Cannon;
import piece.Piece;
import piece.Pieces;
import team.Player;
import team.Team;

public class MemoryGameBoard implements GameBoard {

    private final List<Player> players;

    public MemoryGameBoard() {
        this.players = new ArrayList<>(List.of(
                new Player(makeGreenPieces(), Team.CHO),
                new Player(makeRedPieces(), Team.HAN)
        ));
    }

    public List<Piece> makeRedPieces() {
        List<Piece> redPieces = new ArrayList<>();
        redPieces.addAll(makeRedChariots());
        redPieces.addAll(makeRedElephants());
        redPieces.addAll(makeRedHorses());
        redPieces.addAll(makeRedGuards());
        redPieces.add(makeRedGeneral());
        redPieces.addAll(makeRedCannons());
        redPieces.addAll(makeRedSoldiers());

        return redPieces;
    }

    private List<Piece> makeRedSoldiers() {
        return List.of(
                new Soldier("S", new Point(1, 4), Team.HAN),
                new Soldier("S", new Point(3, 4), Team.HAN),
                new Soldier("S", new Point(5, 4), Team.HAN),
                new Soldier("S", new Point(7, 4), Team.HAN),
                new Soldier("S", new Point(9, 4), Team.HAN)
        );
    }

    private List<Piece> makeRedCannons() {
        return List.of(
                new Cannon("N", new Point(2, 3)),
                new Cannon("N", new Point(8, 3))
        );
    }

    private Piece makeRedGeneral() {
        return new General("G", new Point(5, 2));
    }

    private List<Piece> makeRedGuards() {
        return List.of(
                new Guard("R", new Point(4, 1)),
                new Guard("R", new Point(6, 1))
        );
    }

    private List<Piece> makeRedHorses() {
        return List.of(
                new Horse("H", new Point(2, 1)),
                new Horse("H", new Point(8, 1))
        );
    }

    private List<Piece> makeRedElephants() {
        return List.of(
                new Elephant("E", new Point(3, 1)),
                new Elephant("E", new Point(7, 1))
        );
    }

    private List<Piece> makeRedChariots() {
        return List.of(
                new Chariot("C", new Point(1, 1)),
                new Chariot("C", new Point(9, 1))
        );
    }

    public List<Piece> makeGreenPieces() {
        List<Piece> greenPieces = new ArrayList<>();

        greenPieces.addAll(makeGreenChariots());
        greenPieces.addAll(makeGreenElephants());
        greenPieces.addAll(makeGreenHorses());
        greenPieces.addAll(makeGreenGuards());
        greenPieces.addAll(makeGreenGeneral());
        greenPieces.addAll(makeGreenCannons());
        greenPieces.addAll(makeGreenSoldiers());

        return greenPieces;
    }

    private List<Piece> makeGreenChariots() {
        return List.of(
                new Chariot("c", new Point(1, 10)),
                new Chariot("c", new Point(9, 10))
        );
    }

    private List<Piece> makeGreenElephants() {
        return List.of(
                new Elephant("e", new Point(2, 10)),
                new Elephant("e", new Point(7, 10))
        );
    }

    private List<Piece> makeGreenHorses() {
        return List.of(
                new Horse("h", new Point(3, 10)),
                new Horse("h", new Point(8, 10))
        );
    }

    private List<Piece> makeGreenGuards() {
        return List.of(
                new Guard("r", new Point(4, 10)),
                new Guard("r", new Point(6, 10))
        );
    }

    private List<Piece> makeGreenGeneral() {
        return List.of(
                new General("g", new Point(5, 9))
        );
    }

    private List<Piece> makeGreenCannons() {
        return List.of(
                new Cannon("n", new Point(2, 8)),
                new Cannon("n", new Point(8, 8))
        );
    }

    private List<Piece> makeGreenSoldiers() {
        return List.of(
                new Soldier("s", new Point(1, 7), Team.CHO),
                new Soldier("s", new Point(3, 7), Team.CHO),
                new Soldier("s", new Point(5, 7), Team.CHO),
                new Soldier("s", new Point(7, 7), Team.CHO),
                new Soldier("s", new Point(9, 7), Team.CHO)
        );
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
