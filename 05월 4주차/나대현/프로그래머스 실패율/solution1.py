def solution(N, stages):
    # 마지막 스테이지를 클리어 한 사용자의 숫자는 
    # 마지막 스테이지 + 1 (N+1) 이라서 인덱스 에러는 N+2 부터 방지 가능
    stage_counts = [0] * (N + 2)
    for stage in stages:
        stage_counts[stage] += 1    # 해당 스테이지에 도전하고 있는 사람들의 수
    remaining_players = len(stages) # 현재 스테이지에 도달한 사람의 수
    
    fail_percent = {}               # key:스테이지 번호, value: 실패율
    for i in range(1, N+1):
        fail_players = stage_counts[i]  # 실패한 플레이어 수
        
        if remaining_players > 0:       # ZeroDivisionError 방지
            fail_percent[i] = fail_players / remaining_players
            remaining_players -= fail_players   # 플레이어 수 갱신
        else:
            fail_percent[i] = 0     # 도달한 플레이어 없다면 실패율 0
    # (스테이지 번호, 실패율) 튜플을 담기 위한 리스트
    pecent_list = []
    
    for i in range(1, N+1):
        pecent_list.append((i, fail_percent[i]))
        
    percent = sorted(pecent_list, key=lambda x: x[1], reverse=True)
    
    answer = []
    for n in percent:
        answer.append(n[0])
        
    return answer