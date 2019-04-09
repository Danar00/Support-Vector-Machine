package com.company;

import java.util.ArrayList;

public class SupportVectorMachine {

    static int iterasi = 1;

    //Normalisasi berat badan
    public ArrayList normalizedBeratBadan(ArrayList<Data> data){
        double maxBB = data.get(0).getBeratBadan();
        double minBB = data.get(0).getBeratBadan();

        for (int i=0; i<data.size(); i++){
            if(data.get(i).getBeratBadan() > maxBB){
                maxBB = data.get(i).getBeratBadan();
            }
        }

        for(int i=0; i<data.size(); i++){
            if(data.get(i).getBeratBadan() < minBB){
                minBB = data.get(i).getBeratBadan();
            }
        }

        for(int i=0; i<data.size(); i++){
            data.get(i).setBeratBadan((data.get(i).getBeratBadan() - minBB)/(maxBB - minBB));
        }

        return data;
    }

    //Normalisasi tinggi badan
    public  ArrayList normalizedTinggiBadan(ArrayList<Data> data){
        double maxTT = data.get(0).getTinggiBadan();
        double minTT = data.get(0).getTinggiBadan();

        for (int i=0; i<data.size(); i++){
            if(data.get(i).getTinggiBadan() > maxTT){
                maxTT = data.get(i).getTinggiBadan();
            }
        }

        for(int i=0; i<data.size(); i++){
            if(data.get(i).getTinggiBadan() < minTT){
                minTT = data.get(i).getTinggiBadan();
            }
        }


        for(int i=0; i<data.size(); i++){

            data.get(i).setTinggiBadan((data.get(i).getTinggiBadan() - minTT)/(maxTT- minTT));
        }

        return data;
    }


    //Count matrix from kernel k(xi,xj) then  k + lambda^2
    public double[][] countMatrixKernel(ArrayList<Data> data, double lambda){
        double[][] matrixKernel = new double[data.size()][data.size()];

        //Hitung k(xi,xj)
        for (int i=0; i<matrixKernel.length; i++){
            for(int j=0; j<data.size(); j++){
                matrixKernel[i][j] = Math.pow( (data.get(i).getBeratBadan() * data.get(j).getBeratBadan()) +
                        (data.get(i).getTinggiBadan() * data.get(j).getTinggiBadan()) + 1, 2 );
            }
        }

        //Hitung k + lambda^2
        for(int i=0; i<matrixKernel.length; i++){
            for (int j=0; j<matrixKernel.length; j++){
                matrixKernel[i][j] = matrixKernel[i][j] + Math.pow(lambda,2);
            }
        }

        return matrixKernel;
    }

    //Count matrix from class
    public double[][] countMatrixKelas(ArrayList<Data> data){

        double[][] matrixKelas = new double[data.size()][data.size()];

        for(int i=0; i<data.size(); i++){
            for (int j=0; j<data.size(); j++){
                matrixKelas[i][j] = data.get(i).getKelas() * data.get(j).getKelas();
            }
        }

        return matrixKelas;
    }

    //Count DiDj matix
    public double[][] countD(double[][] matrixKernel, double[][] matrixKelas){
        double[][] D = new double[matrixKernel.length][matrixKernel.length];

        for(int i=0; i<matrixKernel.length; i++){
            for(int j=0; j<matrixKernel.length; j++){
                D[i][j] = matrixKernel[i][j] * matrixKelas[i][j];
            }
        }

        return D;
    }

    //Create ALpha matrx has zero value
    public double[] createAlphaMatrix(ArrayList<Data> data){
        double[] alphaMatrix = new double[data.size()];

        return alphaMatrix;
    }

    //Count alpha matrix
    public void countAlpha(double[][] D, double gamma, double[] alpha,double C){

        double[] Ei = new double[alpha.length];
        double[] dAlphaI = new double[alpha.length];

        //Flag for determine its still looping or not
        boolean loop = false;

        //count Ei = matrix alpha * D
        for(int i=0; i<D.length; i++){
            for(int j=0; j<D.length; j++){
                Ei[i] += D[j][i] * alpha[j];

                //If Ei value > 1 set flag
                if(Ei[0] < 1 ){

                    loop = true;

                }

            }
        }

        //Data d Alpha I = MIN[MAX[gamma * (1 - Ei), - alphai], (C - alphai)]

        for (int i=0; i<D.length; i++){
                dAlphaI[i] = Math.min(Math.max(gamma * (1 - Ei[i]),- alpha[i]),(C - alpha[i]));
        }

        //New alpha = alphai + d Alpha i
        for (int i=0; i<alpha.length; i++){
            alpha[i] = alpha[i] + dAlphaI[i];
        }

        if(loop){

            //====Print untuk cek hasil=====
            System.out.println("Iterasi ke = " + iterasi);
            System.out.print("Ei : ");
            for(int i=0; i<Ei.length; i++){
                System.out.print(" " + Ei[i] + " ");
            }
            System.out.println();
            System.out.print("DAi : ");
            for(int i=0; i<dAlphaI.length; i++){
                System.out.print(" " + dAlphaI[i] + " ");
            }
            System.out.println();
            System.out.print("Alpha : ");
            for(int i=0; i<alpha.length; i++){
                System.out.print(" " + alpha[i] + " ");
            }
            System.out.println();
            iterasi++;
            //=============================

            //Do recursive looping again
            countAlpha(D,gamma,alpha,C);
        }else{
            System.out.println("Sukses");
        }

    }

    //Determine new class
    public void determineClass(double beratBadan, double tinggiBadan,double[] alpha, ArrayList<Data> data){

        double res = 0;

        for (int i=0; i<alpha.length; i++){
            res += Math.pow((data.get(i).getBeratBadan() * beratBadan) + (data.get(i).getTinggiBadan() * tinggiBadan) + 1,2)
                * alpha[i] * data.get(i).getKelas();
        }

        if(res > 0){
            System.out.println("Kelas normal");
        }else{
            System.out.println("Kelas tidak normal");
        }
    }

    //Get maximum value of [Dij]
    public double getMaxD(double[][] D){
        double maxD = D[0][0];

        for (int i=0; i<D.length; i++){
            for (int j=0; j<D[0].length; j++){
                if(D[i][j] > maxD){
                    maxD = D[i][j];
                }
            }
        }

        return maxD;
    }

    public void SVM(ArrayList<Data> data, double gamma,double C,  double lambda,double newBeratBadan, double newTinggiBadam){

        ArrayList<Data> normalizedData = normalizedBeratBadan(data);
        normalizedData = normalizedTinggiBadan(data);

        for (Data data1:normalizedData){
            System.out.printf("%.1f             %.1f            %d\n",data1.getBeratBadan(),data1.getTinggiBadan(),data1.getKelas());
        }

        double[][] matrixKernel = countMatrixKernel(normalizedData, lambda);

        double[][] matrixKelas = countMatrixKelas(normalizedData);

        double[][] D = countD(matrixKernel,matrixKelas);

//        System.out.println("2/Max D " + 2/getMaxD(D));

        double[] alphaMatrix = createAlphaMatrix(normalizedData);

        countAlpha(D,gamma,alphaMatrix,C);

        determineClass(newBeratBadan, newTinggiBadam, alphaMatrix,normalizedData);

    }


}
