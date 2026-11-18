A = 0
B = 99
 
def binary_tree(a):
    global flag
 
    # 탐색 중 목적지(B=99)에 도달, 재귀 종료
    if a == B:
        flag = 1
        return

    visited[a] = True
 
    for i in adj[a]:
        if not visited[i] and flag == 0:
            binary_tree(i)
 
    pass
 
for _ in range(1, 11):
    test_case, E = map(int, input().split())
    info = list(map(int, input().split()))
    visited = [False] * 100
 
    adj = [[] for _ in range(100)]          # 인접 리스트로 접근
    for idx in range(E):
        p, c = info[idx*2], info[idx*2 + 1]
        adj[p].append(c)
 
    flag = 0
    binary_tree(A)
 
    print(f'#{test_case} {flag}')