def solution(orders, course):
    answer = []
    
    for i in range(len(orders)):
        orders[i] = ''.join(sorted(orders[i]))
    
    for target_len in course:
        menu_count = {}
            
        for order in orders:
            n = len(order)
                
            for bit in range(1 << n):
                temp = ""
                
                for j in range(n):
                    if bit & (1 << j):
                        temp += order[j]
                
                if len(temp) == target_len:
                    if temp in menu_count:
                        menu_count[temp] += 1
                    else:
                        menu_count[temp] = 1
        
        max_count = 0
        
        for key in menu_count:
            if menu_count[key] >= 2:
                if menu_count[key] > max_count:
                    max_count = menu_count[key]
        
        for key in menu_count:
            if menu_count[key] == max_count and max_count >= 2:
                answer.append(key)
    
    answer.sort()
    return answer