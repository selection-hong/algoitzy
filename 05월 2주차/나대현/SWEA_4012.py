# 4012 SWEA 요리사
from itertools import combinations

def solve():
    T = int(input())

    for tc in range(1, T + 1):

        # 재료 개수
        N = int(input())

        # 재료 간 시너지 정보 입력
        matrix = [list(map(int, input().split())) for _ in range(N)]

        # P[i] -> i번 재료가 전체 재료들과 만들어내는 시너지 총합
        # 행 합 + 열 합을 더해서 계산
        # (i,j) + (j,i)를 한 번에 포함하기 위함
        P = [0] * N

        for i in range(N):
            # i번 재료가 다른 재료에 주는 시너지
            row_sum = sum(matrix[i])

            # 다른 재료가 i번 재료에 주는 시너지
            col_sum = sum(matrix[j][i] for j in range(N))

            # i번 재료와 관련된 전체 시너지 값 저장
            P[i] = row_sum + col_sum

        # 전체 재료의 시너지 총합
        total_p = sum(P)

        # 최소 차이값 저장용
        min_diff = float('inf')

        # 두 음식은 재료 개수가 N//2개로 동일해야 함
        # 조합 중복 제거를 위해 0번 재료는 항상 A 음식에 포함시킴
        # 따라서 나머지 재료들 중에서 N//2 - 1 개만 선택하면 됨
        for combo in combinations(P[1:], N // 2 - 1):

            # A 음식의 시너지 합
            # 0번 재료 포함 + 현재 선택한 조합
            sum_a = P[0] + sum(combo)

            # B 음식의 시너지 합
            # 전체 합에서 A 음식 값을 빼서 계산
            sum_b = total_p - sum_a

            # 두 음식의 시너지 차이 계산
            diff = abs(sum_a - sum_b)

            # 최소값 갱신
            if diff < min_diff:
                min_diff = diff

            # 차이가 0이면 더 이상 볼 필요 없음
            if min_diff == 0:
                break

        # P 배열은 시너지 값을 두 번씩 포함하고 있으므로
        # 마지막에 2로 나누어서 출력
        print(f'#{tc} {min_diff // 2}')


solve()