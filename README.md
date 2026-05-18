# 🦷 SmileCare Mobile - Application Android

SmileCare Mobile est l'application Android développée dans le cadre du projet intégrateur du programme **Techniques de l'informatique** au Cégep de Sherbrooke. Elle permet aux **clients** de la clinique dentaire de gérer leur compte, consulter leur profil et prendre rendez-vous, le tout depuis leur appareil mobile.

---

## 👥 Équipe 3 — Groupe 4218

| Membre | Initiales | Grandes fonctionnalités développées |
|---|---|---|
| **Sèdrick Zahn** | SZT | Gestion des rendez-vous mobile (CRUD), BD locale RendezVous |
| **Bernardo Gonçalves da Cruz** | BGDC | Gestion des traitements/services mobile, GPS + Google Maps |
| **Abdoulaye Dembele** | AD | Inscription client, profil client, modification profil, désactivation compte, caméra (photo de profil), BD locale SQLite, gestion de session |
| **Alexandre Doe-Langevin** | ADL | Communication API REST (classe API.java), authentification token Sanctum |

---

## ✅ Fonctionnalités implémentées

### Gestion du compte client (MGC)
- **MGC01** — Inscription d'un nouveau client avec photo de profil (caméra)
- **MGC02** — Affichage du profil du client connecté
- **MGC03** — Modification du profil client
- **MGC03** — Désactivation du compte client avec confirmation

### Capteurs (MFI)
- **MFI04** — Permission et utilisation de la caméra pour la photo de profil
- **MFI05** — Permission GPS et localisation de la clinique via Google Maps

### Base de données locale (MFI01)
- BD SQLite locale avec toutes les tables du système (Utilisateur, RendezVous, Service, etc.)
- Synchronisation avec la BD centrale via l'API Laravel
- Gestion de session via fichier interne (connexion/déconnexion)

### API REST
- Communication avec le serveur Laravel via OkHttp
- Authentification par token Laravel Sanctum
- Requêtes GET, POST, DELETE vers l'API

---

## 🔌 API REST utilisée

L'application communique avec le serveur Laravel SmileCare via l'API REST.

> **URL de base (émulateur) :** `http://10.0.2.2/api/`  
> **URL de base (réseau physique) :** Remplacer par l'IP du serveur

### Utilisateurs

| Méthode | Endpoint | Description | Auth |
|---|---|---|---|
| `POST` | `/api/utilisateurAdd` | Créer un compte client | ❌ Public |
| `GET` | `/api/utilisateur/{id}` | Consulter un utilisateur | ✅ Token |
| `PUT` | `/api/utilisateurUpdate/{id}` | Modifier un utilisateur | ✅ Token |
| `DELETE` | `/api/utilisateurDelete/{id}` | Désactiver un compte | ✅ Token |

### Authentification

| Méthode | Endpoint | Description |
|---|---|---|
| `POST` | `/api/token` | Obtenir un token Sanctum |

> **Note :** Toutes les routes protégées requièrent un header `Authorization: Bearer {token}`.

---

## 🗂️ Architecture du projet

L'application respecte l'architecture **MVC** :

```
app/src/main/java/com/example/smilecaremobile/
│
├── Modèles
│   └── Utilisateur.java          — Modèle de données client
│
├── Base de données locale
│   └── SQLiteManager.java        — SQLiteOpenHelper + toutes les tables + méthodes CRUD
│
├── Session
│   └── SessionManager.java       — Gestion session via fichier interne (cours 11)
│
├── API
│   └── API.java                  — Client OkHttp (GET, POST, DELETE)
│
└── Activités (Contrôleurs + Vues)
    ├── MainActivity.java          — Écran d'accueil
    ├── InscriptionActivity.java   — MGC01 — Inscription client
    ├── ProfilActivity.java        — MGC02 — Affichage profil
    └── ModifierProfilActivity.java — MGC03 — Modification profil
```

---

## 🚀 Étapes pour faire fonctionner le projet

### Prérequis
- **Android Studio** (version récente)
- **Émulateur Android** API 30+ ou appareil physique
- **Serveur Laravel SmileCare** en cours d'exécution (voir README Web)

### 1. Cloner le dépôt
```bash
git clone https://github.com/Abdem80/SmileCareMobile.git
```

### 2. Ouvrir dans Android Studio
- `File` → `Open` → sélectionner le dossier `SmileCareMobile`
- Attendre la synchronisation Gradle

### 3. Configurer l'URL du serveur
Dans `API.java`, modifier l'URL selon votre environnement :

```java
// Émulateur Android
public static final String URL = "http://10.0.2.2/";

// Appareil physique (remplacer par l'IP de votre serveur)
public static final String URL = "http://192.168.X.X/";
```

### 4. Lancer l'application
- Sélectionner un émulateur ou appareil connecté
- Cliquer sur **Run** ▶️

---

## 🛢️ Base de données locale SQLite

L'application utilise une BD locale SQLite pour stocker les données hors ligne.

| Table | Description |
|---|---|
| `Utilisateur` | Infos du client connecté |
| `RendezVous` | Rendez-vous du client |
| `EtatRendezVous` | États possibles d'un rdv |
| `Service` | Services/traitements offerts |
| `CategorieService` | Catégories de services |
| `Role` | Rôles des utilisateurs |

> **Version actuelle de la BD :** 2

---

## 🔧 Dépendances (build.gradle)

```gradle
dependencies {
    // OkHttp — Communication API
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    // Google Play Services — GPS
    implementation("com.google.android.gms:play-services-location:21.3.0")

    // Material Design
    implementation("com.google.android.material:material:1.12.0")
}
```

---

## 🔒 Permissions requises

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
```

---

## 🔧 Pistes d'amélioration

- **Upload photo vers le serveur** — nécessite un endpoint multipart côté Laravel
- **Méthode PUT dans API.java** — pour synchroniser les modifications de profil avec la BD centrale
- **Notifications Android** — notifier le client lors d'un nouveau rendez-vous
- **Mode hors ligne complet** — améliorer la synchronisation BD locale ↔ BD centrale
- **Authentification complète** — intégrer le login MFA côté mobile
- **Tests automatisés** — ajouter des tests unitaires Android (JUnit)

---

## 🛠️ Technologies utilisées

| Technologie | Utilisation |
|---|---|
| **Java** | Langage de développement |
| **Android Studio** | Environnement de développement |
| **SQLite** | Base de données locale |
| **OkHttp** | Communication HTTP avec l'API |
| **Laravel Sanctum** | Authentification par token |
| **Google Maps API** | Localisation de la clinique |
| **Git / GitHub** | Gestion de version et collaboration |
