def rotate(num, direction, visited):
    visited[num] = True

    # 왼쪽 자석 확인
    if num > 0 and not visited[num - 1]:
        if magnet[num][6] != magnet[num - 1][2]:
            rotate(num - 1, -direction, visited)

    # 오른쪽 자석 확인
    if num < 3 and not visited[num + 1]:
        if magnet[num][2] != magnet[num + 1][6]:
            rotate(num + 1, -direction, visited)

    # 현재 자석 회전
    if direction == 1:
        magnet[num] = [magnet[num][-1]] + magnet[num][:-1]
    else:
        magnet[num] = magnet[num][1:] + [magnet[num][0]]


T = int(input())

for tc in range(1, T + 1):
    
    K = int(input())

    magnet = [list(map(int, input().split())) for _ in range(4)]

    for _ in range(K):
        num, direction = map(int, input().split())
        num -= 1

        visited = [False] * 4

        rotate(num, direction, visited)

    score = 0

    for i in range(4):
        if magnet[i][0] == 1:
            score += 2 ** i

    print(f"#{tc} {score}")