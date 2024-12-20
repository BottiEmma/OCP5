# Projet 5 Openclassrooms

## Base de données : installation
Ouvrez le fichier P5/back/src/main/resources/application.properties, et modifiez ces lignes avec les informations de votre base de données :  

> spring.datasource.url=jdbc:mysql://localhost:PORT/DATABASE NAME  
> spring.datasource.username=USERNAME    
> spring.datasource.password=PASSWORD

## Application 
Pour lancer l'application, il faut déjà se déplacer dans le dossier /front et exécuter cette commande pour installer les dépendances :  
```
npm install
```
Ensuite, en revenant à la racine, on fait de même pour dans le dossier /back :
```
mvn clean install
```

Maintenant que l'application est installé, pour la faire fonctionner, il faut revenir dans le dosser /front et lancer cette commande : 
```
npm run start
```
Puis, dans le dossier back : 
```
mvn spring-boot:run
```

Il suffit ensuite de se rendre à l'adresse http://localhost:4200/

## Lancer les test
Les tests front-end :
```
npm run test
```
Les tests end-to-end :
```
npm run e2e
npm run e2e:ci
```
Les tests back-end :
```
mvn clean test
```

## Générer les rapports de couverture
Front-end : Ouvrir le fichier front/coverage/jest/lcov-report/index.html dans un navigateur.  
End-to-End : 
```
npm run e2e:coverage
```
La couverture se trouve dans le fichier front/coverage/lcov-report/index.html  
Back-End : Ouvrir le fichier back/target/site/jacoco/index.html dans un navigateur.



