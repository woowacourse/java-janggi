package view;

import java.util.List;

import domain.Country;
import domain.PieceType;
import service.dto.BoardDto;
import service.dto.PositionDto;

public class OutputView {
    public static final String PREFEIX_ERROR_MESSAGE = "[ERROR]";
    private final OutputViewFormatter formatter;

    public OutputView(OutputViewFormatter formatter) {
        this.formatter = formatter;
    }

    public void printGameStartMessage() {
        System.out.println("우테코 장기 게임입니다.");
    }

    public void printEndMessage() {
        System.out.println("게임을 종료합니다.");
    }

    public void printErrorMessage(String message) {
        System.out.println(PREFEIX_ERROR_MESSAGE+message);
    }

    public void printCountry(Country country) {
        System.out.println(formatter.formatCountry(country.getName()));
    }

    public void printTurnStartMessage() {
        System.out.println("\n초나라가 먼저 시작합니다.");
    }

    public void printChangeTurnMessage(String countryName) {
        System.out.printf("\n차례 : %s\n", countryName);
    }

    public void printBoard(BoardDto boardDto) {
        for (BoardDto.Row dto : boardDto.rows()) {
            for (String r : dto.row()) {
                System.out.printf("%-3s", r);
            }
            System.out.println();
        }
    }

    public void printPiecePossiblePosition(PieceType pieceType,List<PositionDto> positionDtos) {
        System.out.printf("\n['%s' 기물의 현재 좌표 목록]\n",pieceType.getName());
        int num = 1;
        for (PositionDto dto : positionDtos) {
            System.out.printf("%d. [%d,%d]\n", num++, dto.x(), dto.y());
        }
    }

}
