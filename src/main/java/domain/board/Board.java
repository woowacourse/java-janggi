package domain.board;

import domain.path.PathInfo;
import domain.piece.Camp;
import domain.piece.Piece;
import domain.piece.PieceType;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Board {
    private Long id;
    private final Map<Position, Piece> pieces;
    private boolean gameInProgress;
    private Camp turn;

    public static Board from(Map<Position, Piece> pieces){
        return new Board(pieces, Camp.CHO);
    }

    public Board(Map<Position, Piece> pieces, Camp turn) {
        this.pieces = pieces;
        this.turn = turn;
    }

    public void assignId(Long id) {
        this.id = id;
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

    public Long id() {
        return id;
    }

    public boolean isGameInProgress() {
        return gameInProgress;
    }

    public Map<Position, Piece> pieces() {
        return Collections.unmodifiableMap(pieces);
    }

    public Camp turn(){
        return turn;
    }

    public Camp winner() {
        if (!gameInProgress) {
            throw new IllegalStateException("아직 게임이 종료되지 않았습니다.");
        }

        return pieces.values().stream()
                .filter(Piece::isGeneral)
                .map(Piece::camp)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("궁이 존재하지 않습니다."));
    }

    public void move(Position departure, Position destination) {
        validateGameStatus();
        validateDepartureAndDestinationPosition(departure, destination);

        executeMove(departure, destination);
    }

    private void validateGameStatus() {
        if (gameInProgress) {
            throw new IllegalStateException("이미 종료된 게임입니다.");
        }
    }

    private void handleCapture(Piece departurePiece, Position destination) {
        Piece destinationPiece = pieceAt(destination);
        validateCapture(departurePiece, destinationPiece);
        updateGameProgress(destinationPiece);
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

    private void updateGameProgress(Piece destinationPiece) {
        if (destinationPiece.isSameType(PieceType.GENERAL)) {
            gameInProgress = true;
        }

        if (destinationPiece.camp().equals(Camp.CHO)) {
            turn = Camp.HAN;
            return;
        }
        turn = Camp.CHO;
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
