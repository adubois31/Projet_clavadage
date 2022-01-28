
# Manuel d'utilisation de FloppeX

##Prérequis

- Avoir java d'installé et correctement placé dans votre dossier bin/ ou équivalent
- Avoir Maven d'installé

## Pour le responsable du déploiement de l'application :

1. Tout d'abord allez dans le dossier floppeX de ce git, puis éditez les champs de config.txt :
BroadcastAdress, BroadcastPort, MessageServerPort par les valeurs appropriées en fonction
de votre réseau local.

2. Utilisez maven pour exportez le projet maven floppeX en un .jar grâce à la commande
```shell
User@mypc:~$ mvn clean package
```

