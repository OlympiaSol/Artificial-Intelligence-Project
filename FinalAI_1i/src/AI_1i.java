import java.util.Scanner;
import java.util.Random;

//Aikaterini Tsitsimikli 4821
//Soldatou Christina-Olympia 4001
//Pantazis Dimosthenis 4136


public class AI_1i
{   public static final int N = 6;
    public static final double prob = 0.1;
    public static int[][] maze = new int[N][N];// Maze-Board NxN with 0 for empty place and 1 for full place
    public static int[][] maze2 = new int[N][N];// Maze2-Board NxN with  1or 2 or 3 or 4 for empty place
    public static structZ start = null;
    public static structZ end = null;

    public static int Sx;//coordinates
    public static int Sy;//coordinates
    public static int G1x;//coordinates
    public static int G1y;//coordinates
    public static int G2x;//coordinates
    public static int G2y;//coordinates

    public static void initializeG1(){

        Scanner input = new Scanner(System.in);
        System.out.println("give coordinates of G1:");
        G1x = input.nextInt();
        input.nextLine();
        G1y = input.nextInt();
        input.nextLine();
    }
    public static void initializeG2(){

        Scanner input = new Scanner(System.in);
        System.out.println("give coordinates of G2:");
        G2x = input.nextInt();
        input.nextLine();
        G2y = input.nextInt();
        input.nextLine();
    }
    public static void initialize()
    {
        structZ p = new structZ();
        int i;
        int j;
        float f;

        Scanner input = new Scanner(System.in);

        start = p;
        end = p;

        System.out.println("give coordinates of S:");
        Sx = input.nextInt();
        input.nextLine();
        Sy = input.nextInt();
        input.nextLine();

        initializeG1();
        initializeG2();

        Random rand = new Random(System.currentTimeMillis());

        for (i = 0;i < N;i++)
        {
            for (j = 0;j < N;j++)
            {
                maze[i][j] = 0;
                if ((i != Sx || j != Sy) && (i != G1x || j != G1y) && (i != G2x || j != G2y))
                {
                    f = rand.nextFloat();
                    if (f < prob)
                    {
                        maze[i][j] = 1;
                    }
                }
            }
        }

        for (i = 0;i < N;i++)
        {
            for (j = 0;j < N;j++)
            {
                if (maze[i][j] == 0)
                {
                    maze2[i][j] = rand.nextInt(4) + 1;
                }
            }
        }

        p.next = null;
        p.up = null;
        p.cost = 0F;
        p.VRT = Sx;
        p.HRZ = Sy;
    }

    public static void print(structZ p)
    {
        int i;
        int j;

        for (i = 0;i < N;i++)
        {
            for (j = 0;j < N;j++)
            {
                if (i == Sx && j == Sy)
                {
                    System.out.printf(" S/%d ",maze2[i][j]);
                }
                else if (i == G1x && j == G1y)
                {
                    System.out.printf(" A/%d ",maze2[i][j]);
                }
                else if (i == G2x && j == G2y)
                {
                    System.out.printf(" B/%d ",maze2[i][j]);
                }
                else if (maze[i][j] == 1)
                {
                    System.out.print("  i  ");
                }
                else
                {
                    System.out.printf(" -/%d ", maze2[i][j]);
                }
            }
            System.out.println();
        }
    }

    public static structZ export()
    {
        // min[g(n)]
        float minimum;
        float m;
        structZ d;
        structZ var = start;
        structZ diffusion = start;
        structZ back = null;

        minimum = diffusion.cost;

        while (diffusion != null)	//loop checks with next states Gs
        {
            d = diffusion;
            if (diffusion.next != null)
            {
                diffusion = diffusion.next;
                m = diffusion.cost;
                if (m < minimum)
                {
                    minimum = m;
                    var = diffusion;	//pointer for state with minimun g(n)
                    back = d;
                }
            }
            else
            {
                break;
            }
        }
        if (back == null)
        {
            start = var.next;
        }
        else
        {
            back.next = var.next;
        }

        if (var.next == null)
        {
            end = back;
        }
        var.next = null;
        return (var);
    }
    public static int finals(structZ p)
    {

        if ( Math.abs(p.VRT - G1x) + Math.abs(p.HRZ - G1y)== 0)	//if p is equal with pos of G1
        {
            return (1);
        }
        if (Math.abs(p.VRT - G2x) + Math.abs(p.HRZ - G2y) == 0)		//if p is equal with pos of G2
        {
            return (1);
        }

        return (0);
    }
    public static void root(structZ p)
    {
        //method for best root and total cost
        structZ teliki = p;
        structZ t = p;

        System.out.printf("total cost = %f\n",teliki.cost);

        while (p.up != null)	//goes to root
        {
            t = p;
            p = p.up;
            p.next = t;
        }

        System.out.printf("optimal root: (%d,%d) -> ",p.VRT,p.HRZ);
        while (t != teliki)
        {
            System.out.printf("(%d,%d) -> ",t.VRT,t.HRZ);
            t = t.next;
        }
        System.out.printf("(%d,%d)\n",t.VRT,t.HRZ);
    }


    public static void define_new(structZ p)
    {
        //create the neighbours
        structZ t;
        //// katakorifa (down)
        if (p.VRT + 1 >= 0 && p.HRZ + 0 >= 0 && p.VRT + 1 <N && p.HRZ + 0 < N&&maze[p.VRT + 1][p.HRZ + 0] == 0)
        {
            t = new structZ();
            t.VRT = p.VRT + 1;
            t.HRZ = p.HRZ + 0;
            t.cost = p.cost + 1F + Math.abs(maze2[p.VRT][p.HRZ] - maze2[t.VRT][t.HRZ]);
            t.up = p;
            t.next = null;
            if (start == null)
            {
                start = t;
                end = start;
            }
            else
            {
                end.next = t;
                end = t;
            }
        }
        // katakorifa (up)
        if (p.VRT + -1 >= 0 && p.HRZ + 0 >= 0 && p.VRT + -1 <N && p.HRZ + 0 < N&&maze[p.VRT + -1][p.HRZ + 0] == 0)
        {
            t = new structZ();
            t.VRT = p.VRT + -1;
            t.HRZ = p.HRZ + 0;
            t.cost = p.cost + 1F + Math.abs(maze2[p.VRT][p.HRZ] - maze2[t.VRT][t.HRZ]);
            t.up = p;
            t.next = null;
            if (start == null)
            {
                start = t;
                end = start;
            }
            else
            {
                end.next = t;
                end = t;
            }
        }
        // orizontia (right)
        if (p.VRT + 0 >= 0 && p.HRZ + 1 >= 0 && p.VRT + 0 <N && p.HRZ + 1 < N&&maze[p.VRT + 0][p.HRZ + 1] == 0)
        {
            t = new structZ();
            t.VRT = p.VRT + 0;
            t.HRZ = p.HRZ + 1;
            t.cost = p.cost + 1F + Math.abs(maze2[p.VRT][p.HRZ] - maze2[t.VRT][t.HRZ]);
            t.up = p;
            t.next = null;
            if (start == null)
            {
                start = t;
                end = start;
            }
            else
            {
                end.next = t;
                end = t;
            }
        }
        // orizontia (left)
        if (p.VRT + 0 >= 0 && p.HRZ + -1 >= 0 && p.VRT + 0 <N && p.HRZ + -1 < N&&maze[p.VRT + 0][p.HRZ + -1] == 0)
        {
            t = new structZ();
            t.VRT = p.VRT + 0;
            t.HRZ = p.HRZ + -1;
            t.cost = p.cost + 1F + Math.abs(maze2[p.VRT][p.HRZ] - maze2[t.VRT][t.HRZ]);
            t.up = p;
            t.next = null;
            if (start == null)
            {
                start = t;
                end = start;
            }
            else
            {
                end.next = t;
                end = t;
            }
        }
        // diagwnia (up left)
        if (p.VRT + -1 >= 0 && p.HRZ + -1 >= 0 && p.VRT + -1 <N && p.HRZ + -1 < N&&maze[p.VRT + -1][p.HRZ + -1] == 0)
        {
            t = new structZ();
            t.VRT = p.VRT + -1;
            t.HRZ = p.HRZ + -1;
            t.cost = p.cost + 0.5F + Math.abs(maze2[p.VRT][p.HRZ] - maze2[t.VRT][t.HRZ]);
            t.up = p;
            t.next = null;
            if (start == null)
            {
                start = t;
                end = start;
            }
            else
            {
                end.next = t;
                end = t;
            }
        }
        // diagwnia (down right)
        if (p.VRT + 1 >= 0 && p.HRZ + 1 >= 0 && p.VRT + 1 <N && p.HRZ + 1 < N&&maze[p.VRT + 1][p.HRZ + 1] == 0)
        {
            t = new structZ();
            t.VRT = p.VRT + 1;
            t.HRZ = p.HRZ + 1;
            t.cost = p.cost + 0.5F + Math.abs(maze2[p.VRT][p.HRZ] - maze2[t.VRT][t.HRZ]);
            t.up = p;
            t.next = null;
            if (start == null)
            {
                start = t;
                end = start;
            }
            else
            {
                end.next = t;
                end = t;
            }
        }
        // diagwnia (up right)
        if (p.VRT + -1 >= 0 && p.HRZ + 1 >= 0 && p.VRT + -1 <N && p.HRZ + 1 < N&&maze[p.VRT + -1][p.HRZ + 1] == 0)
        {
            t = new structZ();
            t.VRT = p.VRT + -1;
            t.HRZ = p.HRZ + 1;
            t.cost = p.cost + 0.5F + Math.abs(maze2[p.VRT][p.HRZ] - maze2[t.VRT][t.HRZ]);
            t.up = p;
            t.next = null;
            if (start == null)
            {
                start = t;
                end = start;
            }
            else
            {
                end.next = t;
                end = t;
            }
        }
        // diagwnia (down left)
        if (p.VRT + 1 >= 0 && p.HRZ + -1 >= 0 && p.VRT + 1 <N && p.HRZ + -1 < N&&maze[p.VRT + 1][p.HRZ + -1] == 0)
        {
            t = new structZ();
            t.VRT = p.VRT + 1;
            t.HRZ = p.HRZ + -1;
            t.cost = p.cost + 0.5F + Math.abs(maze2[p.VRT][p.HRZ] - maze2[t.VRT][t.HRZ]);
            t.up = p;
            t.next = null;
            if (start == null)
            {
                start = t;
                end = start;
            }
            else
            {
                end.next = t;
                end = t;
            }
        }

    }
    public static void printmain(int z){
        int m;
        structZ p;

        initialize();
        print(start);

        while(true){
            if (start == null)
            {
                System.out.println("UNSOLVED\n\n");
                break;
            }
            p = export(); //find state p with min
            if (finals(p) == 1)	//if final state p ->solution
            {
                root(p);
                break;
            }
            else
            {
                z++;
                define_new(p);
            }
        } ;
    }

    public static void main(String[] args) {
        boolean stop = false;
        while (!stop) {
            int external = 0;
            printmain(external);
            System.out.printf("externals: %d\n\n\n", external);


            Scanner input = new Scanner(System.in);

            System.out.println("Would you like to continue? (yes or no)");
            String s = input.nextLine();
            if (s.equals("no")) {
                stop = true;

            }
        }
    }
}