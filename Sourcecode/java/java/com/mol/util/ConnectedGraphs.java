package com.mol.util;

import java.util.*;

import com.mol.construct.childNode;
import com.mol.mono.MonoUnit;


/**
 * ConnectedGraphs: Graph manipulation and curation
 * @author Sudha Eswaran
 *
 */

public class ConnectedGraphs {

	public List<childNode> addingBrancingUnits(List<childNode> cNodeList, Map<Integer, MonoUnit> monounitList, int dp,
			int[] branchingbndsper) {
		int numberof405bnds = branchingbndsper[0];
		int numberof55bnds = branchingbndsper[1];
		int numberofDBDObnds = branchingbndsper[2];

		boolean checkfornext = true;

		// System.out.println("\n\n chain starts");
		while (checkfornext) {
			Map<String, Object> returnObj = connectedChains(cNodeList);
			List<List<String>> connectedgraphs = (List<List<String>>) returnObj.get("connectedchains");
			List<String> missingNodes = (List<String>) returnObj.get("missingnodes");
			int noofunitstoadd = (connectedgraphs != null) ? connectedgraphs.size() : 0;

			/*
			 * System.out.println("\nnoofunitstoadd="+noofunitstoadd);
			 * System.out.println(cNodeList);
			 * System.out.println("connectedgraphs="+connectedgraphs);
			 * System.out.println("missingNodes="+missingNodes);
			 */

			if (noofunitstoadd <= 1 || (numberof405bnds <= 0 && numberof55bnds <= 0)) {
				checkfornext = false;
				// System.exit(0);
			}
			if (checkfornext) {
				boolean is4O5updated = false;
				boolean is55updated = false;
				boolean ispossibletolink = true;
				List<String> disconnetedNodeChain = connectedgraphs.get(connectedgraphs.size() - 1);
				Set<Integer> rndnode = new HashSet<Integer>();
				Random rand = new Random();
				int upperbound = disconnetedNodeChain.size();
				conloop: while (rndnode.size() < disconnetedNodeChain.size()) {
					int int_random = rand.nextInt(upperbound);
					int mn = (int_random == 0 && disconnetedNodeChain.size() == 1) ? int_random : int_random + 1;

					rndnode.add(int_random);
					// System.out.println(int_random);
					String disconnectednode = disconnetedNodeChain.get(int_random);
					int missingnodeid = Integer.parseInt(disconnectednode.substring(0, disconnectednode.length() - 1));
					// System.out.println("disconnectednode"+disconnectednode+",
					// missingnodeid"+missingnodeid);

					if (missingnodeid != 0) {
						MonoUnit newnode = monounitList.get(missingnodeid);
						childNode missingnode = new childNode();
						missingnode.setpType(newnode.getId() + newnode.getType());
						List<String> chainbefore = connectedgraphs.get(connectedgraphs.size() - 2);
						String nodeInMonoChainTwo;

						for (int l1 = 0; l1 < chainbefore.size(); l1++) {
							String nodeInMonoChain = chainbefore.get(l1);
							String prevnodeInMonoChain = null;
							if (chainbefore.size() > l1 + 1) {
								prevnodeInMonoChain = nodeInMonoChain;
								nodeInMonoChain = chainbefore.get(l1 + 1);
							}

							int elemInMonochain = Integer
									.parseInt(nodeInMonoChain.substring(0, nodeInMonoChain.length() - 1));

							// System.out.println("nodeInMonoChain"+nodeInMonoChain+",
							// elemInMonochain"+elemInMonochain);
							MonoUnit monoinchain = monounitList.get(elemInMonochain);

							/*
							 * System.out.println("newnode.isFivthC="+newnode.isFivthC()
							 * +", monoinchain.isFivthC="+monoinchain.isFivthC());
							 * System.out.println("newnode.isFourthC="+newnode.isFourthC()
							 * +", monoinchain.isFourthC="+monoinchain.isFourthC());
							 * System.out.println("newnode.getType="+newnode.getType()
							 * +", monoinchain.getType="+monoinchain.getType());
							 */

							if (Constants.mono_S.equals(newnode.getType())
									&& Constants.mono_S.equals(monoinchain.getType())) {
								ispossibletolink = false;
							}

							boolean randPick55 = true;
							if (numberof405bnds > 0 && numberof55bnds > 0) {
								Random rand2 = new Random();
								randPick55 = rand.nextBoolean();
							}

							// System.out.println("ispossibletolink="+ispossibletolink);
							if (ispossibletolink) {
								/* Creating 55 bond */
								if (randPick55 && newnode.isFivthC() && monoinchain != null && monoinchain.isFivthC()
										&& numberof55bnds > 0) {
									boolean isDBBOUpdated = false;
									/* Creating DBDO Bond */
									if (prevnodeInMonoChain != null && numberofDBDObnds > 0) {
										int prevelemInMonochain = Integer.parseInt(
												prevnodeInMonoChain.substring(0, prevnodeInMonoChain.length() - 1));
										MonoUnit nodebeforInMonoChain = monounitList.get(prevelemInMonochain);
										if (newnode.isFourthC() && nodebeforInMonoChain.isAlphaC()) {
											childNode DBDO = new childNode();
											DBDO.setpType(
													nodebeforInMonoChain.getId() + nodebeforInMonoChain.getType());
											DBDO.setcType(newnode.getId() + newnode.getType());
											newnode.setFourthC(false);
											nodebeforInMonoChain.setBnd1("A");
											nodebeforInMonoChain.setAlphaC(false);
											DBDO.setBondType(Constants.AO4);
											cNodeList.add(DBDO);
											/* adding updated node to monolist - start */
											monounitList.remove(prevelemInMonochain);
											monounitList.put(prevelemInMonochain, nodebeforInMonoChain);
											/* adding updated node to monolist - start */
											numberofDBDObnds--;
											isDBBOUpdated = true;
										}
									}

									// if (isDBBOUpdated || (!newnode.isFourthC() && !monoinchain.isFourthC())) //
									// This might not be needed
									{
										newnode.setBnd1("5");
										newnode.setFivthC(false);
										monoinchain.setpBnd("5");
										monoinchain.setFivthC(false);

										/* adding updated node to monolist - start */
										monounitList.remove(missingnodeid);
										monounitList.put(missingnodeid, newnode);
										monounitList.remove(elemInMonochain);
										monounitList.put(elemInMonochain, monoinchain);
										/* adding updated node to monolist - end */

										missingnode.setpType(monoinchain.getId() + monoinchain.getType());
										missingnode.setcType(newnode.getId() + newnode.getType());
										missingnode.setBondType(monoinchain.getpBnd() + newnode.getBnd1());
										cNodeList.add(missingnode);

										is55updated = true;
									}
								}

								if (!is55updated & numberof405bnds > 0) {
									/* Creating 405 bond */
									if (newnode.isFivthC() && newnode.isFourthC() && monoinchain != null
											&& monoinchain.isFourthC()) {
										newnode.setBnd1("5");
										newnode.setFivthC(false);
										monoinchain.setpBnd("4O");
										monoinchain.setFourthC(false);

										/* adding updated node to monolist - start */
										monounitList.remove(missingnodeid);
										monounitList.put(missingnodeid, newnode);
										monounitList.remove(elemInMonochain);
										monounitList.put(elemInMonochain, monoinchain);
										/* adding updated node to monolist - end */

										missingnode.setpType(monoinchain.getId() + monoinchain.getType());
										missingnode.setcType(newnode.getId() + newnode.getType());
										missingnode.setBondType(monoinchain.getpBnd() + newnode.getBnd1());
										cNodeList.add(missingnode);
										// sop("After cNodeList 1="+cNodeList);
										is4O5updated = true;
									} else if (newnode.isFourthC() && monoinchain != null && monoinchain.isFivthC()
											&& monoinchain.isFourthC()) {
										newnode.setBnd1("O4");
										newnode.setFourthC(false);
										monoinchain.setpBnd("5");
										monoinchain.setFivthC(false);

										/* adding updated node to monolist - start */
										monounitList.remove(missingnodeid);
										monounitList.put(missingnodeid, newnode);
										monounitList.remove(elemInMonochain);
										monounitList.put(elemInMonochain, monoinchain);
										/* adding updated node to monolist - end */

										missingnode.setpType(monoinchain.getId() + monoinchain.getType());
										missingnode.setcType(newnode.getId() + newnode.getType());
										missingnode.setBondType(monoinchain.getpBnd() + newnode.getBnd1());
										cNodeList.add(missingnode);
										// sop("After cNodeList 2="+cNodeList);
										is4O5updated = true;
									}
								}

								if (is4O5updated || is55updated) {
									if (is4O5updated && numberof405bnds > 0)
										numberof405bnds -= 1;
									if (is55updated && numberof55bnds > 0)
										numberof55bnds -= 1;
									break conloop;
								}
							} else {
								numberof405bnds = 0;
								numberof55bnds = 0;
								break conloop;
							}
						}
					}

					// System.exit(0);
				}

				List<List<String>> connectedgraphsAfter = (List<List<String>>) connectedChains(cNodeList)
						.get("connectedchains");
				int noofunitsAfter = (connectedgraphsAfter != null) ? connectedgraphsAfter.size() : 0;
				if (noofunitsAfter == noofunitstoadd) {
					checkfornext = false;
				}

			}

		}

		// System.out.println("\n chain ends \n\n");

		List<childNode> returncNodeList = sortChildNodeList(cNodeList, false);

		return returncNodeList;

	}

	private List<childNode> sortChildNodeList(List<childNode> cNodeList, boolean isptype) {
		List<childNode> sortedNodeList = new ArrayList<childNode>();
		List<Float> key = new ArrayList<Float>();
		List<Integer> value = new ArrayList<Integer>();
		float ct = 0.1f;
		for (childNode cn : cNodeList) {
			String nodeval = "";
			if (isptype)
				nodeval = cn.getpType().substring(0, cn.getpType().length() - 1);
			else
				nodeval = cn.getcType().substring(0, cn.getcType().length() - 1);
			Float num = Float.parseFloat(nodeval);
			if (key.contains(num)) {
				ct += 0.1;
				num = num + ct;
			}
			key.add(num);
			value.add(cNodeList.indexOf(cn));
		}

		List<Float> sortedKeys = new ArrayList<Float>(key);
		Collections.sort(sortedKeys);

		for (Float sortval : sortedKeys) {
			sortedNodeList.add(cNodeList.get(value.get(key.indexOf(sortval))));
		}

		return sortedNodeList;
	}

	/*
	 * Breath first search to identify the connected and disconnected graphs
	 */
	public Map<String, Object> connectedChains(List<childNode> cNodeList) {
		List<List<String>> connectedchains = new ArrayList<List<String>>();
		List<String> connectednodes = new ArrayList<String>();
		List<String> missingNodes = new ArrayList<String>();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		boolean firstnode = true;

		List<childNode> sortedNodeList = sortChildNodeList(cNodeList, true);
		List<String> nodeLabels = new ArrayList<String>();

		int len = 0;
		for (childNode cn : sortedNodeList) {
			len++;
			String node1 = cn.getpType();
			String node2 = cn.getcType();
			if (!nodeLabels.contains(node1))
				nodeLabels.add(node1);
			if (!nodeLabels.contains(node2))
				nodeLabels.add(node2);

			if (firstnode) {
				connectednodes.add(node1);
				connectednodes.add(node2);
				firstnode = false;
			} else if (connectednodes.contains(node1)) {
				connectednodes.add(node2);
			}
			if (!connectednodes.contains(node1)) {
				connectedchains.add(connectednodes);
				if (len <= sortedNodeList.size())
					missingNodes.add(node1);
				connectednodes = new ArrayList<String>();
				connectednodes.add(node1);
				connectednodes.add(node2);
			}
			if (len >= sortedNodeList.size()) {
				connectedchains.add(connectednodes);
			}
		}
		// System.out.println("connectedchains="+sortedNodeList);
		returnMap.put("nodelabels", nodeLabels);
		if (connectedchains.size() > 1) {
			returnMap.put("connectedchains", connectedchains);
			returnMap.put("missingnodes", missingNodes);

		}

		return returnMap;
	}
}
