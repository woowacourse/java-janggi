package domain.board;

import static domain.piece.PieceType.MA;
import static domain.piece.PieceType.SANG;

import domain.piece.Cha;
import domain.piece.Jang;
import domain.piece.Jol;
import domain.piece.Ma;
import domain.piece.None;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Po;
import domain.piece.Sa;
import domain.piece.Sang;
import domain.player.Team;
import domain.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardFactory {

    public static Board createWithFormation(Formation choFormation, Formation hanFormation) {
        Map<Position, Piece> board = createBasicBoard();
        putChoFormation(board, choFormation);
        putHanFormation(board, hanFormation);
        return new Board(board);
    }

    private static void putChoFormation(Map<Position, Piece> board, Formation choFormation) {
        List<PieceType> formation = choFormation.getFormation();
        List<Integer> xPositions = List.of(1, 2, 6, 7);

        for (int i = 0; i < formation.size(); i++) {
            PieceType type = formation.get(i);
            int x = xPositions.get(i);

            if (type == MA) {
                board.put(new Position(x, 0), new Ma(Team.CHO));
            } else if (type == SANG) {
                board.put(new Position(x, 0), new Sang(Team.CHO));
            }
        }
    }

    private static void putHanFormation(Map<Position, Piece> board, Formation hanFormation) {
        List<PieceType> formation = hanFormation.getFormation();
        List<Integer> xPositions = List.of(1, 2, 6, 7);

        for (int i = 0; i < formation.size(); i++) {
            PieceType type = formation.get(i);
            int x = xPositions.get(i);

            if (type == MA) {
                board.put(new Position(x, 9), new Ma(Team.HAN));
            } else if (type == SANG) {
                board.put(new Position(x, 9), new Sang(Team.HAN));
            }
        }
    }

    private static Map<Position, Piece> createBasicBoard() {
        Map<Position, Piece> board = new HashMap<>();

        for (int y = 0; y <= 9; y++) {
            for (int x = 0; x <= 8; x++) {
                board.put(new Position(x, y), new None());
            }
        }

        // CHO 기물 (위쪽: y=0~3)
        board.put(new Position(0, 0), new Cha(Team.CHO));
        board.put(new Position(1, 0), new Sang(Team.CHO));
        board.put(new Position(2, 0), new Ma(Team.CHO));
        board.put(new Position(3, 0), new Sa(Team.CHO));
        board.put(new Position(4, 0), new None());
        board.put(new Position(5, 0), new Sa(Team.CHO));
        board.put(new Position(6, 0), new Ma(Team.CHO));
        board.put(new Position(7, 0), new Sang(Team.CHO));
        board.put(new Position(8, 0), new Cha(Team.CHO));

        board.put(new Position(4, 1), new Jang(Team.CHO));

        board.put(new Position(1, 2), new Po(Team.CHO));
        board.put(new Position(7, 2), new Po(Team.CHO));

        for (int x = 0; x <= 8; x += 2) {
            board.put(new Position(x, 3), new Jol(Team.CHO));
        }

        // HAN 기물 (아래쪽: y=6~9)
        board.put(new Position(0, 9), new Cha(Team.HAN));
        board.put(new Position(1, 9), new Sang(Team.HAN));
        board.put(new Position(2, 9), new Ma(Team.HAN));
        board.put(new Position(3, 9), new Sa(Team.HAN));
        board.put(new Position(4, 9), new None());
        board.put(new Position(5, 9), new Sa(Team.HAN));
        board.put(new Position(6, 9), new Ma(Team.HAN));
        board.put(new Position(7, 9), new Sang(Team.HAN));
        board.put(new Position(8, 9), new Cha(Team.HAN));

        board.put(new Position(4, 8), new Jang(Team.HAN));

        board.put(new Position(1, 7), new Po(Team.HAN));
        board.put(new Position(7, 7), new Po(Team.HAN));

        for (int x = 0; x <= 8; x += 2) {
            board.put(new Position(x, 6), new Jol(Team.HAN));
        }

        return board;
    }
}
