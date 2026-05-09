import java.io.*;

class Main {

    static int[] parents; // 각 게이트가 가리키는 부모(다음에 이용 가능한 게이트 후보)
    static int c; // 커스텀 IO를 위한 현재 읽은 문자 저장 변수
    
    public static void main(String[] args) throws IOException {
        init();
        System.out.println(solve());
    }

    private static void init() throws IOException {
        c = System.in.read(); // 첫 번째 문자 읽기
        int n = readInt(); // 게이트의 수 G
        parents = new int[n + 1];

        // 초기 상태: 각 게이트는 자기 자신을 가리킴
        for(int i = 1; i <= n; i++) parents[i] = i;
    }

    private static int solve() throws IOException {
        int m = readInt(); // 비행기의 수 P
        for(int i = 0; i < m; i++) {
            // gi 이하의 게이트 중 비어 있는 가장 큰 게이트 번호를 찾음
            int p = find(readInt());
            
            // 만약 찾은 부모가 0이라면, 1~gi 사이에 더 이상 빈 게이트가 없다는 뜻 (도킹 불가)
            if(p == 0) return i;
            
            // 게이트 p를 사용했으므로, 이제 p번 게이트를 찾는 요청이 오면
            // p-1번 게이트(혹은 그보다 작은 빈 게이트)로 바로 가도록 집합을 합침
            union(p, p - 1);
        }
        return m; // 모든 비행기가 도킹에 성공한 경우
    }

    // 경로 압축(Path Compression)이 적용된 Find 연산
    // private static int find(int p) {
    //     if(parents[p] == p) return p;
    //     return parents[p] = find(parents[p]);
    // }

    /**
     * 반복문을 이용한 Find 연산 (Path Halving 방식 응용)
     * - 재귀 호출이 없어 깊은 트리 구조에서도 스택 메모리 문제가 발생하지 않음
     */
    private static int find(int p) {
        while(p != parents[p]) {
            // 경로 압축: 현재 노드의 부모를 부모의 부모로 건너뛰게 함 (트리 높이 축소)
            int temp = parents[p];
            parents[p] = parents[temp];
            p = temp;
        }
        return p;
    }

    // 게이트 매칭 후 부모를 왼쪽(번호가 작은 쪽)으로 연결
    private static void union(int a, int b) {
        int pB = find(b);
        parents[a] = pB; // a의 부모를 b의 루트로 설정
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