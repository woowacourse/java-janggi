package domain;

import domain.piece.Piece;
import domain.piece.Team;
import domain.position.Position;
import domain.settingType.SettingType;
import java.util.Collections;
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

    public void move(Team turn, Position start, Position destination) {
        validateIsAlly(turn, start);

        validateIsReachable(start, destination);

        validateCanMove(start, destination);

        validateCrashWithAlly(start, destination);

        Piece startPiece = getPieceOrThrowException(start);
        pieces.remove(start);
        pieces.put(destination, startPiece);
    }

    private void validateIsAlly(Team turn, Position startPosition) {
        Piece startPiece = getPieceOrThrowException(startPosition);
        if (!startPiece.isSameTeam(turn)) {
            throw new IllegalArgumentException(SHOULD_CHOOSE_CORRECT_TEAM_PIECE);
        }
    }

    private void validateIsReachable(Position start, Position destination) {
        Piece startPiece = getPieceOrThrowException(start);

        startPiece.findMovablePath(start, destination);
    }

    private void validateCanMove(Position start, Position destination) {
        Piece startPiece = getPieceOrThrowException(start);

        List<Position> movablePath = startPiece.findMovablePath(start, destination);
        Map<Position, Piece> pathMap = toPathMap(movablePath);
        startPiece.movePolicy(PathContext.from(pathMap));
    }

    private Map<Position, Piece> toPathMap(List<Position> movablePath) {
        return movablePath.stream()
                .filter(position -> getPieceWithNull(position) != null)
                .collect(Collectors.toMap(
                        Function.identity(),
                        pieces::get));
    }

    private void validateCrashWithAlly(Position start, Position destination) {
        Piece startPiece = getPieceOrThrowException(start);
        Piece destinationPiece = getPieceWithNull(destination);

        if (destinationPiece == null) {
            return;
        }
        startPiece.capture(destinationPiece);
    }

    public Piece getPieceOrThrowException(Position position) {
        Piece piece = pieces.get(position);
        if (piece == null) {
            throw new IllegalArgumentException(EMPTY_POSITION);
        }
        return piece;
    }

    public Piece getPieceWithNull(Position position) {
        return pieces.get(position);
    }

    public Map<Position, Piece> getPieces() {
        return Collections.unmodifiableMap(pieces);
    }
}
