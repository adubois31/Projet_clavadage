
# Manuel d'utilisation de FloppeX

## Prérequis

- Avoir java d'installé et correctement placé dans votre dossier bin/ ou équivalent
- Avoir Maven d'installé

## Pour le responsable du déploiement de l'application :

1. Tout d'abord allez dans le dossier floppeX de ce git, puis éditez les champs de config.txt :
BroadcastAdress, BroadcastPort, MessageServerPort par les valeurs appropriées en fonction
de votre réseau local.

2. Assurez vous que les scripts approriés à votre système d'exploitation soient bien exécutables. Pour le confirmer il suffit d'entrer la commande dans votre terminal : 
- Si vous êtez sous Linux :
```shell
User@mypc:~$ chmod +x *.sh
```
Déplacez ensuite les scripts BuildApp et LaunchApp appropriés dans le dossier floppeX

3. Exportez le projet floppeX en un .jar grâce à la commande :
- Si vous êtes sous Linux :
```shell
User@mypc:~$ ./BuildApp.sh
```
Si l'excution de ce script ne fonctionne pas esseyez :
```shell
User@mypc:~$ mvn clean package
```

4. Assurez vous que les fichiers floppeX.jar et LaunchApp soient dans le même dossier. Vous pouvez replacer BuildApp dans son dossier d'origine. 

## Pour l'utilisateur :

Pour lancer l'application de chat il suffit d'aller d'entrer la commande : 
-Si vous êtes sous Linux :
```shell
User@mypc:~$ ./LaunchApp.sh
```
Si ce fichier n'est pas disponible, non exécutable ou toute autre erreur exécutez cette commande :
```shell
User@mypc:~$ java -jar floppeX.jar
```

- **Pour vous connecter** :
Il vous suffira ensuite de rentrer un pseudo pour communiquer avec les autres utilisateurs.
Ce pseudo devra être unique parmi les utilisateurs actifs , l'application le vérifiera pour vous.
Si ce pseudo est déjà pris, veuillez en choisir un autre.

- **Pour envoyer un message à un utilisateur** :
Cliquez sur l'utilsateur actif dans le cadre à gauche intitulé ACTIVE USERS, 
puis tappez dans la zone de texte message à lui envoyer.
Pressez la touche entrée ou cliquez sur le bouton Flop It.
Pour changer d'utilisateur avec lequel discuter il suffit de cliquer sur un autre utilisateur dans la liste des ACTIVE USERS.

- **Pour changer de pseudo** :
Cliquez sur le bouton CHANGE PSEUDO, confirmez que vous souhaitez changer de pseudo en cliquant sur oui dans la fenêtre pop up ayant apparue.
Entrez votre nouveau pseudo. Si ce dernier est déjà utilisé, sinon vous retourner sur la page de chat.
Vous pouvez de nouveau discutez avec les autres utilisateurs.

- **Pour fermer l'application ou vous déconnecter** : 
Cliquez sur la croix rouge en haut à droite de la fenêtre de l'application pour la fermer.

