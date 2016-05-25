//package mashup;

import java.io.File;
import java.util.*;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.SimpleIRIMapper;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


public class populate {

	public static void main(String args[])
	   {
		
		try {
			
			Le_fameux parser = new Le_fameux(); 
			parser.parserParis("/home/abdi/java_workspace/TP_Web_Semantique/Paris.csv"); //chemin à adapter à l'execution 
			parser.parserMontpellier("/home/abdi/java_workspace/TP_Web_Semantique/VilleMTP_MTP_FilmsTournes (1).csv");
		
	
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		
		/*chargement de l'ontologie ie le vocabulaire 
		 * à utiliser pour décrire les données
		 */
		
		//fichier contenant l'ontologie
		File file = new File("/home/abdi/Web_semantique/TP1.owl"); //modifier et mettre le bon chemin
		//parsage du fichier pour obtenir un objet onto de type OWLOntology 
		OWLOntology onto = manager.loadOntologyFromOntologyDocument(file);
		System.out.println("Loaded ontology: " + onto);
		
		//récupération de l'IRI de l'ontologie onto 
		IRI IRIonto = onto.getOntologyID().getOntologyIRI();
		
		/*création d'une nouvelle ontologie ontoPeup 
		* qui contiendra les instances et relations entre instances extraites des sources
		* et décrites à partir du vocabulaire défini dans l'ontologie onto
		 */
		
		//création d'une nouvelle IRI à partir de l'IRI de l'ontologie onto
		IRI IRIontoPeup = IRI.create(IRIonto.toString()+"peup");
		// création de la nouvelle ontologie ontoPeupidentifiée à partir de cette IRI
		OWLOntology ontoPeup = manager.createOntology(new OWLOntologyID(IRIontoPeup));
		// création du fichier qui permettra de sauvegarder ontoPeup une fois peuplée
		File fileOut = new File("/home/abdi/Web_semantique/new_onto.owl");
		
		//récupération de l'ensemble des données contenues dans les ontologies chargées
		OWLDataFactory factory = manager.getOWLDataFactory();

		//import de l'ontologie onto dans l'ontologie ontoPeup de façon à faire évoluer les deux indépedamment
		OWLImportsDeclaration importDeclaraton = factory.getOWLImportsDeclaration(IRIonto);
		manager.applyChange(new AddImport(ontoPeup, importDeclaraton));
		
		// définitions de prefixes à partir des IRI des deux ontologies pour accéder plus facilement à leur élément
		PrefixManager pmonto = new DefaultPrefixManager(IRIonto.toString()+"#");
		PrefixManager pmontoP = new DefaultPrefixManager(IRIontoPeup.toString()+"#");
		
		
		/*****************************************Ajout des informations de Montpellier************************************** */
		
		Iterator<recup_ligne_Montpellier> it =parser.getListMont().iterator()  ;  
		//int cpt=0;
		while(it.hasNext())
		{
			
			//System.out.println("cpt : " + cpt);cpt++;
		/*
		 * exemple de récupération d'une classe dans l'ontologie onto
		 */
				OWLClass Film = factory.getOWLClass(":film", pmonto);
				recup_ligne_Montpellier ligne_courante=it.next();
				
				/*
				 * exemple de création d'une instance dans l'ontologie ontoPeup
				 */
				OWLNamedIndividual nvfilm = factory.getOWLNamedIndividual(":"+ligne_courante.getTitre(),pmontoP);
				
				/* PAS UTILE
				 * exemple d'assertion indiquant que l'instance nvfilm est de type Film
				 */
				/*OWLClassAssertionAxiom classAssertion =
						factory.getOWLClassAssertionAxiom(Film, nvfilm);
				manager.addAxiom(ontoPeup, classAssertion); //on enregistre l'assertion dans l'ontologie ontoPeup
				*/
				
				
				/*
				 * exemple d'ajout de label à l'instance nvfilm
				 * LE TITRE DU FILM EST AUSSI UN LABEL VERS CELUI-CI
				 */
				OWLAnnotation lab = factory.getOWLAnnotation(factory.getRDFSLabel(),factory.getOWLLiteral(ligne_courante.getTitre(),"fr"));
				OWLAnnotationAssertionAxiom labassertion= factory.getOWLAnnotationAssertionAxiom(nvfilm.getIRI(), lab);
				manager.addAxiom(ontoPeup, labassertion);
				
				
				/*ajout date de sortie 
				 * exemple d'ajout d'une dataproperty à l'instance nvfilm
				 */
				if(ligne_courante.getDate_sortie()!=null)
				{
					OWLDatatype integerDatatype = factory.getOWLDatatype(OWL2Datatype.XSD_DATE_TIME.getIRI());//recuration du type INTEGER
					OWLLiteral literal3 = factory.getOWLLiteral(ligne_courante.getDate_sortie().toString(), integerDatatype); //creation DATE DE SORTIE
					
					OWLDataProperty pro_date_de_sortie=factory.getOWLDataProperty(":date_de_sortie",pmonto);// récupération de la dataproperty définie dans onto
					OWLDataPropertyAssertionAxiom dataassersion = factory.getOWLDataPropertyAssertionAxiom(pro_date_de_sortie, nvfilm, literal3);// assertion du lien entre l instance, la propriété et le litéral
					manager.addAxiom(ontoPeup, dataassersion);	//stockage de l'assertion dans ontoPeup
				}
				
				/*
				 * ajout du réalisateur 
				 * exemple d'ajout d'une objectproperty à l'instance nvfilm
				 */
					
				OWLNamedIndividual nvreal = factory.getOWLNamedIndividual(":"+ligne_courante.getRealisateur() ,pmontoP); //création d'une instance nvreal
		
				OWLAnnotation labreal = factory.getOWLAnnotation(factory.getRDFSLabel(),factory.getOWLLiteral(ligne_courante.getRealisateur() ,"fr"));
				OWLAnnotationAssertionAxiom labassertionreal= factory.getOWLAnnotationAssertionAxiom(nvreal.getIRI(), labreal);
				manager.addAxiom(ontoPeup, labassertionreal);
				
				OWLObjectProperty propReal =factory.getOWLObjectProperty(":est_réalisé_par",pmonto);//recupération de l objectproperty dans onto
				OWLObjectPropertyAssertionAxiom objectassersion = factory.getOWLObjectPropertyAssertionAxiom(propReal, nvfilm, nvreal); //assertion du lien
				manager.addAxiom(ontoPeup, objectassersion); //ajout de l'assertion dans ontoPeup
				
				/*=============================================**/
				
				/*Ajout de l'année de tournage*/
				
				OWLDatatype integerDatatype1 = factory.getOWLDatatype(OWL2Datatype.XSD_INTEGER.getIRI());//recuration du type INTEGER
				OWLLiteral literal4 = factory.getOWLLiteral(String.valueOf(ligne_courante.getAnnee_tournage()), integerDatatype1); //creation DATE DE SORTIE
					
				OWLDataProperty pro_annee_de_tournage=factory.getOWLDataProperty(":annee_de_tournage",pmonto);// récupération de la dataproperty définie dans onto
				OWLDataPropertyAssertionAxiom dataassersion1 = factory.getOWLDataPropertyAssertionAxiom(pro_annee_de_tournage, nvfilm, literal4);// assertion du lien entre l instance, la propriété et le litéral
				manager.addAxiom(ontoPeup, dataassersion1);	//stockage de l'assertion dans ontoPeup
				
				
				/*======================================================**/
					/*Ajout d'un lieu de tournage, on rajoute qu'un seul */
				
				
				OWLNamedIndividual nvLieu= factory.getOWLNamedIndividual(":"+ligne_courante.getLieu_tournage1() ,pmontoP); //création d'une instance nvreal
		
				OWLAnnotation labrealLieuTournage = factory.getOWLAnnotation(factory.getRDFSLabel(),factory.getOWLLiteral(ligne_courante.getLieu_tournage1() ,"fr"));
				OWLAnnotationAssertionAxiom labassertionTournage= factory.getOWLAnnotationAssertionAxiom(nvLieu.getIRI(),labrealLieuTournage);
				manager.addAxiom(ontoPeup, labassertionTournage);
				
				OWLObjectProperty propLieuTournage =factory.getOWLObjectProperty(":de_tournage",pmonto);//recupération de l objectproperty dans onto
				OWLObjectPropertyAssertionAxiom objectassersionLieuTournage = factory.getOWLObjectPropertyAssertionAxiom(propLieuTournage, nvfilm, nvLieu); //assertion du lien
				manager.addAxiom(ontoPeup, objectassersionLieuTournage); //ajout de l'assertion dans ontoPeup
				
				//ajout de faits supplémentaires pour s'amuser...
				/*OWLNamedIndividual france = factory.getOWLNamedIndividual(":France",pmonto);
				OWLNamedIndividual toulouse = factory.getOWLNamedIndividual(":Toulouse",pmontoP);
				OWLObjectPropertyAssertionAxiom tenfassersion = factory.getOWLObjectPropertyAssertionAxiom(factory.getOWLObjectProperty(":seSitueEn",pmonto), toulouse, france);
				manager.addAxiom(ontoPeup, tenfassersion);
				OWLObjectPropertyAssertionAxiom nvfatassersion = factory.getOWLObjectPropertyAssertionAxiom(factory.getOWLObjectProperty(":tourneA",pmonto), nvfilm, toulouse);
				manager.addAxiom(ontoPeup, nvfatassersion);*/
				
				
				/*======================================================**/
				/*Ajout d'une url de description */
				
				
				if(ligne_courante.getUrl_description()!=null)
				{
					OWLDatatype Stringtype = factory.getOWLDatatype(OWL2Datatype.XSD_STRING.getIRI());//recuration du type INTEGER
					OWLLiteral literal13 = factory.getOWLLiteral(ligne_courante.getUrl_description(),Stringtype); //creation DATE DE SORTIE
					
					OWLDataProperty pro_URL=factory.getOWLDataProperty(":url_description",pmonto);// récupération de la dataproperty définie dans onto
					OWLDataPropertyAssertionAxiom KYGO = factory.getOWLDataPropertyAssertionAxiom(pro_URL, nvfilm, literal13);// assertion du lien entre l instance, la propriété et le litéral
					manager.addAxiom(ontoPeup, KYGO);	//stockage de l'assertion dans ontoPeup
				}
				
				
				
				//sauvegarde de l'ontologie peuplée dans le fichier fileOut
				manager.saveOntology(ontoPeup, IRI.create(fileOut.toURI()));
				System.out.println("Sauvegarde de l'ontologie peuplée :" + fileOut.toURI());
		}
		/********************************************Ajout informations Paris **************************************************************/
		//Remarque : on a essayé de rajouter les informations sur paris, mais elles ne sont pas pertinentes car plusieurs films sont répétés en double.
		//par conséquent on se focalise sur Montpellier
		Iterator<recup_ligne_Paris> itbis =parser.getListParis().iterator()  ;  
		/*int cpt=0;
		while(itbis.hasNext() && cpt <300)
		{
			
		
		 //exemple de récupération d'une classe dans l'ontologie onto
		
				OWLClass FilmParis = factory.getOWLClass(":film", pmonto);
				recup_ligne_Paris ligne_courantebis=itbis.next();
				
				OWLNamedIndividual nvfilmBis = factory.getOWLNamedIndividual(":"+ligne_courantebis.getTitre(),pmontoP);
				
				
				
				
				OWLAnnotation labBis = factory.getOWLAnnotation(factory.getRDFSLabel(),factory.getOWLLiteral(ligne_courantebis.getTitre(),"fr"));
				OWLAnnotationAssertionAxiom labassertionBis= factory.getOWLAnnotationAssertionAxiom(nvfilmBis.getIRI(), labBis);
				manager.addAxiom(ontoPeup, labassertionBis);
				
				
				//sauvegarde de l'ontologie peuplée dans le fichier fileOut
				manager.saveOntology(ontoPeup, IRI.create(fileOut.toURI()));
				System.out.println("Sauvegarde de l'ontologie peuplée :" + fileOut.toURI());
				
		}*/
		
		}
		catch (Exception ie) {
			ie.printStackTrace();
			
		}
			
			
		
	   
	   }
	
}
