import java.io.*;

public class Main {

    static int[][] map; // 입력값 저장용 배열
    static int c;       // FastIO 변수

    public static void main(String[] args) throws IOException {
        c = System.in.read();
        int n = readInt();      // 배열 Size
        inputArray(n);          // 입력값 저장
        System.out.println(calMaxAndMin(n));
    }

    // 배열 저장
    private static void inputArray(int n) throws IOException {
        map = new int[n][n];
        for(int y = 0; y < n; y++) {
            for(int x = 0; x < n; x++) {
                int val = inputValue(y, x); // FastIO
                map[y][x] = val;
            }
        }
    }

    // 최댓값과 최솟값 연산
    private static String calMaxAndMin(int n) {
        // 최댓값과 최솟값을 저장할 DP 배열 각각 생성
        int[][] maxValue = new int[n][n];
        int[][] minValue = new int[n][n];

        maxValue[0][0] = map[0][0];
        minValue[0][0] = map[0][0];
        for(int y = 0; y < n; y++) {
            for(int x = 0; x < n; x++) {
                if(y == 0 && x == 0) continue; // 시작 지점..
                if(((y + x) & 1) == 0) { 
                    // 해당 칸이 숫자가 기입된 칸인 경우
                    calValue(maxValue, minValue, y, x);
                } else {
                    // 해당 칸이 연산자가 기입된 칸인 경우
                    setValue(maxValue, minValue, y, x);
                }
            }
        }
        return maxValue[n-1][n-1] + " " + minValue[n-1][n-1];
    }

    // 해당 경로의 최댓값과 최솟값을 계산
    private static void calValue(int[][] max, int[][] min, int y, int x) {
        if(y == 0) {
            // 0행 => 왼쪽에서 오른쪽으로 이동하는 경우
            int val = getValue(max[y][x-1], map[y][x-1], map[y][x]);

            // 경로가 하나로 고정되어 최댓값과 최솟값이 동일함
            max[y][x] = val;
            min[y][x] = val;
        } else if(x == 0){
            // 0열 => 위에서 아래로 내려오는 경우
            int val = getValue(max[y-1][x], map[y-1][x], map[y][x]);

            // 경로가 하나로 고정되어 최댓값과 최솟값이 동일함
            max[y][x] = val;
            min[y][x] = val;
        } else {
            int num = map[y][x];
            // 최댓값 갱신
            max[y][x] = getMaxValue(max, y, x, num);
            // 최솟값 갱신
            min[y][x] = getMinValue(min, y, x, num);
        }
    }

    // 최댓값 계산
    private static int getMaxValue(int[][] arr, int y, int x, int num) {
        int val1 = getValue(arr[y-1][x], map[y-1][x], num);
        int val2 = getValue(arr[y][x - 1], map[y][x - 1], num);
        return Math.max(val1, val2);
    }

    // 최솟값 계산
    private static int getMinValue(int[][] arr, int y, int x, int num) {
        int val1 = getValue(arr[y-1][x], map[y-1][x], num);
        int val2 = getValue(arr[y][x - 1], map[y][x - 1], num);
        return Math.min(val1, val2);
    }

    // 연산
    private static int getValue(int num1, int opt, int num2) {
        // 연산자의 종류가 '+' 또는 '-'로 고정됨
        return opt == '+' ? num1 + num2 : num1 - num2;
    }

    // 연산자가 있는 칸에서 최솟값, 최댓값 갱신
    private static void setValue(int[][] max, int[][] min, int y, int x) {
        if(y == 0) {
            max[y][x] = max[y][x-1];
            min[y][x] = min[y][x-1];
        } else if(x == 0) {
            max[y][x] = max[y-1][x];
            min[y][x] = min[y-1][x];
        } else {
            max[y][x] = Math.max(max[y][x-1], max[y-1][x]);
            min[y][x] = Math.min(min[y][x-1], min[y-1][x]);
        }
    }


    // FastIO 기반 입력 전처리
    private static int inputValue(int y, int x) throws IOException {
        while(c <= ' ') c = System.in.read();
        int val = ((y + x) & 1) == 0 ? (c & 15) : c;
        c = System.in.read();
        return val;
    }

    // FastIO
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
