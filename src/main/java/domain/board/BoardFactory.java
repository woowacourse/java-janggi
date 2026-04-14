package domain.board;

import domain.vo.Position;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class BoardFactory {

    private BoardFactory() {}

    public static Board setUp(Formation hanFormation, Formation chuFormation) {
        Map<Position, Piece> board = new TreeMap<>(Comparator
                .comparingInt(Position::getRow).reversed()
                .thenComparingInt(Position::getCol));

        board.put(Position.of(0, 0), Piece.of(Team.CHU, Type.CHARIOT, Type.CHARIOT.createStrategy()));
        board.put(Position.of(0, 1), Piece.of(Team.CHU, Type.ELEPHANT, Type.ELEPHANT.createStrategy()));
        board.put(Position.of(0, 2), Piece.of(Team.CHU, Type.HORSE, Type.HORSE.createStrategy()));
        board.put(Position.of(0, 3), Piece.of(Team.CHU, Type.GUARD, Type.GUARD.createStrategy()));
        board.put(Position.of(0, 5), Piece.of(Team.CHU, Type.GUARD, Type.GUARD.createStrategy()));
        board.put(Position.of(0, 6), Piece.of(Team.CHU, Type.ELEPHANT, Type.ELEPHANT.createStrategy()));
        board.put(Position.of(0, 7), Piece.of(Team.CHU, Type.HORSE, Type.HORSE.createStrategy()));
        board.put(Position.of(0, 8), Piece.of(Team.CHU, Type.CHARIOT, Type.CHARIOT.createStrategy()));
        board.put(Position.of(1, 4), Piece.of(Team.CHU, Type.GENERAL, Type.GENERAL.createStrategy()));
        board.put(Position.of(2, 1), Piece.of(Team.CHU, Type.CANNON, Type.CANNON.createStrategy()));
        board.put(Position.of(2, 7), Piece.of(Team.CHU, Type.CANNON, Type.CANNON.createStrategy()));
        board.put(Position.of(3, 0), Piece.of(Team.CHU, Type.SOLDIER, Type.SOLDIER.createStrategy()));
        board.put(Position.of(3, 2), Piece.of(Team.CHU, Type.SOLDIER, Type.SOLDIER.createStrategy()));
        board.put(Position.of(3, 4), Piece.of(Team.CHU, Type.SOLDIER, Type.SOLDIER.createStrategy()));
        board.put(Position.of(3, 6), Piece.of(Team.CHU, Type.SOLDIER, Type.SOLDIER.createStrategy()));
        board.put(Position.of(3, 8), Piece.of(Team.CHU, Type.SOLDIER, Type.SOLDIER.createStrategy()));

        board.put(Position.of(9, 0), Piece.of(Team.HAN, Type.CHARIOT, Type.CHARIOT.createStrategy()));
        board.put(Position.of(9, 1), Piece.of(Team.HAN, Type.ELEPHANT, Type.ELEPHANT.createStrategy()));
        board.put(Position.of(9, 2), Piece.of(Team.HAN, Type.HORSE, Type.HORSE.createStrategy()));
        board.put(Position.of(9, 3), Piece.of(Team.HAN, Type.GUARD, Type.GUARD.createStrategy()));
        board.put(Position.of(9, 5), Piece.of(Team.HAN, Type.GUARD, Type.GUARD.createStrategy()));
        board.put(Position.of(9, 6), Piece.of(Team.HAN, Type.ELEPHANT, Type.ELEPHANT.createStrategy()));
        board.put(Position.of(9, 7), Piece.of(Team.HAN, Type.HORSE, Type.HORSE.createStrategy()));
        board.put(Position.of(9, 8), Piece.of(Team.HAN, Type.CHARIOT, Type.CHARIOT.createStrategy()));
        board.put(Position.of(8, 4), Piece.of(Team.HAN, Type.GENERAL, Type.GENERAL.createStrategy()));
        board.put(Position.of(7, 1), Piece.of(Team.HAN, Type.CANNON, Type.CANNON.createStrategy()));
        board.put(Position.of(7, 7), Piece.of(Team.HAN, Type.CANNON, Type.CANNON.createStrategy()));
        board.put(Position.of(6, 0), Piece.of(Team.HAN, Type.SOLDIER, Type.SOLDIER.createStrategy()));
        board.put(Position.of(6, 2), Piece.of(Team.HAN, Type.SOLDIER, Type.SOLDIER.createStrategy()));
        board.put(Position.of(6, 4), Piece.of(Team.HAN, Type.SOLDIER, Type.SOLDIER.createStrategy()));
        board.put(Position.of(6, 6), Piece.of(Team.HAN, Type.SOLDIER, Type.SOLDIER.createStrategy()));
        board.put(Position.of(6, 8), Piece.of(Team.HAN, Type.SOLDIER, Type.SOLDIER.createStrategy()));

        formatElephantAndHorse(chuFormation, Team.CHU, 0, board);
        formatElephantAndHorse(hanFormation, Team.HAN, 9, board);

        return Board.of(board);
    }

    private static void formatElephantAndHorse(Formation formation, Team team, int row, Map<Position, Piece> board) {
        if (formation == Formation.LEFT_ELEPHANT_RIGHT_ELEPHANT) {  // 상마상마
            board.put(Position.of(row, 1), Piece.of(team, Type.ELEPHANT, Type.ELEPHANT.createStrategy()));
            board.put(Position.of(row, 2), Piece.of(team, Type.HORSE, Type.HORSE.createStrategy()));

            board.put(Position.of(row, 6), Piece.of(team, Type.ELEPHANT, Type.ELEPHANT.createStrategy()));
            board.put(Position.of(row, 7), Piece.of(team, Type.HORSE, Type.HORSE.createStrategy()));
        }
        if (formation == Formation.LEFT_HORSE_RIGHT_HORSE) {      // 마상마상
            board.put(Position.of(row, 1), Piece.of(team, Type.HORSE, Type.HORSE.createStrategy()));
            board.put(Position.of(row, 2), Piece.of(team, Type.ELEPHANT, Type.ELEPHANT.createStrategy()));

            board.put(Position.of(row, 6), Piece.of(team, Type.HORSE, Type.HORSE.createStrategy()));
            board.put(Position.of(row, 7), Piece.of(team, Type.ELEPHANT, Type.ELEPHANT.createStrategy()));
        }
        if (formation == Formation.LEFT_ELEPHANT_RIGHT_HORSE) {    // 상마마상
            board.put(Position.of(row, 1), Piece.of(team, Type.ELEPHANT, Type.ELEPHANT.createStrategy()));
            board.put(Position.of(row, 2), Piece.of(team, Type.HORSE, Type.HORSE.createStrategy()));

            board.put(Position.of(row, 6), Piece.of(team, Type.HORSE, Type.HORSE.createStrategy()));
            board.put(Position.of(row, 7), Piece.of(team, Type.ELEPHANT, Type.ELEPHANT.createStrategy()));
        }
        if (formation == Formation.LEFT_HORSE_RIGHT_ELEPHANT) {    // 마상상마
            board.put(Position.of(row, 1), Piece.of(team, Type.HORSE, Type.HORSE.createStrategy()));
            board.put(Position.of(row, 2), Piece.of(team, Type.ELEPHANT, Type.ELEPHANT.createStrategy()));

            board.put(Position.of(row, 6), Piece.of(team, Type.ELEPHANT, Type.ELEPHANT.createStrategy()));
            board.put(Position.of(row, 7), Piece.of(team, Type.HORSE, Type.HORSE.createStrategy()));
        }
    }
}
