package com.company;

import jdk.internal.util.xml.impl.Input;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here

        Scanner scanner = new Scanner(System.in);

        ArrayList<Data> data = new ArrayList<>();

        data.add(new Data(60,165,1));
        data.add(new Data(70,160,1));
        data.add(new Data(80,165,1));
        data.add(new Data(100,155,-1));
        data.add(new Data(40,175,-1));

        System.out.println("================SVM=================");
        System.out.print("Input berat badan : ");
        double bb = scanner.nextDouble();

        System.out.print("Input berat tinggi : ");
        double tt = scanner.nextDouble();

        System.out.println("Berat Badan | Tinggi badan | Kelas");

        for (Data data1:data){
            System.out.printf("%.1f         %.1f            %d\n",data1.getBeratBadan(),data1.getTinggiBadan(),data1.getKelas());
        }

        System.out.println("============Normalisasi Data=============");
        System.out.println("Berat Badan | Tinggi badan | Kelas");
        SupportVectorMachine SVM = new SupportVectorMachine();
        SVM.SVM(data,0.01,5,4,bb,tt);


    }
}
