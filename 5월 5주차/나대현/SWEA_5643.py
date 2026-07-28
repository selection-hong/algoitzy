from collections import deque

def compare_bfs(start, graph, N):
    queue = deque([start])
    visited = [False] * (N + 1)
    visited[start] = True
    count = 0
    
    while queue:
        current = queue.popleft()
        for neighbor in graph[current]:
            if not visited[neighbor]:
                visited[neighbor] = True
                count += 1
                queue.append(neighbor)
                
    return count

T = int(input())

for tc in range(1, T + 1):
    N = int(input())
    M = int(input())
    
    forward_graph = [[] for _ in range(N + 1)]
    backward_graph = [[] for _ in range(N + 1)]
    
    for _ in range(M):
        u, v = map(int, input().split())
        forward_graph[u].append(v)   
        backward_graph[v].append(u)  
        
    answer = 0
    
    for i in range(1, N + 1):
        taller_count = compare_bfs(i, forward_graph, N)
        shorter_count = compare_bfs(i, backward_graph, N)
        
        if taller_count + shorter_count == N - 1:
            answer += 1
            
    print(f"#{tc} {answer}")