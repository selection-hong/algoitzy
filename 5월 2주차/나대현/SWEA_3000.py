# 3000 SWEA 중간값 구하기
import heapq

# 문제 조건: 정답을 이 수로 나눈 나머지를 출력
MOD = 20171109

T = int(input())

for t in range(1, T + 1):

    # [핵심 아이디어] 수열을 두 힙으로 절반씩 나눠 관리한다.
    # lower_heap(최대힙): 작은 절반 / upper_heap(최소힙): 큰 절반
    # 중간값은 항상 upper_heap 의 루트([0])에 위치한다.

    # 큰 절반을 관리하는 최소힙 -- 루트([0])가 항상 현재 중간값
    upper_heap = []

    # 작은 절반을 관리하는 최대힙
    # 파이썬 heapq 는 최소힙만 지원하므로, 값을 음수로 뒤집어 저장한다.
    # 저장: 8 -> -8 / 복원: -(-8) = 8
    lower_heap = []

    N, A = map(int, input().split())

    # 첫 번째 수 A 는 upper_heap 에 먼저 넣는다.
    # 수가 1개일 때 중간값은 자기 자신이므로, 중간값을 담는 upper_heap 에 위치시킨다.
    heapq.heappush(upper_heap, A)

    # 중간값들의 누적 합
    total = 0

    for i in range(N):

        num1, num2 = map(int, input().split())

        # 두 수를 크기 기준으로 분류
        big   = max(num1, num2)
        small = min(num1, num2)

        # 큰 수는 upper_heap, 작은 수는 lower_heap 에 추가한다.
        heapq.heappush(upper_heap, big)
        heapq.heappush(lower_heap, -small)  # 최대힙이므로 음수로 변환하여 저장

        # [힙 보정] lower 최댓값 > upper 최솟값이면 두 값을 교환하여 경계를 바로잡는다.
        # 조건: lower_heap 의 최댓값 <= upper_heap 의 최솟값(중간값)
        while upper_heap and lower_heap and upper_heap[0] < -lower_heap[0]:

            # 두 힙에서 경계를 벗어난 값을 각각 꺼낸다.
            wrong_upper = heapq.heappop(upper_heap)
            wrong_lower = -heapq.heappop(lower_heap)  # 음수 복원

            # 올바른 힙으로 이동시킨다.
            heapq.heappush(upper_heap, wrong_lower)
            heapq.heappush(lower_heap, -wrong_upper)

        # 보정 완료 후 upper_heap[0] 이 현재 수열의 중간값
        current_median = upper_heap[0]

        # 누적 합산. 중간에 MOD 처리하여 오버플로우를 방지한다.
        total = (total + current_median) % MOD

    print(f'#{t} {total}')