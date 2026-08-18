import java.util.*;
import java.io.*;

public class Main {

    private static class Ship {
        int p, r;             // p: 파워, r: 재장전 대기 시간
        int ver = 1;          // 현재 버전
        int time = 0;         // 쿨타임
        boolean ready = true; // true: 대기 상태, false: 재장전

        Ship(int p, int r) {
            this.p = p;
            this.r = r;
        }
    }

    static Map<Integer, Ship> map = new HashMap<>();

    // 공격 대기 큐, 0: 공격력, 1: 선박 번호, 2: 버전
    static PriorityQueue<int[]> ready = new PriorityQueue<>((a, b) -> {
        if(a[0] != b[0]) return b[0] - a[0];
        else return a[1] - b[1];
    });

    // 재장전 큐, 0: 시간, 1: 선박 번호, 2: 버전
    static PriorityQueue<int[]> reload = new PriorityQueue<>((a, b) -> {
        return a[0] - b[0];
    });

    static int c;

    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        c = System.in.read();

        int T = readInt();
        inputShip(); // 공격 준비

        for(int t = 1; t < T; t++) {
            int comm = readInt();
            if(comm == 200) {        // 지원 요청
                requestSupport();
            } else if(comm == 300) { // 함포 교체
                change();
            } else if(comm == 400) { // 공격 명령
                sb.append(attack(t)).append('\n');
            }
        }

        System.out.println(sb);
    }

    private static void inputShip() throws IOException {
        int comm = readInt();
        int n = readInt();

        while(n-- > 0) {
            int d = readInt();
            int p = readInt();
            int r = readInt();
            Ship s = new Ship(p, r);
            map.put(d, s);

            // 공격 대기 상태
            ready.add(new int[]{p, d, 1});
        }
    }

    private static void requestSupport() throws IOException {
        int id = readInt();
        int p = readInt();
        int r = readInt();
        
        // 대기 상태 선박 추가
        Ship s = new Ship(p, r);
        map.put(id, s);

        // 추가 선박 -> 공격 대기 상태
        ready.add(new int[]{p, id, 1});
    }

    private static void change() throws IOException {
        int id = readInt();
        int pw = readInt();

        Ship s = map.get(id);
        s.p = pw;
        s.ver++;

        // true -> 공격 대기 큐, false -> 재장전 큐
        if(s.ready) {
            ready.add(new int[]{s.p, id, s.ver});
        } else {
            reload.add(new int[]{s.time, id, s.ver});
        }
    }

    private static String attack(int time) throws IOException {
        while(!reload.isEmpty() && reload.peek()[0] <= time) {
            int[] cur = reload.poll();
            int id = cur[1];
            int ver = cur[2];

            // Lazy Delete (지연 삭제)
            if(map.get(id).ver > ver) continue;

            // 공격 대기 상태로 전환
            ready.add(new int[]{map.get(id).p, id, ver});
            map.get(id).ready = true;
        }

        StringBuilder sb = new StringBuilder();
        int cnt = 0, damage = 0;
        while(!ready.isEmpty() && cnt < 5) {
            int[] cur = ready.poll();
            int p = cur[0];
            int id = cur[1];
            int ver = cur[2];

            // Lazy Delete (지연 삭제)
            if(map.get(id).ver > ver) continue;

            // 재장전 상태로 전환
            reload.add(new int[]{time + map.get(id).r, id, ver});
            map.get(id).ready = false;

            // 상태 갱신
            damage += p;
            cnt++;

            sb.append(' ').append(id);
        }

        return damage + " " + cnt + sb.toString();
    }

    // Fast IO
    private static int readInt() throws IOException {
        while(c <= ' ') c = System.in.read();
        int n = 0;
        while(c >= '0' && c <= '9') {
            n = (n << 3) + (n << 1) + (c & 15);
            c = System.in.read();
        }
        return n;
    }
}
