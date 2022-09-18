/*
* AUTHOR: Victoria Santos
* FILE: PA12Main.java
* ASSIGNMENT: PA12 Anagrams
* COURSE: CSc 210 001; Spring 2022
* PURPOSE: This program takes in three command line args:
* a .txt dictionary file, a word, and a max int. The
* program then finds all the possible anagrams for the
* given word that can be created from the dictionary words.
* words. If a max int is specified, it will only print
* anagrams with that number of words or fewer. 
* 
* USAGE: 
* java .txt infile, String word, String max int
*
* where infile is the name of an input file in the following format
*
* --------------------------- EXAMPLE INPUT ----------------------------------
* Input file: dict1.txt
* ----------------------------------------------------------------------------
* | 
* | abash
* | aura
* | bar
* | barb
* | bee
* | beg
* | blush
* | bog
* | bogus
* | bough
* | bow
* | brew
* | briar
* | brow
* | brush
* | ....
* | 
* -----------------------------------------------------------------------------
* 
*/


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class PA12Main {

    /**
     * Main function for PA12. Uses String[] args to get
     * the given .txt file, word, and max int. Calls
     * the necessary functions and prints some of the
     * final output.
     * 
     * @param String[]
     *            args, with .txt file, word, and max
     * 
     * @return none
     */
    public static void main(String[] args) {
        File wordsDict = new File(args[0]);
        LetterInventory letterInventory = new LetterInventory(args[1]);
        List<String> usableWords = filterDict(wordsDict, letterInventory);
        int max = Integer.parseInt(args[2]);

        System.out.println("Phrase to scramble: " + args[1]);
        System.out.println();
        System.out.println("All words found in " + args[1] + ":");
        System.out.println(usableWords);
        System.out.println();
        System.out.println("Anagrams for " + args[1] + ":");

        findAnagrams(usableWords, letterInventory, max);

    }

    /**
     * Reads in the words from the dictionary and only
     * keeps the ones that can be used to form an
     * anagram for the given word. The eligible
     * words are added to a list of Strings, which
     * is used by the recursive functions.
     * 
     * @param wordsDict,
     *            the .txt File object of words
     * @param letterInventory,
     *            the LetterInventory object
     *            of letters for the given word
     * 
     * @return none
     */
    private static List<String> filterDict(File wordsDict,
            LetterInventory letterInventory) {
        Scanner scanner;
        List<String> usableWords = new ArrayList<String>();

        try {
            scanner = new Scanner(wordsDict);
            while (scanner.hasNext()) {
                String word = scanner.next();
                if (letterInventory.contains(word)) {
                    usableWords.add(word);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }

        return usableWords;

    }

    /**
     * Starts the recursive backtracking for finding
     * all the given word's anagrams. Starts the call
     * with the list of usable words, the word's
     * letter inventory, the specified max, and empty
     * anagram & current anagram lists.
     * 
     * @param usableWords,
     *            the String list of possible
     *            anagram words
     * @param letterInventory,
     *            the LetterInventory object
     *            of letters for the given word
     * @param max,
     *            the specified max as an int
     * 
     * @return none
     */
    private static void findAnagrams(
            List<String> usableWords,
            LetterInventory letterInventory, int max) {

        ArrayList<ArrayList<String>> anagrams = new ArrayList<ArrayList<String>>();
        ArrayList<String> currAnagram = new ArrayList<String>();

        findAnagramsHelper(usableWords, letterInventory, anagrams, currAnagram,
                max);

    }

    /**
     * Does the recursive backtracking. Creates anagram
     * combinations by only adding words which have
     * letters in the letterInventory, and seeing if
     * the current anagram has covered all the letters
     * in the inventory. If a max has been specified,
     * it will only print anagrams with that number
     * of words or fewer.
     * 
     * @param usableWords,
     *            the String list of possible
     *            anagram words
     * @param letterInventory,
     *            the LetterInventory object
     *            of letters for the given word
     * @param max,
     *            the specified max as an int
     * @param anagrams,
     *            the final list of anagrams
     * @param currAnagram,
     *            the current anagram
     *            combination as a list of Strings
     * 
     * @return none
     */
    private static void findAnagramsHelper(
            List<String> usableWords,
            LetterInventory letterInventory,
            ArrayList<ArrayList<String>> anagrams,
            ArrayList<String> currAnagram, int max) {

        if (letterInventory.isEmpty()) {
            System.out.println(currAnagram);
            anagrams.add(currAnagram);
        } else if (max == 0 || currAnagram.size() < max) {
            for (int i = 0; i < usableWords.size(); i++) {

                // Choose
                String currWord = usableWords.get(i);
                if (letterInventory.contains(currWord)) {
                    currAnagram.add(currWord);
                    letterInventory.subtract(currWord);

                    // Explore
                    findAnagramsHelper(usableWords, letterInventory, anagrams,
                            currAnagram, max);

                    // Unchoose
                    currAnagram.remove(currAnagram.size() - 1);
                    letterInventory.add(currWord);
                }
            }
        }
    }
}
