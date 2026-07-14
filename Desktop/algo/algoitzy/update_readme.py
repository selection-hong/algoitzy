import json
import os
from collections import Counter

def generate_readme():
    file_path = 'study_plan.json'
    if not os.path.exists(file_path):
        print(f"❌ '{file_path}' 파일이 없습니다.")
        return

    with open(file_path, 'r', encoding='utf-8') as f:
        data = json.load(f)

    prob_map = data.get('problem_data', {})
    
    # 1. 알고리즘 통계 계산
    algo_counts = Counter()
    # 3월 3주차, 4주차 등 모든 주차의 문제 정보를 가져와 알고리즘 집계
    for week in data.get('weeks', []):
        for num in week['problems']:
            info = prob_map.get(str(num))
            if isinstance(info, dict) and info.get("algo"):
                # "BFS / DFS" 같은 경우 분리하여 카운트
                algos = [a.strip() for a in info.get("algo").replace('/', ',').split(',')]
                for a in algos:
                    if a != "-":
                        algo_counts[a] += 1
            # Baekjoon 문제 중 수동으로 알고리즘을 분류하고 싶다면 여기에 로직 추가 가능

    # 2. README 내용 생성
    # 이미지 UI처럼 접이식(details) 구조 생성
    content = f"<details>\n"
    content += f"<summary><b>🐢 SW 역량테스트 대비 알고있지 스터디 🐰</b></summary>\n\n"
    content += "매주 정해진 문제를 풀고 Commit 올려주세요!\n\n"
    
    # 요약 테이블 생성
    content += "| 주요 알고리즘 | 문제 수 |\n"
    content += "| :--- | :--- |\n"
    
    # 통계가 있을 경우 출력, 없을 경우 예시 데이터 출력 (이미지 기준)
    if algo_counts:
        for algo, count in algo_counts.items():
            content += f"| {algo} | {count} |\n"
    else:
        # 데이터가 없을 경우 이미지와 유사한 예시 출력
        content += "| DP | 1 |\n"
        content += "| 다익스트라 | 1 |\n"
    
    content += "\n</details>\n"

    # 3. 파일 저장
    with open('README.md', 'w', encoding='utf-8') as f:
        f.write(content)
    
    print("✅ README.md가 이미지 UI 스타일로 업데이트되었습니다!")

if __name__ == "__main__":
    generate_readme()