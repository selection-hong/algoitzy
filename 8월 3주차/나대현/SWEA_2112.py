# SWEA 보호필름
def check():
    # 모든 열에서 같은 특성이 K개 연속되는지 확인
    for col in range(W):
        cnt = 1
 
        for row in range(1, D):
            if board[row][col] == board[row - 1][col]:
                cnt += 1
            else:
                cnt = 1
            # 특성이 K개 이상 연속 탐지
            if cnt >= K:
                break
        else:
            return False
 
    return True
 
 
def protector_dfs(row, count):
    global best
 
    # 가지치기, 이미 찾은 답보다 많이 사용했다면 탐색할 필요 X
    if count >= best:
        return
 
    # 모든 행의 처리를 결정한 경우
    if row == D:
        if check():
            best = count
        return
 
    # 1. 약품을 사용하지 않는다.
    protector_dfs(row + 1, count)
 
    # 현재 행을 나중에 원래 상태로 되돌리기 위해 저장
    backup = board[row][:]
 
    # 2. 현재 행을 모두 0으로 변경
    board[row] = [0] * W
    protector_dfs(row + 1, count + 1)
 
    # 3. 현재 행을 모두 1로 변경
    board[row] = [1] * W
    protector_dfs(row + 1, count + 1)
 
    # 다른 경우의 탐색을 위해 원래 상태로 복구
    board[row] = backup
 
 
T = int(input())
 
for tc in range(1, T + 1):
    D, W, K = map(int, input().split())
    board = [list(map(int, input().split())) for _ in range(D)]
 
    # 아직 답을 찾지 못했으므로 충분히 큰 값으로 시작
    best = D
 
    protector_dfs(0, 0)
 
    print(f'#{tc} {best}')