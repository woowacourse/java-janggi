package janggi.db;

import janggi.domain.InitialElephantSetting;
import janggi.domain.PiecesInitializer;
import janggi.domain.Team;
import janggi.domain.piece.Piece;
import java.sql.SQLException;
import java.util.List;

public class DatabaseInitializer {
    private final Table table;

    public DatabaseInitializer(final Table table) {
        this.table = table;
    }

    public void initialize(final PieceDao pieceDao, final TurnDao turnDao) throws SQLException {
        if (!table.isTableExist("piece") ||
                !table.isTableExist("turn")) {
            if (table.isTableExist("piece")) {
                table.dropTable("piece");
            }
            if (table.isTableExist("turn")) {
                table.dropTable("turn");
            }

            table.createPieceTable();
            table.createTurnTable();

            List<Piece> initialPieces = PiecesInitializer.initializePieces(InitialElephantSetting.INNER_ELEPHANT)
                    .getPieces();

            for (Piece initialPiece : initialPieces) {
                pieceDao.addPiece(initialPiece);
            }
            turnDao.addTeam(Team.BLUE);
        }
    }
}
