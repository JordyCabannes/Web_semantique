import java.util.Date;


/*Cette classe permet de stocker les informations du fichier de données de Paris*/

public class recup_ligne_Paris 
{
	
	private String titre;
	private String realisateur;
	private Date deb_evt;
	private Date fin_evt ;
	private String cadre ;
	private String lieu ; 
	private String adresse;
	private int arrondissement ;
	private String adresse_Complete; 
	private float coordX;
	private float coordY; 
	
	
	public recup_ligne_Paris(String titre , String realisateut,Date deb_evt, Date fin_evt,String cadre,String lieu,String adresse,int arrond, String addr_compl)
	{
		this.titre=titre; 
		this.realisateur= realisateut;
		this.deb_evt =deb_evt;
		this.fin_evt = fin_evt; 
		this.cadre=cadre;
		this.lieu=lieu;
		this.adresse=adresse;
		this.arrondissement=arrond;
		this.adresse_Complete =addr_compl;
		
		
	}
	
	public void addCoord(float x ,float  y )
	{
		this.coordX =coordX;
		this.coordY=coordY;
	}
	
	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getRealisateur() {
		return realisateur;
	}

	public void setRealisateur(String realisateur) {
		this.realisateur = realisateur;
	}

	public Date getDeb_evt() {
		return deb_evt;
	}

	public void setDeb_evt(Date deb_evt) {
		this.deb_evt = deb_evt;
	}

	public Date getFin_evt() {
		return fin_evt;
	}

	public void setFin_evt(Date fin_evt) {
		this.fin_evt = fin_evt;
	}

	public String getCadre() {
		return cadre;
	}

	public void setCadre(String cadre) {
		this.cadre = cadre;
	}

	public String getLieu() {
		return lieu;
	}

	public void setLieu(String lieu) {
		this.lieu = lieu;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public int getArrondissement() {
		return arrondissement;
	}

	public void setArrondissement(int arrondissement) {
		this.arrondissement = arrondissement;
	}

	public String getAdresse_Complete() {
		return adresse_Complete;
	}

	public void setAdresse_Complete(String adresse_Complete) {
		this.adresse_Complete = adresse_Complete;
	}

	public float getCoordX() {
		return coordX;
	}

	public void setCoordX(float coordX) {
		this.coordX = coordX;
	}

	public float getCoordY() {
		return coordY;
	}

	public void setCoordY(float coordY) {
		this.coordY = coordY;
	}

	


}
