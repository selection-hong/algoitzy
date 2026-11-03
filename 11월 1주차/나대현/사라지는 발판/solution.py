# 방향 벡터를 모듈 전역 상수로 분리
DX = [-1, 1, 0, 0]
DY = [0, 0, -1, 1]


def dfs(cur, other, board, n, m):
    x, y = cur

    # 현재 플레이어의 발판이 사라졌다면 패배
    if board[x][y] == 0:
        return False, 0

    can_move = False
    can_win = False

    min_count = 100000
    max_count = 0

    # 상하좌우 이동
    for d in range(4):
        nx = x + DX[d]
        ny = y + DY[d]

        # 범위를 벗어나거나 발판이 없으면 이동 불가
        if nx < 0 or nx >= n or ny < 0 or ny >= m:
            continue

        if board[nx][ny] == 0:
            continue

        can_move = True

        # 현재 발판 제거
        board[x][y] = 0

        # 상대방 차례로 DFS 진행 (역할 교대)
        other_win, count = dfs(other, [nx, ny], board, n, m)

        # 백트래킹: 발판 복구
        board[x][y] = 1

        count += 1

        # 상대방이 진다면 현재 플레이어는 승리
        if not other_win:
            can_win = True
            min_count = min(min_count, count)

        # 상대방이 이긴다면 현재 플레이어는 패배
        else:
            max_count = max(max_count, count)

    # 이기는 방법이 있다면 가장 빨리 이기는 방법 선택
    if can_win:
        return True, min_count

    # 이동할 수 있지만 이기는 방법이 없다면 최대한 늦게 패배
    if can_move:
        return False, max_count

    # 이동할 수 있는 발판이 없다면 패배
    return False, 0


def solution(board, aloc, bloc):
    n = len(board)
    m = len(board[0])

    return dfs(aloc, bloc, board, n, m)[1]