package test;

import java.util.Scanner;
import java.util.StringTokenizer;

public class bj2869_al_step7_4 {

	public static void main(String[] args) {
		
		long starttimes = System.currentTimeMillis();
		
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		StringTokenizer st = new StringTokenizer(input);
		int[] numbers = new int[3];
		
		for(int i = 0 ; i < numbers.length ; i++) numbers[i] = Integer.valueOf(st.nextToken());
		
		int sum = numbers[2]-numbers[0]+1;
		System.out.println(sum);
		
		long endtimes = System.currentTimeMillis();
		long diff = endtimes- starttimes;
		System.out.println(diff);

		
		// ��Ȯ���� ���ؿ��� �ð��ʰ��� �������� �ȵǴ� �ڵ��ε�
		// �������� �Է��غ��� 300ms ���� ����
		// �Ƹ�����Ǯ�� �� �޶� �ȵǴ� �� ���Ƽ� �ϴ� �ۼ��� �ø�

	}

}
