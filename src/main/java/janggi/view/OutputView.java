package janggi.view;

import janggi.domain.Country;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import janggi.domain.position.PositionFile;
import janggi.domain.position.PositionRank;

import java.util.List;
import java.util.Map;

public class OutputView {

    private static final int BOARD_FILE_SIZE = 10;

    private final ViewUtil viewUtil;

    public OutputView(final ViewUtil viewUtil) {
        this.viewUtil = viewUtil;
    }

    public void outputBoard(final Map<Country, List<Piece>> board) {
        final String[][] boardValue = initializeBoard();
        insertPieces(board, boardValue);

        final StringBuilder sb = new StringBuilder();
        for (int rankIndex = PositionRank.maxRankAmount(); rankIndex >= 0; rankIndex--) {
            for (int fileIndex = 0; fileIndex <= PositionFile.maxFileAmount(); fileIndex++) {
                sb.append(boardValue[rankIndex][fileIndex]);
            }
            sb.append("\n");
        }
        System.out.print(sb);
    }

    private static String[][] initializeBoard() {
        final String[][] boardValue = new String[PositionRank.maxRankAmount() + 1][PositionFile.maxFileAmount() + 1];
        for (int rankIndex = 0; rankIndex <= PositionRank.maxRankAmount(); rankIndex++) {
            for (int fileIndex = 0; fileIndex <= PositionFile.maxFileAmount(); fileIndex++) {
                boardValue[rankIndex][fileIndex] = "\t";
            }
        }
        for (PositionRank rank : PositionRank.values()) {
            boardValue[rank.amount()][0] = rank.amount() + "\t";
        }
        for (PositionFile file : PositionFile.values()) {
            boardValue[0][file.amount()] = file.amount() + "\t";
        }
        return boardValue;
    }

    private void insertPieces(final Map<Country, List<Piece>> board, final String[][] boardValue) {
        for (PositionRank rank : PositionRank.values()) {
            for (PositionFile file : PositionFile.values()) {
                Position position = new Position(file, rank);
                boardValue[rank.amount()][file.amount()] = viewUtil.parsePieceOf(board, position);
            }
        }
    }

    public void outputWinner(final Country winner, final int winnerScore, final int looserScore) {
        System.out.printf("%s 승리!! ( %d : %d )\n", viewUtil.convertCountry(winner), winnerScore, looserScore);
    }
}
