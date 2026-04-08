package domain.board;

import domain.piece.Piece;
import domain.piece.PieceInfo;
import domain.piece.PieceType;
import domain.state.EmptyState;
import domain.state.FullState;
import domain.state.State;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Board {
    private static final String NOT_FOUND_PIECE_FROM_POSITION = "[ERROR] 해당 좌표에 기물이 존재하지 않습니다.";
    private static final String NOT_MY_PIECE = "[ERROR] 본인 진영의 기물이 아닙니다.";
    private static final String CAN_NOT_MOVE_TO_POSITION = "[ERROR] 해당 경로로 기물을 이동시킬 수 없습니다.";

    private final Map<Position, State> board;

    private Board(Map<Position, State> board) {
        this.board = board;
    }

    public static Board load(Map<Position, PieceInfo> pieceInfos) {
        Map<Position, State> board = new LinkedHashMap<>();
        for (Map.Entry<Position, PieceInfo> entry : pieceInfos.entrySet()) {
            PieceInfo pieceInfo = entry.getValue();
            Piece piece = pieceInfo.pieceType().createPiece(pieceInfo.country());
            board.put(entry.getKey(), new FullState(piece));
        }
        new BoardInitializer().fillEmptyPositions(board);
        return new Board(board);
    }

    public static Board create(TableSetting choSetting, TableSetting hanSetting) {
        Map<Position, State> board = new BoardInitializer().initialize(choSetting, hanSetting);
        return new Board(board);
    }

    public void validateFromPosition(Position from, Country country) {
        State fromState = board.get(from);
        if (fromState.isEmpty()) {
            throw new IllegalArgumentException(NOT_FOUND_PIECE_FROM_POSITION);
        }
        if (fromState.getPiece().getPieceCountry() != country) {
            throw new IllegalArgumentException(NOT_MY_PIECE);
        }
    }

    public boolean move(Position from, Position to) {
        if (isEmpty(from)) {
            throw new IllegalArgumentException(NOT_FOUND_PIECE_FROM_POSITION);
        }
        Piece fromPiece = board.get(from).getPiece();
        List<Position> paths = fromPiece.findPaths(from, to);

        validateDestination(from, to);
        validatePath(fromPiece, paths);
        boolean isGeneralCaught = isGeneralCaught(to);
        movePiece(from, to, fromPiece);

        return isGeneralCaught;
    }

    public Map<Position, PieceInfo> getPieceInfos() {
        Map<Position, PieceInfo> pieceInfos = new LinkedHashMap<>();
        for (Entry<Position, State> entry : board.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                pieceInfos.put(entry.getKey(), entry.getValue().getPiece().getPieceInfo());
            }
        }
        return pieceInfos;
    }

    public boolean isEmpty(Position position) {
        return board.get(position).isEmpty();
    }

    private boolean isGeneralCaught(Position to) {
        return !isEmpty(to) && board.get(to).getPiece().getPieceType() == PieceType.GENERAL;
    }

    public Map<Country, Double> calculateScore() {
        double choInitScore = 0;
        double hanInitScore = 1.5;

        for (PieceInfo pieceInfo : getPieceInfos().values()) {
            if (pieceInfo.country() == Country.CHO) {
                choInitScore += pieceInfo.pieceType().getScore();
            }
            if (pieceInfo.country() == Country.HAN) {
                hanInitScore += pieceInfo.pieceType().getScore();
            }
        }
        Map<Country, Double> totalScores = new LinkedHashMap<>();
        totalScores.put(Country.CHO, choInitScore);
        totalScores.put(Country.HAN, hanInitScore);
        return totalScores;
    }

    private void validateDestination(Position from, Position to) {
        if (board.get(to).isEmpty()) {
            return;
        }
        Country fromCountry = board.get(from).getPiece().getPieceCountry();
        Country toCountry = board.get(to).getPiece().getPieceCountry();
        if (fromCountry == toCountry) {
            throw new IllegalArgumentException(CAN_NOT_MOVE_TO_POSITION);
        }
    }

    private void validatePath(Piece fromPiece, List<Position> paths) {
        Map<Position, PieceType> piecesOnPath = new LinkedHashMap<>();
        for (Position position : paths.subList(0, paths.size() - 1)) {
            if (!isEmpty(position)) {
                piecesOnPath.put(position, board.get(position).getPiece().getPieceType());
            }
        }
        PieceType destinationPieceType = getDestinationPieceType(paths.getLast());
        fromPiece.validateClearPath(piecesOnPath, destinationPieceType);
    }

    private PieceType getDestinationPieceType(Position destination) {
        if (isEmpty(destination)) {
            return PieceType.EMPTY;
        }
        return board.get(destination).getPiece().getPieceType();
    }

    private void movePiece(Position from, Position to, Piece fromPiece) {
        board.put(to, new FullState(fromPiece));
        board.put(from, new EmptyState());
    }
}
