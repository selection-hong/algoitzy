def solution(N, stages):
    # 각 스테이지에 도전 중인 사람 수를 카운트
    # index 0 → 스테이지 1, index 1 → 스테이지 2, ..., index N → 스테이지 N+1
    stage_counts = [stages.count(i) for i in range(1, N + 2)]
    
    # 현재 스테이지에 도달한 플레이어 수 초기화
    remaining = len(stages)
    
    # 각 스테이지의 실패율을 저장할 리스트
    # 튜플 형태: (스테이지 번호, 실패율)
    fail_rates = []

    # 1번 스테이지부터 N번 스테이지까지 반복
    for i in range(1, N + 1):
        # 남은 플레이어가 0보다 크면 실패율 계산
        if remaining > 0:
            fail_rate = stage_counts[i - 1] / remaining
        else:
            # 남은 플레이어가 없으면 실패율은 0
            fail_rate = 0

        # 실패율과 스테이지 번호를 튜플로 추가
        fail_rates.append((i, fail_rate))
        
        # 현재 스테이지에서 실패한 플레이어 수만큼 남은 플레이어 수 갱신
        remaining -= stage_counts[i - 1]

    # 실패율 기준 내림차순 정렬
    # 실패율이 같으면 스테이지 번호 오름차순으로 정렬
    fail_rates.sort(key=lambda x: (-x[1], x[0]))

    # 정렬된 결과에서 스테이지 번호만 추출
    return [stage for stage, _ in fail_rates]