# Age Décor Interior Design — Android App (Mumbai) 🏛️✨

A high-end, luxury **Android Application** engineered specifically for **Age Décor Interior Design** (Mumbai, Maharashtra).

This application is built with a **Privacy-First & Data-Sovereignty Architecture**, ensuring that the **user has 100% control over all their data** (inquiries, personal contact details, preferences, and saved projects).

---

## 🌟 Key Features

### 1. 🛡️ User Data Sovereignty & Control Hub ("User ke har ek data ko control me rakhe")
* **Zero Third-Party Tracking**: Personal data is saved **locally on the user's device** using an encrypted SQLite database (`AppDatabaseHelper.java`).
* **Granular Deletion**: Users can view every consultation inquiry they have ever submitted and **delete individual inquiries** anytime.
* **Full Data Portability (Export JSON)**: Users can export their complete data archive (`inquiries`, `saved_projects`, `timestamp`) into a structured JSON format with 1 click.
* **Right to be Forgotten (Wipe All Data)**: A single button completely erases all user inquiries, bookmarks, and preferences from the device.
* **Privacy Switches**: Toggle strict local-only storage and disable anonymous analytics.

### 2. 🏛️ Brand Identity & Mumbai Localization
* **Company**: **Age Décor Interior Design**
* **Brand Aesthetic**: Deep Obsidian Charcoal (`#0F1115`), Royal Champagne Gold (`#D4AF37`), Warm Copper Rose Gold (`#C88A58`).
* **Bespoke Vector Logo**: Stylized architectural geometric "AD" monogram emblem (`ic_age_decor_logo.xml`).
* **Mumbai Prime Hubs**: Specifically designed for Mumbai clients across **Worli Sea Face, Bandra West (Pali Hill), Juhu Tara Road, Powai, BKC, and South Mumbai (Marine Drive / Colaba)**.

### 3. 📐 Signature Project Portfolio with Transparent Pricing
Every project showcases clear specifications and estimated turnkey costs:
* **The Celestia Sea-Facing Duplex** (Worli Sea Face) — 3,400 sq.ft | ₹52L - ₹68L (₹1,850/sq.ft) | 90 Days
* **The Minimalist Sanctuary Penthouse** (Bandra West) — 2,600 sq.ft | ₹38L - ₹48L (₹1,650/sq.ft) | 75 Days
* **Skyline Horizon 3BHK Residence** (Powai) — 1,650 sq.ft | ₹22L - ₹29L (₹1,450/sq.ft) | 55 Days
* **The Prestige Corporate Suite** (BKC) — 4,200 sq.ft | ₹65L - ₹85L (₹1,750/sq.ft) | 60 Days
* **Heritage Art-Deco Revival** (Marine Drive) — 2,100 sq.ft | ₹34L - ₹44L (₹1,700/sq.ft) | 70 Days
* **The Coastal Villa Estate** (Juhu Tara Road) — 4,800 sq.ft | ₹75L - ₹1.1 Cr (₹1,950/sq.ft) | 120 Days

### 4. 📝 Interactive Need-Fulfillment Requirement Form ("Form fill karke need puri kare")
When a client likes a design or needs their space designed:
1. Tap **"Inquire Now"** or **"Book Consultation"**.
2. Pre-fills the selected project and area.
3. Form captures:
   - Full Name & Phone/WhatsApp number
   - Mumbai Locality
   - Property Configuration (1BHK, 2BHK, 3BHK, Villa, Office, Penthouse)
   - Carpet Area (sq.ft) & Budget Slider (₹10L to ₹1.5 Cr+)
   - Specific needs (modular kitchen, acoustic ceilings, walk-in closets, custom lighting)
4. Saves safely into the user's private SQLite data store.
5. Option to directly send formatted message via WhatsApp to Age Décor's Mumbai design team.

### 5. ⚡ Mumbai Turnkey Cost Estimator
Interactive calculator allowing users to enter carpet area and choose finish grades (Essential Luxury, Signature Luxury, Haute Architecture) to receive instant budget and timeline estimates.

---

## 📁 Project Structure

```
AgeDecorInteriorApp/
├── build.gradle                              # Root Gradle build config
├── settings.gradle                           # Project settings
├── gradle.properties                         # AndroidX & JVM tuning
├── preview/
│   └── index.html                            # Interactive Android Device Simulator (Runs in browser!)
└── app/
    ├── build.gradle                          # App module dependencies (Material3, Gson)
    ├── proguard-rules.pro                    # Obfuscation rules
    └── src/
        └── main/
            ├── AndroidManifest.xml           # Manifest declaring activities & theme
            ├── java/com/agedecor/interiordesign/
            │   ├── MainActivity.java         # Project catalog, filter chips, navigation
            │   ├── ProjectDetailActivity.java# Specifications, finishes, consultation CTA
            │   ├── InquiryFormActivity.java  # Requirement collection & WhatsApp link
            │   ├── UserDataControlActivity.java# Privacy hub: view, delete, export, wipe data
            │   ├── CostEstimatorActivity.java# Interactive rate calculator
            │   ├── adapter/
            │   │   ├── ProjectAdapter.java   # Card binding & favorite toggle
            │   │   └── InquiryAdapter.java   # Inquiry list with granular deletion
            │   └── data/
            │       ├── AppDatabaseHelper.java# SQLite local database with data export & wipe
            │       ├── ProjectModel.java     # Project entity
            │       ├── InquiryModel.java     # Consultation request entity
            │       ├── ProjectRepository.java# Mumbai project catalog
            │       └── UserPrivacyPreferences.java# Preference toggles
            └── res/
                ├── drawable/
                │   ├── ic_age_decor_logo.xml # Luxury geometric monogram vector logo
                │   ├── bg_gold_button.xml    # Gold gradient button
                │   ├── bg_card_luxury.xml    # Card border background
                │   ├── bg_privacy_pill.xml   # Privacy badge pill
                │   ├── ic_shield_check.xml   # Security icon
                │   ├── ic_mumbai_pin.xml     # Location pin
                │   └── ic_favorite_heart.xml # Wishlist icon
                ├── layout/
                │   ├── activity_main.xml
                │   ├── activity_project_detail.xml
                │   ├── activity_inquiry_form.xml
                │   ├── activity_user_data_control.xml
                │   ├── activity_cost_estimator.xml
                │   ├── item_project_card.xml
                │   └── item_inquiry_card.xml
                └── values/
                    ├── colors.xml            # Luxury Champagne & Obsidian palette
                    ├── strings.xml           # Mumbai brand strings
                    └── themes.xml            # Material3 Dark Theme
```

---

## 🚀 How to Run and Test

### Option 1: Instant Interactive Browser Preview
Open the companion Android simulator in your web browser:
```bash
xdg-open /home/rudy/Downloads/AgeDecorInteriorApp/preview/index.html
```
Or start a local web server:
```bash
cd /home/rudy/Downloads/AgeDecorInteriorApp/preview
python3 -m http.server 8080
```
Then visit `http://localhost:8080` to experience the exact Android app with working forms, SQLite/localStorage data control, project filters, and cost estimation!

### Option 2: Open in Android Studio
1. Open **Android Studio**.
2. Select **Open an Existing Project** and choose `/home/rudy/Downloads/AgeDecorInteriorApp`.
3. Let Gradle sync dependencies.
4. Run on an Android Emulator or connected physical Android device.
