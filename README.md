# 🛗 SimulationAscenseur

**Simulation d'un système multi-ascenseurs** dans un immeuble, avec algorithmes de scheduling pour optimiser la gestion des requêtes utilisateurs (appels, destinations, temps d'attente).

> 📚 Projet de groupe (3 développeurs, 111 commits) réalisé en 2ème année d'IUT Informatique dans le cadre du module de développement orienté objet. Non maintenu, conservé comme trace de mon apprentissage de la conception d'algorithmes et du travail en équipe sur Git.

## 🎯 Concept

Simuler le fonctionnement d'un immeuble équipé de plusieurs ascenseurs qui doivent :
- Répondre aux appels des utilisateurs à différents étages
- Décider quel ascenseur envoyer pour minimiser les temps d'attente
- Gérer les destinations multiples au sein d'une même cabine
- Éviter les collisions et les incohérences de mouvement

## 🧠 Aspects techniques

- **Algorithme de scheduling** — attribution des requêtes aux ascenseurs selon leur position et charge courante
- **Simulation événementielle** — le temps avance par « ticks », chaque ascenseur met à jour son état à chaque cycle
- **Architecture orientée objet** — séparation entre le modèle (ascenseurs, requêtes, immeuble), la logique de simulation et l'affichage

## 🚀 Lancer la simulation

```bash
# Compilation
javac -d out src/**/*.java

# Exécution
java -cp out Main
```

*(Ajuster le nom de la classe principale selon la structure du projet)*

## 🛠️ Stack

- **Java**
- Développé sous IntelliJ IDEA

## 👥 Équipe

Projet réalisé en équipe de 3 étudiants 
