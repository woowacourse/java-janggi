package janggi.team;

import janggi.board.Position;
import janggi.palace.Palace;
import janggi.palace.PalaceArea;
import janggi.piece.Piece;
import janggi.piece.PieceStatus;
import java.util.List;

public class Team {

    private static final String INVALID_PIECE_NAME = "이동할 말을 찾을 수 없습니다.";
    private static final String PIECE_NOT_FOUND = "해당 위치에 해당 말이 존재하지 않습니다.";
    private static final String PIECE_NOT_IN_PALACE = "해당 말은 궁성 밖으로 이동할 수 없습니다.";
    private static final String PIECE_OCCUPIED = "이미 해당 위치에 같은 팀의 말이 자리하고 있습니다.";
    private static final String PIECE_MOVE_NOT_ALLOWED = "해당 경로는 이동할 수 없습니다.";

    private static final String PIECE_NAME_KING = "K";
    private static final String PIECE_NAME_GUARD = "G";
    private static final String PIECE_NAME_CANNON = "P";
    private static final int VALUE_ONE = 1;
    private static final int VALUE_ZERO = 0;

    private final List<Piece> pieces;
    private final Palace palace;
    private final TeamScore teamScore;
    private final TeamName teamName;

    public Team(List<Piece> pieces, Palace palace, TeamName teamName) {
        this.pieces = pieces;
        this.palace = palace;
        this.teamName = teamName;
        this.teamScore = TeamScore.from(teamName);
    }

    public void validatePieceMovement(String pieceName, Position currentPosition, Position destination) {
        PalaceArea palaceArea = isInPalaceArea(currentPosition);
        Piece piece = findPieceByName(pieceName, currentPosition);

        piece.validateMovement(currentPosition, destination, palaceArea);
    }

    public PalaceArea isInPalaceArea(Position position) {
        return PalaceArea.from(palace.isPieceInsidePalace(position));
    }

    public Piece findPieceByName(String pieceName, Position startPosition) {
        return pieces.stream()
                .filter(piece ->
                        piece.matchName(pieceName) && piece.matchPosition(startPosition)
                ).
                findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_PIECE_NAME));
    }

    public void validatePiece(String pieceName, Position position) {
        boolean exists = pieces.stream()
                .anyMatch(piece -> piece.matchName(pieceName)
                        && piece.matchPosition(position)
                        && piece.matchStatus(PieceStatus.ALIVE)
                );
        if (!exists) {
            throw new IllegalArgumentException(PIECE_NOT_FOUND);
        }
    }

    public void validateKingGuardDestinationIsInPalace(String pieceName, Position destination) {
        boolean isStillInPalace = pieces.stream()
                .anyMatch(piece -> piece.getName().equalsIgnoreCase(pieceName)
                        && palace.isPieceInsidePalace(destination));

        if (!isStillInPalace && (PIECE_NAME_KING.equalsIgnoreCase(pieceName)
                || PIECE_NAME_GUARD.equalsIgnoreCase(pieceName))) {
            throw new IllegalArgumentException(PIECE_NOT_IN_PALACE);
        }
    }

    public void validateDestinationIsNotOccupiedBySameTeam(Position movedPosition) {
        boolean isOccupied = pieces.stream()
                .anyMatch(piece -> piece.isOccupiedByMe(movedPosition));
        if (isOccupied) {
            throw new IllegalArgumentException(PIECE_OCCUPIED);
        }
    }

    public void validateLegalMove(String pieceName, List<Position> positionsOnPath) {
        if (PIECE_NAME_CANNON.equalsIgnoreCase(pieceName)) {
            validateLegalMoveForCannon(positionsOnPath);
        }
        boolean isNotLegalMove = pieces.stream()
                .anyMatch(piece -> positionsOnPath.contains(piece.getPosition()));

        if (isNotLegalMove) {
            throw new IllegalArgumentException(PIECE_MOVE_NOT_ALLOWED);
        }
    }

    private void validateLegalMoveForCannon(List<Position> positionsOnPath) {
        int obstacleCount = VALUE_ZERO;
        for (Piece piece : pieces) {
            if (positionsOnPath.contains(piece.getPosition())) {
                obstacleCount += VALUE_ONE;
            }
        }
        boolean isSameCannonKind = pieces.stream()
                .anyMatch(piece -> positionsOnPath.contains(piece.getPosition())
                        && PIECE_NAME_CANNON.equalsIgnoreCase(piece.getName()));

        boolean isLegalMove = obstacleCount == VALUE_ONE && !isSameCannonKind;
        if (!isLegalMove) {
            throw new IllegalArgumentException(PIECE_MOVE_NOT_ALLOWED);
        }
    }

    public void move(Piece currentPiece, Position endPosition) {
        String pieceName = currentPiece.getName();
        Position startPosition = currentPiece.getPosition();
        Piece targetPiece = findPieceByName(pieceName, startPosition);
        targetPiece.move(endPosition);
    }

    public void updateStatusIfCaught(Position opponentPosition) {
        for (Piece piece : pieces) {
            if (piece.isOccupiedByMe(opponentPosition)) {
                piece.updateStatusIfCaught(opponentPosition);
            }
        }
    }

    public boolean isKingCaught() {
        return pieces.stream()
                .anyMatch(piece -> PIECE_NAME_KING.equalsIgnoreCase(piece.getName())
                        && piece.matchStatus(PieceStatus.CAUGHT));
    }

    public double checkTeamScore() {
        return teamScore.calculateScore(pieces);
    }

    public List<Piece> getBoard() {
        return pieces;
    }

    public TeamName getTeamName() {
        return teamName;
    }
}
