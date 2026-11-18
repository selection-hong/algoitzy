def calc_wait(type_requests, mentors):
    # 한 유형에 멘토가 mentors명일 때 총 대기 시간
    end_times = [0] * mentors                   # 멘토별 상담이 끝나는 시각
    total_wait = 0
    for request_time, duration in type_requests:
        end_times.sort()                        # 가장 빨리 끝나는 멘토를 맨 앞으로
        if end_times[0] > request_time:         # 멘토가 아직 바쁘면 → 기다림
            total_wait += end_times[0] - request_time
            end_times[0] += duration
        else:                                   # 멘토가 한가하면 → 바로 시작
            end_times[0] = request_time + duration
    return total_wait


def solution(k, n, reqs):
    answer = 0

    # 1. 유형별로 요청 나누기
    requests_by_type = [[] for _ in range(k + 1)]
    for request_time, duration, type_no in reqs:
        requests_by_type[type_no].append((request_time, duration))

    # 2. best[used] = 지금까지 본 유형들에 멘토 used명을 썼을 때 최소 대기 시간
    best = [float('inf')] * (n + 1)
    best[0] = 0

    # 3. 유형을 하나씩 추가하며 best 갱신
    for type_no in range(1, k + 1):
        new_best = [float('inf')] * (n + 1)         # 같은 유형이 두 번 더해지지 않도록 새 리스트에 기록
        for mentors in range(1, n + 1):             # 이번 유형에 줄 멘토 수 (최소 1명)
            wait = calc_wait(requests_by_type[type_no], mentors)
            for used in range(n - mentors + 1):     # 앞 유형들이 쓴 멘토 수
                total = best[used] + wait
                if total < new_best[used + mentors]:
                    new_best[used + mentors] = total
        best = new_best

    # 4. 멘토를 정확히 n명 쓴 경우가 답
    answer = best[n]
    return answer