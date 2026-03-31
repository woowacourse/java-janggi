package janggi.domain;

import janggi.domain.board.Board;
import janggi.domain.board.strategy.ElephantHorseElephantHorse;
import janggi.domain.board.strategy.ElephantHorseHorseElephant;
import janggi.domain.board.strategy.FormationStrategy;
import janggi.domain.board.strategy.HorseElephantElephantHorse;
import janggi.domain.board.strategy.HorseElephantHorseElephant;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;

import java.util.List;
import java.util.Map;

public class Janggi {
    private static final List<FormationStrategy> FORMATIONS = List.of(
            new HorseElephantElephantHorse(),
            new HorseElephantHorseElephant(),
            new ElephantHorseHorseElephant(),
            new ElephantHorseElephantHorse()
    );

    private final Board board;

    private Janggi(Board board) {
        this.board = board;
    }

    public static Janggi start(int choFormationNumber, int hanFormationNumber) {
        return new Janggi(Board.initializeToBoard(
                readFormation(choFormationNumber),
                readFormation(hanFormationNumber)));
    }

    private static FormationStrategy readFormation(int choice) {
        if (choice < 1 || choice > FORMATIONS.size()) {
            throw new IllegalArgumentException("1~4 중 선택해주세요.");
        }
        return FORMATIONS.get(choice - 1);
    }

    public void movePiece(int row, int col, Position position) {
        board.movePiece(row, col, position);
    }

    public void validateCamp(int row, int col, Camp camp) {
        Piece piece = board.selectPiece(row, col);
        if (!piece.isSameCamp(camp)) {
            throw new IllegalArgumentException("자신의 기물만 선택할 수 있습니다.");
        }
    }

    public Map<Position, Piece> getBoard() {
        return board.janggiBoard();
    }
}
