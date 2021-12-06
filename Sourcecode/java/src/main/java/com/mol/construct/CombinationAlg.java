package com.mol.construct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * CombinationAlg: 
 * @author Sudha Eswaran
 *
 */

class CombinationAlg {
	/*
	 * arr[] ---> Input Array data[] ---> Temporary array to store current
	 * combination start & end ---> Staring and Ending indexes in arr[] index --->
	 * Current index in data[] r ---> Size of a combination to be printed
	 */

	static ArrayList<ArrayList> arrList = new ArrayList<ArrayList>();

	static void combinationUtil(int arr[], int n, int r, int index, int data[], int i) {
		// Current combination to be returned
		ArrayList<Integer> tempArr = new ArrayList<Integer>();
		if (index == r) {
			for (int j = 0; j < r; j++) {
				tempArr.add(data[j]);
			}
			arrList.add(tempArr);
			return;
		}

		// When no more elements are there to put in data[]
		if (i >= n)
			return;

		// current is included, put next at next location
		data[index] = arr[i];
		combinationUtil(arr, n, r, index + 1, data, i + 1);

		// current is excluded, replace it with next (Note that
		// i+1 is passed, but index is not changed)
		combinationUtil(arr, n, r, index, data, i + 1);
	}

	// The main function that prints all combinations of size r
	// in arr[] of size n. This function mainly uses combinationUtil()
	private static ArrayList<ArrayList> getCombination(int n) {
		int arr[] = new int[n];
		for (int i = 1; i < n + 1; i++) {
			arr[i - 1] = i;
		}
		int r = 2;
		// A temporary array to store all combination one by one
		int data[] = new int[r];

		// Print all combination using temporary array 'data[]'
		combinationUtil(arr, n, r, 0, data, 0);
		// System.out.println("Combinations inside="+arrList);
		return arrList;
	}

	/**
	 * possiblegraph
	 * @param arlist
	 * @param dp
	 * @param bc
	 * @return returnMap
	 */
	private static ArrayList<ArrayList<Integer>> possiblegraph(ArrayList<ArrayList> arlist, int dp, int bc) {
		HashMap<Integer, ArrayList<ArrayList<Integer>>> returnMap = new HashMap<Integer, ArrayList<ArrayList<Integer>>>();
		ArrayList<ArrayList<Integer>> returnList = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> possiblerootNodes = new ArrayList<ArrayList<Integer>>();
		for (List data : arlist) {
			if ((int) data.get(0) == 1 && (int) data.get(1) <= dp) {
				possiblerootNodes.add((ArrayList<Integer>) data);
			}
		}
		int i = 0;

		for (List<Integer> rootnode : possiblerootNodes) {
			returnList.add((ArrayList<Integer>) rootnode);

			int prevCNode = (int) rootnode.get(1);

			int cnt = 0;
			List<ArrayList<Integer>> brachedData = new ArrayList<ArrayList<Integer>>();
			for (List data : arlist) {

				if (bc == 0 || (brachedData.size() > 0 && (int) data.get(0) != (int) brachedData.get(0).get(1)
						&& (int) data.get(1) != (int) brachedData.get(0).get(1))) {
					if (prevCNode == (int) data.get(0)) {
						returnList.add((ArrayList<Integer>) data);
						prevCNode = (int) data.get(1);
					}
				}
				if (bc != 0 & cnt == 0 && (int) data.get(1) > (int) data.get(0) + 1) {
					int nc = getRandomNumberInRange(1, arlist.size() - 1);
					cnt++;
					brachedData.add(arlist.get(nc));
					returnList.add(arlist.get(nc));
				}
			}
			returnMap.put(i++, returnList);
			returnList = new ArrayList<ArrayList<Integer>>();
		}

		return returnMap.get(0);
	}

	static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	static ArrayList<ArrayList<Integer>> getCombination(int n, int bc) {
		return (ArrayList<ArrayList<Integer>>) possiblegraph(getCombination(n), n, bc);
	}

	private static void helper(List<int[]> combinations, int data[], int start, int end, int index) {
		if (index == data.length) {
			int[] combination = data.clone();
			combinations.add(combination);
		} else if (start <= end) {
			data[index] = start;
			helper(combinations, data, start + 1, end, index + 1);
			helper(combinations, data, start + 1, end, index);
		}
	}

	public static List<int[]> generate(int n, int r) {
		List<int[]> combinations = new ArrayList<>();
		helper(combinations, new int[r], 1, n, 0);
		return combinations;
	}

	/* Driver function to check for above function */
	public static void main(String[] args) {
		int n = 6;
		int r = 2;

		List<int[]> combinations = generate(n, r);
		for (int[] combination : combinations) {
			System.out.println(Arrays.toString(combination));
		}
		System.out.printf("generated %d combinations of %d items from %d ", combinations.size(), r, n);
	}

}
