# tournament

Réalisation d'une application exposant une API REST pour gérer le classement de joueurs lors d'un tournoi.

Les joueurs sont triés en fonction du nombre de points de chacun, du joueur ayant le plus de points à celui qui en a le moins.

L'API devra permettre :

- d'ajouter un **joueur** (son **pseudo**)

- de mettre à jour le nombre de **points du joueur**

- de récupérer les données d'un joueur (pseudo, nombre de points et classement dans le tournoi)

- de retourner les **joueurs triés** en fonction de leur nombre de points

- de supprimer tous les joueurs à la fin du tournoi

L'application devra être réalisée en Kotlin, pourra utiliser le framework d'injection Koin (Optionnel), et basée sur Ktor.

L'application pourra utiliser la technologie de base de données de votre choix, de préférence MongoDB.


Le service devra s'initialiser et se lancer par un script bash.



(Bonus optionnel :

Fournir une interface d'administration (une UI) permettant de visualiser les joueurs présents dans l'application; en Angular.)

Livraison via Git.

---

## TO-DO

- Certaines amélioriations pourraient être apportées pour la gestion des scores égaux entre plusieurs joueurs.

Les spécifications actuelles concernant une égalité de score sont couvertes par un test end-to-end ("Player module end to end should return player with ranking 2 when added in second and same score".)

- testApplication est appelé 1 fois par cas de test pour les tests côté serveur (PlayerAdapterTI et PlayerEndToEndTest). 

Cette manière de faire pourrait sûrement être améliorée en créant une fonction qui contiendrait la configuration et serait appelée en début de chaque testcase.

- Documenter tous les endpoints pour Swagger
- Optimiser le script launch.sh pour build uniquement si des modifications sont détectées / lancer l'application dans un conteneur Docker
- Mettre en place une CI avec Gitlab-CI 
---

This project was created using the [Ktor Project Generator](https://start.ktor.io).

Here are some useful links to get you started:

- [Ktor Documentation](https://ktor.io/docs/home.html)
- [Ktor GitHub page](https://github.com/ktorio/ktor)
- The [Ktor Slack chat](https://app.slack.com/client/T09229ZC6/C0A974TJ9). You'll need to [request an invite](https://surveys.jetbrains.com/s3/kotlin-slack-sign-up) to join.

## Features

Liste des bibliothèques utilisées par le projet :

| Name                                                                   | Description                                                                        |
|------------------------------------------------------------------------|------------------------------------------------------------------------------------|
| [Content Negotiation](https://start.ktor.io/p/content-negotiation)     | Provides automatic content conversion according to Content-Type and Accept headers |
| [Routing](https://start.ktor.io/p/routing)                             | Provides a structured routing DSL                                                  |
| [kotlinx.serialization](https://start.ktor.io/p/kotlinx-serialization) | Handles JSON serialization using kotlinx.serialization library                     |
| [Koin](https://start.ktor.io/p/koin)                                   | Provides dependency injection                                                      |
| [MongoDB](https://start.ktor.io/p/mongodb)                             | Adds MongoDB database to your application                                          |
| [Kotest](https://kotest.io/)                                           | Test framework to test with style                                                  |
| [Mockk](https://mockk.io/)                                             | Mocking library for kotlin                                                         |
| [Testcontainers](https://testcontainers.com/)                          | Library for containerized tests                                                    |
| [Swagger](https://start.ktor.io/p/swagger)                             | Serves Swagger UI for your project                                                 |
| [CORS](https://start.ktor.io/p/cors)                                   | Enables Cross-Origin Resource Sharing (CORS)                                       |

## Building, Running & Testing

Pour build ou lancer l'application, utiliser une de ces commandes :

| Task              | Description                                             |
|-------------------|---------------------------------------------------------|
| `./gradlew build` | Build everything                                        |
| `./gradlew test`  | Run the tests (make sure to have docker daemon running) |
| `./gradlew run`   | Run the server                                          |
| `./launch.sh`     | Run the server from existing installation if exists     |
