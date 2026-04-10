package janggi.domain.board;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceFactory;
import janggi.domain.piece.Team;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BoardInitializer {

    private static final Map<Integer, List<List<Integer>>> HAN_ATTIRE = Map.of(
            1, List.of(List.of(2, 7), List.of(2, 8)),
            2, List.of(List.of(3, 8), List.of(2, 7)),
            3, List.of(List.of(3, 7), List.of(2, 8)),
            4, List.of(List.of(2, 8), List.of(3, 7))
    );

    private static final Map<Integer, List<List<Integer>>> CHO_ATTIRE = Map.of(
            1, List.of(List.of(3, 8), List.of(2, 7)),
            2, List.of(List.of(2, 7), List.of(3, 8)),
            3, List.of(List.of(3, 7), List.of(2, 8)),
            4, List.of(List.of(2, 8), List.of(3, 7))
    );

    public static Board initializeBoard(final int hanChoice, final int choChoice) {
        Map<Position, Piece> board = new LinkedHashMap<>();
        initializeTeam(board, Team.HAN,
                1, 4, 3, 2,
                BoardDirection.UP, HAN_ATTIRE.get(hanChoice));
        initializeTeam(board, Team.CHO,
                10, 7, 8, 9,
                BoardDirection.DOWN, CHO_ATTIRE.get(choChoice));
        return new Board(board);
    }

    private static void initializeTeam(Map<Position, Piece> board, Team team,
                                       int baseRow, int soldierRow, int cannonRow, int generalRow,
                                       BoardDirection direction, List<List<Integer>> attire) {
        initializeChariots(board, team, baseRow);
        initializeGuards(board, team, baseRow);
        initializeGeneral(board, team, generalRow);
        initializeCannons(board, team, cannonRow);
        initializeSoldiers(board, team, soldierRow, direction);
        initializeAttire(board, team, baseRow, attire);
    }

    private static void initializeChariots(Map<Position, Piece> board, Team team, int row) {
        board.put(new Position(1, row), PieceFactory.createChariot(team));
        board.put(new Position(9, row), PieceFactory.createChariot(team));
    }

    private static void initializeGuards(Map<Position, Piece> board, Team team, int row) {
        board.put(new Position(4, row), PieceFactory.createGuard(team));
        board.put(new Position(6, row), PieceFactory.createGuard(team));
    }

    private static void initializeGeneral(Map<Position, Piece> board, Team team, int row) {
        board.put(new Position(5, row), PieceFactory.createGeneral(team));
    }

    private static void initializeCannons(Map<Position, Piece> board, Team team, int row) {
        board.put(new Position(2, row), PieceFactory.createCannon(team));
        board.put(new Position(8, row), PieceFactory.createCannon(team));
    }

    private static void initializeSoldiers(Map<Position, Piece> board, Team team,
                                           int row, BoardDirection direction) {
        for (int x = 1; x <= 9; x += 2) {
            board.put(new Position(x, row), PieceFactory.createSolider(team, direction));
        }
    }

    private static void initializeAttire(Map<Position, Piece> board, Team team,
                                         int baseRow, List<List<Integer>> formation) {
        List<Integer> elephant = formation.get(0);
        List<Integer> horse = formation.get(1);
        board.put(new Position(elephant.get(0), baseRow), PieceFactory.createElephant(team));
        board.put(new Position(elephant.get(1), baseRow), PieceFactory.createElephant(team));
        board.put(new Position(horse.get(0), baseRow), PieceFactory.createHorse(team));
        board.put(new Position(horse.get(1), baseRow), PieceFactory.createHorse(team));
    }
}
