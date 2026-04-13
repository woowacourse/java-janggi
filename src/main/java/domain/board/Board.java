package domain.board;

import domain.piece.Camp;
import domain.position.ElephantFormation;
import domain.piece.PieceGenerator;
import domain.piece.PieceType;
import domain.piece.Piece;
import domain.position.Position;

import java.util.*;

public class Board implements BoardReader {

    private final Map<Position, Piece> board = new HashMap<>();

    public void generatePiecesBy(Camp camp, ElephantFormation elephantFormation) {
        Map<Position, Piece> pieces = PieceGenerator.generatePieces(camp, elephantFormation);

        board.putAll(pieces);
    }

    public void locatePiece(Position position, Piece piece) {
        if (!isExist(position)) {
            board.put(position, piece);
            return;
        }

        if (getPieceFrom(position).isSameCamp(piece)) {
            throw new IllegalArgumentException("[ERROR] 같은 팀은 잡을 수 없습니다!");
        }

        board.put(position, piece);
    }

    public Piece getPieceFrom(Position position) {
        return board.get(position);
    }

    @Override
    public boolean isExist(Position position) {
        return board.containsKey(position);
    }

    public void move(Position fromPosition, Position toPosition) {
        if (fromPosition.equals(toPosition)) {
            throw new IllegalArgumentException("[ERROR] 제자리 이동은 불가능합니다.");
        }

        Piece piece = board.get(fromPosition);
        boolean canMove = piece.canMove(fromPosition, toPosition, this);
        if (canMove) {
            locatePiece(toPosition, piece);
            board.remove(fromPosition);
            return;
        }

        throw new IllegalArgumentException("[ERROR] 이동할 수 없습니다");
    }

    @Override
    public boolean isDifferentPieceType(Position position, Piece piece) {
        if (!board.containsKey(position)) {
            return true;
        }

        return board.get(position).isDifferentPieceType(piece);
    }

    public Map<Position, Piece> getBoardStatus() {
        Map<Position, Piece> boardStatus = new HashMap<>(board);

        return Collections.unmodifiableMap(boardStatus);
    }

    public boolean isPieceOfCamp(Position position, Camp camp) {
        if (!board.containsKey(position)) {
            return false;
        }

        return board.get(position).isSameCamp(camp);
    }

    public Map<Camp, Integer> getScoreByCamp() {
       Map<Camp, Integer> scoreByCamp = new EnumMap<>(Camp.class);
        for (Piece piece : board.values()) {
            int score = scoreByCamp.getOrDefault(piece.getCamp(), 0) + piece.getScore();
            scoreByCamp.put(piece.getCamp(), score);
        }

        return scoreByCamp;
    }

    public Optional<Camp> checkWinner() {
        if(isGeneralDead(Camp.CHO)) {
            return Optional.of(Camp.HAN);
        }

        if(isGeneralDead(Camp.HAN)) {
            return Optional.of(Camp.CHO);
        }

        return Optional.empty();
    }

    private boolean isGeneralDead(Camp camp) {
        return board.values()
                .stream()
                .noneMatch(piece -> piece.isSameCamp(camp) && piece.getPieceType() == PieceType.GENERAL);
    }
}
