package com.astar;

import java.awt.*;
import java.util.ArrayList;

//By: Brandon Beckwith
class AStar {

    // NOTE: This is the only class you need to edit.
    //Feel free to add whatever methods you need here!

    /**
     * Runs A star on the given board
     * @param board The board to run A Star on
     * @param start The starting Point
     * @param end The ending Point
     * @return The spaces in order from the start Point to the end Point
     */

     /**
    * Calculates the Manhattan distance heuristic between two points.
    * @param a The first point.
    * @param b The second point.
    * @return The Manhattan distance between a and b.
     */

    private static int manhattanDistance(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    private static ArrayList<Space> reconstructPath(Space endSpace){
        ArrayList<Space> path = new ArrayList<>();
        Space current = endSpace;
        while (current != null) {
            path.add(0, current);
            current = current.getPrevious();
        }
        return path;
    }

    public static ArrayList<Space> findPath(Board board, Point start, Point end) {
        ArrayList<Space> openList = new ArrayList<>();
        ArrayList<Space> closedList = new ArrayList<>();

        Space startSpace = board.getSpace(start.x, start.y);
        Space endSpace = board.getSpace(end.x, end.y);
        startSpace.setG(0);
        startSpace.setH(manhattanDistance(start, end));

        openList.add(startSpace);

        while(!openList.isEmpty()) {
            Space current = openList.get(0);
            for (Space s : openList) {
                if(s.getF() < current.getF()){
                    current = s;
                }
            }

            openList.remove(current);
            closedList.add(current);

            if (current.equals(endSpace)) {
                return reconstructPath(current);
            }

            for (Space neighbor : board.getNeighbors(current)) {
                if (closedList.contains(neighbor) || neighbor.getType() == SpaceType.BLOCK) {
                    continue;
                }
                int tentativeG = current.getG() + 1;
                if (!openList.contains(neighbor) || tentativeG < neighbor .getG()){
                    neighbor.setPrevious(current);
                    neighbor.setG(tentativeG);
                    neighbor.setH(manhattanDistance(neighbor.getPoint(), end));
                    
                    if (!openList.contains(neighbor)) {
                        openList.add(neighbor);
                    }
                }
            }
        }
        return new ArrayList<>();
    }
}
