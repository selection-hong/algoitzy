import java.io.*;

public class SWEA_1868_파핑파핑지뢰찾기_최동욱 {

    final static int BIT_SHIFT = 9; // 행렬 평탄화
    // 방향 벡터 (8방향)
	final static int[] DICT = {
        -1, 1,
        -(1 << BIT_SHIFT), (1 << BIT_SHIFT),
        -(1 << BIT_SHIFT) - 1,
        -(1 << BIT_SHIFT) + 1,
        (1 << BIT_SHIFT) - 1,
        (1 << BIT_SHIFT) + 1
    };

    static int[] arr = new int[(300 << BIT_SHIFT) | 300];
    static int[] deque = new int[300 * 300]; 
    static int c, head, tail;
	
	public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        c = System.in.read();
		int T = readInt();
		for(int t = 1; t <= T; t++) {
            int n = readInt();			
			inputArray(n); 	// 배열 정보 저장
			int clicks = countClick(n); // 클릭 횟수 구하기
            sb.append('#').append(t).append(' ').append(clicks).append('\n');
		}
        System.out.print(sb);
	}
	
	private static void inputArray(int n) throws IOException {
		for(int i = 0; i < n; i++) {
            while(c <= ' ') c = System.in.read();
			for(int j = 0; j < n; j++) {
				arr[i << BIT_SHIFT | j] = c == '.' ? 0 : 2;	// 지뢰 위치 구분
                c = System.in.read();
			}
		}
	}
    
	private static int countClick(int n) {
		int cnt = 0;
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
                int cur = i << BIT_SHIFT | j;
                // 아직 방문하지 않았고, 주변에 지뢰가 없는 칸 BFS 탐색
				if(arr[cur] == 0 && checkBomb(cur, n)) {
					bfs(n, cur);
					cnt++;
				}
			}
		}
		
		// 남은 위치 전부 클릭
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				if(arr[i << BIT_SHIFT | j] <= 0) cnt++;
			}
		}
		
		return cnt;
	}
	
	// bfs 탐색
	private static void bfs(int n, int s) {
        head = 0; tail = 0;
        deque[tail++] = s;
		arr[s] = 1;
		
		while(head < tail) {
			int cur = deque[head++];
			
			for(int d : DICT) {
                int next = cur + d;
				if(check(next, n) && arr[next] <= 0) {
					// 선택하지 않은 위치이고 주변에 지뢰가 없다면 탐색 
					if(arr[next] == 0 && checkBomb(next, n)) {
						deque[tail++] = next;
					}
					arr[next] = 1;
				}
			}
		}
	}
	
	// 배열 범위 조절
	private static boolean check(int next, int n) {
        int y = next >> BIT_SHIFT;
        int x = next & ((1 << BIT_SHIFT) - 1);
		return y >= 0 && y < n && x >= 0 && x < n;
	}
	
	// 주변에 지뢰 확인, 지뢰가 있다면 -1로 변경(중복 탐색 줄이기)
    private static boolean checkBomb(int cur, int n) {
        for(int d : DICT) {
            int next = cur + d;
            if(check(next, n) && arr[next] == 2) {
                arr[cur] = -1;
                return false;
            }
        }
        return true;
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
