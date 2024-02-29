import java.util.Scanner;
import java.util.Random;

class DefineConstants
{
  public static final int N = 7;
  public static final double prob = 0.3;
}

class K
{
  public K next;
  public K up;
  public float kostos;
  public int ver;
  public int hor;
}

public class Ask1_i
{
	public static int[][] LAV = new int[DefineConstants.N][DefineConstants.N];
	public static int[][] val = new int[DefineConstants.N][DefineConstants.N];
	public static K search_top = null;
	public static K search_bottom = null;

	public static int S_ver;
	public static int S_hor;
	public static int G1_ver;
	public static int G1_hor;
	public static int G2_ver;
	public static int G2_hor;

	public static void initialize()
	{
		K p = new K();
	  int x;
	  int y;
	  float f;

	  Scanner scan = new Scanner(System.in);

	  search_top = p;
	  search_bottom = p;

	  System.out.printf("Doste tis sintetagmenes (x,y) tou S:\n");
	  S_ver = scan.nextInt();
      scan.nextLine();
	  S_hor = scan.nextInt();
      scan.nextLine();
	  System.out.printf("Doste tis sintetagmenes (x,y) tou G1:\n");
	  G1_ver = scan.nextInt();
      scan.nextLine();
	  G1_hor = scan.nextInt();
      scan.nextLine();
	  System.out.printf("Doste tis sintetagmenes (x,y) tou G2:\n");
	  G2_ver = scan.nextInt();
      scan.nextLine();
	  G2_hor = scan.nextInt();
      scan.nextLine();

	  Random rand = new Random(System.currentTimeMillis());

	  for (x = 0;x < DefineConstants.N;x++)
	  {
		  for (y = 0;y < DefineConstants.N;y++)
		  {
			LAV[x][y] = 0;
		  if ((x != S_ver || y != S_hor) && (x != G1_ver || y != G1_hor) && (x != G2_ver || y != G2_hor))
		  {
			  f = rand.nextFloat();
			if (f < DefineConstants.prob)
			{
			  LAV[x][y] = 1;
			}
		  }
		  }
	  }

	  for (x = 0;x < DefineConstants.N;x++)
	  {
		  for (y = 0;y < DefineConstants.N;y++)
		  {
			if (LAV[x][y] == 0)
			{
			  val[x][y] = rand.nextInt(4) + 1;
			}
		  }
	  }

	  p.next = null;
	  p.up = null;
	  p.kostos = 0F;
	  p.ver = S_ver;
	  p.hor = S_hor;
	}

	public static void print(K p)
	{
		int x;
		int y;

	  for (x = 0;x < DefineConstants.N;x++)
	  {
		  for (y = 0;y < DefineConstants.N;y++)
		  {
			if (x == S_ver && y == S_hor)
			{
		   System.out.printf(" S/%d ",val[x][y]);
			}
		  else if (x == G1_ver && y == G1_hor)
		  {
		   System.out.printf(" A/%d ",val[x][y]);
		  }
		  else if (x == G2_ver && y == G2_hor)
		  {
		   System.out.printf(" B/%d ",val[x][y]);
		  }
		  else if (LAV[x][y] == 1)
		  {
			System.out.printf("  X  ");
		  }
		  else
		  {
			System.out.printf(" -/%d ",val[x][y]);
		  }
		  }
		 System.out.printf("\n");
	  }
	}
	
	public static float euretiki(struct K *p)
	{ return(0.5);
	}

	public static K eksagogi()
	{
		float minimum;
		float m;
	  K d;
	  K wanted = search_top;
	  K diasxisi = search_top;
	  K back = null;

	  minimum = diasxisi.kostos + euretiki(diasxisi);

	  while (diasxisi != null)
	  {
		  d = diasxisi;
		if (diasxisi.next != null)
		{
			diasxisi = diasxisi.next;
		  m = diasxisi.kostos + euretiki(diasxisi);
		  if (m < minimum)
		  {
			  minimum = m;
			wanted = diasxisi;
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
	   search_top = wanted.next;
	  }
	  else
	  {
	   back.next = wanted.next;
	  }

	  if (wanted.next == null)
	  {
	   search_bottom = back;
	  }
	  wanted.next = null;
	  return (wanted);
	}
	public static int teliki(K p)
	{
		int apostasi1;
		int apostasi2;

	  apostasi1 = Math.abs(p.ver - G1_ver) + Math.abs(p.hor - G1_hor);
	  apostasi2 = Math.abs(p.ver - G2_ver) + Math.abs(p.hor - G2_hor);

	  if (apostasi1 == 0)
	  {
	   return (1);
	  }
	  if (apostasi2 == 0)
	  {
	   return (1);
	  }

	  return (0);
	}
	public static void diadromi(K p)
	{
		K teliki = p;
		K t = p;

	  System.out.printf("H anazitisi eixe sinoliko kostos = %f\n",teliki.kostos);

	  while (p.up != null)
	  {
		  t = p;
		p = p.up;
		p.next = t;
	  }

	  System.out.printf("Veltisti Diadromi: (%d,%d) -> ",p.ver,p.hor);
	  while (t != teliki)
	  {
		  System.out.printf("(%d,%d) -> ",t.ver,t.hor);
		t = t.next;
	  }
	  System.out.printf("(%d,%d)\n",t.ver,t.hor);
	}

	public static void dimiourgia(K p, int mv, int mh, float kos)
	{
		K t;

	  if (p.ver + mv >= 0 && p.hor + mh >= 0 && p.ver + mv < DefineConstants.N && p.hor + mh < DefineConstants.N)
	  {
		  if (LAV[p.ver + mv][p.hor + mh] == 0)
		  {
			t = new K();
		  t.ver = p.ver + mv;
		  t.hor = p.hor + mh;
		  t.kostos = p.kostos + kos + Math.abs(val[p.ver][p.hor] - val[t.ver][t.hor]);
		  t.up = p;
		  t.next = null;
		  if (search_top == null)
		  {
			  search_top = t;
			search_bottom = search_top;
		  }
		  else
		  {
			  search_bottom.next = t;
			search_bottom = t;
		  }
		  }
	  }
	}

	public static void new_K(K p)
	{
	  dimiourgia(p, 1, 0, 1F); // katakorifa (katw)
	  dimiourgia(p, -1, 0, 1F); // katakorifa (panw)
	  dimiourgia(p, 0, 1, 1F); // orizontia (deksia)
	  dimiourgia(p, 0, -1, 1F); // orizontia (aristera)
	  dimiourgia(p, -1, -1, 0.5F); // diagwnia (panw aristera)
	  dimiourgia(p, 1, 1, 0.5F); // diagwnia (katw dexia)
	  dimiourgia(p, -1, 1, 0.5F); // diagwnia (panw dexia)
	  dimiourgia(p, 1, -1, 0.5F); // diagwnia (katw aristera)
	}

	public static void main(String[] args)
	{
		int epektaseis = 0;
		int stopped = 0;
		int m;
	  K p;

	  initialize();
	  print(search_top);

	  do
	  {
		  if (search_top == null)
		  {
			System.out.printf("UNSOLVED\n\n\n");
		  break;
		  }
		p = eksagogi();
		if (teliki(p) == 1)
		{
			diadromi(p);
		  stopped = 1;
		}
		else
		{
			epektaseis++;
		  new_K(p);
		}
	  } while (stopped == 0);

	  System.out.printf("EPEKTASEIS %d\n\n\n",epektaseis);
	}

}
