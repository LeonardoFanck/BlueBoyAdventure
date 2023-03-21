package ai;

import java.util.ArrayList;

import Caracters.Entity;
import Screens.Quarto;

public class PathFinder {

	Quarto quarto;
	Node[][] node;
	ArrayList<Node> openList = new ArrayList<>();
	public ArrayList<Node> pathList = new ArrayList<>();
	Node startNode, goalNode, currentNode;
	boolean goalReached = false;
	int step = 0;
	
	public PathFinder(Quarto quarto) {
		
		this.quarto = quarto;
		instantiateNode();
	}
	
	public void instantiateNode() {
		
		node = new Node[quarto.maxWorldCollum][quarto.maxWorldRow];
		
		int col = 0;
		int row = 0;
		
		while(col < quarto.maxWorldCollum && row < quarto.maxWorldRow) {
			node[col][row] = new Node(col, row);
			col++;
			
			if(col == quarto.maxWorldCollum) {
				col = 0;
				row++;
			}
		}
	}
	
	public void resetNodes() {
		
		int col = 0;
		int row = 0;
		
		while(col < quarto.maxWorldCollum && row < quarto.maxWorldRow) {
			// RESETANDO 'OPEN', 'CHECKED' E 'SOLID STATE'
			node[col][row].open = false;
			node[col][row].checked = false;
			node[col][row].solid = false;
			col++;
			
			if(col == quarto.maxWorldCollum) {
				col = 0;
				row++;
			}
		}
		
		// RESETANDO OUTRAS CONFIGURAÇÕES
		openList.clear();
		pathList.clear();
		goalReached = false;
		step = 0;
	}
	
	public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
		
		resetNodes();
		
		// SETANDO O 'START' E 'GOAL NODE'
		startNode = node[startCol][startRow];
		currentNode = startNode;
		goalNode = node[goalCol][goalRow];
		openList.add(currentNode);
		
		int col = 0;
		int row = 0;
		
		while(col < quarto.maxWorldCollum && row < quarto.maxWorldRow) {
			// SETANDO AS NODES SOLIDAS
			// CHECK TILES
			int tileNum = quarto.tileM.mapTileNum[quarto.currentMap][col][row];
			if(quarto.tileM.tile[tileNum].colission == true) {
				node[col][row].solid = true;
			}
			// VERIFICANDO AS INTERACTIVE TILES
			for(int i = 0; i < quarto.iTile[1].length; i++) {
				if(quarto.iTile[quarto.currentMap][i] != null && quarto.iTile[quarto.currentMap][i].destructible == true) {
					int itCol = quarto.iTile[quarto.currentMap][i].mundoX/quarto.tileSize;
					int itRow = quarto.iTile[quarto.currentMap][i].mundoY/quarto.tileSize;
					node[itCol][itRow].solid = true;
				}
			}
			// SETANDO O CUSTO
			getCost(node[col][row]);
			
			col++;
			if(col == quarto.maxWorldCollum) {
				col = 0;
				row++;
			}
		}
	}
	
	public void getCost(Node node) {
		
		// CUSTO DE 'G'
		int xDistance = Math.abs(node.col - startNode.col);
		int yDistance = Math.abs(node.row - startNode.row);
		node.gCost = xDistance + yDistance;
		
		// CUSTO DE 'H'
		xDistance = Math.abs(node.col - goalNode.col);
		yDistance = Math.abs(node.row - goalNode.row);
		node.hCost = xDistance + yDistance;
		
		// CUSTO DE 'F'
		node.fCost = node.gCost + node.hCost;
	}
	
	public boolean search() {
		
		while(goalReached == false && step < 500) {
			int col = currentNode.col;
			int row = currentNode.row;
			
			// VERIFICANDO O 'CURRENT NODE'
			currentNode.checked = true;
			openList.remove(currentNode);
			
			// ABRINDO A NODE SUPERIOR
			if(row -1 >= 0) {
				openNode(node[col][row-1]);
			}
			// ABRINDO A NODE ESQUERDA
			if(col - 1 >= 0) {
				openNode(node[col-1][row]);
			}
			// ABRINDO A NODE INFERIOR
			if(row + 1 < quarto.maxWorldRow) {
				openNode(node[col][row+1]);
			}
			// ABRINDO A NODE DIREITA
			if(col + 1 < quarto.maxWorldCollum) {
				openNode(node[col+1][row]);
			}
			
			// ENCONTRANDO A MELHOR NODE
			int bestNodeIndex = 0;
			int bestNodeFCost = 999;
			
			for(int i = 0; i < openList.size(); i++) {
				// VERIFICANDO SE ESSA NODE TEM O MELHOR CUSTO DE 'F'
				if(openList.get(i).fCost < bestNodeFCost) {
					bestNodeIndex = i;
					bestNodeFCost = openList.get(i).fCost;
				}
				// SE O CUSTO DE 'F' FOR IGUAL, VERIFICAR O CUSTO DE 'G'
				else if(openList.get(i).fCost == bestNodeFCost) {
					if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
						bestNodeIndex = i;
					}
				}
			}
			
			// SE NÃO TIVER NENHUM NODE NO 'OPENLIST', FINALIZA O LOOP
			if(openList.size() == 0) {
				break;
			}
			
			// APÓS O LOOP, OPENLIST[BEST NODE INDEX] É O PRÓXIMO PASSO (= currentNode)
			currentNode = openList.get(bestNodeIndex);
			
			if(currentNode == goalNode) {
				goalReached = true;
				trackThePath(); 
			}
			step++;
		}
		
		return goalReached;
	}
	
	public void openNode(Node node) {
		
		if(node.open == false && node.checked == false && node.solid == false) {
			node.open = true;
			node.parent = currentNode;
			openList.add(node);
		}
	}
	
	public void trackThePath() {
		
		Node current = goalNode;
		
		while(current != startNode) {
			pathList.add(0, current);
			current = current.parent;
		}
	}
	
}
