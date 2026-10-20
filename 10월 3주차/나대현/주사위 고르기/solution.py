def get_sums(selected, dice):
    sums = [0]

    # 선택된 주사위를 하나씩 굴려 만들 수 있는 모든 합 계산
    for idx in selected:
        temp = []

        for total in sums:
            for num in dice[idx]:
                temp.append(total + num)

        sums = temp

    return sums


def dfs(start, selected, dice, max_sum, best):
    n = len(dice)

    # 주사위 선택 완료
    if len(selected) == n // 2:
        other = []

        for i in range(n):
            if i not in selected:
                other.append(i)

        a = get_sums(selected, dice)
        b = get_sums(other, dice)

        # b_count[x] = B의 합이 정확히 x인 경우의 수
        b_count = [0] * (max_sum + 1)

        for num in b:
            b_count[num] += 1

        # 누적합 계산: less_equal[x] = B의 합이 x 이하인 경우의 수
        less_equal = b_count
        for i in range(1, max_sum + 1):
            less_equal[i] += less_equal[i - 1]

        # A의 합이 num일 때 이기려면 B의 합이 num보다 작아야 하므로
        # B의 합이 num - 1 이하인 경우의 수를 더한다.
        # (주사위 눈이 최소 1이라 num - 1이 음수가 되지 않는다)
        wins = 0

        for num in a:
            wins += less_equal[num - 1]

        # 최고 기록 갱신
        if wins > best[0]:
            best[0] = wins
            best[1] = selected[:]

        return

    # 다음 주사위 선택
    for i in range(start, n):
        selected.append(i)
        dfs(i + 1, selected, dice, max_sum, best)
        selected.pop()


def solution(dice):
    n = len(dice)
    max_sum = 100 * (n // 2)  # 주사위 눈의 최댓값은 100

    # best[0]: 지금까지의 최대 승리 횟수
    # best[1]: 그때의 주사위 번호 목록 (0부터 시작)
    best = [-1, []]

    dfs(0, [], dice, max_sum, best)

    # 문제에서 요구하는 1부터 시작하는 번호로 변환
    answer = []

    for idx in best[1]:
        answer.append(idx + 1)

    return answer