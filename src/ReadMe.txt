Le code du projet a été réalisé en Java, il est fourni avec un fchier
build.xml pour apache ant qui permet les commandes suivantes :

ant compile
    pour compiler le projet

ant run -Darg0="<motif a rechercher>" -Darg1=<chier texte>
    pour lancer la recherche du motif sur un texte

ant clean
    pour nettoyer le répertoire du projet