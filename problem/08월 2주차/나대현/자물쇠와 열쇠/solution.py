# 1. 열쇠를 시계 방향으로 90도 회전하는 함수
def rotate(key):
    M = len(key)
    return [[key[M - 1 - j][i] for j in range(M)] for i in range(M)]

def solution(key, lock):
    M = len(key)
    N = len(lock)
    
    # 2. 자물쇠의 홈(0) 좌표와 총 개수 파악
    lock_grooves = []
    for r in range(N):
        for c in range(N):
            if lock[r][c] == 0:
                lock_grooves.append((r, c))
    groove_count = len(lock_grooves)
    
    # 자물쇠에 홈이 없으면 이미 열린 상태
    if groove_count == 0:
        return True

    # 3. 4가지 회전 방향 탐색
    for _ in range(4):
        key = rotate(key)
        
        # [핵심] 열쇠의 돌기(1) 좌표만 추출
        key_bumps = []
        for r in range(M):
            for c in range(M):
                if key[r][c] == 1:
                    key_bumps.append((r, c))
        
        # 4. 열쇠를 (dx, dy) 만큼 이동시키며 자물쇠에 대어보기
        for dx in range(-M + 1, N):
            for dy in range(-M + 1, N):
                filled_count = 0  # 채워진 자물쇠 홈의 개수
                is_valid = True    # 돌기 충돌 여부
                
                for kx, ky in key_bumps:
                    lx, ly = kx + dx, ky + dy  # 자물쇠 위에서의 돌기 위치
                    
                    # 돌기가 자물쇠 영역 안으로 들어온 경우만 검사
                    if 0 <= lx < N and 0 <= ly < N:
                        if lock[lx][ly] == 1:  # 자물쇠 돌기와 충돌
                            is_valid = False
                            break
                        else:                  # 자물쇠 홈을 채움
                            filled_count += 1
                
                # 돌기 충돌이 없고, 자물쇠의 모든 홈이 채워졌다면 성공
                if is_valid and filled_count == groove_count:
                    return True

    return False