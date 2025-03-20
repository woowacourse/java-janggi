package view;

import java.util.Map;

import dto.BoardDto;
import dto.PositionDto;
import dto.TeamDto;
import model.Position;
import model.board.Board;
import view.util.Color;

public class OutputView {

    public void startGame() {
        System.out.printf("장기 게임을 시작합니다.%n%n");
    }

    // TODO: 밥먹고와서 수정
    public void board(BoardDto dto) {
        Map<PositionDto, String> pieces = dto.pieces();

        for (int y = 0; y < Board.HEIGHT_SIZE; y++) {
            StringBuilder line = new StringBuilder();
            line.append(y).append("  ");
            for (int x = 0; x < Board.WIDTH_SIZE; x++) {
                line.append(pieces.getOrDefault(PositionDto.from(new Position(x,y)), "＿"));
                line.append(" ");
            }
            System.out.println(line);
        }
        System.out.println("     a  b  c  d  e   f   g  h   i");
    }

    public void turn(TeamDto teamDto) {
        System.out.printf("%n%s의 차례입니다.%n%n", Color.apply(teamDto, teamDto.getDisplayName()));
    }

    public void result(TeamDto winnerTeamDto) {
        String winner = Color.apply(winnerTeamDto, winnerTeamDto.getDisplayName());
        System.out.printf("%n%s가 승리했습니다. 게임을 종료합니다.%n", winner);
    }
}
