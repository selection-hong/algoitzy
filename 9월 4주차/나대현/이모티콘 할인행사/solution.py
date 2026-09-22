# 1. 할인율 조합을 탐색하고 정산하는 DFS 함수
def emoticons_discount(depth, selected, users, emoticons, answer):
    rates = [10, 20, 30, 40]
    
    # 1-1. 탈출 조건: 모든 이모티콘의 할인율을 결정했을 때
    if depth == len(emoticons):
        plus_count = 0   # 이번 조합의 플러스 가입자 수
        total_sales = 0  # 이번 조합의 매출액
        
        # 유저 한 명씩 순회하면서 계산
        for i in range(len(users)):
            user_rate = users[i][0]   # 유저가 원하는 최소 할인율
            user_price = users[i][1]  # 유저의 예산
            
            user_sum = 0  # 이 유저가 결제할 금액
            
            for j in range(len(emoticons)):
                # 선택한 할인율이 유저 기준 이상이면 구매
                if selected[j] >= user_rate:
                    user_sum += emoticons[j] * (100 - selected[j]) // 100
            
            # 총금액이 예산 이상이면 플러스 가입, 아니면 매출에 합산
            if user_sum >= user_price:
                plus_count += 1
            else:
                total_sales += user_sum
        
        # 1-2. 최댓값 갱신
        if plus_count > answer[0]:
            answer[0] = plus_count
            answer[1] = total_sales
        elif plus_count == answer[0]:
            if total_sales > answer[1]:
                answer[1] = total_sales
        
        return

    # 1-3. 4가지 할인율 대입 및 백트래킹
    for i in range(4):
        selected.append(rates[i])
        emoticons_discount(depth + 1, selected, users, emoticons, answer)
        selected.pop()  # 원상복구


# 2. 메인 solution 함수
def solution(users, emoticons):
    answer = [0, 0]  # [최대 가입자 수(1순위), 최대 매출액(2순위)]
    selected = []    # 선택한 할인율을 담을 리스트
    
    # 필요한 변수(users, emoticons, answer 등)를 인자로 넘겨줌
    emoticons_discount(0, selected, users, emoticons, answer)
    
    return answer