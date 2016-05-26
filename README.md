# Web_semantique


/************************** Partie facultative **************************/

Tout d'abord il faut changer les chemins se trouvant aux lignes 169 et 170 de la classe Le_fameux.java. Cette classe se
se trouve dans le dossier Web_semantique/java_workspace/parser_csv/src
Il faut mettre le bon chemin vers les fichiers csv à parser, ces fichiers se trouvent dans le dossier 
/java_workspace/TP_Web_Semantique

/************************************************************************/

Pour peupler l'ontologie TP1.owl, il faut aller dans la classe populate.java se trouvant dans le dossier
Web_semantique/java_workspace/parser_csv/src. Ensuite une fois dans cette classe il faut modifier les chemins 
des lignes 43 et 44 pour donner les bons chemins vers les fichiers parser se trouvant dans le dossier  
Web_semantique/java_workspace/TP_Web_Semantique

Puis il faut modifier les chemins des lignes 54 et 72. À la ligne 54 il faut donner le chemin vers l'ontologie TP1.owl,
cette ontologie se trouve à la racine du dossier Web_semantique. Puis à la ligne 72 il faut donner le chemin vers 
l'ontologie créé et peuplée à l'issu de l'excécution du main de populate.java. Notre ontologie peuplée se trouve également
à la racine du dossier Web_semantique, elle s'appelle new_onto.

Enfin on lance le main de la classe populate et le peuplement de l'ontologie s'effectue
