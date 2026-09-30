# 1949 SWEA 등산로 조성
dx = [-1, 1, 0, 0]
dy = [0, 0, -1, 1]

def hike_mtn(r, c, cut):
    """
    r, c: 현재 위치
    cut: 공사 진행 여부
        0 -> 아직 공사 안 함
        1 -> 이미 공사함
    return: 현재 위치부터 만들 수 있는 가장 긴 등산로 길이
    """

    result = 1              # 현재 위치 포함 (길이 1부터 시작)
    visited[r][c] = True    # 현재 위치 방문 처리

    for i in range(4):      # 4방향 델타
        nr, nc = r + dx[i], c + dy[i]
        # 경계값 범위 설정, 아직 방문하지 않은 경우만 탐색 시작
        if 0 <= nr < N and 0 <= nc < N and not visited[nr][nc]:
            # 1. 다음 칸이 현재 칸보다 낮다면 이동
            if grid[nr][nc] < grid[r][c]:
                result = max(result, 1 + hike_mtn(nr, nc, cut))
            # 2. 다음 칸이 현재 칸보다 낮지 않고, 아직 공사하지 않았다면
            elif cut == 0:      # 각 경로 당 1번의 공사만 가능, 공사하지 않았다면 공사를 시작
                new_h = grid[r][c] - 1      # 다음 칸을 현재 칸보다 1 낮게 깎기
                if new_h >= 0 and grid[nr][nc] - new_h <= K:    # 최대 공사 가능 깊이 이하인지 확인
                    original = grid[nr][nc] # 원래 높이 담는 변수 (복구 목적으로 활용)
                    grid[nr][nc] = new_h    # 새로운 높이로 변경
                    result = max(result, 1 + hike_mtn(nr, nc, 1))   # 공사 여부 표시, DFS 진행
                    grid[nr][nc] = original # DFS 종료 후 원래 상태로 복구
    # 현재 위치에 대해서 방문을 해제, 다른 경로에서도 사용할 수 있도록
    visited[r][c] = False
    return result   # 현재 위치에서 최대 길이 반환

T = int(input())

for tc in range(1, T + 1):
    N, K = map(int, input().split())
    grid = [list(map(int, input().split())) for _ in range(N)]

    max_h = max(max(row) for row in grid)       # 가장 높은 봉우리 찾기 (시작점 후보)
    visited = [[False] * N for _ in range(N)]   # 방문 체크 배열 생성

    ans = 0                 # 전체 중 가장 긴 등산로 길이를 담을 변수
    for r in range(N):
        for c in range(N):
            if grid[r][c] == max_h: # 최고 높이인 지점에서 시작 (최고 높이인 지점 여러 개)
                ans = max(ans, hike_mtn(r, c, 0))   # DFS 방식으로 탐색 시작

    print(f'#{tc} {ans}')

# 2
# 5 1
# 9 3 2 3 2
# 6 3 1 7 5
# 3 4 8 9 9
# 2 3 7 7 7
# 7 6 5 5 8
# 3 2
# 1 2 1
# 2 1 2
# 1 2 1