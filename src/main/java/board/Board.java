package board;

import java.util.ArrayList;
import java.util.List;

import piece.Team;
import piece.Piece;
import piece.PieceType;

public class Board {

    public static final int WIDTH_SIZE = 9;
    public static final int HEIGHT_SIZE = 10;

    private final List<Piece> pieces;

    public Board(List<Piece> pieces) {
        this.pieces = new ArrayList<>(pieces);
    }

    public static Board generate() {
        return new Initializer().generate();
    }

    public boolean isInboard(Position position) {
        return position.x() < WIDTH_SIZE && position.x() >= 0
            && position.y() < HEIGHT_SIZE && position.y() >= 0;
    }

    public boolean hasPieceOn(Position position) {
        return pieces.stream()
            .anyMatch(piece -> piece.onPosition(position));
    }

    public Piece get(Position position) {
        return pieces.stream()
            .filter(piece -> piece.onPosition(position))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 위치에 기물이 존재하지 않습니다."));
    }

    public void take(Piece target) {
        pieces.remove(target);
    }

    public Team getWinnerIfGameOver() {
        List<Piece> palaces = getPalaces();
        if (palaces.size() == 1) {
            return palaces.getFirst().getTeam();
        }
        return null;
    }

    public List<Piece> getPieces() {
        return new ArrayList<>(pieces);
    }

    public void abstain(Team team) {
        Piece palace = getPalaces().stream()
            .filter(piece -> piece.getTeam() == team)
            .findAny()
            .orElseThrow(() -> new IllegalStateException("[ERROR] 존재하지 않는 팀이 기권했습니다."));

        pieces.remove(palace);
    }

    private List<Piece> getPalaces() {
        return pieces.stream()
            .filter(piece -> piece.type() == PieceType.PALACE)
            .toList();
    }
}
