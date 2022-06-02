package com.score6;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class DiceImpl implements Dice{

	private List<DiceVO> lists;

	Scanner sc = new Scanner(System.in);

	DiceVO vo = new DiceVO();

	private String path = System.getProperty("user.dir");
	private File f = new File(path, "\\doc\\diceScore.txt");

	int ch, com, money=0;
	int seed=0,su;
	String str, ch2, str1;
	char cho;

	public DiceImpl() {

		try {

			if(!f.getParentFile().exists()) { 
				f.getParentFile().mkdirs(); 
			}
			if(f.exists()) { 

				FileInputStream fis = new FileInputStream(f);  
				ObjectInputStream ois = new ObjectInputStream(fis);  

				lists = (List<DiceVO>)ois.readObject();
				fis.close();
				ois.close();

			}


		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}


	@Override
	public void input() {

		//		DiceVO vo = new DiceVO();
		try {
			System.out.println();
			System.out.print("                  닉네임 입력! : ");
			vo.setName(sc.next());
			System.out.println();
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println();
			System.out.print("                당신의 시드 머니는 : (500원 이상 10만 원 이하) ");
			vo.seed=sc.nextInt(); // = sc.nextInt();
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");

			if(vo.seed<500||vo.seed>100000) {
				System.out.println("                다시 입력하세요!");
				input();
			}
			setDice();

			if(lists==null) {
				lists = new ArrayList<>();  
				//			lists.add(vo);
			}else {
				lists.add(vo);
			}



		}catch (NumberFormatException e) {
			System.out.println("정수를 입력하세요."); // NumberFormatException 정수 값을 판가름하는 녀석 



		} catch (Exception e) {
			System.out.println("정확한 금액을 입력하세요.");
		}
	}

	@Override
	public void setDice() {


		try {


			Random rd = new Random();

			Thread muk = new Thread(new MyThread1());
			Thread hol = new Thread(new Holsu());
			Thread jjak = new Thread(new Jjaksu());

			while(true){
				String[] a = {"홀","짝"};
				com = rd.nextInt(2);

				muk.setDaemon(true);
				muk.start();

				do {
					try {

						Thread.sleep(1000);

					} catch (Exception e) {
						// TODO: handle exception
					}
					//				System.out.println(vo.seed+"~Test~");  // 테스트용~~~
					System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
					System.out.println();
					System.out.print("1.홀 2.짝\n\n 당신의 선택은?");
					su = sc.nextInt()-1;
					System.out.println();
					System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");



				}
				while(su==1 && su==2);

				

			try {

				if(com==0) {
					hol.start();

					try {

						Thread.sleep(2000);

					} catch (Exception e) {

					}

				}else if(com!=0){
					jjak.start();

					try {

						Thread.sleep(2000);

					} catch (Exception e) {
						// TODO: handle exception
					}

				}

			} catch (Exception e) {
				// TODO: handle exception
			}
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println();
			System.out.print( "당신의 선택은 "+ a[su] +", 구슬의 개수는 " + a[com]+"!" );
			System.out.println();

			if(com==su) { 
				vo.seed= (int)((vo.seed) + ((vo.seed)*0.5));

				str= "\n맞추셨네요!\n";

			} else  {
				vo.seed = (int)((vo.seed) - ((vo.seed)*0.5));
				str = "\n지셨네요...\n";
				//					System.out.println(seed+"~~");  //
				//	vo.getSeed() = seed;
			}
			System.out.println();
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");

			System.out.println(str);

			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println();
			System.out.println("                     현재 잔액은 " + (vo.seed) + "원입니다.");
			System.out.println();
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");

			if(vo.seed<500) {
				Thread pepe = new Thread(new Homeless());
				pepe.start();

				try {

					Thread.sleep(2000);

				} catch (Exception e) {

				}
				System.out.println("500원도 없으시네요. ");
				save();
			}else {
			}
			System.out.println();
			

			System.out.print("                          계속하시겠습니까? [Y/N]");
			ch = (char)System.in.read();

			if(ch=='n' || ch=='N') {
				System.out.println("                          현재 잔액 " +(vo.seed) +"원");
				save();
				break;
			}else if(ch=='y' || ch=='Y'){
				setDice();
			}else {
				System.out.println("잘못 입력하셨습니다.");
			}
			
		}

	}catch (Exception e) {
		System.out.println();
	}

}


@Override
public void Ranking() {


	Iterator<DiceVO> it = lists.iterator();

	while(it.hasNext()) {
		DiceVO vo = it.next();

		System.out.printf(vo.toString());
	}

}

public void print() {   

	Comparator<DiceVO> comp = new Comparator<DiceVO>() {

		@Override
		public int compare(DiceVO vo1, DiceVO vo2) {
			return vo1.getSeed() < vo2.getSeed() ? 1:-1;
		}
	};

	Collections.sort(lists,comp);

	Ranking();

}



@Override 
public void save() {

	//		DiceVO vo = new DiceVO();

	try {

		if(lists!=null) {

			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(lists);
			fos.close();
			oos.close();
			System.out.println();
			System.out.println("                   게임을 종료합니다. 랭킹에 반영되었습니다!"); 
			System.out.println();

		}

	} catch (Exception e) {
		System.out.println(e.toString());
	}

}

//	@Override
//	public String toString() {
//
//		DiceVO vo = new DiceVO();
//		
//		return String.format("등 "+vo.getName()+", 잔액 :"+seed+"원\n");
//	}




}
