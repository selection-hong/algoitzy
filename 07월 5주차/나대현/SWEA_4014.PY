def check_possible(line):
    n = len(line)
    visited = [False] * n

    for i in range(n-1):
        diff = line[i] - line[i+1]
        if diff == 0:
            continue
        if abs(diff) > 1:
            return False
        if diff == 1:
            for j in range(i+1, i+X+1):
                if j >= n or line[i+1] != line[j] or visited[j]:
                    return False
                visited[j] = True
        elif diff == -1:
            for j in range(i, i-X, -1):
                if j < 0 or line[i] != line[j] or visited[j]:
                    return False
                visited[j] = True
    return True

T = int(input())
for tc in range(1, T+1):
    N, X = map(int, input().split())
    arr = [list(map(int, input().split())) for _ in range(N)]

    ans = 0

    for r in range(N):
        if check_possible(arr[r]):
            ans += 1

    for c in range(N):
        col = [arr[r][c] for r in range(N)]
        if check_possible(col):
            ans += 1

    print(f'#{tc} {ans}')