# 1486 SWEA 장훈이의 높은 선반
def find_min_diff(idx, current_height):
    global min_height
 
    if current_height >= min_height:
        return
 
    if idx == N:
        if current_height >= B:
            min_height = min(min_height, current_height)
        return
 
    find_min_diff(idx + 1, current_height + heights[idx])
    find_min_diff(idx + 1, current_height)
 
T = int(input())
for tc in range(1, T + 1):
    N, B = map(int, input().split())
    heights = list(map(int, input().split()))
     
    min_height = float('inf')
 
    find_min_diff(0, 0)
     
    result = min_height - B
    print(f"#{tc} {result}")