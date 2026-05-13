import java.util.*;

class Solution {

    static class Edge {
        int to;
        int weight;

        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    static class Node {
        int num;
        int intensity;

        Node(int num, int intensity) {
            this.num = num;
            this.intensity = intensity;
        }
    }

    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        List<Edge>[] graph = new ArrayList[n + 1];

        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] path : paths) {
            int from = path[0];
            int to = path[1];
            int weight = path[2];

            graph[from].add(new Edge(to, weight));
            graph[to].add(new Edge(from, weight));
        }

        boolean[] isGate = new boolean[n + 1];
        boolean[] isSummit = new boolean[n + 1];

        for (int gate : gates) {
            isGate[gate] = true;
        }

        for (int summit : summits) {
            isSummit[summit] = true;
        }

        int[] intensity = new int[n + 1];
        Arrays.fill(intensity, Integer.MAX_VALUE);

        PriorityQueue<Node> pq = new PriorityQueue<>(
            (a, b) -> Integer.compare(a.intensity, b.intensity)
        );

        for (int gate : gates) {
            intensity[gate] = 0;
            pq.offer(new Node(gate, 0));
        }

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            int now = current.num;
            int nowIntensity = current.intensity;

            if (nowIntensity > intensity[now]) {
                continue;
            }

            // 산봉우리에 도착하면 더 이상 다른 곳으로 이동하면 안 됨
            if (isSummit[now]) {
                continue;
            }

            for (Edge next : graph[now]) {
                // 다른 출입구를 중간에 방문하면 안 됨
                if (isGate[next.to]) {
                    continue;
                }

                int nextIntensity = Math.max(nowIntensity, next.weight);

                if (nextIntensity < intensity[next.to]) {
                    intensity[next.to] = nextIntensity;
                    pq.offer(new Node(next.to, nextIntensity));
                }
            }
        }

        Arrays.sort(summits);

        int answer = 0;
        int minIntensity = Integer.MAX_VALUE;

        for (int summit : summits) {
            if (intensity[summit] < minIntensity) {
                answer = summit;
                minIntensity = intensity[summit];
            }
        }

        return new int[] { answer, minIntensity };
    }
}
