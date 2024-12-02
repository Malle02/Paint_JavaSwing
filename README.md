README: PaintIt Modern 🎨


Description du projet


PaintIt Modern est une application de dessin interactive et moderne créée en Java. Elle offre des outils de dessin variés, une interface ergonomique avec mode sombre, et des fonctionnalités avancées comme la gestion des formes, la manipulation du texte, la sauvegarde/chargement de dessins et l'import/export d'images. L'application est idéale pour expérimenter la créativité tout en explorant les concepts avancés de Java Swing.

Fonctionnalités principales


1. Barre d'outils intuitive
Palette de couleurs pour changer la couleur des formes et du pinceau.
Choix des outils : rectangle, cercle, ligne, triangle, dessin libre, texte.
Gomme pour effacer partiellement des zones.
Mode sélection pour manipuler les formes.
2. Zone de dessin
Une interface fluide pour dessiner et manipuler les formes.
Possibilité d'ajuster la taille du pinceau avec un curseur.
3. Gestion des formes
Ajouter des formes simples (rectangle, cercle, triangle, ligne).
Déplacement et suppression des formes.
Modification du texte déjà ajouté.
Mode sombre/claire pour un confort visuel optimal.
4. Sauvegarde et chargement
Sauvegarde des formes dans un fichier binaire pour un ré-usage ultérieur.
Chargement de formes à partir d'un fichier existant.
5. Gestion des images
Importation d'images à dessiner ou manipuler dans la zone de dessin.
Exportation de la zone de dessin au format PNG.



Fonctionnalités non entièrement fonctionnelles


Gomme : Fonctionne, mais l'effacement n'est pas optimal (zone d'effacement peu précise).
Redimensionnement des formes : Non complètement implémenté ou ne fonctionne pas comme prévu pour toutes les formes.
Déplacement : Fonctionnel, mais manque parfois de précision.


Comment utiliser l'application


1. Outils disponibles
Libre ✏️ : Dessinez à main levée.
Rectangle ▭ : Tracez un rectangle avec une couleur personnalisée.
Cercle ⚫ : Tracez un cercle.
Ligne ➖ : Tracez une ligne droite.
Triangle 🔺 : Dessinez un triangle.
Texte 📝 : Ajoutez du texte personnalisé.
2. Manipulation des formes
Utilisez le mode Sélection pour :
Déplacer une forme en cliquant et glissant.
Modifier un texte existant en sélectionnant et en cliquant sur "Modifier Texte".
Supprimer une forme sélectionnée avec le bouton Supprimer.
3. Couleur et pinceau
Choisissez une couleur prédéfinie ou cliquez sur "🎨 Perso" pour sélectionner une couleur personnalisée.
Ajustez la taille du pinceau avec le curseur dans la section Taille du pinceau.
4. Sauvegarde et chargement
Cliquez sur 💾 Sauvegarder pour enregistrer les formes dans un fichier.
Cliquez sur 📂 Charger pour ouvrir un fichier et récupérer les formes sauvegardées.
5. Gestion des images
Cliquez sur 📷 Importer Image pour ajouter une image à la zone de dessin.
Cliquez sur 🖼️ Sauvegarder Image pour exporter la zone de dessin au format PNG.
Configuration et exécution
Prérequis


Java 11 ou supérieur installé.

Un environnement de développement comme IntelliJ IDEA ou Eclipse.
Étapes
Clonez le dépôt ou copiez les fichiers sources dans votre IDE.
Compilez et exécutez la classe PaintFrame.