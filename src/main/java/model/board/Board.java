package model.board;

import java.util.ArrayList;
import java.util.List;

import model.Position;
import model.Team;
import model.piece.BoardSearcher;
import model.piece.Piece;
import model.piece.PieceType;

public class Board implements BoardSearcher {

    public static final int WIDTH_SIZE = 9;
    public static final int HEIGHT_SIZE = 10;

    private final List<Piece> pieces = new ArrayList<>();

    public void addTeamPieces(Team team, TableSetting tableSetting) {
        pieces.addAll(Initializer.settingWith(team, tableSetting));
    }

    public boolean hasPieceOn(Position position) {
        return pieces.stream()
            .anyMatch(piece -> piece.onPosition(position));
    }

    public Piece get(Position position) {
        Piece piece = find(position);
        if (piece == null) {
            throw new IllegalArgumentException("[ERROR] 해당 위치에 기물이 존재하지 않습니다.");
        }
        return piece;
    }

    public Piece find(Position position) {
        return pieces.stream()
            .filter(piece -> piece.onPosition(position))
            .findAny()
            .orElse(null);
    }

    private void take(Piece target) {
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
            .filter(piece -> piece.equalsTeam(team))
            .findAny()
            .orElseThrow(() -> new IllegalStateException("[ERROR] 존재하지 않는 팀이 기권했습니다."));

        pieces.remove(palace);
    }

    private List<Piece> getPalaces() {
        return pieces.stream()
            .filter(piece -> piece.type() == PieceType.PALACE)
            .toList();
    }

    public void movePiece(Position source, Position destination, Team currentTurn) {
        validateInBoard(destination);
        Piece piece = get(source);
        Piece target = find(destination);
        piece.move(this, currentTurn, destination.difference(source));
        takePieceIfExists(target);
    }

    private void takePieceIfExists(Piece target) {
        if (target != null) {
            take(target);
        }
    }

    private void validateInBoard(Position position) {
        if (!isInBoard(position)) {
            throw new IllegalArgumentException("[ERROR] 장기판 내에서만 이동할 수 있습니다.");
        }
    }

    public boolean isInBoard(Position position) {
        return position.x() < WIDTH_SIZE && position.x() >= 0 && position.y() < HEIGHT_SIZE && position.y() >= 0;
    }
}
