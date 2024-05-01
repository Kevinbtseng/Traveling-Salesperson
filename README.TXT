PROJECT TITLE: Traveling Salesperson Problem
PURPOSE OF PROJECT: Implement two greedy heuristics in the attempt to find
an optimal solution to the Traveling Salesperson Problem
DATE: December 8, 2023
ALGORITHM DESCRIPTIONS: 
    insertNearest()                                                                                                                           
    insertNearest() uses the Nearest Neighbor heuristic to insert point P after whichever point it is closest to. Our algorithm first checks if 
    the tour is completely empty, and if it is, it simply adds the point to the tour. If anyting else, it uses a singular while loop to go through
    the tour, checking each point's distance to the point P, and if it is closer than any previously checked point, it saves the nearest point
    and shortest distance. After identifying the nearest point, it inserts the point directly after whichever point it found to be nearest. It
    then increments the tour size because we added a point, calculates distance just to be safe, and ends the method. The complexity of this 
    algorithm, using Big-O analysis, would be O(N^2) as the algorithm goes through the list twice.
    
    insertSmallest()
    insertSmallest() uses the Insert Smallest heuristic to insert point P wherever the total distance of the tour will increase the least. The 
    method begins by adressing base cases, like if the tour is empty or there is only one point. If any of those two cases, it adds the point
    to the tour and returns. If anything else, it begins by assigning a current best value as if P is the second point in the tour. It then sets 
    the distance to be a baseline of how the distance would be affected by having the point being right after the first node, then it goes onto
    the next list to check the next list. It then does the same calculation by utilizing a while loop. If it finds a distance that is less than
    best distance, it sets the best distance to the new best distance and saves the list in which the distance is the lowest. Because we didn't 
    calculate if the point should go in between rear and front, we check to see if the distance between the front, point p, and rear is less than
    our best distance. We then insert the point P where we found it to add the least amount of distance to the tour.The complexity of this 
    algorithm, using Big-O analysis, would be O(N^2) as the algorithm goes through the list twice.