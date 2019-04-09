/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package svm;

import java.util.Scanner;

/**
 *
 * @author Danar
 */
public class SVM {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        double Input[]=new double [2];
        System.out.print("Masukan Berat   : ");
        Input[0]= input.nextDouble();
        System.out.print("Masukan Tinggi  : ");
        Input[1]= input.nextDouble();
        for (int i = 0; i < Input.length; i++) {
            System.out.println(Input[i]);
        }
        int lamda = 2;
        double gamma = 0.002;
        int c = 2;
        double alfa[] = {0, 0, 0, 0, 0};
        double ei[] = new double[5];
        double deltaAlfa[] = new double[5];
        double data[][] = new double[5][3];
        data[0][0] = 60;
        data[0][1] = 165;
        data[0][2] = 1;
        data[1][0] = 70;
        data[1][1] = 160;
        data[1][2] = 1;
        data[2][0] = 80;
        data[2][1] = 165;
        data[2][2] = 1;
        data[3][0] = 100;
        data[3][1] = 155;
        data[3][2] = -1;
        data[4][0] = 40;
        data[4][1] = 175;
        data[4][2] = -1;
        System.out.println("Tinggi | Berat | Kelas");
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                System.out.print(data[i][j]);
                System.out.print(" | ");
            }
            System.out.println("");
        }
        
        int len = data.length;
        double minValueBeratBadan, maxValueBeratBadan,
                minValueTinggiBadan, maxValueTinggitBadan;
        minValueBeratBadan = maxValueBeratBadan = data[0][0];
        minValueTinggiBadan = maxValueTinggitBadan = data[0][1];
        
        //mencari min berat badan
        for (int i = 0; i < len; i++) {
            if (minValueBeratBadan > data[i][0]) {
                minValueBeratBadan = data[i][0];
                
            }
        }
        
        //mencari max berat badan
        for (int i = 0; i < len; i++) {
            if (maxValueBeratBadan < data[i][0]) {
                maxValueBeratBadan = data[i][0];
            }
        }        
        
        //mencari min tinggi badan
        for (int i = 0; i < len; i++) {
            if (minValueTinggiBadan > data[i][1]) {
                minValueTinggiBadan = data[i][1];
            }
        }
        
        //mencari max tinggi badan
        for (int i = 0; i < len; i++) {
            if (maxValueTinggitBadan < data[i][1]) {
                maxValueTinggitBadan = data[i][1];
            }
        }
        //menghitung normalisasi berat badan
        for (int i = 0; i < len; i++) {            
            data[i][0] = ((data[i][0] - minValueBeratBadan) /(maxValueBeratBadan - minValueBeratBadan));
        }
        
        //menghitung normalisasi tinggi badan
        for (int i = 0; i < len; i++) {
            data[i][1] = ((data[i][1] - minValueTinggiBadan)/(maxValueTinggitBadan - minValueTinggiBadan));
        }
        
        // matriks kernel dengan polinomial ordo 2
        double matriks_kernel[][] = new double[5][5];
        System.out.println("Matriks Kernel");
        for (int i = 0; i < matriks_kernel.length; i++) {
            
            matriks_kernel[i][0] = Math.pow((data[0][0] * data[i][0]) + (data[0][1] * data[i][1]) + 1, 2);
            matriks_kernel[i][1] = Math.pow((data[1][0] * data[i][0]) + (data[1][1] * data[i][1]) + 1, 2);
            matriks_kernel[i][2] = Math.pow((data[2][0] * data[i][0]) + (data[2][1] * data[i][1]) + 1, 2);
            matriks_kernel[i][3] = Math.pow((data[3][0] * data[i][0]) + (data[3][1] * data[i][1]) + 1, 2);
            matriks_kernel[i][4] = Math.pow((data[4][0] * data[i][0]) + (data[4][1] * data[i][1]) + 1, 2);
        }
        
        for (int i = 0; i < matriks_kernel.length; i++) {
            for (int j = 0; j < matriks_kernel[i].length; j++) {
                System.out.print(matriks_kernel[i][j]);
                System.out.print(" | ");
            }
            System.out.println("");
        }
        //mencari matriks yi,yj
        System.out.println("Matriks YiYj");
        int matriks_yiyj[][] = new int[5][5];
        for (int i = 0; i < matriks_yiyj.length; i++) {
            matriks_yiyj[0][i] = (int) data[0][2] * (int) data[i][2];
            matriks_yiyj[1][i] = (int) data[1][2] * (int) data[i][2];
            matriks_yiyj[2][i] = (int) data[2][2] * (int) data[i][2];
            matriks_yiyj[3][i] = (int) data[3][2] * (int) data[i][2];
            matriks_yiyj[4][i] = (int) data[4][2] * (int) data[i][2];
        }
        for (int i = 0; i < matriks_yiyj.length; i++) {
            for (int j = 0; j < matriks_yiyj[i].length; j++) {
                System.out.print(matriks_yiyj[i][j]);
                System.out.print(" ");
            }
            System.out.println("");
        }
        //mencari matriks di,dj
        System.out.println("Matriks DiDj");
        double matriks_didj[][] = new double[5][5];
        for (int i = 0; i < matriks_kernel.length; i++) {
            for (int j = 0; j < matriks_kernel[0].length; j++) {
                matriks_kernel[i][j] = matriks_kernel[i][j] + Math.pow(lamda, 2);
            }
        }
        for (int i = 0; i < matriks_didj.length; i++) {
            for (int j = 0; j < matriks_didj[0].length; j++) {
                matriks_didj[i][j] = matriks_kernel[i][j] * matriks_yiyj[i][j];
            }
        }
        for (int i = 0; i < matriks_didj.length; i++) {
            for (int j = 0; j < matriks_didj[i].length; j++) {
                System.out.print(matriks_didj[i][j]);
                System.out.print(" | ");
            }
            System.out.println("");
        }
        
        //Ngecek 2/MaxDiDj
        double maxDiDj = matriks_didj[0][0];
        for (int i = 0; i < matriks_didj.length; i++) {
            for (int j = 0; j < matriks_didj.length; j++) {
                if (maxDiDj < matriks_didj[i][j]) {
                    maxDiDj = matriks_didj[i][j];
                }
            }
        }
        System.out.println("INI MATRIKS DiDj TERBESAR : "+maxDiDj);
        
        int z = 0;
        double total = 0;
        // z !=100
        while (z != 100) {
                System.out.println("Iterasi Ke-"+z);
                for (int i = 0; i < alfa.length; i++) {
                    for (int j = 0; j < alfa.length; j++) {
                        total += (alfa[i] * matriks_didj[i][j]);
                    }
                    if (ei[i] < 1) {
                        ei[i] = total;
                        deltaAlfa[i] = Math.min(Math.max(gamma * (1 - ei[i]), -alfa[i]), (c - alfa[i]));
                        alfa[i] = alfa[i] + deltaAlfa[i];
                    }
                }
                System.out.println("Nilai ei");
                for (int i = 0; i < ei.length; i++) {
                    System.out.print(ei[i] + " ");
                }
                System.out.println("");
                System.out.println("Nilai ∂α");
                for (int i = 0; i < alfa.length; i++) {
                    System.out.println(deltaAlfa[i]);
                }
                System.out.println("Alfa baru");
                for (int i = 0; i < alfa.length; i++) {
                    System.out.println(alfa[i]);
                }
            }
        
        double result[] =new double [5];
        result[0]=Math.pow(((data[0][0]*Input[0])+(data[0][1]*Input[1])+1), 2);
        result[1]=Math.pow(((data[1][0]*Input[0])+(data[1][1]*Input[1])+1), 2);
        result[2]=Math.pow(((data[2][0]*Input[0])+(data[2][1]*Input[1])+1), 2);
        result[3]=Math.pow(((data[3][0]*Input[0])+(data[3][1]*Input[1])+1), 2);
        result[4]=Math.pow(((data[4][0]*Input[0])+(data[4][1]*Input[1])+1), 2);
        System.out.println("=====================");
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }
        for (int i = 0; i < alfa.length; i++) {
            result[i]= alfa[i]*result[i];
            if (i==3 || i==4 ) {
                result[i]=-1*result[i];
            }
        }
        System.out.println("=======================");
        int resultakhir =0;
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }
        for (int i = 0; i < result.length; i++) {
            resultakhir += result[i];
        }
        System.out.println("==============Result Akhir============= ");
        System.out.println(resultakhir);
        if (resultakhir>0) {
            System.out.println("Kelas Normal");
        }else{
            System.out.println("Kelas Tidak Normal");
        }
    }

}
