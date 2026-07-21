def solution(info, edges):
    tree = [[] for _ in range(len(info))]
    for p, c in edges:
        tree[p].append(c)   # 자식 노드 관계만 저장
        
    ans = 0

    def find_sheep(s, w, possible):
        nonlocal ans
        
        ans = max(ans, s)
        
        for i in range(len(possible)):
            nxt = possible[i]   # 현재 선택한 노드
            
            if info[nxt] == 0:
                next_s = s + 1
                next_w = w
            else:
                next_s = s
                next_w = w + 1
            
            if next_s > next_w:     # 양이 늑대보다 많을 때 진행
                # 선택한 노드는 빼고 나머지 후보들 + 선택한 노드의 자식들
                nxt_possible = possible[:i] + possible[i+1:] + tree[nxt]
                
                find_sheep(next_s, next_w, nxt_possible)

    find_sheep(1, 0, tree[0])
    
    return ans