

/*cette classe permet de parser les données json*/
public class TraitementResOMDB 
{
	public TraitementResOMDB(){}
	
	public int traitementRuntime(String runtime)
	{
		//System.out.println("-----"+runtime);
		
		String a  = runtime.substring(0,runtime.length()-4); 
		return  Integer.parseInt(a);
	}

	public String[] TraitementGenre(String listegenre)
	{
		String[] tab = listegenre.split(",");
		
		return tab;
	}
	
	public String[] TraitementActeurs(String listegenre)
	{
		String[] tab = listegenre.split(",");
		
		return tab;
	}
	
	
}
