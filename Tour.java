import java.util.*;
import java.awt.Graphics;

/**
 * This class is a specialized Linked List of Points that represents a
 * Tour of locations attempting to solve the Traveling Salesperson Problem
 * 
 * @version 1
 */

public class Tour implements TourInterface
{
    // instance variables
    private ListNode front;
    private ListNode rear;
    private int size;

    // constructor
    public Tour()
    { //sets all instance variables to be empty, creates and creates an empy list 
        size = 0;
        front = null;
        rear = null;
    }

    //return the number of points (nodes) in the list   
    public int size()
    { //returns the size
        return size;
    }

    // append Point p to the end of the list
    public void add(Point p)
    {
        ListNode list = new ListNode(p); //creates a new list with the point p as its only element
        if (front == null) //checks to see if front is null to see which would check to see if there is a pre-existing list
        {
            front = list; //sets the front to list
            rear = list; //also sets the rear to list as front and rear would be the same and only point in the list
            size++; //increments size
        }
        else //if there's already a list...
        { 
            rear.setNext(list); //sets the next node, the node after rear, as list
            rear = list; //sets the variable of rear to equal list;
            size++; //increments size
        } 
    }

    // print every node in the list 
    public void print()
    {   
        ListNode list = front; //sets the variable list to equal front
        while(list!=null) //keeps looping until the list is empty/ cannot go to a next node
        {
            System.out.println(list.data); //prints the data of the node on each line
            list = list.next; //sets the node to equal the next node, which stops the while loop from infinitely iterating
        }
    }

    // draw the tour using the given graphics context

    public void draw(Graphics g)
    {
        ListNode list = front; //sets the node to first
        int startx = 0; //instantiates the variable and sets it to 0 to allow for the drawline at the end of the method to run
        int starty = 0; //instantiates the variable and sets it to 0 to allow for the drawline at the end of the method to run
        int endx = 0; //instantiates the variable and sets it to 0 to allow for the drawline at the end of the method to run
        int endy = 0; //instantiates the variable and sets it to 0 to allow for the drawline at the end of the method to run
        if(list!=null) //if the list is not null, the start x and y become the values of the first point
        {
            startx = (int)front.data.getX();
            starty = (int)front.data.getY();
        }
        while(list!=null) //keeps looping until the list is empty/ cannot go to a next node
        {
            int x1 = (int)list.data.getX(); //sets to the x value of the first point
            int y1 = (int)list.data.getY(); //sets to the y value of the first point
            g.fillOval(x1-2, y1-2, 4, 4); //creats a circle around the point with radius 2
            int x2 = x1; // saves x1 as x2
            int y2 = y1; //saves y1 as y2
            if(list.next!= null) // so no null pointers
            {
                x1 = (int)list.next.data.getX();
                y1 = (int)list.next.data.getY(); //sets x1 and y1 to the point after x2/y2
                g.drawLine(x1,y1,x2,y2); // draws a line between them
            }
            if(list.next == null)
            {
                endx = (int)list.data.getX();
                endy = (int)list.data.getY(); // if the end of the list, set the end variables to the current point
            }
            list = list.next; // move to next node
        }
        g.drawLine(endx, endy, startx, starty); // draws line from last point to first to complete the tour
    }
    //calculate the distance of the Tour, but summing up the distance between adjacent points
    //NOTE p.distance(p2) gives the distance where p and p2 are of type Point
    public double distance()
    {
        if(front == null) // if no nodes in the tour
        {
            return -1;
        }
        double count = 0;  //new count var
        ListNode list = front; // new ListNode to go through tour
        while(list!=rear) // doing distance from front to the rear but stopping when list = rear
        {
            count += list.data.distance(list.next.data); // add the dist between current point and next point to count
            list = list.next; // next node
        }
        count+= front.data.distance(rear.data); // add dist between front to end to complete the tour
        return count; // return total dist
    }

    // add Point p to the list according to the NearestNeighbor heuristic
    public void insertNearest(Point p) 
    {
        if (front == null) // if no nodes in tour, just add p and return
        {
            add(p);
            distance();
            return;
        } 
        else 
        {
            ListNode PP = new ListNode(p); // making  point p a listnode
            ListNode list = front; // list var to loop through tour
            double bestD = p.distance(front.data); // starting value for best distance from point P to front
            ListNode nearest = front; // starting value for the closest point

            while (list.next !=null) // so no null poijnters
            {
                list = list.next; // we already checked front, so just start on the node after front
                if (list.data.distance(p) < bestD)  // if the current point is closer than our previous best
                {
                    bestD = list.data.distance(p); //best distance is dist from current point to point p
                    nearest = list; // nearest point is set to List
                }
            }
            if (nearest.next == null) // if the nearest point is the rear just put p at the end and return
            {
                add(p); // add p at the end
                distance(); // call distance to  be safe
                return; // return   
            }
            ListNode temp = nearest.next; //saves the point(s) after nearest as temp
            nearest.next = PP; // sets the point after nearest to point p
            PP.next = temp; // adds the rest of the points back after point p
            distance(); // call distance to be safe
            size++; // increment size cuz we added a point
        }
    }

    // add Point p to the list according to the InsertSmallest heuristic
    public void insertSmallest(Point p) 
    {
        if (front == null || front == rear) 
        {
            add(p);
            return; // this if statement checks if the list is empty or theres only one node either way p is getting added and the method ends here
        }
        else //if anything else, we must find where p should be in the list of points
        {
            ListNode bestList = front; //instantializing bestList tracker
            ListNode list = front; //instantializing the list we will use to move through the tour
            ListNode PP = new ListNode(p); // turning point p to a listNode
            Double bestDist = (list.data.distance(p) + p.distance(list.next.data)) - list.data.distance(list.next.data);
            //above line sets a bestDist var to how much distance is increased when PP is the second value in the Tour, just a baseline of where to start
            list = list.next; // moving on to the next point in list before entering the while loop
            while (list.next != null) //i want the loop to stop before rear, you will see why in a second
            {
                double currDist = (list.data.distance(p) + p.distance(list.next.data)) - list.data.distance(list.next.data);
                //above line sets how much distance is added if PP is placed in List.next for every point in the tour
                if (currDist < bestDist) //if the distance added when PP is here is less than our previously found bestDistance
                {
                    bestList = list; // optimal place for PP to go is after List, so List is stored in bestList
                    bestDist = currDist; //current optimal distance is stored
                }
                list = list.next;  // Move to the next node
            }
            if(((rear.data.distance(p) + p.distance(front.data)) - rear.data.distance(front.data)) < bestDist)
            //because we didnt calculate if PP should go in between rear and front, this line checks if that possiblity is more optimal than 
            //the optimal position we found in the while loop
            {
                add(p); // places PP in between rear and front if it is more optimal
                return; // end method here, we found optimal position
            }
            PP.next = bestList.next;
            bestList.next = PP;
            //those two lines set list.next (the position we found PP is most optimal to be in) to PP, and set PP.next to the value that was
            //originally after list
            size++; //increment size cuz we just added a point;
        }
    }

    // This is a private inner class, which is a separate class within a class.
    private class ListNode
    {
        private Point data;
        private ListNode next;
        public ListNode(Point p, ListNode n)
        {
            this.data = p;
            this.next = n;
        }

        public ListNode(Point p)
        {
            this(p, null);
        }

        public void setNext(ListNode n)
        {
            next = n; //sets next node to n 
        }
    }

}