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
			System.out.print("                  �г��� �Է�! : ");
			vo.setName(sc.next());
			System.out.println();
			System.out.println("�����������������������������������������������������");
			System.out.println();
			System.out.print("                ����� �õ� �Ӵϴ� : (500�� �̻� 10�� �� ����) ");
			vo.seed=sc.nextInt(); // = sc.nextInt();
			System.out.println("�����������������������������������������������������");

			if(vo.seed<500||vo.seed>100000) {
				System.out.println("                �ٽ� �Է��ϼ���!");
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
			System.out.println("������ �Է��ϼ���."); // NumberFormatException ���� ���� �ǰ����ϴ� �༮ 



		} catch (Exception e) {
			System.out.println("��Ȯ�� �ݾ��� �Է��ϼ���.");
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
				String[] a = {"Ȧ","¦"};
				com = rd.nextInt(2);

				muk.setDaemon(true);
				muk.start();

				do {
					try {

						Thread.sleep(1000);

					} catch (Exception e) {
						// TODO: handle exception
					}
					//				System.out.println(vo.seed+"~Test~");  // �׽�Ʈ��~~~
					System.out.println("�����������������������������������������������������");
					System.out.println();
					System.out.print("1.Ȧ 2.¦\n\n ����� ������?");
					su = sc.nextInt()-1;
					System.out.println();
					System.out.println("�����������������������������������������������������");



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
			System.out.println("�����������������������������������������������������");
			System.out.println();
			System.out.print( "����� ������ "+ a[su] +", ������ ������ " + a[com]+"!" );
			System.out.println();

			if(com==su) { 
				vo.seed= (int)((vo.seed) + ((vo.seed)*0.5));

				str= "\n���߼̳׿�!\n";

			} else  {
				vo.seed = (int)((vo.seed) - ((vo.seed)*0.5));
				str = "\n���̳׿�...\n";
				//					System.out.println(seed+"~~");  //
				//	vo.getSeed() = seed;
			}
			System.out.println();
			System.out.println("�����������������������������������������������������");

			System.out.println(str);

			System.out.println("�����������������������������������������������������");
			System.out.println();
			System.out.println("                     ���� �ܾ��� " + (vo.seed) + "���Դϴ�.");
			System.out.println();
			System.out.println("�����������������������������������������������������");

			if(vo.seed<500) {
				Thread pepe = new Thread(new Homeless());
				pepe.start();

				try {

					Thread.sleep(2000);

				} catch (Exception e) {

				}
				System.out.println("500���� �����ó׿�. ");
				save();
			}else {
			}
			System.out.println();
			

			System.out.print("                          ����Ͻðڽ��ϱ�? [Y/N]");
			ch = (char)System.in.read();

			if(ch=='n' || ch=='N') {
				System.out.println("                          ���� �ܾ� " +(vo.seed) +"��");
				save();
				break;
			}else if(ch=='y' || ch=='Y'){
				setDice();
			}else {
				System.out.println("�߸� �Է��ϼ̽��ϴ�.");
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
			System.out.println("                   ������ �����մϴ�. ��ŷ�� �ݿ��Ǿ����ϴ�!"); 
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
//		return String.format("�� "+vo.getName()+", �ܾ� :"+seed+"��\n");
//	}




}
