/*
* 전체 영화가 들어있는 pq 선언 (1개)
* 장르별 pq 선언 (5개)
*
* ※ 마지막 로직
* pq를 어떻게 구분할 것인가
* genre_id를 key로 가지는 unordered_map<int, priority_queue<MOVIE_INFO, vector<MOVIE_INFO>, MOVIE_COMP>> pq_map 사용
* 사용 시 priority_queue<MOVIE_INFO, vector<MOVIE_INFO>, MOVIE_COMP> pq = pq_map[genre_id]로 접근
*
*
*/

#include <queue>
#include <vector>
#include <algorithm>
#include <unordered_map>
#include <iostream>

#define MAX_MOVIE_ID 1000000001

using namespace std;

struct RESULT
{
    int cnt;
    int IDs[5];
};

struct MOVIE_INFO {
    int id, genre_id, total_score;
    int apply_time, last_update;
};

struct MOVIE_COMP {
    bool operator() (const MOVIE_INFO& a, const MOVIE_INFO& b) {
        if (a.total_score == b.total_score) {
            return a.apply_time < b.apply_time; // apply_time 내림차순
        }

        return a.total_score < b.total_score; // total_score 내림차순
    }
};

unordered_map<int, priority_queue<MOVIE_INFO, vector<MOVIE_INFO>, MOVIE_COMP>> pq_map;

int movie_idx;
MOVIE_INFO movies[10000]; // 영화 정보

unordered_map<int, int> movie_map; // mID 기반 movie_idx 조회

unordered_map<int, bool> exist_map; // 영화 존재 여부

unordered_map<long long, int> watch_map; // {uID * MAX_MOVIE_ID + mID} -> 시청 횟수

int watch_movie_count[1001]; // uID_idx -> 시청한 영화 개수
vector<int> watch_movie[1001]; // uID_idx -> 시청한 영화 번호


int apply_time = 0; // 영화 등록 시기

int duple_watch_idx;
int duple_watch[10001];

void init(int N)
{
    pq_map.clear();
    movie_idx = 0;
    movie_map.clear();
    exist_map.clear();
    watch_map.clear();
    fill(watch_movie_count, watch_movie_count + 1001, 0);
    fill(watch_movie, watch_movie + 1001, vector<int>());
    apply_time = 0;

    return;
}

/*
* add 시 전체 영화 및 장르에 맞는 pq에 push
* ㄴ 이때, 영화가 등록된 시기 apply++
* 존재하는 영화 표시용 unordered_map<int, bool> exist_map 사용
* 영화 정보는 {apply_time, total_score, genre_id, last_update}로 구성
* pq 정렬 조건은 total_scort 내림차순, apply_time 내림차순
*/
int add(int mID, int mGenre, int mTotal)
{
    // 이미 영화가 있을 경우
    if (exist_map[mID]) {
        return 0;
    }

    movies[movie_idx] = { mID, mGenre, mTotal, apply_time++, 0 }; // 영화 정보 저장
    pq_map[0].push(movies[movie_idx]); // 전체 영화 pq에 push
    pq_map[mGenre].push(movies[movie_idx]); // 장르별 pq에 push
    exist_map[mID] = true; // 영화 존재 표시
    movie_map[mID] = movie_idx;
    movie_idx++;

    return 1;
}


/*
* 영화 존재 여부용 unordered_map<int, bool> exist_map 사용
* ㄴ exist_map[mID] = false 라면 없는것 -> return 0
* ㄴ 그 외 exist_map[mID] = false -> return 1
*/
int erase(int mID)
{
    if (exist_map[mID]) {
        exist_map[mID] = false;
        return 1;
    }

    return 0;
}


/*
* watch 시 영화 존재 여부 확인
* ㄴ exist_map[mID] = false 라면 없는것 -> return 0
* ㄴ watch_map[{uID, mID}] = 1 이상 이라면 이미 시청한것 -> return 0
* ㄴ 그 외 watch_map[{uID, mID}] = mRating, total_score += mRating, last_update++
*    ㄴ 이후 해당 장르에 해당하는 pq에 영화 정보 push -> return 1
*/
int watch(int uID, int mID, int mRating)
{
    // 영화가 있을 경우
    if (exist_map[mID]) {
        long long key = (long long)uID * MAX_MOVIE_ID + mID;
        if (!watch_map[key]) { // 영화 시청 기록이 없을 경우
            watch_map[key] = mRating; // 시청 기록 저장

            // 영화 정보 업데이트
            movies[movie_map[mID]].total_score += mRating;
            movies[movie_map[mID]].last_update++;
            pq_map[0].push(movies[movie_map[mID]]); // 전체 영화 pq에 push
            pq_map[movies[movie_map[mID]].genre_id].push(movies[movie_map[mID]]); // 장르별 pq에 push

            // 시청한 영화 목록 업데이트
            watch_movie_count[uID]++;
            watch_movie[uID].push_back(mID);

            return 1;
        }
    }

    return 0;
}


bool resent_info_check(MOVIE_INFO& a, MOVIE_INFO& b) {
    return a.last_update == b.last_update;
}

/*
* suggest 시 uID가 시청한 영화 목록 확인
* uID가 시청한 영화를 저장할 vector<int>[1000] 사용 -> int[uID][0 ~ k] = 시청한 영화 번호
* uID가 시청한 최근 시청한 영화 5개를 표시할 vector<int>[1000] 사용 -> int[uID] = 시청한 영화 개수
*
* 시청한 영화가 없다면(int[uID] == 0) 전체 영화 pq에서 top 5개를 res.IDs에 넣으면서 res.cnt++;
* res.cnt의 초기값은 0
*
* 시청한 영화가 있다면 가장 많이 시청한 장르 확인
* ㄴ for (int[uID] -> int[uID] - 5 && int[uID] > 0 ) -> int[uID][i]
* ㄴ 총점이 더 높은 장르 확인
*
* 해당 장르의 pq에서 top 5개를 res.IDs에 넣으면서 res.cnt++;
* ㄴ watch_map[{uID, mID}] = 1 이상인 영화는 제외
* ㄴ res.IDs에 저장하면서 res.cnt++;
* ㄴ 이후 res.ID에 있는 영화를 다시 pq에 push
*/
RESULT suggest(int uID)
{
    RESULT res;
    res.cnt = 0;
    duple_watch_idx = 0;

    int cnt = watch_movie_count[uID];
    if (!cnt) { // 시청한 영화가 없을 경우
        // 전체 영화에서 조회
        priority_queue<MOVIE_INFO, vector<MOVIE_INFO>, MOVIE_COMP>& pq = pq_map[0];
        while (res.cnt < 5 && !pq.empty()) {
            MOVIE_INFO movie_data = pq.top();
            pq.pop();

            // 존재하지 않는 영화일 경우 continue
            if (!exist_map[movie_data.id]) {
                continue;
            }

            // 최신 영화 정보 여부 확인
            if (resent_info_check(movies[movie_map[movie_data.id]], movie_data)) {
                // 시청한 영화인지 확인
                long long key = (long long)uID * MAX_MOVIE_ID + movie_data.id;
                if (watch_map[key]) {
                    duple_watch[duple_watch_idx++] = movie_data.id;
                    continue;
                }

                res.IDs[res.cnt++] = movie_data.id;
            }
        }

        for (int i = 0; i < res.cnt; i++) {
            pq.push(movies[movie_map[res.IDs[i]]]);
        }
    }
    else {
        int genre_id = 0;
        int max_score = 0;
        for (int i = cnt - 1; i >= cnt - 5 && i >= 0; i--) {
            int movie_id = watch_movie[uID][i];
            if (!exist_map[movie_id]) {
                cnt--;
                continue;
            }

            long long key = (long long)uID * MAX_MOVIE_ID + movie_id;
            if (max_score < watch_map[key]) {
                max_score = watch_map[key];
                genre_id = movies[movie_map[movie_id]].genre_id;
            }
        }

        // 특정 장르의 영화만 추천
        priority_queue<MOVIE_INFO, vector<MOVIE_INFO>, MOVIE_COMP>& pq = pq_map[genre_id];
        while (res.cnt < 5 && !pq.empty()) {
            MOVIE_INFO movie_data = pq.top();
            pq.pop();

            // 존재하지 않는 영화일 경우 continue
            if (!exist_map[movie_data.id]) {
                continue;
            }

            // 최신 영화 정보 여부 확인
            if (resent_info_check(movies[movie_map[movie_data.id]], movie_data)) {
                // 시청한 영화인지 확인
                long long key = (long long)uID * MAX_MOVIE_ID + movie_data.id;
                if (watch_map[key]) {
                    duple_watch[duple_watch_idx++] = movie_data.id;
                    continue;
                }

                res.IDs[res.cnt++] = movie_data.id;
            }
        }

        for (int i = 0; i < res.cnt; i++) {
            pq.push(movies[movie_map[res.IDs[i]]]);
        }

        for (int i = 0; i < duple_watch_idx; i++) {
            pq.push(movies[movie_map[duple_watch[i]]]);
        }
    }

    return res;
}