package view;

import domain.constant.Country;
import domain.constant.PieceType;
import dto.GameRecordDto;
import dto.GameResultDto;
import java.util.List;
import java.util.Map;
import dto.BoardDto;
import dto.PieceDto;
import dto.PositionDto;

public class OutputView {

    private final ViewFormatter formatter;

    public OutputView(ViewFormatter formatter) {
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
        for (int row = 1; row <= 10; row++) {
            boardResult.append(formatter.formatRowNumber(row));
            printBoardColumn(boardResult, pieces, row);
            boardResult.append(formatter.formatRightVerticalLine());
        }
        boardResult.append(formatter.formatHorizontalLine());
        System.out.print(boardResult);
    }

    private void printBoardColumn(StringBuilder boardResult, Map<PositionDto, PieceDto> pieces, int row) {
        for (int col = 1; col <= 9; col++) {
            printPiece(boardResult, pieces, row, col);
        }
    }

    private void printPiece(StringBuilder boardResult, Map<PositionDto, PieceDto> pieces, int row, int col) {
        PositionDto nowPosition = new PositionDto(row, col);
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
            result.append(formatter.formatPossiblePosition(num++, dto.row(), dto.col()));
        }

        System.out.print(result);
    }

    public void printGameResult(GameResultDto gameResultDto) {
        System.out.println("\n---\n");
        System.out.println("게임이 종료되었습니다.");
        System.out.print(formatter.formatGameResult(gameResultDto));
    }

    public void printNewGameStart() {
        System.out.println("\n저장된 게임이 존재하지 않습니다. 새 게임을 시작합니다.\n");
    }

    public void printGameRecords(List<GameRecordDto> gameRecordDtos) {
        if (gameRecordDtos.isEmpty()) {
            System.out.println("종료된 게임이 없습니다. 게임을 시작합니다.");
        }

        for (GameRecordDto gameRecordDto : gameRecordDtos) {
            System.out.printf(formatter.formatGameRecord(gameRecordDto));
        }
    }
}
