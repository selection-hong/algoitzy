def solution(n, info_A):
    rate = []
    for i in range(10): 
        score = 10 - i
        cost = info_A[i] + 1
        benefit = score * (2 if info_A[i] > 0 else 1)
        rate.append([score, benefit, cost])
        
    rate.sort()  

    dp = [[0, [], 11] for _ in range(n + 1)]
    
    for score, benefit, cost in rate:
        for w in range(n, cost - 1, -1):
            new_benefit = dp[w - cost][0] + benefit
            if new_benefit > dp[w][0] or (new_benefit == dp[w][0] and dp[w - cost][2] < dp[w][2]):
                dp[w][0] = new_benefit
                dp[w][1] = dp[w-cost][1] + [score]
                dp[w][2] = min(dp[w][1])

    info_L = [0] * 11
    for score in dp[n][1]:
        info_L[10 - score] = info_A[10 - score] + 1
    info_L[10] = n - sum(info_L) 

    diff = 0
    for i in range(11):
        if info_A[i] == 0 and info_L[i] == 0: 
            continue
        if info_L[i] > info_A[i]:
            diff += (10 - i)
        else:
            diff -= (10 - i)

    return info_L if diff > 0 else [-1]