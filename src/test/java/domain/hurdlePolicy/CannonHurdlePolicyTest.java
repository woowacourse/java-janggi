package domain.hurdlePolicy;

class CannonHurdlePolicyTest {
    private final CannonHurdlePolicy policy = new CannonHurdlePolicy();

//    @DisplayName("다른 기물이 존재하지 않는 경우, 포는 움직일 수 없다.")
//    @Test
//    void notExistOtherPieces() {
//        // given
//        final List<Path> coordinates = List.of(
//                new Path(List.of(
//                        JanggiPositionFactory.of(4, 4),
//                        JanggiPositionFactory.of(3, 4),
//                        JanggiPositionFactory.of(2, 4),
//                        JanggiPositionFactory.of(1, 4),
//                        JanggiPositionFactory.of(0, 4)
//                )),
//                new Path(List.of(
//                        JanggiPositionFactory.of(5, 3),
//                        JanggiPositionFactory.of(5, 2),
//                        JanggiPositionFactory.of(5, 1),
//                        JanggiPositionFactory.of(5, 0)
//                )),
//                new Path(List.of(
//                        JanggiPositionFactory.of(5, 5),
//                        JanggiPositionFactory.of(5, 6),
//                        JanggiPositionFactory.of(5, 7),
//                        JanggiPositionFactory.of(5, 8)
//                )),
//                new Path(List.of(
//                        JanggiPositionFactory.of(6, 4),
//                        JanggiPositionFactory.of(7, 4),
//                        JanggiPositionFactory.of(8, 4),
//                        JanggiPositionFactory.of(9, 4)
//                ))
//        );
//        JanggiPiecePositions positions = new JanggiPiecePositions(new EmptyPositionsGenerator());
//
//        // when
//        List<JanggiPosition> destinations = policy.pickDestinations(JanggiTeam.BLUE, coordinates, positions);
//
//        // then
//        assertThat(destinations).isEmpty();
//    }
//
//    @DisplayName("다른 기물이 존재하지 않는 경우, 포는 움직일 수 없다.")
//    @Test
//    void notExistOtherPieces() {
//        // given
//        final List<Path> coordinates = List.of(
//                new Path(List.of(
//                        JanggiPositionFactory.of(4, 3),
//                        JanggiPositionFactory.of(3, 3),
//                        JanggiPositionFactory.of(2, 3),
//                        JanggiPositionFactory.of(1, 3),
//                        JanggiPositionFactory.of(0, 3)
//                )),
//                new Path(List.of(
//                        JanggiPositionFactory.of(5, 2),
//                        JanggiPositionFactory.of(5, 1),
//                        JanggiPositionFactory.of(5, 0)
//                )),
//                new Path(List.of(
//                        JanggiPositionFactory.of(5, 4),
//                        JanggiPositionFactory.of(5, 5),
//                        JanggiPositionFactory.of(5, 6),
//                        JanggiPositionFactory.of(5, 7),
//                        JanggiPositionFactory.of(5, 8)
//                )),
//                new Path(List.of(
//                        JanggiPositionFactory.of(6, 3),
//                        JanggiPositionFactory.of(7, 3),
//                        JanggiPositionFactory.of(8, 3),
//                        JanggiPositionFactory.of(9, 3)
//                ))
//        );
//        JanggiPiecePositions positions = new JanggiPiecePositions(new EmptyPositionsGenerator());
//
//        // when
//        List<JanggiPosition> destinations = policy.pickDestinations(JanggiTeam.BLUE, coordinates, positions);
//
//        // then
//        assertThat(destinations).isEmpty();
//    }
}
