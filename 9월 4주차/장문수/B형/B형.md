문제 설명

1\. n \* n 크기의 격자가 주어진다

2\. 이후 직사각형 1개씩 주어진다

3\. 주어진 직사각형의 변과 1칸이라도 맞닿아있는 직사각형이 있다면 이를 하나의 그룹으로 본다



// 초기화 함수

void init(int n)  \[n <= 45000]

&#x20;n: 격자의 크기



// 직사각형 추가 함수

int add\_block(int index, int x1, int y1, int x2, int y2) \[index <= 15000(순서대로 x)] 15000회 호출



&#x20;index: 주어진 직사각형의 id

&#x20;x1, y1: 직사각형의 작은쪽 좌표

&#x20;x2, y2: 직사각형의 큰쪽 좌표



return 주어진 index가 속한 그룹의 크기



// 주어진 두 index가 동일한 그룹 내에 있는지 여부 반환

bool is\_same(int index1, int index2) 5000회 호출

&#x20; return 동일한 그룹 내 ? true : false



// index가 속한 그룹의 크기 반환

int qroup\_size(int index) 8000회 호출

&#x20; return index가 속한 그룹의 크기 반환



// 상위 10개 그룹 정보 반환

RESULT top10() 5000회 호출

&#x20; ☆ 사이즈 내림차, index 오름차 순으로 정렬하여 반환



&#x20;  return 반환 그룹 수, 그룹 index, 그룹 사이즈 반환

