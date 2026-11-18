# diff[x] = (x의 무게) - (parent[x]의 무게)
# find 이후에는 diff[x] = (x의 무게) - (루트의 무게)

def find(x, parent, diff):
    if parent[x] != x:
        p = parent[x]
        parent[x] = find(p, parent, diff)  # 경로 압축 (p의 diff가 루트 기준으로 갱신됨)
        diff[x] += diff[p]                 # x의 diff를 루트 기준으로 누적
    return parent[x]

def union(a, b, w, parent, diff, rank):
    # w = (b의 무게) - (a의 무게)
    root_a = find(a, parent, diff)
    root_b = find(b, parent, diff)

    if root_a == root_b:  # 이미 같은 집합이면 무시
        return

    # union by rank: 낮은 rank 쪽을 높은 rank 쪽에 붙임
    if rank[root_a] < rank[root_b]:
        parent[root_a] = root_b
        diff[root_a] = diff[b] - diff[a] - w  # root_a의 무게 - root_b의 무게
    else:
        parent[root_b] = root_a
        diff[root_b] = diff[a] - diff[b] + w  # root_b의 무게 - root_a의 무게

        if rank[root_a] == rank[root_b]:
            rank[root_a] += 1

# 메인 루프
T = int(input())

for tc in range(1, T + 1):
    N, M = map(int, input().split())

    parent = list(range(N + 1))
    diff = [0] * (N + 1)
    rank = [0] * (N + 1)

    answers = []

    for _ in range(M):
        command, *values = input().split()

        if command == "!":  # 측정 결과 추가
            a, b, w = map(int, values)
            union(a, b, w, parent, diff, rank)
        elif command == "?":  # 무게 차이 질의
            a, b = map(int, values)

            root_a = find(a, parent, diff)
            root_b = find(b, parent, diff)

            if root_a != root_b:  # 다른 집합이면 알 수 없음
                answers.append("UNKNOWN")
            else:
                answers.append(str(diff[b] - diff[a]))  # 같은 루트 기준이므로 차이 계산 가능

    print(f"#{tc}", *answers)