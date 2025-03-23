package janggi.board;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import janggi.position.Position;

import janggi.piece.Piece;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board {

    private final Map<Position, Piece> onPosition;

    public Board(Set<Piece> pieces) {
        onPosition = pieces.stream().collect(toMap(Piece::position, identity()));
    }

    public static Board generate() {
        return new Board(new Initializer().generate());
    }

    public boolean hasPieceOn(Position position) {
        return toSet().stream()
            .anyMatch(piece -> piece.onPosition(position));
    }

    public Piece get(Position position) {
        return toSet().stream()
            .filter(piece -> piece.onPosition(position))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 위치에 기물이 존재하지 않습니다."));
    }

    public Set<Piece> toSet(){
        return new HashSet<>(onPosition.values());
    }

    public void take(Piece destination) {
        onPosition.remove(destination.position());
    }

    public Map<Position, Piece> onPosition() {
        return onPosition;
    }

//
//    public Team getWinnerIfGameOver() {
//        List<Piece> palaces = getPalaces();
//        if (palaces.size() == 1) {
//            return palaces.getFirst().getTeam();
//        }
//        return null;
//    }

//    public List<Piece> getPieces() {
//        return new ArrayList<>(pieces);
//    }

//    public void abstain(Team team) {
//        Piece palace = getPalaces().stream()
//            .filter(piece -> piece.getTeam() == team)
//            .findAny()
//            .orElseThrow(() -> new IllegalStateException("[ERROR] 존재하지 않는 팀이 기권했습니다."));
//
//        pieces.remove(palace);
//    }
//
//    private List<Piece> getPalaces() {
//        return pieces.stream()
//            .filter(piece -> piece.type() == PieceType.PALACE)
//            .toList();
//    }
}
