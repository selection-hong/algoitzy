# 1520 BOJ 내리막 길
import sys
sys.setrecursionlimit(500 * 500 * 10)   # 문제 최대 격자 수 만큼 재귀 한도를 설정
input = sys.stdin.readline              # 파이썬은 보통 재귀 깊이가 1000 넘으면 오류 발생

def route(row, col):
    # 목적지(행렬의 우하단)에 도착하면 경로 수 1 증가, 경로 1개 발견으로 처리
    # memo[row][col] += route(nr, nc) 부분에서 증가분 반영
    if row == M - 1 and col == N - 1:
        return 1

    # 메모이제이션 활용, 시간 복잡도를 줄이는 방법
    # 이미 계산된 칸 여부를 '-1' 기준으로 판단
    # 같은 칸을 여러 경로에서 방문해도 딱 한 번만 실제로 계산
    if memo[row][col] != -1:
        return memo[row][col]

    # 첫 방문 칸 0으로 초기화(탐색 중인 상태, 아직 경로를 찾지 못한 상태 의미)
    memo[row][col] = 0

    # 다음 좌표 탐색 시작, 4방향 델타 탐색
    for dr, dc in [(-1, 0), (1, 0), (0, -1), (0, 1)]:
        nr, nc = row + dr, col + dc

        # 경계값 설정
        if 0 <= nr < M and 0 <= nc < N:

            # 다음 칸이 현재 칸보다 낮을 때(내리막 탐색) 이동
            if downhill[nr][nc] < downhill[row][col]:
                # 다음 칸에서 목적지까지의 경로 수를 누적
                memo[row][col] += route(nr, nc)

    # 계산 완료된 값 반환
    return memo[row][col]

M, N = map(int, input().split())
downhill = [list(map(int, input().split())) for _ in range(M)]
# 메모이제이션 2차원 리스트를 생성, 모든 값은 -1로 설정
# (0: 막힌 길, 1 이상: 목적지까지 연결된 길의 수)
memo = [[-1]*N for _ in range(M)]
# 계산 완료된 값 반환
print(route(0, 0))
# print(memo)
# 입력
# 4 5
# 50 45 37 32 30
# 35 50 40 20 25
# 30 30 25 17 28
# 27 24 22 15 10
# 메모이제이션 출력
# [[3, 2, 2, 2, 1],
#  [1, -1, -1, 1, 1],
#  [1, -1, -1, 1, -1],
#  [1, 1, 1, 1, -1]]
