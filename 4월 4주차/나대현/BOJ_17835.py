# 17835 BOJ 면접보는 승범이네
import sys
import heapq

input = sys.stdin.readline

N, M, K = map(int, input().split())

# reversed_graph[b] = [(a, c), ...]
# 원래 a -> b (비용 c) 였다면,
# 뒤집어서 b -> a (비용 c) 로 저장
graph = [[] for _ in range(N + 1)]

for _ in range(M):
    a, b, c = map(int, input().split())
    graph[b].append((a, c))

interviews = list(map(int, input().split()))

INF = 10**18
dist = [INF] * (N + 1)

pq = []

# 다중 시작점 다익스트라
for city in interviews:
    dist[city] = 0
    heapq.heappush(pq, (0, city))

while pq:
    cur_dist, cur_city = heapq.heappop(pq)

    if cur_dist != dist[cur_city]:
        continue

    for next_city, cost in graph[cur_city]:
        nd = cur_dist + cost
        if nd < dist[next_city]:
            dist[next_city] = nd
            heapq.heappush(pq, (nd, next_city))

# 가장 먼 도시 찾기
answer_city = 1
answer_dist = -1

for city in range(1, N + 1):
    if dist[city] > answer_dist:
        answer_dist = dist[city]
        answer_city = city
    elif dist[city] == answer_dist and city < answer_city:
        answer_city = city

print(answer_city)
print(answer_dist)