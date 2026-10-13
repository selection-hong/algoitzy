class Solution {
private:
    static const int MAX_N = 100000;

    int firstChild[MAX_N];
    int nextSibling[MAX_N];
    int longest1[MAX_N];
    int longest2[MAX_N];

    int answer;

    // 상태 초기화
    void initialize(int n) {
        answer = 1;

        for (int i = 0; i < n; i++) {
            firstChild[i] = -1;
            nextSibling[i] = -1;
            longest1[i] = 0;
            longest2[i] = 0;
        }
    }

    // 부모 배열을 이용해 자식 목록 구성
    void buildTree(const int parent[], int n) {
        for (int child = 1; child < n; child++) {
            int p = parent[child];

            nextSibling[child] = firstChild[p];
            firstChild[p] = child;
        }
    }

    // 가장 긴 자식 경로 두 개 유지
    void updateLongestPaths(int node, int length) {
        if (length > longest1[node]) {
            longest2[node] = longest1[node];
            longest1[node] = length;
        } else if (length > longest2[node]) {
            longest2[node] = length;
        }
    }

    // 문자가 다른 자식의 경로만 연결
    void connectChild(
        int node,
        int child,
        int length,
        const string& s
    ) {
        if (s[node] == s[child]) {
            return;
        }

        updateLongestPaths(node, length);
    }

    // 두 갈래를 연결한 값으로 전체 정답 갱신
    void updateAnswer(int node) {
        int length = longest1[node] + longest2[node] + 1;
        answer = max(answer, length);
    }

    // 자식으로 내려갔다가 돌아오면서 경로 계산
    int dfs(int node, const string& s) {
        for (
            int child = firstChild[node];
            child != -1;
            child = nextSibling[child]
        ) {
            int length = dfs(child, s);
            connectChild(node, child, length, s);
        }

        updateAnswer(node);

        // 부모에게는 가장 긴 한 갈래만 반환
        return longest1[node] + 1;
    }

public:
    int longestPath(vector<int>& parent, string s) {
        int n = parent.size();

        initialize(n);
        buildTree(parent.data(), n);
        dfs(0, s);

        return answer;
    }
};