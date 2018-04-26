package com.qunar.interviewtest;

/**
 * Created by zhipengwu on 18-4-2.
 */
public class BubbleSort {
    public static void main(String[] args) {

        int[] A=new int[5];
        A[0]=10;
        A[1]=18;
        A[2]=7;
        A[3]=100;
        A[4]=14;
//        bubbleSort(A);


        quickSort(A,0,A.length-1);

        for (int i = 0; i < A.length; i++) {
            System.out.println(A[i]);
        }

    }

    public static void bubbleSort(int[] A){
        int len=A.length;
        for (int i = len-1; i > 0; i--) {
            int maxIndex=0;
            for (int j = 1; j <= i; j++) {
                if (A[j]>A[maxIndex]){
                    maxIndex=j;
                }
                int tmp=A[i];
                A[i]=A[maxIndex];
                A[maxIndex]=tmp;
            }
        }


    }

    public static void quickSort(int[] A, int start,int end){
        if (start<end){
            int mid=partition(A,start,end);
            quickSort(A,start,mid-1);
            quickSort(A,mid+1,end);
        }
    }


    public static int partition(int[] A,int start ,int end){
        int pivot=A[start];
        while (start<end){

            while (start<end&&A[end]>pivot){
                end--;
            }
            A[start]=A[end];
            while (start<end&&A[start]<=pivot){
                start++;
            }
            A[end]=A[start];
        }
        A[start]=pivot;
        return start;
    }
}
