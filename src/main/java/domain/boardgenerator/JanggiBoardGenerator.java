package domain.boardgenerator;

import domain.Position;
import domain.Team;
import domain.movestrategy.BasicFixedMoveStrategy;
import domain.movestrategy.BasicRangeMoveStrategy;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.King;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.player.Player;
import domain.player.Players;
import java.util.HashMap;
import java.util.Map;

public class JanggiBoardGenerator implements BoardGenerator {

    private final Players players;

    public JanggiBoardGenerator(Players players) {
        this.players = players;
    }

    @Override
    public Map<Position, Piece> generateBoard() {
        Map<Position, Piece> board = new HashMap<>();
        Player redPlayer = players.getPlayerByTeam(Team.RED);
        Player bluePlayer = players.getPlayerByTeam(Team.BLUE);

        board.put(new Position(1, 1), new Chariot(redPlayer, new BasicRangeMoveStrategy(), 13));
        board.put(new Position(1, 2), new Horse(redPlayer, 5));
        board.put(new Position(1, 3), new Elephant(redPlayer, 3));
        board.put(new Position(1, 4), new Guard(redPlayer, 3));
        board.put(new Position(1, 6), new Guard(redPlayer, 3));
        board.put(new Position(1, 7), new Elephant(redPlayer, 3));
        board.put(new Position(1, 8), new Horse(redPlayer, 5));
        board.put(new Position(1, 9), new Chariot(redPlayer, new BasicRangeMoveStrategy(), 13));
        board.put(new Position(2, 5), new King(redPlayer, 0));
        board.put(new Position(3, 2), new Cannon(redPlayer, new BasicRangeMoveStrategy(), 7));
        board.put(new Position(3, 8), new Cannon(redPlayer, new BasicRangeMoveStrategy(), 7));
        board.put(new Position(4, 1), new Pawn(redPlayer, new BasicFixedMoveStrategy(), 2));
        board.put(new Position(4, 3), new Pawn(redPlayer, new BasicFixedMoveStrategy(), 2));
        board.put(new Position(4, 5), new Pawn(redPlayer, new BasicFixedMoveStrategy(), 2));
        board.put(new Position(4, 7), new Pawn(redPlayer, new BasicFixedMoveStrategy(), 2));
        board.put(new Position(4, 9), new Pawn(redPlayer, new BasicFixedMoveStrategy(), 2));
        board.put(new Position(7, 1), new Pawn(bluePlayer, new BasicFixedMoveStrategy(), 2));
        board.put(new Position(7, 3), new Pawn(bluePlayer, new BasicFixedMoveStrategy(), 2));
        board.put(new Position(7, 5), new Pawn(bluePlayer, new BasicFixedMoveStrategy(), 2));
        board.put(new Position(7, 7), new Pawn(bluePlayer, new BasicFixedMoveStrategy(), 2));
        board.put(new Position(7, 9), new Pawn(bluePlayer, new BasicFixedMoveStrategy(), 2));
        board.put(new Position(8, 2), new Cannon(bluePlayer, new BasicRangeMoveStrategy(), 7));
        board.put(new Position(8, 8), new Cannon(bluePlayer, new BasicRangeMoveStrategy(), 7));
        board.put(new Position(9, 5), new King(bluePlayer, 0));
        board.put(new Position(10, 1), new Chariot(bluePlayer, new BasicRangeMoveStrategy(), 13));
        board.put(new Position(10, 2), new Horse(bluePlayer, 5));
        board.put(new Position(10, 3), new Elephant(bluePlayer, 3));
        board.put(new Position(10, 4), new Guard(bluePlayer, 3));
        board.put(new Position(10, 6), new Guard(bluePlayer, 3));
        board.put(new Position(10, 7), new Elephant(bluePlayer, 3));
        board.put(new Position(10, 8), new Horse(bluePlayer, 5));
        board.put(new Position(10, 9), new Chariot(bluePlayer, new BasicRangeMoveStrategy(), 13));
        return board;
    }
}
