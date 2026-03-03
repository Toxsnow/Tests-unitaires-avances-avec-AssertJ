# TP : Tests Unitaires Avancés avec AssertJ

## Description
Ce projet contient des tests unitaires pour la classe `TaskManager`, qui gère une liste de tâches avec leurs dates d'échéance.

## Technologies Utilisées
- **Java 17**
- **JUnit 5** (Jupiter) - Framework de tests
- **AssertJ 3.25.3** - Bibliothèque d'assertions expressives
- **Gradle 8.13** - Outil de build
- **JaCoCo** - Couverture de code

## Structure du Projet
```
testsUnitairesAvancés/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── taskmanager/
│   │           └── TaskManager.java          # Classe à tester
│   └── test/
│       └── java/
│           └── taskmanager/
│               └── TaskManagerTest.java       # Tests unitaires
├── build.gradle                               # Configuration Gradle
└── settings.gradle                            # Paramètres du projet
```

## Fonctionnalités Testées

### a) Ajout de Tâches (6 tests)
- ✅ Ajout d'une tâche valide
- ✅ Vérification du nom et de la date d'échéance
- ✅ Gestion des exceptions (nom vide, null, ou blank)

### b) Récupération des Tâches (4 tests)
- ✅ Liste de toutes les tâches
- ✅ Nombre d'éléments correct
- ✅ Liste vide si aucune tâche
- ✅ Retour d'une copie indépendante

### c) Filtrage par Date (6 tests)
- ✅ Filtrage des tâches avant une date donnée
- ✅ Exclusion des dates égales ou postérieures
- ✅ Gestion des cas limites

### d) Suppression de Tâches (8 tests)
- ✅ Suppression par nom (insensible à la casse)
- ✅ Retour de booléens appropriés
- ✅ Mise à jour correcte de la liste
- ✅ Suppression multiple

## Résultats des Tests

**24 tests passent avec succès** ✅

```
Tests d'ajout de tâches: 6/6 ✅
Tests de récupération: 4/4 ✅
Tests de filtrage: 6/6 ✅
Tests de suppression: 8/8 ✅
```

## Commandes

### Exécuter les tests
```bash
gradlew.bat test
```

### Générer le rapport de couverture
```bash
gradlew.bat jacocoTestReport
```

Le rapport sera disponible dans : `build/reports/jacoco/test/html/index.html`

### Nettoyer le projet
```bash
gradlew.bat clean
```

## Assertions AssertJ Utilisées

Ce projet démontre l'utilisation de plusieurs assertions AssertJ :

- `assertThat(...).hasSize(int)` - Vérifier la taille d'une collection
- `assertThat(...).isEmpty()` - Vérifier qu'une collection est vide
- `assertThat(...).isEqualTo(...)` - Vérifier l'égalité
- `assertThat(...).isTrue()` / `.isFalse()` - Vérifier des booléens
- `assertThat(...).extracting(...)` - Extraire des propriétés d'objets
- `assertThat(...).containsExactly(...)` - Vérifier le contenu exact
- `assertThat(...).doesNotContain(...)` - Vérifier l'absence d'éléments
- `assertThat(...).allMatch(...)` - Vérifier une condition sur tous les éléments
- `assertThatThrownBy(...)` - Vérifier les exceptions

## Principes Respectés

- ✅ **Un test = un comportement** - Chaque test vérifie un seul aspect
- ✅ **Noms explicites** - Format `should_do_something_when_condition()`
- ✅ **Tests indépendants** - Utilisation de `@BeforeEach` pour l'initialisation
- ✅ **Pattern AAA** - Arrange, Act, Assert
- ✅ **Tests organisés** - Utilisation de `@Nested` pour regrouper les tests

## Auteur
TP réalisé dans le cadre du cours de Tests Unitaires Avancés

## Date
Mars 2026
