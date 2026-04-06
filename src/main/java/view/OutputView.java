package view;

import domain.constant.Country;
import domain.constant.PieceType;
import java.util.List;
import java.util.Map;
import dto.BoardDto;
import dto.PieceDto;
import dto.PositionDto;

public class OutputView {

    private final OutputViewFormatter formatter;

    public OutputView(OutputViewFormatter formatter) {
        this.formatter = formatter;
    }

    public void printGameStartMessage() {
        System.out.println("우테코 장기 게임입니다.");
    }

    public void printErrorMessage(String message) {
        System.out.println(formatter.formatErrorMessage(message));
    }

    public void printCountry(Country country) {
        System.out.println(formatter.formatCountry(country.getName()));
    }

    public void printTurnStartMessage() {
        System.out.println("\n초나라가 먼저 시작합니다.");
    }

    public void printChangeTurnMessage(String countryName, double score) {
        System.out.println(formatter.formatChangeTurn(countryName));
        System.out.println(countryName + " 점수: " + score);
    }

    public void printBoard(BoardDto boardDto) {
        Map<PositionDto, PieceDto> pieces = boardDto.pieces();
        StringBuilder boardResult = new StringBuilder(formatter.formatColNumbers());

        boardResult.append(formatter.formatHorizontalLine());
        for (int x = 1; x <= 10; x++) {
            boardResult.append(formatter.formatRowNumber(x));
            printBoardColumn(boardResult, pieces, x);
            boardResult.append(formatter.formatRightVerticalLine());
        }
        boardResult.append(formatter.formatHorizontalLine());
        System.out.print(boardResult);
    }

    private void printBoardColumn(StringBuilder boardResult, Map<PositionDto, PieceDto> pieces, int x) {
        for (int y = 1; y <= 9; y++) {
            printPiece(boardResult, pieces, x, y);
        }
    }

    private void printPiece(StringBuilder boardResult, Map<PositionDto, PieceDto> pieces, int x, int y) {
        PositionDto nowPosition = new PositionDto(x, y);
        if (!pieces.containsKey(nowPosition)) {
            boardResult.append(formatter.formatEmptyPiece());
            return;
        }

        PieceDto pieceDto = pieces.get(nowPosition);
        boardResult.append(
                formatter.formatPiece(PieceColor.getColorCode(pieceDto.countryName()), pieceDto.pieceName()));
    }

    public void printPiecePossiblePosition(PieceType pieceType, List<PositionDto> positionDtos) {
        StringBuilder result = new StringBuilder(formatter.formatPossiblePositionHeader(pieceType.getName()));
        int num = 1;
        for (PositionDto dto : positionDtos) {
            result.append(formatter.formatPossiblePosition(num++, dto.x(), dto.y()));
        }

        System.out.print(result);
    }

    public void printGameResult(String countryName) {
        System.out.println("\n---\n");
        System.out.println("게임이 종료되었습니다.");
        printGameWinner(countryName);
    }

    private void printGameWinner(String countryName) {
        System.out.print(formatter.formatGameWinner(countryName));
    }

    public void printNewGameStart() {
        System.out.println("\n저장된 게임이 존재하지 않습니다. 새 게임을 시작합니다.\n");
    }
}
