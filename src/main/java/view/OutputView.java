package view;

import dao.PieceDao;
import game.Team;
import java.util.List;
import location.Position;
import piece.Piece;
import piece.PieceType;

public class OutputView {

    public static void displayBoard(List<Piece> greenPieces, List<Piece> redPieces) {
        String[][] pieceName = new String[11][10];

        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 9; j++) {
                pieceName[i][j] = ".";
            }
        }

        for (Piece piece : greenPieces) {
            PieceType pieceType = piece.getPieceType();
            Position position = piece.getCurrentPosition();
            pieceName[position.y()][position.x()] = OutputFormatter.getGreenPiecePrintFormatBy(pieceType);
        }

        for (Piece piece : redPieces) {
            PieceType pieceType = piece.getPieceType();
            Position position = piece.getCurrentPosition();
            pieceName[position.y()][position.x()] = OutputFormatter.getRedPiecePrintFormatBy(pieceType);
        }

        System.out.println();
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 9; j++) {
                System.out.print(pieceName[i][j]);
            }
            System.out.println(" " + i);
        }
        System.out.println("123456789");
        System.out.println();
    }

    public static void displayWrongPoint() {
        System.out.println("자신의 기물이 위치한 좌표를 선택해주세요.");
    }

    public static void displayResult(Team winTeam, double greenPlayerTotalScore, double redPlayerTotalScore) {
        System.out.println("---------- 게임 결과 ----------");
        System.out.println("승: " + OutputFormatter.getTeamPrintFormatBy(winTeam));
        System.out.println(OutputFormatter.getTeamPrintFormatBy(Team.GREEN) + "점수: " + greenPlayerTotalScore);
        System.out.println(OutputFormatter.getTeamPrintFormatBy(Team.RED) + "점수: " + redPlayerTotalScore);
    }
}
