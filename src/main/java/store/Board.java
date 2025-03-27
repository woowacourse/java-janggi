package store;

import static game.Team.GREEN;
import static game.Team.RED;

import game.Team;
import java.util.ArrayList;
import java.util.List;
import location.Position;
import piece.Cannon;
import piece.Chariot;
import piece.Elephant;
import piece.General;
import piece.GreenSoldier;
import piece.Guard;
import piece.Horse;
import piece.Piece;
import piece.RedSoldier;

public class Board {

    private final List<Player> players;

    public Board() {
        List<Piece> greenPieces = new ArrayList<>();
        List<Piece> redPieces = new ArrayList<>();
        greenPieces.add(new Chariot(new Position(1, 10)));
        greenPieces.add(new Chariot(new Position(9, 10)));

        greenPieces.add(new Elephant(new Position(2, 10)));
        greenPieces.add(new Elephant(new Position(7, 10)));

        greenPieces.add(new Horse(new Position(3, 10)));
        greenPieces.add(new Horse(new Position(8, 10)));

        greenPieces.add(new Guard(new Position(4, 10)));
        greenPieces.add(new Guard(new Position(6, 10)));

        greenPieces.add(new General(new Position(5, 9)));

        greenPieces.add(new Cannon(new Position(2, 8)));
        greenPieces.add(new Cannon(new Position(8, 8)));

        greenPieces.add(new GreenSoldier(new Position(1, 7)));
        greenPieces.add(new GreenSoldier( new Position(3, 7)));
        greenPieces.add(new GreenSoldier(new Position(5, 7)));
        greenPieces.add(new GreenSoldier(new Position(7, 7)));
        greenPieces.add(new GreenSoldier(new Position(9, 7)));

        // red
        redPieces.add(new Chariot(new Position(1, 1)));
        redPieces.add(new Chariot(new Position(9, 1)));

        redPieces.add(new Elephant(new Position(3, 1)));
        redPieces.add(new Elephant(new Position(7, 1)));

        redPieces.add(new Horse(new Position(2, 1)));
        redPieces.add(new Horse(new Position(8, 1)));

        redPieces.add(new Guard(new Position(4, 1)));
        redPieces.add(new Guard(new Position(6, 1)));

        redPieces.add(new General(new Position(5, 2)));

        redPieces.add(new Cannon(new Position(2, 3)));
        redPieces.add(new Cannon(new Position(8, 3)));

        redPieces.add(new RedSoldier(new Position(1, 4)));
        redPieces.add(new RedSoldier(new Position(3, 4)));
        redPieces.add(new RedSoldier(new Position(5, 4)));
        redPieces.add(new RedSoldier(new Position(7, 4)));
        redPieces.add(new RedSoldier(new Position(9, 4)));

        this.players = new ArrayList<>(List.of(
                new Player(new Pieces(greenPieces), GREEN),
                new Player(new Pieces(redPieces), RED)
        ));
    }

    public Player findPlayerBy(Team team) {
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
