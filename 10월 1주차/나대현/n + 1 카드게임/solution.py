def solution(coin, cards):
    answer = 1
    n = len(cards)

    # 처음에 받은 카드
    # 이 카드는 코인을 사용하지 않고 사용할 수 있음
    my_card = cards[:n / 3]

    # 이후 라운드에서 새로 뽑은 카드
    # 지금 당장 쓰지 않아도 나중에 사용할 수 있음
    new_card = []

    index = n / 3

    while index < n:

        # 이번 라운드에서 카드 2장 추가
        new_card.append(cards[index])
        new_card.append(cards[index + 1])

        index += 2

        found = False

        # 1. 처음 받은 카드 + 처음 받은 카드
        # coin 0개
        for x in my_card:
            partner = n + 1 - x

            if x != partner and partner in my_card:
                my_card.remove(x)
                my_card.remove(partner)

                found = True
                break

        if found:
            answer += 1
            continue

        # 2. 처음 받은 카드 + 새로 뽑은 카드
        # coin 1개
        if coin >= 1:
            for x in my_card:
                partner = n + 1 - x

                if partner in new_card:
                    my_card.remove(x)
                    new_card.remove(partner)

                    coin -= 1
                    found = True
                    break

        if found:
            answer += 1
            continue

        # 3. 새로 뽑은 카드 + 새로 뽑은 카드
        # coin 2개
        if coin >= 2:
            for x in new_card:
                partner = n + 1 - x

                if x != partner and partner in new_card:
                    new_card.remove(x)
                    new_card.remove(partner)

                    coin -= 2
                    found = True
                    break

        if found:
            answer += 1
            continue

        # 세 가지 방법 모두 실패
        break

    return answer