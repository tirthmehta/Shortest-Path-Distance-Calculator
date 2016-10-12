/**
 * Created by Tirthmehta on 9/14/16.
 */
import java.io.*;
import java.util.*;

class Node
{
    String location;
    Node(String locs)
    {
        location=locs;
    }
    ArrayList<Edge> arr =new ArrayList<Edge>();
    int distancetravelled;
    int timestamp;
    public void setTimestamp(int timestamp)
    {
        this.timestamp=timestamp;
    }
}

class Edge
{
    String dest;
    String source;
    int weight;
    int sundayvalue1,sundayvalue2;
    Edge(String sourc,String destination,int value)
    {
        source=sourc;
        dest=destination;
        weight=value;
    }
    public void setSundayvalue(int sun1,int sun2)
    {
        sundayvalue1=sun1;
        sundayvalue2=sun2;
    }
}

class CreateGraph
{
    int length;
    Node rootnode;
    HashMap<String,Node> hmap=new HashMap<String,Node>();
    public void setRootNode(Node n)
    {
        rootnode=n;
    }
    public void setLength(int len)
    {
        length=len;
    }
    public Node getRootNode()
    {
        return rootnode;
    }
    public void addnode(String loc,String start)
    {
        Node n=new Node(loc);
        hmap.put(loc,n);
        if(n.location.equals(start)){
            setRootNode(n);}
    }
    public void addedge(String sourc,Edge e)
    {
        hmap.get(sourc).arr.add(e);
    }


    public void bfs(String start,String end) throws IOException
    {
        Map<String, String> hmapbfs = new HashMap<String, String>();
        Map<String,Boolean> visited=new HashMap<String, Boolean>();
        hmapbfs.put(rootnode.location,null);
        Queue q=new LinkedList();
        q.add(this.rootnode);
        visited.put(rootnode.location,true);
        String temp=rootnode.location;
        while(!q.isEmpty()) {
            Node n = (Node) q.remove();
            if(n.location.equals(end))
            {
                break;
            }
            temp = n.location;
            Node children = null;
            for (int i = 0; i < n.arr.size(); i++) {
                Edge e1 = n.arr.get(i);
                Node iterate = hmap.get(e1.dest);
                if (visited.get(iterate.location) == null) {
                    hmapbfs.put(iterate.location, n.location);
                    visited.put(iterate.location, true);
                    q.add(iterate);
                }

            }
        }
        printpathbfs(hmapbfs,end);
    }



    public void dfs(String start,String end) throws IOException
    {
        Map<String, String> hmapdfs = new HashMap<String, String>();
        Map<String,Boolean> visited=new HashMap<String, Boolean>();
        hmapdfs.put(rootnode.location,null);
        Stack s=new Stack();
        Stack aux=new Stack();
        s.push(this.rootnode);
        visited.put(rootnode.location,true);
        String temp=rootnode.location;


        while(!s.isEmpty()) {
            Node n = (Node)s.peek();
            if(n==rootnode)
            {
                n=(Node)s.pop();
            }

            while(!s.isEmpty())
            {
                aux.push(s.pop());
            }
            if((!aux.isEmpty())&&(aux.peek()!=rootnode))
            {
                n=(Node)aux.pop();
            }
            if(n.location.equals(end))
            {
                break;
            }

            for (int i = 0; i < n.arr.size(); i++) {
                Edge e1 = n.arr.get(i);
                Node iterate = hmap.get(e1.dest);

                if (visited.get(iterate.location) == null) {
                    hmapdfs.put(iterate.location, n.location);
                    visited.put(iterate.location, true);
                    s.push(iterate);
                }

            }
            while(!aux.isEmpty())
            {
                s.push(aux.pop());
            }
        }
        printpathdfs(hmapdfs,end);
    }



    public void ucs(String start,String end) throws IOException
    {
        int timer=0;
        Map<String, String> hmapucs = new HashMap<String, String>();
        Map<String, Integer> hmapucs2 = new HashMap<String, Integer>();
        Map<String,Boolean> visited=new HashMap<String, Boolean>();
        hmapucs.put(rootnode.location,null);
        hmapucs2.put(rootnode.location,0);
        rootnode.distancetravelled=0;

        Comparator<Node> comparator = new StringLengthComparator();
        PriorityQueue<Node> queue =
                new PriorityQueue<Node>(length, comparator);
        rootnode.setTimestamp(timer);
        timer++;
        queue.add(rootnode);

        visited.put(rootnode.location,true);
        String temp=rootnode.location;

        while(!queue.isEmpty()) {
            Node n = (Node) queue.poll();
            temp = n.location;
            if(n.location.equals(end))
            {
                break;
            }
            Node children = null;
            for (int i = 0; i < n.arr.size(); i++) {
                Edge e1 = n.arr.get(i);
                Node iterate = hmap.get(e1.dest);
                if (visited.get(iterate.location) == null) {
                    iterate.distancetravelled=e1.weight+n.distancetravelled;
                    hmapucs.put(iterate.location, n.location);
                    iterate.setTimestamp(timer);
                    timer++;
                    visited.put(iterate.location, true);
                    queue.add(iterate);
                    hmapucs2.put(iterate.location,iterate.distancetravelled);
                }
                else if(visited.get(iterate.location)==true)
                {
                    if(iterate.distancetravelled>n.distancetravelled+e1.weight)
                    {
                        iterate.distancetravelled=e1.weight+n.distancetravelled;
                        hmapucs.remove(iterate.location);
                        hmapucs.remove(iterate.location);
                        hmapucs.put(iterate.location, n.location);
                        iterate.setTimestamp(timer);
                        timer++;
                        visited.put(iterate.location, true);
                        queue.add(iterate);
                        hmapucs2.put(iterate.location,iterate.distancetravelled);
                    }
                }

            }
        }

        printpathucs(hmapucs,hmapucs2,end);
    }



    public void astar(String start,String end) throws IOException
    {

        int timer=0;
        Map<String, String> hmapastar = new HashMap<String, String>();
        Map<String, Integer> hmapastar2 = new HashMap<String, Integer>();
        Map<String, Integer> hmapastar3 = new HashMap<String, Integer>();
        Map<String,Boolean> visited=new HashMap<String, Boolean>();
        hmapastar.put(rootnode.location,null);
        hmapastar2.put(rootnode.location,0);
        hmapastar3.put(rootnode.location,0);
        rootnode.distancetravelled=0;

        Comparator<Node> comparator = new StringLengthComparator();
        PriorityQueue<Node> queue =
                new PriorityQueue<Node>(length, comparator);
        rootnode.setTimestamp(timer);
        timer++;
        queue.add(rootnode);

        visited.put(rootnode.location,true);
        String temp=rootnode.location;

        while(!queue.isEmpty()) {
            Node n = (Node) queue.poll();
            temp = n.location;
            Node children = null;
            if(n.location.equals(end))
            {
                break;
            }
            for (int i = 0; i < n.arr.size(); i++) {
                Edge e1 = n.arr.get(i);
                Node iterate = hmap.get(e1.dest);
                if (visited.get(iterate.location) == null) {
                    iterate.distancetravelled=e1.weight+n.distancetravelled+e1.sundayvalue2-e1.sundayvalue1;
                    hmapastar.put(iterate.location, n.location);
                    iterate.setTimestamp(timer);
                    timer++;
                    visited.put(iterate.location, true);
                    queue.add(iterate);
                    hmapastar2.put(iterate.location,iterate.distancetravelled);
                    hmapastar3.put(iterate.location,iterate.distancetravelled-e1.sundayvalue2);
                }
                else if(visited.get(iterate.location)==true)
                {
                    if(iterate.distancetravelled>n.distancetravelled+e1.weight+e1.sundayvalue2-e1.sundayvalue1)
                    {
                        iterate.distancetravelled=n.distancetravelled+e1.weight+e1.sundayvalue2-e1.sundayvalue1;
                        hmapastar.remove(iterate.location);
                        hmapastar2.remove(iterate.location);
                        hmapastar3.remove(iterate.location);
                        hmapastar.put(iterate.location, n.location);
                        iterate.setTimestamp(timer);
                        timer++;
                        visited.put(iterate.location, true);
                        queue.add(iterate);
                        hmapastar2.put(iterate.location,iterate.distancetravelled);
                        hmapastar3.put(iterate.location,iterate.distancetravelled-e1.sundayvalue2);
                    }
                }

            }
        }
        printpathastar(hmapastar,hmapastar3,end);
    }





    private void printpathbfs(Map<String,String> hmap,String end) throws IOException
    {
        int value=0;
        ArrayList printing=new ArrayList();
        printing.add(end);

        String a= hmap.get(end);
        printing.add(a);
        while(a!=null)
        {
            a=hmap.get(a);
            if(a!=null)
                printing.add(a);
        }
        writerbfs(printing,value);
    }
    public void writerbfs(ArrayList printing,int value) throws IOException
    {
        FileWriter fw = new FileWriter("output.txt");
        PrintWriter pw = new PrintWriter(fw);
        for(int i=printing.size()-1;i>=0;i--) {

            pw.println(printing.get(i)+" "+value);
            value++;
        }
        fw.close();
    }


    private void printpathdfs(Map<String,String> hmap,String end) throws IOException
    {
        int value=0;
        ArrayList printing=new ArrayList();
        printing.add(end);

        String a= hmap.get(end);
        printing.add(a);
        while(a!=null)
        {
            a=hmap.get(a);
            if(a!=null)
                printing.add(a);
        }
        writerdfs(printing,value);
    }
    public void writerdfs(ArrayList printing,int value) throws IOException
    {
        FileWriter fw = new FileWriter("output.txt");
        PrintWriter pw = new PrintWriter(fw);
        for(int i=printing.size()-1;i>=0;i--) {

            pw.println(printing.get(i)+" "+value);
            value++;
        }
        fw.close();
    }



    private void printpathucs(Map<String,String> hmap,Map<String,Integer> hmap2,String end) throws IOException
    {
        int value=0;
        ArrayList printing=new ArrayList();
        int values[]=new int[hmap.size()];
        printing.add(end);
        values[value]=hmap2.get(end);
        value++;
        String a= hmap.get(end);
        values[value]=hmap2.get(a);
        value++;
        printing.add(a);
        while(a!=null)
        {
            a=hmap.get(a);
            if(a!=null) {
                printing.add(a);
                values[value]=hmap2.get(a);
                value++;
            }
        }
        writerucs(printing,values);
    }

    public void writerucs(ArrayList printing,int values[]) throws IOException
    {
        FileWriter fw = new FileWriter("output.txt");
        PrintWriter pw = new PrintWriter(fw);
        int temp=0;
        for(int i=printing.size()-1;i>=0;i--) {
            pw.println(printing.get(i)+" "+values[i]);
        }
        fw.close();
    }



    private void printpathastar(Map<String,String> hmap,Map<String,Integer> hmap2,String end) throws IOException
    {
        int value=0;
        ArrayList printing=new ArrayList();
        int values[]=new int[hmap.size()];
        printing.add(end);
        values[value]=hmap2.get(end);
        value++;
        String a= hmap.get(end);
        values[value]=hmap2.get(a);
        value++;
        printing.add(a);
        while(a!=null)
        {
            a=hmap.get(a);
            if(a!=null) {
                printing.add(a);
                values[value]=hmap2.get(a);
                value++;
            }
        }
        writerastar(printing,values);
    }

    public void writerastar(ArrayList printing,int values[]) throws IOException
    {
        FileWriter fw = new FileWriter("output.txt");
        PrintWriter pw = new PrintWriter(fw);
        int temp=0;
        for(int i=printing.size()-1;i>=0;i--) {
            pw.println(printing.get(i)+" "+values[i]);
        }
        fw.close();
    }


    public void writerstartgoal(String starter) throws IOException
    {
        FileWriter fw = new FileWriter("output.txt");
        PrintWriter pw = new PrintWriter(fw);
        pw.println(starter+"  0");
        fw.close();
    }
}

public class homework {
    public static void main(String[] args) throws IOException {

        String list[]=reader();


        String algo=list[0];
        String startstate=list[1];
        String goalstate=list[2];
        int livetrafficlines=Integer.parseInt(list[3]);
        String livetrafficlist[]=new String[livetrafficlines];
        int m=0;
        for(int i=4;i<livetrafficlines+4;i++){
            livetrafficlist[m]=list[i];
            m++;}
        int sundaytrafficlines=Integer.parseInt(list[livetrafficlines+4]);
        String sundaytrafficlist[]=new String[sundaytrafficlines];
        int n=0;
        for(int i=livetrafficlines+5;i<livetrafficlines+sundaytrafficlines+5;i++)
        {
            sundaytrafficlist[n]=list[i];
            n++;
        }
        String arr1[]=new String[3];
        String arr2[]=new String[2];

        CreateGraph cg=new CreateGraph();


        Map<String,Integer> hmapsunday=new HashMap<String,Integer>();
        for(int i=0;i<sundaytrafficlines;i++)
        {
            arr2=sundaytrafficlist[i].split(" ");
            hmapsunday.put(arr2[0],Integer.parseInt(arr2[1]));
        }
        hmapsunday.remove(startstate);
        hmapsunday.put(startstate,0);
        for(int i=0;i<livetrafficlines;i++)
        {
            arr1=livetrafficlist[i].split(" ");
            if(!(cg.hmap.containsKey(arr1[0]))){
                cg.addnode(arr1[0],startstate);}
            if(!(cg.hmap.containsKey(arr1[1])))
            {cg.addnode(arr1[1],startstate);}
            Edge e=new Edge(arr1[0],arr1[1],Integer.parseInt(arr1[2]));
            e.setSundayvalue(hmapsunday.get(arr1[0]),hmapsunday.get(arr1[1]));
            cg.addedge(arr1[0],e);
            arr1=null;
        }
        cg.setLength(cg.hmap.size());

        int afact=0;
        if(startstate.equals(goalstate))
        {
            cg.writerstartgoal(startstate);
            afact=1;
        }
        if(afact!=1) {
            if (algo.equals("BFS")) {
                cg.bfs(startstate, goalstate);
            } else if (algo.equals("DFS")) {
                cg.dfs(startstate, goalstate);
            } else if (algo.equals("UCS")) {
                cg.ucs(startstate, goalstate);
            } else if (algo.equals("A*")) {
                cg.astar(startstate, goalstate);
            }
        }
    }
    public static String[] reader() throws IOException
    {

        FileReader fr=new FileReader("input.txt");
        BufferedReader br=new BufferedReader(fr);
        String c;
        int lines=0;
        while((c=br.readLine())!=null)
            lines++;
        String arr[]=new String[lines];
        int i=0;
        br.close();
        FileReader fr2=new FileReader("input.txt");
        BufferedReader br2=new BufferedReader(fr2);
        while((c=br2.readLine())!=null) {
            arr[i]=c;
            i++;
        }
        br.close();
        return arr;
    }
}

class StringLengthComparator implements Comparator<Node>
{
    @Override
    public int compare(Node o1, Node o2) {

        if(o1.distancetravelled!=o2.distancetravelled){
            return o1.distancetravelled-o2.distancetravelled;}
        else{
            return o1.timestamp-o2.timestamp;
        }
    }
}


