def update_min_max(l, val):
    """
    l   : 현재까지 사용한 연산자의 개수 (재귀의 깊이 / Depth)
    val : 현재 단계까지 계산된 중간 결과값
    """
    global ops, nums, N, MAX, MIN

    # 연산자를 모두 골랐다면 최댓값과 최솟값을 갱신하고 탐색을 종료
    if l == N-1:
        MAX = max(MAX, val)
        MIN = min(MIN, val)

    for idx in range(4):
        if ops[idx] != 0:   # 해당 연산자가 아직 남아있는 경우에만 탐색 진행
            ops[idx] -= 1
            if operators[idx] == '+':
                result = val + nums[l+1]
            elif operators[idx] == '-':
                result = val - nums[l+1]
            elif operators[idx] == '*':
                result = val * nums[l+1]
            else:
                result = int ( val / nums[l+1] )

            update_min_max(l+1, result)
            ops[idx] += 1   # 다른 경로 탐색을 위해 복원 (Backtracking)
        
operators = ['+', '-', '*', '/']

TC = int(input())
for test_case in range(1, TC+1):
    N = int(input())
    ops = list(map(int, input().split()))   # 각 연산자의 수
    nums = list(map(int, input().split()))  

    MAX = -10**8
    MIN = 10**8
    update_min_max(0, nums[0])

    print(f'#{test_case} {MAX-MIN}')