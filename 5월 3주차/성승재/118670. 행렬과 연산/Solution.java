import java.util.*;

class Solution {
    private ArrayDeque<Integer> left;
    private ArrayDeque<Integer> right;
    private ArrayDeque<ArrayDeque<Integer>> middleRows;

    private int rLength;
    private int cLength;

    public int[][] solution(int[][] rc, String[] operations) {
        rLength = rc.length;
        cLength = rc[0].length;

        left = new ArrayDeque<>();
        right = new ArrayDeque<>();
        middleRows = new ArrayDeque<>();

        // 행렬을 left / middle / right 로 분리
        for (int i = 0; i < rLength; i++) {
            left.addLast(rc[i][0]);
            right.addLast(rc[i][cLength - 1]);

            ArrayDeque<Integer> middle = new ArrayDeque<>();
            for (int j = 1; j < cLength - 1; j++) {
                middle.addLast(rc[i][j]);
            }
            middleRows.addLast(middle);
        }

        for (String operation : operations) {
            if (operation.equals("ShiftRow")) {
                shiftRow();
            } else {
                rotate();
            }
        }

        return buildAnswer();
    }

    private void shiftRow() {
        left.addFirst(left.pollLast());
        right.addFirst(right.pollLast());
        middleRows.addFirst(middleRows.pollLast());
    }

    private void rotate() {
        // 열이 2개인 경우 middleRows 내부가 비어 있으므로 따로 처리
        if (cLength == 2) {
            right.addFirst(left.pollFirst());
            left.addLast(right.pollLast());
            return;
        }
        
        ArrayDeque<Integer> topMiddle = middleRows.peekFirst();
        ArrayDeque<Integer> bottomMiddle = middleRows.peekLast();

        // 왼쪽 위 -> 위쪽 가운데
        topMiddle.addFirst(left.pollFirst());

        // 위쪽 가운데 오른쪽 끝 -> 오른쪽 위
        right.addFirst(topMiddle.pollLast());

        // 오른쪽 아래 -> 아래쪽 가운데 오른쪽 끝
        bottomMiddle.addLast(right.pollLast());

        // 아래쪽 가운데 왼쪽 끝 -> 왼쪽 아래
        left.addLast(bottomMiddle.pollFirst());
    }

    private int[][] buildAnswer() {
        int[][] answer = new int[rLength][cLength];

        for (int i = 0; i < rLength; i++) {
            answer[i][0] = left.pollFirst();

            ArrayDeque<Integer> middle = middleRows.pollFirst();
            for (int j = 1; j < cLength - 1; j++) {
                answer[i][j] = middle.pollFirst();
            }

            answer[i][cLength - 1] = right.pollFirst();
        }

        return answer;
    }
}
