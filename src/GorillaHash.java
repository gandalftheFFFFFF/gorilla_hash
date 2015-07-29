import java.util.Arrays;


public class GorillaHash {
	
	public static void main(String[] args) {
		
		int k = 15;
		int d = 10000;
		int numberOfSpecies = 12;
		
		String[] species = new String[numberOfSpecies];
		String[] sequences = new String[numberOfSpecies];
		Arrays.fill(sequences, "");
		
		int p = 0;
		while (StdIn.hasNextLine()) {
			
			species[p] = StdIn.readString();
			if (!species[p].startsWith(">")) throw new RuntimeException("Didn't start with >...");
			species[p] = species[p].replace(">", "");
			StdIn.readLine(); // Skip unnecessary stuff
			for (int l = 0; l < 3; l++) {
				sequences[p] = sequences[p] + StdIn.readLine();
			}
			
			p++;
		}
		
		int[][] profiles = new int[numberOfSpecies][d]; // This double array is meant to hold the profiles of each species
		
		for (int i = 0; i < numberOfSpecies; i++) {
			profiles[i] = getProfile(sequences[i], k, d);
		}
		
		printSimilarities(profiles, species);
		System.out.printf("%5f  %5f", similarity(profiles[0], profiles[11]), similarity(profiles[0], profiles[1]));
		System.out.println();
		
		test();
		
	}
	
	private static double similarity(int[] p, int[] q) {
		
		double similarity = 0;
		
		// p \dot q divided by ( len(p) * len(q))
		
		// dot product of p dot q = p1*q1 + p2*q2 + p3*q3 ... pn*qn
		
		double dotProduct = 0;
		for (int i = 0; i < p.length; i++) {
			dotProduct += (p[i] * q [i]);
		}
		
		// Length of a n-dim vector is = \sqrt{x1^2 + x2^2 + x3^2 +... xn^2} 
		double lenP = vectorLength(p);
		double lenQ = vectorLength(q);
		double denominator = lenP * lenQ;

		// one equation to rule them all
		similarity = dotProduct / denominator;
		
		// and in the darkness, return them
		return similarity;
	}
	
	public static double vectorLength(int[] p) {
		double lenP = 0;
		for (int i = 0; i < p.length; i++) {
			lenP += Math.pow(p[i], 2);
		}
		lenP = Math.sqrt(lenP);
		return lenP;
	}
	
	private static int[] getProfile(String s, int gramLength, int profileLength) {
		
		// Generate each gram of the string s, and map it to the profile array
		
		int[] profile = new int[profileLength];
		
		// get grams of a dna string
		String[] grams = getGrams(s, gramLength);
		
		// hashcode % profileLength each gram to the profile
		for (int i = 0; i < grams.length; i++) {
			int hash = Math.abs(grams[i].hashCode()) % profileLength;
			profile[hash] = profile[hash] + 1;
		}
		
		return profile;
	}
	
	private static String[] getGrams(String s, int gramLength) {
		
		String[] grams = new String[s.length()-gramLength+1]; // +1 because otherwise the 2-grams of "niels" wouldn't include "ls" (the last gram)
		for (int i = 0; i < grams.length; i++) {
			int end = i + (gramLength);
			grams[i] = s.substring(i, end);
		}
		
		return grams;
	}
	
	public static void printSimilarities(int[][] profiles, String[] species) {

		System.out.printf("%15s", "");
		for (int i = 0; i < species.length; i++) {
			System.out.printf("%15s", species[i]);
		}
		
		System.out.println();
		
		for (int i = 0; i < profiles.length; i++) {
			System.out.printf("%15s", species[i]);
			for (int j = 0; j < profiles.length; j++) {
				System.out.printf("%15f", similarity(profiles[i], profiles[j]));
			}
			System.out.println();
		}
		
	}
	
	public static void test() {
		
		int[] ar1 = new int[]{0,1};
		int[] ar2 = new int[]{0,2};
		int[] ar3 = new int[]{1,0};
		int[] ar4 = new int[]{0,-1};
		
		System.out.println("------------ cos_angle --------------");
		
		System.out.println(similarity(ar1, ar1));
		System.out.println(similarity(ar1, ar2));
		System.out.println(similarity(ar1, ar3));
		System.out.println(similarity(ar1, ar4));
		
		int[] ar5 = new int[1000];
		int[] ar6 = new int[1000];
		for (int i = 0; i < 1000; i++) {
			ar5[i] = (int)(Math.random() + 0.5);
			ar6[i] = (int)(Math.random() + 0.5);
		}
		
		System.out.println(similarity(ar5, ar6));
		
		System.out.println("------------ lengths --------------");
		
		System.out.println(vectorLength(ar1));
		System.out.println(vectorLength(ar2));
		System.out.println(vectorLength(ar3));
		System.out.println(vectorLength(ar4));
		System.out.println(vectorLength(ar5));
		System.out.println(vectorLength(ar6));
		
		
	}
	

}