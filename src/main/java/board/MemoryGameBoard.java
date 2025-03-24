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
        this.players = new ArrayList<>(List.of(
                new Player(makeGreenPieces(), GREEN),
                new Player(makeRedPieces(), RED)
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
                new Piece("S", new Point(1, 4), new SoldierMovement(RED.direction())),
                new Piece("S", new Point(3, 4), new SoldierMovement(RED.direction())),
                new Piece("S", new Point(5, 4), new SoldierMovement(RED.direction())),
                new Piece("S", new Point(7, 4), new SoldierMovement(RED.direction())),
                new Piece("S", new Point(9, 4), new SoldierMovement(RED.direction())));
    }

    private List<Piece> makeRedCannons() {
        return List.of(
                new Piece("N", new Point(2, 3), new CannonMovement()),
                new Piece("N", new Point(8, 3), new CannonMovement()));
    }

    private Piece makeRedGeneral() {
        return new Piece("G", new Point(5, 2), new GeneralMovement());
    }

    private List<Piece> makeRedGuards() {
        return List.of(
                new Piece("R", new Point(4, 1), new GuardMovement()),
                new Piece("R", new Point(6, 1), new GuardMovement())
        );
    }

    private List<Piece> makeRedHorses() {
        return List.of(
                new Piece("H", new Point(2, 1), new HorseMovement(RED.direction())),
                new Piece("H", new Point(8, 1), new HorseMovement(RED.direction()))
        );
    }

    private List<Piece> makeRedElephants() {
        return List.of(
                new Piece("E", new Point(3, 1), new ElephantMovement(RED.direction())),
                new Piece("E", new Point(7, 1), new ElephantMovement(RED.direction()))
        );
    }

    private List<Piece> makeRedChariots() {
        return List.of(
                new Piece("C", new Point(1, 1), new ChariotMovement()),
                new Piece("C", new Point(9, 1), new ChariotMovement())
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
                new Piece("c", new Point(1, 10), new ChariotMovement()),
                new Piece("c", new Point(9, 10), new ChariotMovement())
        );
    }

    private List<Piece> makeGreenElephants() {
        return List.of(
                new Piece("e", new Point(2, 10), new ElephantMovement(GREEN.direction())),
                new Piece("e", new Point(7, 10), new ElephantMovement(GREEN.direction()))
        );
    }

    private List<Piece> makeGreenHorses() {
        return List.of(
                new Piece("h", new Point(3, 10), new HorseMovement(GREEN.direction())),
                new Piece("h", new Point(8, 10), new HorseMovement(GREEN.direction()))
        );
    }

    private List<Piece> makeGreenGuards() {
        return List.of(
                new Piece("r", new Point(4, 10), new GuardMovement()),
                new Piece("r", new Point(6, 10), new GuardMovement())
        );
    }

    private List<Piece> makeGreenGeneral() {
        return List.of(
                new Piece("g", new Point(5, 9), new GeneralMovement())
        );
    }

    private List<Piece> makeGreenCannons() {
        return List.of(
                new Piece("n", new Point(2, 8), new CannonMovement()),
                new Piece("n", new Point(8, 8), new CannonMovement())
        );
    }

    private List<Piece> makeGreenSoldiers() {
        return List.of(
                new Piece("s", new Point(1, 7), new SoldierMovement(GREEN.direction())),
                new Piece("s", new Point(3, 7), new SoldierMovement(GREEN.direction())),
                new Piece("s", new Point(5, 7), new SoldierMovement(GREEN.direction())),
                new Piece("s", new Point(7, 7), new SoldierMovement(GREEN.direction())),
                new Piece("s", new Point(9, 7), new SoldierMovement(GREEN.direction()))
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
