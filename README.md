
# Manuel d'utilisation de FloppeX

## Informations sur la version actuelle : 

Après quelques tests de l'exécution du .jar obtenu avec maven, nous avons remarqué que la base de données n'était pas exploitée correctement.
Cependant run cette application dans eclipse permet d'utiliser toutes les fonctionnalités actuellement disponibles.
Pour ce faire il suffit de définir comme workspace d'eclipse ce dossier git (Projet_clavadage) puis d'importer un projet maven en selectionnant le dossier floppeX.
Il ne reste plus qu'à run App.java en tant qu'application java.

## Prérequis

- Avoir java d'installé et correctement placé dans votre dossier bin/ ou équivalent
- Avoir Maven d'installé

## Pour le responsable du déploiement de l'application :

1. Tout d'abord allez dans le dossier floppeX de ce git, puis éditez les champs de config.txt :
BroadcastAdress, BroadcastPort, MessageServerPort par les valeurs appropriées en fonction
de votre réseau local.

2. Assurez-vous que les scripts appropriés à votre système d'exploitation soient bien exécutables. Pour le confirmer, il suffit d'entrer la commande dans votre terminal : 
- Si vous êtes sous Linux :
```shell
User@mypc:~$ chmod +x *.sh
```

3. Exportez le projet floppeX en un .jar depuis Scripts grâce à cette commande :
- Si vous êtes sous Linux :
```shell
User@mypc:~$ ./BuildApp.sh
```


## Pour l'utilisateur :

Pour lancer l'application de chat, il suffit d'aller dans Scripts d'entrer la commande : 
-Si vous êtes sous Linux :
```shell
User@mypc:~$ ./LaunchApp.sh
```

- **Pour vous connecter** :
Il vous suffira ensuite de rentrer un pseudo pour communiquer avec les autres utilisateurs.
Ce pseudo devra être unique parmi les utilisateurs actifs, l'application le vérifiera pour vous.
Si ce pseudo est déjà pris, veuillez en choisir un autre.

- **Pour envoyer un message à un utilisateur** :
Cliquez sur l'utilisateur actif dans le cadre à gauche intitulé ACTIVE USERS, 
puis tappez dans la zone de texte message à lui envoyer.
Pressez la touche entrée ou cliquez sur le bouton Flop It.
Pour changer d'utilisateur avec lequel discuter, il suffit de cliquer sur un autre utilisateur dans la liste des ACTIVE USERS.

- **Pour changer de pseudo** :
Cliquez sur le bouton CHANGE PSEUDO, confirmez que vous souhaitez changer de pseudo en cliquant sur oui dans la fenêtre pop up ayant apparue.
Entrez votre nouveau pseudo. Si ce dernier est déjà utilisé, sinon vous retourner sur la page de chat.
Vous pouvez de nouveau discuter avec les autres utilisateurs.

- **Pour fermer l'application ou vous déconnecter** : 
Cliquez sur la croix rouge en haut à droite de la fenêtre de l'application pour la fermer.

