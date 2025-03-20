package janggi.game;

import janggi.board.*;
import janggi.position.Position;
import janggi.team.Team;
import janggi.view.Input;
import janggi.view.Output;

import java.util.ArrayList;
import java.util.regex.*;
import java.util.Arrays;
import java.util.List;

public class GameManager {
    private final Input input;
    private final Output output;

    public GameManager() {
        this.input = new Input();
        this.output = new Output();
    }

    public void run() {
        BoardCho boardCho = new BoardCho(receiveOption(Team.CHO));
        BoardHan boardHan = new BoardHan(receiveOption(Team.HAN));
        Board board = new Board(boardCho, boardHan);

        output.printBoard(boardHan, boardCho);

        ///
        String pieceTeamInfo = input.readPieceTeamName();
        Team teamName = Team.from(pieceTeamInfo);

        String pieceCurrentInfo = input.readPieceStartPoint();
        List<String> pieceCurrentInfos = Arrays.asList(pieceCurrentInfo.split("-"));

        String pieceName = pieceCurrentInfos.getFirst();
        Position pieceCurrentPosition = parsePosition(pieceCurrentInfos.getLast());

        System.out.println("해당 말의 이름과 시작 좌표가 해당 팀에 존재하는가? " + board.isAvailablePiece(teamName, pieceName, pieceCurrentPosition));

        String pieceMovedInfo  = input.readPieceDestination();
        Position pieceMovedPosition = parsePosition(pieceMovedInfo);
        // 이동할 위치에 우리 팀의 말이 있는지 확인
        validateMovedPosition(teamName, pieceMovedPosition, boardHan, boardCho);

        // 이동 위치 사이에 있는 좌표들 (실제로 이동가능한지 검증 필요)
        BoardNavigator boardNavigator = new BoardNavigator();
        List<Position> positionsOnPath = boardNavigator.findPositionsOnPath(pieceCurrentPosition, pieceMovedPosition);
        System.out.println("해당 말이 이동하고자 하는 경로에 어떤 말이 이미 차지하고 있는가? (포 제외) " + board.checkLegalMove(positionsOnPath));

        // 말 이동 업데이트
        if (teamName.equals(Team.CHO)) {
            boardCho.move(pieceName, pieceCurrentPosition,pieceMovedPosition);
        }
        if (teamName.equals(Team.HAN)) {
            boardHan.move(pieceName, pieceCurrentPosition,pieceMovedPosition);
        }

        output.printBoard(boardHan, boardCho);
    }

    public BoardOption receiveOption(Team team) {
        return BoardOption.of(team, input.readPositionOption(team));
    }

    public Position parsePosition(String piecePosition) {
        Pattern pattern = Pattern.compile("\\d+"); // \\d+는 연속된 숫자를 추출
        Matcher matcher = pattern.matcher(piecePosition);
        List<Integer> piecePoints = new ArrayList<>();
        while (matcher.find()) {
            piecePoints.add(Integer.parseInt(matcher.group()));
        }
        return new Position(piecePoints.getFirst(), piecePoints.getLast());
    }

    public void validateMovedPosition(Team teamName, Position pieceMovedPosition, BoardHan boardHan, BoardCho boardCho) {
        boolean isOccupied = false;
        if (teamName.equals(Team.CHO)) {
           isOccupied = boardCho.isOccupiedByOurTeamPiece(teamName, pieceMovedPosition);
        }
        if (teamName.equals(Team.HAN)) {
           isOccupied = boardHan.isOccupiedByOurTeamPiece(teamName, pieceMovedPosition);
        }
        System.out.println("해당 위치는 같은 팀의 말에 의해 막혀있는가? " + isOccupied);
    }
}
