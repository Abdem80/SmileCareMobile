# 🦷 SmileCare Mobile - Application Android

SmileCare Mobile est l'application Android développée dans le cadre du projet intégrateur du programme **Techniques de l'informatique** au Cégep de Sherbrooke. Elle permet aux **clients** de la clinique dentaire de gérer leur compte, consulter leur profil et interagir avec le système SmileCare depuis leur appareil mobile.

---

## 👥 Équipe 3 — Groupe 4218

| Membre | Initiales | Fonctionnalités développées (Mobile) |
|---|---|---|
| **Sèdrick Zahn** | SZT | Authentification mobile (Login + MFA), intégration API Google Maps |
| **Bernardo Gonçalves da Cruz** | BGDC | Activité principale (MainActivity), activité traitements/services |
| **Abdoulaye Dembele** | AD | Inscription client (MGC01), profil client (MGC02), modification profil (MGC03), désactivation compte, caméra (MFI04), BD locale SQLite, gestion de session |
| **Alexandre Doe-Langevin** | ADL | Connexion BD centrale, gestion rendez-vous mobile (CRUD) |

---

## ✅ Fonctionnalités implémentées

### Gestion du compte client (MGC) — Abdoulaye
- **MGC01** — Inscription d'un nouveau client avec validation des champs et photo de profil (caméra)
- **MGC02** — Affichage du profil du client connecté depuis la BD locale
- **MGC03** — Modification du profil client (nom, prénom, courriel, adresse, téléphone, assurance)
- **MGC03** — Désactivation du compte client avec confirmation (AlertDialog)

### Capteurs (MFI) — Abdoulaye / Sèdrick
- **MFI04** — Permission et utilisation de la caméra pour la photo de profil
- **MFI05** — Permission GPS et localisation de la clinique via Google Maps (Sèdrick)

### Base de données locale SQLite — Abdoulaye
- BD SQLite locale avec toutes les tables du système
- Insertion et lecture des données utilisateur
- Gestion de session via fichier interne (connexion/déconnexion)

### Communication API REST — Alexandre / Abdoulaye
- Classe `API.java` — client OkHttp (GET, POST, DELETE)
- Inscription client via `POST /api/utilisateurAdd`
- Désactivation compte via `DELETE /api/utilisateurDelete/{id}`

### Interface principale — Bernardo
- `MainActivity` — écran d'accueil avec navigation vers les fonctionnalités
- Layout et structure de base de l'application

### Authentification — Sèdrick
- Login avec MFA (en cours)
- Layouts pages login et récupération de mot de passe

---

## 🔌 API REST utilisée

L'application communique avec le serveur Laravel SmileCare.

> **URL de base (émulateur) :** `http://10.0.2.2/api/`  
> **URL de base (réseau physique) :** Remplacer par l'IP du serveur Laravel

### Utilisateurs

| Méthode | Endpoint | Description | Auth |
|---|---|---|---|
| `POST` | `/api/utilisateurAdd` | Créer un compte client | ❌ Public |
| `GET` | `/api/utilisateur/{id}` | Consulter un utilisateur | ✅ Token |
| `PUT` | `/api/utilisateurUpdate/{id}` | Modifier un utilisateur | ✅ Token — ⚠️ non implémenté dans API.java |
| `DELETE` | `/api/utilisateurDelete/{id}` | Désactiver un compte | ✅ Token |

### Authentification

| Méthode | Endpoint | Description |
|---|---|---|
| `POST` | `/api/token` | Obtenir un token Sanctum |

> **Note :** Toutes les routes protégées requièrent un header `Authorization: Bearer {token}`.

---

## 🗂️ Architecture du projet (MVC)

```
app/src/main/java/com/example/smilecaremobile/
│
├── Modèles
│   └── Utilisateur.java              — Modèle de données client (PHM-02)
│
├── Base de données locale
│   └── SQLiteManager.java            — SQLiteOpenHelper + tables + CRUD
│
├── Session
│   └── SessionManager.java           — Gestion session via fichier interne
│
├── API
│   └── API.java                      — Client OkHttp (GET, POST, DELETE)
│
└── Activités
    ├── MainActivity.java             — Écran d'accueil
    ├── InscriptionActivity.java      — MGC01 — Inscription + caméra
    ├── ProfilActivity.java           — MGC02 — Affichage profil
    └── ModifierProfilActivity.java   — MGC03 — Modification profil
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

| Table | Description | Responsable |
|---|---|---|
| `Utilisateur` | Infos du client connecté | AD |
| `RendezVous` | Rendez-vous du client | ADL |
| `EtatRendezVous` | États possibles d'un rdv | ADL |
| `Service` | Services/traitements offerts | BGDC |
| `CategorieService` | Catégories de services | BGDC |
| `Role` | Rôles des utilisateurs | AD |

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

- **Méthode PUT dans API.java** — pour synchroniser les modifications de profil avec la BD centrale
- **Upload photo vers le serveur** — nécessite un endpoint multipart côté Laravel
- **Authentification complète** — finaliser le login MFA côté mobile (Sèdrick)
- **Gestion des rendez-vous** — finaliser le CRUD mobile (Alexandre)
- **Activité services** — finaliser l'affichage des traitements (Bernardo)
- **Notifications Android** — notifier le client lors d'un nouveau rendez-vous
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
| **Google Maps API** | Localisation de la clinique (MFI05) |
| **Git / GitHub** | Gestion de version et collaboration |
