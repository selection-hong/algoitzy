import java.io.*;

// 폭탄 주변의 칸은 BFS를 탐색하지 않도록 미리 마킹
public class SWEA_1868_파핑파핑지뢰찾기_최동욱_전처리풀이 {

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
			inputArray(n); // 배열 정보 저장
			searchBomb(n); // 폭탄 위치 탐색
            sb.append('#').append(t).append(' ').append(countClick(n)).append('\n');
		}
        System.out.print(sb);
	}
	
	private static void inputArray(int n) throws IOException {
		head = 0; tail = 0;
		for(int i = 0; i < n; i++) {
            while(c <= ' ') c = System.in.read();
			for(int j = 0; j < n; j++) {
				if(c == '.') {
					arr[i << BIT_SHIFT | j] = 0;
				} else {
					arr[i << BIT_SHIFT | j] = 2;
					deque[tail++] = i << BIT_SHIFT | j;
				}
                c = System.in.read();
			}
		}
	}
	
	// 폭탄 위치 탐색
	private static void searchBomb(int n) {
		while(head < tail) {
			int cur = deque[head++];
			// 폭탄 주변의 칸 미리 마킹 (전처리)
			for(int d : DICT) {
	            int next = cur + d;
				if(check(next, n) && arr[next] == 0) arr[next] = -1;
			}
		}
	}
	
	// 클릭 수 구하기
	private static int countClick(int n) {
		int cnt = 0;
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
                int cur = i << BIT_SHIFT | j;
				if(arr[cur] == 0) {
					bfs(n, cur);
					cnt++;
				}
			}
		}
		
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				if(arr[i << BIT_SHIFT | j] == -1) cnt++;
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
					if(arr[next] == 0) {
						deque[tail++] = next;
					}
					arr[next] = 1;
				}
			}
		}
	}
	
	// 범위 체크
	private static boolean check(int next, int n) {
        int y = next >> BIT_SHIFT;
        int x = next & ((1 << BIT_SHIFT) - 1);
		return y >= 0 && y < n && x >= 0 && x < n;
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