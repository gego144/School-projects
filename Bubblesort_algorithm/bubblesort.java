
import java.util.Scanner;



public class bubblesort {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // asking the user how many items are in the list
        System.out.println("How many items are in the list?");
        int list_length = in.nextInt();
        String g = in.nextLine();
        // asking the user if they want string or ints
        System.out.println("Are we sorting String or Integers?");
        String string_or_integers = in.nextLine();
        // variable to hold each value
        String value;
        int number;
        // adding all the values to the array
        System.out.println("Enter values");
        Integer[] numbers;
        String[] values;


        // making the integer array
        if(string_or_integers.equals("Integers")){
            numbers = new Integer[list_length];
            for(int i = 0; i < list_length; i++){
                number = in.nextInt();
                numbers[i] = number;
            }
            // asking the user if they want the max or min and calling either bubblesortmax or bubblesortmin
            System.out.println("Do you want to sort by Max or Min?");
            g = in.nextLine();
            String max_or_min = in.nextLine();
            if(max_or_min.equals("Max")){
                bubbleSortMax(numbers);
            }
            else{
                bubbleSortMin(numbers);
            }
            // output
            for (int j = 0; j < numbers.length; j++){
                System.out.print(numbers[j] + " ");
            }
        }

        else{
            // making the string array
            values = new String[list_length];
            for(int i = 0; i < list_length; i++){
                value = in.nextLine();
                values[i] = value;
            }
            //asking the user if they want the max or min and calling either bubblesortmax or bubblesortmin
            System.out.println("Do you want to sort by Max or Min?");
            String max_or_min = in.nextLine();
            if(max_or_min.equals("Max")){
                bubbleSortMax(values);
            }
            else{
                bubbleSortMin(values);
            }
            //output
            for (int j = 0; j < values.length; j++){
                System.out.print(values[j] + " ");
            }
        }


    }
    public static <T extends Comparable<T>> T[] bubbleSortMin (T[] list){
        // variables to keep track of the amount of sorts and if its sorted or not yet
        int swaps = 0;
        boolean sorted = false;
        // while its not sorted go through the while loop
        while(!sorted){
            // setting sorted to true and only changing it if its not by comparing all the values and finding
            // looking for one instance of a value being in the wrong spot
            sorted = true;
            for(int i=0; i < list.length-1; i++){
                    if (list[i].compareTo(list[i + 1]) > 0) {
                        sorted = false;
                    }
            }
            // if its still true then break the loop and print the amount of swaps
            if(sorted){
                System.out.println("Sorting complete. Total swaps:" + swaps);
                break;
            }

            // for loop that swaps the values
            for(int q=0; q < list.length-1; q++){
                T holder_value;
                int c = q +1;
                // if the next value is larger then dont swap them
                    if(list[q].compareTo(list[q+1]) < 0){
                    }
                    // else swap them by using a holder value to save the current value and iterate the swaps
                    else{
                        holder_value = list[q];
                        list[q] = list[c];
                        list[c] =  holder_value;
                        swaps ++;
                    }
                }
            }
        // return the list
        return list;
    }
    public static <T extends Comparable<T>> T[] bubbleSortMax (T[] list){
        // variables to keep track of the amount of sorts and if its sorted or not yet
        int swaps = 0;
        boolean sorted = false;
        // while its not sorted go through the while loop
        while(!sorted){
            // setting sorted to true and only changing it if its not by comparing all the values and finding
            // looking for one instance of a value being in the wrong spot
            sorted = true;
            for(int i=0; i < list.length-1; i++){
                if (list[i].compareTo(list[i + 1]) < 0) {
                    sorted = false;
                }
            }
            // if its still true then break the loop and print the amount of swaps
            if(sorted){
                System.out.println("Sorting complete. Total swaps:" + swaps);
                break;
            }

            // for loop that swaps the values
            for(int q=0; q < list.length-1; q++){
                T holder_value;
                int c = q +1;
                // if the next value is smaller then dont swap them
                if(list[q].compareTo(list[q+1]) > 0){
                }
                // else swap them by using a holder value to save the current value and iterate the swaps
                else{
                    holder_value = list[q];
                    list[q] = list[c];
                    list[c] =  holder_value;
                    swaps ++;
                }
            }
        }
        // return the list
        return list;
    }


}
