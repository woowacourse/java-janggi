package janggi.team;

import janggi.piece.*;
import janggi.position.Position;

import java.util.*;

public class TeamCho {
    private List<Piece> pieces;

    // TODO 추후 상마-마상 패턴에 따라 장기판 초기화 -> 이후 board 객체에게 위임: x[0왼-8], y[0하-9]
    public TeamCho() {
        pieces = new ArrayList<>(List.of(
                new King(new Position(4, 1)),
                new Horse(new Position(2, 0)), new Horse(new Position(7, 0)),
                new Elephant(new Position(1, 0)), new Elephant(new Position(6, 0)),
                new Cannon(new Position(1, 2)), new Cannon(new Position(7, 2)),
                new Chariot(new Position(0, 0)), new Chariot(new Position(8, 0)),
                new SoldierCho(new Position(0, 3)),
                new SoldierCho(new Position(2, 3)),
                new SoldierCho(new Position(4, 3)),
                new SoldierCho(new Position(6, 3)),
                new SoldierCho(new Position(8, 3)),
                new Guard(new Position(3, 0)), new Guard(new Position(5, 0))
        ));
    }

    public List<Position> isLegalPositions(Piece selectedPiece, List<Piece> teamHanPieces) {
        List<Position> possiblePositions = selectedPiece.checkPossibleMoves(); //사용자가 선택한 말이 이동가능한 위치들

        Map<Position, Boolean> finalAllowedPositions = new HashMap<>();

        //자기 자신의 진영에 대해 확인
        for (Piece piece : pieces) {
            if (piece.equals(selectedPiece)) { // 자기 자신 말에 대해서는 확인하지 않는다
                continue;
            }
            // 참이라면, 해당 좌표는 이미 차지하고 있는 것. 기존 값이 false인데(비어있는데), true가 반환된다면 값 덮어쓰기 //참 == 자리가 차있다
            Map<Position, Boolean> allowedPosition = piece.isAlreadyLocatedWithinOneSpaceOrAtThatSpace(selectedPiece);
            allowedPosition.forEach((position, isNotAllowed) ->
                    finalAllowedPositions.merge(position, isNotAllowed, (oldValue, newValue) -> oldValue ? true : newValue)
            );
            // 각 말에 대해 확인 //여기서 Position은 선택한 말의 위치 <- 각 piece에 대해 반환된 position, boolean 값에 대해 false인 경우, true를 덮어씌운다 (이동 불가능하다는 뜻)
            // 선택한 말의 한칸 앞에 아무 말이 없다 == 어떤 말에게 묻는다: 사용자가 선택한 말의 한칸 앞에 그 어떤 말이 이미 있는가?
            // 그 어떤 말의 입장에서 자기 현 위치와 == 선택한 말이 이동하고자 하는 위치 목록들 순회하며 비교->  true returned: 그 어떤 말은 선택한 말의 한칸 앞에 없다는 뜻
        }

        //상대 팀 진영에 대해 확인
        for (Piece piece : teamHanPieces) {
            Map<Position, Boolean> allowedPositionRelativeToOpponent = piece.isAlreadyLocatedWithinOneSpaceOrAtThatSpace(selectedPiece);
            allowedPositionRelativeToOpponent.forEach((position, isNotAllowed) ->
                    finalAllowedPositions.merge(position, isNotAllowed, (oldValue, newValue) -> oldValue ? true : newValue)
            );
        }

        List<Position> actualPossiblePositions = finalAllowedPositions.entrySet()
                .stream()
                .filter(Map.Entry::getValue) // Boolean 값이 false인 경우만 필터링!!!!고쳐야됨
                .map(Map.Entry::getKey) // Position 값만 추출
                .toList();

        return actualPossiblePositions;
        // 해당 말이 장기 판 내에서 이동 가능한 위치 (selectedPiece.possibleMoves)에 대해
        // 초가 가진 말들을 순회한다
        // case a; 한칸 앞을 확인해서 비어있는 경우 + 이동할 위치에 말이 없는 경우 legalMove 후보 대상에서 배제되지 않는다: 차, 왕, 사, 병졸, 상, 마
        // todo case b; 이동할 위치와 현 위치 사이에 말이 단 하나만 있는 경우(단, 넘는 대상이 포인 경우 제외) + 이동할 위치에 말이 없는 경우 legalMove 후보 대상에서 배제되지 않는다 : 포

        // 한이 가진 말들을 순회한다
        // case a;
        // todo case b;
        // 최종 legalMove 후보 위치 목록을 사용자에게 반환한다

        // 인풋: 사용자가 특정 위치 좌표를 선택한다
        // 말... 해당 위치로 갱신된다 // 턴.... 다음 진영으로 변환한다
    }

}
