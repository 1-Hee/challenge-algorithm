package com.study.A;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

public class BOJ_2573_빙산 {

	static class Ice{
		int x, y, cnt;

		public Ice(int x, int y, int cnt) {
			this.x = x;
			this.y = y;
			this.cnt = cnt;
		}
		// 맵에서 얼음을 녹이는 메서드, 음수가 나오지 않도록 3항 연산 함.
		void melt() {
			map[x][y] = map[x][y]-cnt<0 ? 0 : map[x][y]-cnt;
		}
		@Override
		public String toString() {
			return "Ice [x=" + x + ", y=" + y + ", cnt=" + cnt + "]";
		}
	}
	
	private static int R, C;
	private static int[][] map;
	private static Queue<Ice> ices; 
	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("data/boj21611.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		map = new int[R][C];		
		ices = new LinkedList<>();
		
		for(int r = 0 ; r < R ; r++) {
			st= new StringTokenizer(br.readLine());
			for(int c = 0 ;c <C; c ++) {
				map[r][c] = Integer.parseInt(st.nextToken());
				if(map[r][c]>0) ices.offer(new Ice(r, c, 0));
			}
		}
		int result = simulation();
		System.out.println(result);		
	}
	
	
	// 빙산을 녹이는 메서드
	private static final int[] dx = {-1,1,0,0};
	private static final int[] dy = {0,0,-1,1};	
	private static int simulation() {
		
		int time = 0;		
		while(true) {			
			// 값을 입력받을 때 이미 큐에 빙산이 들어 있습니다(>0인 걸 담음).
			// 빙산을 하나씩 꺼내서 주변이 바다인지(0인지) 확인합니다.
			int size = ices.size();			
			for(int s=0; s<size; s++) {
				Ice cur = ices.poll();
				
				// 4방 탐색을 통해,주변이 바다라면(0이라면) 
				// 바다 갯수만큼 카운트 계수를 올립니다.				
				for(int d = 0 ; d< 4 ;d ++) {
					int nx = cur.x+dx[d];
					int ny = cur.y+dy[d];
					if(nx<0||ny<0||nx>=R||ny>=C) continue;
					if(map[nx][ny]==0) {
						cur.cnt++;
					}										
				}
				// 두번째 작업( 얼음 녹이기 ) 를 위해 다시 큐에 담아서,
				ices.offer(cur);
			}
			
			// 종료지점입니다.
			// 전부다 녹았거나, 첨부터 빙산이 없는 경우도 거를 수 있도록 중앙에 종료조건 배치.
			if(ices.size()==0) break;
			int res = countIsland(); // 빙산덩어리 개수를 구하는 메서드
			if(res>1) { // 빙산 덩어리 개수가 1보다 크면 
				return time; // 즉시 시뮬레이션 종료하고, 시간을 리턴.
			}
			
			// 얼음 녹이기..!
			for(int s=0; s<size; s++) {
				Ice cur = ices.poll();
				cur.melt(); // 맵에서 자신의 좌표의 얼음을 녹임.
				if(map[cur.x][cur.y]>0) { // 다 녹아서 바다가 되이 않았으면,
					ices.offer(new Ice(cur.x, cur.y, 0));
					// 다시 큐에 빙산을 넣음.
				}
			}			
			time++;		
		}		
		return 0;
	}
	
	private static int countIsland() {
				
		if(ices.size()==0) return 0; // 기저조건
		boolean[][] isVisited = new boolean[R][C];
		Stack<Ice> items = new Stack<>(); // for dfs..?
		int size = ices.size();
		int cnt = 0;
				
		// 현재 시간의 빙산의 덩어리는 
		// 최댓 값이 빙산 큐의 크기이므로,
		for(int s=0; s<size; s++) {
			Ice ice = ices.poll(); // 일단 큐에서 빙산을 한번씩 다 꺼내서,
			items.push(ice); // 스택에 넣어보고
			ices.offer(ice); // 다시 돌린 다음
			if(!isVisited[ice.x][ice.y]) cnt++; // 방문 안했으면 덩어리개수++ 한다.
			isVisited[ice.x][ice.y] = true;

			while(!items.isEmpty()) { // 4방 탐색을 하여 스택에 쌓는데,
				// 아무리 거대해도 1덩어리면 첫번째 반복에서 다 방문처리 되기에, cnt를 증가시키지 못한다.
				Ice cur = items.pop();
				for(int d = 0; d< 4 ;d ++) {
					int nx = cur.x + dx[d];
					int ny = cur.y + dy[d];
					if(nx < 0 || ny < 0 || nx>=R || ny>=C) continue;
					if(map[nx][ny]<=0) continue; 
					if(isVisited[nx][ny]) continue;
					isVisited[nx][ny] = true;
					items.push(new Ice(nx, ny, 0));	
				}	
			}
		}		
		return cnt;
	}

}
