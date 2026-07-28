# 4485 BOJ 녹색 옷 입은 애가 젤다지?
# 2차원 행렬 [0, 0]에서 [N-1, N-1]까지 이동하는 경로 중 숫자 최소 합 구하는 문제
import heapq

INF = int(1e9)

# 상하좌우 이동을 위한 방향 벡터
dr = [-1, 1, 0, 0]
dc = [0, 0, -1, 1]

count = 1
while True:
    N = int(input())
    if N == 0:
        break

    graph = [list(map(int, input().split())) for _ in range(N)]

    # 최단 거리 테이블을 모두 무한대로 초기화
    distance = [[INF] * N for _ in range(N)]


    # 다익스트라 알고리즘 수행
    def dijkstra():
        q = []
        # 시작 지점 설정 (비용, r, c)
        heapq.heappush(q, (graph[0][0], 0, 0))
        distance[0][0] = graph[0][0]

        while q:
            # 가장 최단 거리가 짧은 노드에 대한 정보 꺼내기
            dist, r, c = heapq.heappop(q)

            # 현재 노드가 이미 처리된 적이 있다면 무시
            if distance[r][c] < dist:
                continue

            # 현재 노드와 연결된 인접한 노드들 확인 (상하좌우)
            for i in range(4):
                nr = r + dr[i]
                nc = c + dc[i]

                # 맵 범위 내에 있는지 확인
                if 0 <= nr < N and 0 <= nc < N:
                    cost = dist + graph[nr][nc]
                    # 현재 노드를 거쳐서 이동하는 게 더 짧은 경우
                    if cost < distance[nr][nc]:
                        distance[nr][nc] = cost
                        heapq.heappush(q, (cost, nr, nc))


    dijkstra()

    print(f"Problem {count}: {distance[N - 1][N - 1]}")
    count += 1