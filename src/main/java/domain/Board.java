package domain;

import domain.piece.Piece;
import domain.piece.Team;
import domain.position.Position;
import domain.settingType.SettingType;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Board {
    private static final String SHOULD_CHOOSE_CORRECT_TEAM_PIECE = "자신의 아군 기물만 이동할 수 있습니다.";
    private static final String EMPTY_POSITION = "해당 위치에 기물이 존재하지 않습니다.";
    private final Map<Position, Piece> pieces;

    private Board(Map<Position, Piece> pieces) {
        this.pieces = pieces;
    }

    public static Board of(SettingType choSettingType, SettingType hanSettingType) {
        Map<Position, Piece> setup = new BoardInitializer().setup(choSettingType, hanSettingType);
        return new Board(setup);
    }

    public void move(Position start, Position destination) {
        Piece startPiece = getPiece(start);
        pieces.remove(start);
        pieces.put(destination, startPiece);
    }

    public Piece getPiece(Position position) {
        Piece piece = pieces.get(position);
        if (piece == null) {
            throw new IllegalArgumentException(EMPTY_POSITION);
        }
        return piece;
    }

    public void validateIsAlly(Team turn, Position startPosition) {
        Piece startPiece = getPiece(startPosition);
        if (!startPiece.isSameTeam(turn)) {
            throw new IllegalArgumentException(SHOULD_CHOOSE_CORRECT_TEAM_PIECE);
        }
    }

    public BoardStatus getBoardStatus() {
        return BoardStatus.from(Map.copyOf(pieces));
    }

    public void validateIsMovable(Position start, Position destination) {
        validateIsReachable(start, destination); // 변할 수 없는 Rule (이동 가능 위치와 목표 좌표가 다르면 이동 불가)
        validateCanMove(start, destination); // 변할 수 있는 RUle
        validateCrashWithAlly(start, destination); // 변할 수 없는 Rule (아군이 있으면 이동할 수 없는 건 고정)
    }

    public void validateIsReachable(Position start, Position destination) {
        Piece startPiece = getPiece(start);

        startPiece.findMovablePath(start, destination);
    }

    public void validateCanMove(Position start, Position destination) {
        Piece startPiece = getPiece(start);

        List<Position> movablePath = startPiece.findMovablePath(start, destination);
        Map<Position, Piece> pathMap = toPathMap(movablePath);
        startPiece.movePolicy(PathContext.from(pathMap));
    }

    private Map<Position, Piece> toPathMap(List<Position> movablePath) {
        return movablePath.stream()
                .filter(position -> pieces.get(position) != null)
                .collect(Collectors.toMap(
                        Function.identity(),
                        pieces::get
                ));
    }

    public void validateCrashWithAlly(Position start, Position destination) {
        // TODO: 아군과 충돌하는가?
        Piece startPiece = getPiece(start);
        Piece destinationPiece = pieces.get(destination);

        if (destinationPiece == null) {
            return;
        }
        startPiece.capture(destinationPiece);
    }
}
