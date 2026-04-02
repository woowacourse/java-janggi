package janggi.domain;

import janggi.domain.board.Board;
import janggi.domain.board.strategy.*;
import janggi.domain.piece.Piece;
import janggi.view.dto.PieceStatus;

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
    private boolean ongoing;

    private Janggi(Board board, boolean ongoing) {
        this.board = board;
        this.ongoing = ongoing;
    }

    public static Janggi start(int choFormationNumber, int hanFormationNumber) {
        return new Janggi(Board.initializeToBoard(
                readFormation(choFormationNumber),
                readFormation(hanFormationNumber)),
                true);
    }

    private static FormationStrategy readFormation(int choice) {
        if (choice < 1 || choice > FORMATIONS.size()) {
            throw new IllegalArgumentException("1~4 중 선택해주세요.");
        }
        return FORMATIONS.get(choice - 1);
    }

    public void movePiece(Position from, Position to) {
        board.movePiece(from, to);
    }

    public void validateCamp(Position position, Camp camp) {
        Piece piece = board.selectPiece(position);
        if (!piece.isSameCamp(camp)) {
            throw new IllegalArgumentException("자신의 기물만 선택할 수 있습니다.");
        }
    }

    public List<PieceStatus> piecesStatus() {
        Map<Position, String> displayBoard = board.displayBoard();
        return displayBoard.keySet()
                .stream()
                .map(position -> PieceStatus.from(position,
                        board.checkCampOfThePiece(position),
                        displayBoard.get(position)))
                .toList();
    }

    public boolean isOnGoing() {
        return ongoing;
    }

    public void stopGame() {
        ongoing = false;
    }
}
