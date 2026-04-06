package domain.board;

import domain.path.PathInfo;
import domain.piece.Camp;
import domain.piece.Piece;
import domain.piece.PieceType;

import java.util.List;
import java.util.Map;

public class Board {
    private final Map<Position, Piece> pieces;
    private boolean gameOver;
    private Camp winner;

    public Board(Map<Position, Piece> pieces) {
        this.pieces = pieces;
    }

    public boolean isExistPieceAt(Position position) {
        return pieces.containsKey(position);
    }

    public Piece pieceAt(Position position) {
        if (!isExistPieceAt(position)) {
            throw new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다.");
        }

        return pieces.get(position);
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Camp winner() {
        if (!gameOver) {
            throw new IllegalStateException("아직 게임이 종료되지 않았습니다.");
        }

        return winner;
    }

    public void move(Position departure, Position destination) {
        validateGameStatus();
        validateDepartureAndDestinationPosition(departure, destination);

        executeMove(departure, destination);
    }

    private void validateGameStatus() {
        if (gameOver) {
            throw new IllegalStateException("이미 종료된 게임입니다.");
        }
    }

    private void handleCapture(Piece departurePiece, Position destination) {
        Piece destinationPiece = pieceAt(destination);
        validateCapture(departurePiece, destinationPiece);
        updateGameOver(departurePiece, destinationPiece);
    }

    private void validateDepartureAndDestinationPosition(Position departure, Position destination) {
        if (departure.equals(destination)) {
            throw new IllegalArgumentException("출발 위치와 도착 위치가 같습니다.");
        }
    }

    private void validateCapture(Piece departurePiece, Piece destinationPiece) {
        if (departurePiece.isSameCamp(destinationPiece)) {
            throw new IllegalArgumentException("같은 진영의 기물은 잡을 수 없습니다.");
        }
    }

    private List<PathInfo> getPath(Position departure, Position destination, Piece piece) {
        return piece.getPath(departure, destination).stream()
                .map(position -> new PathInfo(position, pieces.get(position)))
                .toList();
    }

    private void updateGameOver(Piece departurePiece, Piece destinationPiece) {
        if (destinationPiece.isSameType(PieceType.GENERAL)) {
            gameOver = true;
            winner = departurePiece.getCamp();
        }
    }

    private void executeMove(Position departure, Position destination) {
        Piece departurePiece = pieceAt(departure);
        List<PathInfo> path = getPath(departure, destination, departurePiece);

        departurePiece.validateBlockingPiece(path, destination);

        if (isExistPieceAt(destination)) {
            handleCapture(pieceAt(departure), destination);
        }

        pieces.remove(departure);
        pieces.put(destination, departurePiece);
    }
}
