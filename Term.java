/*
 * Term.java
 *
 */
import java.util.*;

/**
 * Term is an immutable data type that represents an auto complete term: 
 * 
 * @author Aayush
 * @version 3/11/17
 * 
 */
public class Term implements Comparable<Term> {
	/**
	 * Instance variable for holding string
	 */
	private final String query;
	/**
	 * Instance variable for holding weight
	 */
	private final long weight;


	/**
	 * Constructor: Initializes a term with the specified query/weight
	 * @param query the query to be searched for
	 * @param weight the weight of the query
	 * @throws NullPointerException if string is null
	 * @throws IllegalArgumentException if weight < 0
	 */
	public Term(String query, long weight) {
		if (query == null) 
			throw new NullPointerException("query can't be null");
		if (weight < 0) 
			throw new IllegalArgumentException("weight must be nonnegative");

		this.query = query;
		this.weight = weight;
	}

	/**
	 * comparator method that provides method for comparing terms using their corresponding weights. 
	 * @return -1 if this.weight < that.weight, 0 if this.weight == that.weight
	 *          1 if this.weight > that.weight
	 */
	public static Comparator<Term> byReverseWeightOrder() {
		return new Comparator<Term>() {
			public int compare(Term x, Term y) {
				if (x.weight > y.weight) {
					return -1;
				} else if (x.weight < y.weight) {
					return 1;
				} else {
					return 0;
				}
			}
		};

	}




	/**
	 * Returns comparator that provides method for comparing terms 
	 * in lexicographic order using the first r characters of each query. 
	 * @param r the first r characters. 
	 * @return -1 if this.r <= that.r, 0 if this.r == that.r, 1 if this.r > that.r
	 * @throws new IllegalArgumentException if r < 0
	 */
	public static Comparator<Term> byPrefixOrder(int r) {
		if (r < 0) 
			throw new IllegalArgumentException("R should be >=  0");
		return new ByPrefixOrder(r);
	}

	/**
	 * Returns a string representation of this term in the following format:
	 * the weight, followed by a tab, followed by the query.
	 */
	public String toString() {
		return weight +"\t" + query;
	}

	/**
	 * Compares two terms in lexicographic order by query.
	 * @param that The term
	 * @return -1 if this < that, 0 if this == that, 1 if this > that
	 */
	@Override
	public int compareTo(Term that) {
		int comp = this.query.toLowerCase().compareTo(that.query.toLowerCase());
		if(comp <= -1) 
			return -1;
		else if (comp >= 1)
			return 1;
		else 
			return 0;       
	}

	/**
	 * Private class for byPrefxOrder
	 * @author Aayush
	 *
	 */
	private static class ByPrefixOrder implements Comparator<Term>  {
		/**
		 * int r for checking first r characters
		 */
		private final int r;        

		/**
		 * Private constructor for byPrefixOrder class
		 * @param r int for checking for r chars
		 */
		private ByPrefixOrder(int r) {
			this.r = r;
		}

		/**
		 * Compare method for comparing terms
		 * @param t1 The term 1
		 * @param t2 The term 2
		 */
		@Override
		public int compare(Term t1, Term t2) {
			int len1 = t1.query.length();
			int len2 = t2.query.length();

			if (len1 >= r && len2 >= r) {
				String s1 = t1.query.toLowerCase().substring(0, r);
				String s2 = t2.query.toLowerCase().substring(0, r);

				int comp = s1.compareTo(s2);
				if (comp <= -1) {
					return -1;
				}
				else if (comp >= 1)  {
					return 1;
				}
				else  
					return 0;
			} 
			else if (len1 < r && len2 >= r) {
				String s1 = t1.query.toLowerCase();
				String s2 = t2.query.toLowerCase().substring(0, r);

				int comp = s1.compareTo(s2);
				if (comp <= -1) return -1;
				else if (comp >= 1)  return 1;
				else                return 0;
			}
			else if (len2 < r && len1 >= r) {
				String s1 = t1.query.toLowerCase().substring(0, r);
				String s2 = t1.query.toLowerCase();

				int comp = s1.compareTo(s2);
				if (comp <= -1)
					return -1;
				else if (comp >= 1)
					return 1;
				else  
					return 0;
			}
			else {
				String s1 = t1.query.toLowerCase();
				String s2 = t2.query.toLowerCase();

				int comp = s1.compareTo(s2);
				if (comp <= -1) 
					return -1;
				else if (comp >= 1)
					return 1;
				else   
					return 0;
			}
		}
	}




	/**
	 * Application method
	 * @param args The command line arguments
	 */
	public static void main(String[] args) {

		// Creating new terms and printing it to check methods of this class
		Term[] terms = {new Term("Aayush", 3), new Term("aayush", 8), new Term("Abcd", (long) 1.52), new Term("abcd", (long) 0.71)};
		for (Term term : terms) 
			System.out.println(term);
		System.out.println();

		Arrays.sort(terms, Term.byReverseWeightOrder());
		for (Term term : terms) 
			System.out.println(term);
		System.out.println();

		Arrays.sort(terms, Term.byPrefixOrder(2));
		for (Term term : terms) 
			System.out.println(term);
		System.out.println();

	}
}