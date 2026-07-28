import java.io.*;
import java.util.*;

// 프로그래머스 | 2019 KAKAO BLIND RECRUITMENT | 무지의 먹방 라이브
public class Main {
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        // 테스트 케이스
        int[] foods = {3, 1, 2};
        long k = 5;
        
        // 실행 및 결과 출력
        System.out.println(sol.solution(foods, k)); // 기댓값: 1
    }
}

class Solution {
    public int solution(int[] food_times, long k) {
        // cnt : 남은 음식 개수 | sum : 전체 음식 먹는 시간 | round : 몇 바퀴 돌았는지 계산
        long cnt = food_times.length;
        long sum = 0;
        long round = 0;
        
        // 시간 적은 순, 번호 작은 순
        PriorityQueue<int[]> pq1 = new PriorityQueue<>((a, b)->{
            if (a[1] == b[1]) return Integer.compare(a[0], b[0]);
            return Integer.compare(a[1], b[1]);
        });
        
        for (int i = 0; i < cnt; i++) {
            sum += food_times[i];
            pq1.add(new int[] {i+1, food_times[i]});
        }
        
        // 만약 전체 음식을 먹는 시간보다 k가 크면 return -1
        if (sum <= k) return -1;
        
        // 먹는 시간이 가장 적게 남은 음식을 다 먹을 때까지 걸리는 시간을 구해 k에서 한 번에 빼주는 방식
        while (!pq1.isEmpty()) {
            long need = (pq1.peek()[1] - round) * cnt;
            if (need <= k) {
                cnt--;
                k -= need;
                round = pq1.poll()[1];
            } else break;
        }
        
        // k가 need보다 작아지면 아직 다 먹지 않은 음식만 카운트하여 정답 도출
        long jump = k % cnt;
        int idx;
        for (idx = 0; idx < food_times.length; idx++) {
            if (food_times[idx] - round > 0) jump--;
            if (jump < 0) break;
        }
        
        return idx + 1; // 음식 번호 1부터 시작
    }
}