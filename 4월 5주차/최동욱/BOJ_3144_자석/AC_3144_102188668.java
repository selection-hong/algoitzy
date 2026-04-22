/**
 * [BOJ] 3144 - 자석
 * - 제출 날짜: 2026년 1월 22일
 * - 결과: 맞았습니다!!
 * - 메모리: 51196 KB
 * - 시간: 272 ms
 */

import java.util.*;
import java.io.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int l = Integer.parseInt(st.nextToken());

        ArrayList<Integer> list = inputArray(n, st, br);
        System.out.println(solve(list, l));
    }

    private static ArrayList<Integer> inputArray(int n, StringTokenizer st, BufferedReader br) throws IOException {
        st = new StringTokenizer(br.readLine());
        String magnet = st.nextToken();
        int cnt = 1;
        
        ArrayList<Integer> list = new ArrayList<>();
        while(st.hasMoreTokens()) {
            String temp = st.nextToken();
            if(!magnet.equals(temp)) {
                list.add(cnt);
                cnt = 0;
                magnet = temp;
            }
            cnt++;
        }
        list.add(cnt);

        return list;
    }

    private static int solve(ArrayList<Integer> list, int l) {
        int left = 0, right = 1, cnt = list.get(0);
        int minValue = Integer.MAX_VALUE;
        
        while(left < list.size()) {
            while(right < list.size() && cnt < l) {
                cnt += list.get(right++);
            }
            
            if(cnt == l && ((right - left) % 2 == 1 || (left == 0 || right == list.size()))) {
                minValue = Math.min(minValue, right - left);
            }
    
            cnt -= list.get(left++);
        }
            
        return minValue / 2;
    }
}