package lab2.drdaniela.implementations;

import lab1.drdaniela.Cipher;

import java.awt.*;
public class PlayfairCipher1 implements Cipher
{
    //length of digraph array
    private int length = 0;
    private static String key = null;
    //creates a matrix for Playfair cipher
    private String [][] table;


    public PlayfairCipher1(String text, String key2)
    {
        String key = parseString(key2);
        while(key.equals(""))
            key = parseString(key2);
        table = this.cipherTable(key);

        String input = parseString(text);
        while(input.equals(""))
            input = parseString(text);
    }


    // this method parses an input string to remove numbers, punctuation,
    // and as well, replaces all letters J with I
    // and makes string massage all uppers case letters
    public String parseString(String message)
    {
        String parse = message;
        //converts the message in upper case
        parse = parse.toUpperCase();
        //the string to be substituted by space for each match (A to Z)
        parse = parse.replaceAll("[^A-Z]", "");
        //replace the letter J by I
        parse = parse.replace("J", "I");
        return parse;
    }


    //creates the cipher table based on some input string
    public String[][] cipherTable(String key)
    {
        //we will create a matrix of 5*5
        String[][] playfairTable = new String[5][5];
        // and will add to the key the Alphabet
        String keyString = key + "ABCDEFGHIKLMNOPQRSTUVWXYZ";
        // fill string array with empty string
        for(int i = 0; i < 5; i++)
            for(int j = 0; j < 5; j++)
                playfairTable[i][j] = "";
        for(int k = 0; k < keyString.length(); k++)
        {
            boolean repeat = false;
            boolean used = false;
            for(int i = 0; i < 5; i++)
            {
                for(int j = 0; j < 5; j++)
                {
                    if(playfairTable[i][j].equals("" + keyString.charAt(k)))
                    {
                        repeat = true;
                    }
                    // if the character hasn't been used and repeated
                    else if(playfairTable[i][j].equals("") && !repeat && !used)
                    {
                        // then we include the character, and set the usage as true
                        playfairTable[i][j] = "" + keyString.charAt(k);
                        used = true;
                    }
                }
            }
        }
        return playfairTable;
    }


    //---------------encryption logic-----------------
    //encodes the digraph input with the cipher's specifications
    public String[] encodeDigraph(String digraph[])
    {
        String[] encipher = new String[length];
        for(int i = 0; i < length; i++)
        {
            char a = digraph[i].charAt(0);
            char b = digraph[i].charAt(1);
            int r1 = (int) getPoint(a).getX();
            int r2 = (int) getPoint(b).getX();
            int c1 = (int) getPoint(a).getY();
            int c2 = (int) getPoint(b).getY();
            //executes if the letters of digraph appear in the same row
            //in such case shift columns to right
            if(r1 == r2)
            {
                c1 = (c1 + 1) % 5;
                c2 = (c2 + 1) % 5;
            }
            //executes if the letters of digraph appear in the same column
            //in such case shift rows down
            else if(c1 == c2)
            {
                r1 = (r1 + 1) % 5;
                r2 = (r2 + 1) % 5;
            }
            //executes if the letters of digraph appear in the different row and different column
            //in such case swap the first column with the second column
            else
            {
                int temp = c1;
                c1 = c2;
                c2 = temp;
            }
            // we perform the table look-up and puts those values into the encoded array
            encipher[i] = table[r1][c1] + "" + table[r2][c2];
        }
        return encipher;
    }


    // returns a point containing the row and column of the letter
    public Point getPoint(char c)
    {
        Point pt = new Point(0,0);
        for(int i = 0; i < 5; i++)
            for(int j = 0; j < 5; j++)
                if(c == table[i][j].charAt(0))
                    pt = new Point(i,j);
        return pt;
    }

    @Override
    public String encrypt(String message) {
        length = (int) message.length() / 2 + message.length() % 2;
        //we insert x between double-letter digraphs & redefines "length"
        for(int i = 0; i < (length - 1); i++)
        {
            // if 2 letters stay near each other
            if(message.charAt(2 * i) == message.charAt(2 * i + 1))
            {
                message = new StringBuffer(message).insert(2 * i + 1, 'X').toString();
                length = (int) message.length() / 2 + message.length() % 2;
            }
        }
        // we need to make plaintext of even length
        // create an array of digraphs
        String[] digraph = new String[length];
        //loop iterates over the plaintext
        for(int j = 0; j < length ; j++)
        {
            //checks the plaintext is of even length or not
            if(j == (length - 1) && message.length() / 2 == (length - 1))
                //if not addends X at the end of the plaintext
                message = message + "X";
            digraph[j] = message.charAt(2 * j) + "" + message.charAt(2 * j + 1);
        }
        //encodes the digraphs and returns the output
        String out = "";
        String[] encDigraphs = new String[length];
        encDigraphs = encodeDigraph(digraph);
        for(int k = 0; k < length; k++)
            out = out + encDigraphs[k];
        return out;
    }

    @Override
    public String decrypt(String message) {
        String decoded = "";
        for(int i = 0; i < message.length() / 2; i++)
        {
            char a = message.charAt(2 * i);
            char b = message.charAt(2 * i + 1);
            int r1 = (int) getPoint(a).getX();
            int r2 = (int) getPoint(b).getX();
            int c1 = (int) getPoint(a).getY();
            int c2 = (int) getPoint(b).getY();
            if(r1 == r2)
            {
                c1 = (c1 + 4) % 5;
                c2 = (c2 + 4) % 5;
            }
            else if(c1 == c2)
            {
                r1 = (r1 + 4) % 5;
                r2 = (r2 + 4) % 5;
            }
            else
            {
            //swapping logic
                int temp = c1;
                c1 = c2;
                c2 = temp;
            }
            decoded = decoded + table[r1][c1] + table[r2][c2];
        }
        //returns the decoded message
        return decoded;
    }
}
