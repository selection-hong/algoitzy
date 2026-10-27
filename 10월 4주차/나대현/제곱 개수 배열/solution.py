def solution(arr, l, r):
    n = len(arr)
    length = [0] * (n + 1)
    square = [0] * (n + 1)

    # 블록 길이와 원소 합의 누적합
    for i in range(n):
        length[i + 1] = length[i] + arr[i]
        square[i + 1] = square[i] + arr[i] * arr[i]

    # brr의 앞에서 x개 원소의 합
    def prefix(x):
        if x == length[n]:
            return square[n]

        left, right = 0, n
        while left + 1 < right:
            mid = (left + right) // 2
            if length[mid] <= x:
                left = mid
            else:
                right = mid

        return square[left] + arr[left] * (x - length[left])

    size = r - l + 1
    K = prefix(r) - prefix(l - 1)

    # 처음부터 size개 원소의 합
    S = prefix(size)
    max_start = length[n] - size
    s = 0
    i = j = 0
    C = 0

    # 구간의 시작 위치를 블록 단위로 이동
    while s < max_start:
        while length[i + 1] <= s:
            i += 1
        while length[j + 1] <= s + size:
            j += 1

        end = min(length[i + 1], length[j + 1] - size, max_start)
        count = end - s
        d = arr[j] - arr[i]

        if d == 0:
            if S == K:
                C += count
        else:
            diff = K - S
            if diff % d == 0:
                q = diff // d
                if 0 <= q < count:
                    C += 1

        S += d * count
        s = end

    if S == K:
        C += 1

    return [K, C]