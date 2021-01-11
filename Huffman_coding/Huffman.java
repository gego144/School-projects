
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.*;

public class Huffman {
    //C:\Users\adane\IdeaProjects\Assignment4_2110\src\Pokemon.txt
    /**
     Code
     provided from previous version and modified for 2020.
     */
    public static void encode()throws IOException {
        // initialize Scanner to capture user input
        Scanner sc = new Scanner(System.in);

        // capture file information from user and read file
        System.out.print("Enter the filename to read from/encode: ");
        String f = sc.nextLine();

        // create File object and build text String
        File file = new File(f);
        Scanner input = new Scanner(file).useDelimiter("\\z");
        String text = input.next();

        // close input file
        input.close();

        // initialize Array to hold frequencies (indices correspond to
        // ASCII values)
        int[] freq = new int[256];
        // concatenate/sanitize text String and create character Array
        // nice that \\s also consumes \n and \r
        // we can add the whitespace back in during the encoding phase

        char[] chars = text.replaceAll("\\s", "").toCharArray();

        // count character frequencies
        for (char c : chars)
            freq[c]++;


        //Your work starts here************************************8


        // making a pair arraylist and equating the character value to the probability of it showing up
        ArrayList<Pair> pairs = new ArrayList<Pair>();
        for (int i = 0; i < 256; i++) {
            if (freq[i] != 0) {
                // this method of rounding is good enough
                Pair p = new Pair((char) i, Math.round(freq[i] * 10000d / chars.length) / 10000d);
                pairs.add(p);
            }
        }
        Collections.sort(pairs);


        //Apply the huffman algorithm here and build the tree ************************************

        // making list for s and t
        Queue<BinaryTree<Pair>> s = new LinkedList<BinaryTree<Pair>>();
        Queue<BinaryTree<Pair>> t = new LinkedList<BinaryTree<Pair>>();


        // adding pairs to the tree
        for (int i = 0; i < pairs.size(); i++) {
            BinaryTree<Pair> pairtree = new BinaryTree<Pair>();
            pairtree.makeRoot(pairs.get(i));
            s.add(pairtree);
        }

        // making a and b
        BinaryTree<Pair> A = new BinaryTree<Pair>();
        BinaryTree<Pair> B = new BinaryTree<Pair>();

        // while loop that keeps on going until s is empty
        while (!s.isEmpty()) {
            // if t is empty remove the front two from s
            if (t.isEmpty()) {
                A = s.poll();
                B = s.poll();
            } else {

                // check to see if the front of t is smaller or s and removing the smaller one and making it a
                if (t.peek().getData().getProb() < s.peek().getData().getProb()) {
                    A = t.poll();
                } else {
                    A = s.poll();
                }

                // check to see if s and t is not empty
                if(!t.isEmpty() && !s.isEmpty()) {
                    // check to see if the front of t is smaller or s and removing the smaller one and making it b
                    if (t.peek().getData().getProb() < s.peek().getData().getProb()) {
                        B = t.poll();
                    } else {
                        B = s.poll();
                    }
                }
                // if t is empty keep removing the front from s
                else if(t.isEmpty()){
                    B = s.poll();
                }
                // if s is empty keep removing the front from t
                else if(s.isEmpty()){
                    B = t.poll();
                }

            }
            // getting the probabilities of a and b and making the huffman tree
            BinaryTree<Pair> E = new BinaryTree<Pair>();
            Pair abc = new Pair('⁂', A.getData().getProb() + B.getData().getProb());
            E.makeRoot(abc);
            E.attachLeft(A);
            E.attachRight(B);
            t.add(E);

        }

        // while the size of t is greater then 1, keep removing the front two of t and adding them to the huffman tree
        while(t.size() > 1){
            A = t.poll();
            B = t.poll();
            BinaryTree<Pair> P = new BinaryTree<Pair>();
            Pair thePair = new Pair('⁂', A.getData().getProb() + B.getData().getProb());
            P.makeRoot(thePair);
            P.attachLeft(A);
            P.attachRight(B);
            t.add(P);
        }


        //can be used to get the codes
        String[] codes = findEncoding(t.peek());

        PrintStream output = new PrintStream("Encoded.txt");

        // for loop to write all the text into the encoded text
        for(int x =0; x<text.length(); x++){
            if(text.charAt(x) == ' '){
                output.print(' ');
            }
            else{
                output.print(codes[text.charAt(x)]);
            }
        }

        System.out.println("Codes generated. Printing codes to Huffman.txt");
        System.out.println("Printing encoded text to Encoded.txt");
        System.out.println("");
        System.out.println("*****");
        System.out.println("");

        PrintStream second_one = new PrintStream("Huffman.txt");

        // for loop that writes all the text in huffman text
        second_one.println("Symbol prob. Huffman Code");
        for(int q =0; q < pairs.size(); q++){
            second_one.println(pairs.get(q).getValue() + "            " + pairs.get(q).getProb()+ "  " + codes[pairs.get(q).getValue()]);
        }


    }


    public static void decode()throws IOException{
        // initialize Scanner to capture user input
        Scanner sc = new Scanner(System.in);

        // capture file information from user and read file
        System.out.print("Enter the filename to read from/decode: ");
        String f = sc.nextLine();

        // create File object and build text String
        File file = new File(f);
        Scanner input = new Scanner(file).useDelimiter("\\Z");
        String text = input.next();
        // ensure all text is consumed, avoiding false positive end of
        // input String
        input.useDelimiter("\\z");


        // close input file
        input.close();

        // capture file information from user and read file
        System.out.print("Enter the filename of document containing Huffman codes: ");
        f = sc.nextLine();

        // create File object and build text String
        file = new File(f);
        input = new Scanner(file).useDelimiter("\\Z");
        String codes = input.next();

        // close input file
        input.close();

        //Your work starts here********************************************

        // using huffman_code to hold the huffman codes and letter to hold the letters and each index value in both
        // arraylists match up each huffman code to a letter
        ArrayList<String> huffman_code = new ArrayList<>();
        ArrayList<Character> letter = new ArrayList<>();

        Scanner ls = new Scanner(codes);
        ls.nextLine();
        while(ls.hasNextLine()){
            char c = ls.next().charAt(0);
            ls.next(); // consume/discard probability
            String s = ls.next();
            huffman_code.add(s);
            letter.add(c);
        }

        // sentence variable holds full sentence, current_code holds current code being looked at, location_letter looks
        // at the the letter value at the same index at the code value
        String sentence = "";
        String current_code = "";
        int location_letter = 0;
        // for loop that goes over the encoded text file
        for(int j = 0; j < text.length(); j++){
            // if the value is space then add a space to the sentence
            if(text.charAt(j) == ' '){
                sentence += " ";
            }
            else {
                // adding each index value to current_code until that code value appears in huffman_code then adding it
                // to sentence and resetting the current_code value
                current_code += text.charAt(j);
                for(String code: huffman_code){
                    if (current_code.equals(code)){
                        location_letter = huffman_code.indexOf(code);
                        sentence += letter.get(location_letter);
                        current_code = "";
                    }
                }
            }
        }
        // writing the sentence in decoded text
        PrintStream output_decode = new PrintStream("Decoded.txt");
        output_decode.print(sentence);
        System.out.println("Printing decoded text to Decoded.txt");
    }

    // the findEncoding helper method returns a String Array containing
    // Huffman codes for all characters in the Huffman Tree (characters not
    // present are represented by nulls)
    // this method was provided by Srini (Dr. Srini Sampalli). Two versions are below, one for Pairtree and one for BinaryTree


    private static String[] findEncoding(BinaryTree<Pair> bt){
        String[] result = new String[256];
        findEncoding(bt, result, "");
        return result;
    }


    private static void findEncoding(BinaryTree<Pair> bt, String[] a, String prefix){
        // test is node/tree is a leaf
        if (bt.getLeft()==null && bt.getRight()==null){
            a[bt.getData().getValue()] = prefix;
        }
        // recursive calls
        else{
            findEncoding(bt.getLeft(), a, prefix+"0");
            findEncoding(bt.getRight(), a, prefix+"1");
        }
    }

}

