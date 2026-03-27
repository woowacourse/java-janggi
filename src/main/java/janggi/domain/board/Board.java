package janggi.domain.board;

import janggi.domain.route.Path;
import janggi.domain.route.Paths;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Piece;
import janggi.domain.game.Side;
import janggi.dto.PlayerDTO;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> piecePosition;

    private Board(Map<Position, Piece> piecePosition) {
        this.piecePosition = new HashMap<>(piecePosition);
    }

    public static Board initialize() {
        Map<Position, Piece> initialBoard = new HashMap<>();
        initializeEach(InitialBoardInfo.values(), initialBoard);
        return new Board(initialBoard);
    }

    private static void initializeEach(InitialBoardInfo[] boardInfos, Map<Position, Piece> initialBoard) {
        Arrays.stream(boardInfos)
                .forEach(boardInfo -> {
                    boardInfo.setPieces(initialBoard);
                });
    }

    //    !------------임시---------------!
    public Map<Position, Piece> getPiecePosition() {
        Map<Position, Piece> piecePositions = new HashMap<>(piecePosition.size());
        piecePositions.putAll(this.piecePosition);
        return Collections.unmodifiableMap(piecePositions);
    }

    public List<Position> calculateDestinations(Position currentPosition) {
        Piece piece = piecePosition.get(currentPosition);
        Paths moveablePaths = piece.calculatePaths(currentPosition);
        Map<Position, Piece> boardState = generateStateByPaths(moveablePaths);

        return piece.determineDestinations(moveablePaths, boardState);
    }

    private Map<Position, Piece> generateStateByPaths(Paths moveablePaths) {
        Map<Position, Piece> boardState = new HashMap<>();
        moveablePaths.forEach(path -> {
            generateStateByPath(path, boardState);
        });
        return boardState;
    }

    private void generateStateByPath(Path path, Map<Position, Piece> boardState) {
        path.forEach(position -> {
            Piece piece = piecePosition.get(position);
            if (piece != null) {
                boardState.put(position, piece);
            }
        });
    }

    public void movePiece(Position selected, Position target) {
        Piece movingPiece = piecePosition.remove(selected);
        piecePosition.put(target, movingPiece);
    }

    public boolean isPieceExist(Position position) {
        return piecePosition.containsKey(position);
    }

    public boolean isThereOwnPiece(Position selected, PlayerDTO currentPlayer) {
        Piece piece = piecePosition.get(selected);
        Side pieceSide = piece.side();
        Side playerSide = currentPlayer.side();
        return pieceSide.isSameSide(playerSide);
    }
}
