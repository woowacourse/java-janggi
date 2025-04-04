package janggi.service.fake;

import janggi.domain.board.JanggiBoard;
import janggi.domain.board.dao.JanggiBoardDAO;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Pieces;
import janggi.domain.value.JanggiPosition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakeJanggiBoardTable implements JanggiBoardDAO {
    private final Map<Integer, List<Piece>> database = new HashMap<>();

    @Override
    public void insertPieces(JanggiBoard janggiBoard) {
        database.put(1, janggiBoard.getChoPieces());
        database.put(2, janggiBoard.getHanPieces());
    }

    @Override
    public void dropTables() {
        database.clear();
    }

    @Override
    public void updateRecords(JanggiPosition current, JanggiPosition destination, int teamId) {
        List<Piece> targetTeamPieces = database.get(teamId);
        if (teamId == 1) {
            Piece targetPiece = targetTeamPieces.stream()
                    .filter(piece -> piece.getPosition().equals(current))
                    .findFirst()
                    .orElseThrow();

            Piece movedPiece = targetPiece.move(destination, new Pieces(database.get(2)), new Pieces(targetTeamPieces));
            database.put(teamId, List.of(movedPiece));
            return;
        }
        Piece targetPiece = targetTeamPieces.stream()
                .filter(piece -> piece.getPosition().equals(current))
                .findFirst()
                .orElseThrow();

        Piece movedPiece = targetPiece.move(destination, new Pieces(database.get(1)), new Pieces(targetTeamPieces));
        database.put(teamId, List.of(movedPiece));
    }

    @Override
    public void deleteRecords(JanggiPosition destination, int teamId) {
        List<Piece> pieces = database.get(teamId);

        boolean isInRecord = pieces.stream()
                .anyMatch(piece -> piece.getPosition()
                        .equals(destination));

        if (isInRecord) {
            database.put(teamId, List.of());
        }
    }

    @Override
    public List<Piece> selectChoRecords() {
        if (database.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(database.get(1));
    }

    @Override
    public List<Piece> selectHanRecords() {
        if (database.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(database.get(2));
    }
}
