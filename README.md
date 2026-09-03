# Hospital Management System (HMS)

Welcome to the **Hospital Management System (HMS)**! This user-friendly desktop application is designed to easily manage daily hospital operations such as keeping track of doctors, admitting patients, managing hospital beds, booking blood bags, and calculating bills automatically.

It offers two simple interfaces:
1. **Graphical User Interface (GUI):** A modern visual screen with buttons, forms, and tables (recommended for most users).
2. **Command Line Interface (CLI):** A text-based menu for terminal users.

---

## 🌟 Key Features

* 🩺 **Doctor Management:** Add new doctors with automatic ID generation (`DOC-001`, `DOC-002`), remove doctors, and view all doctors in a neat table.
* 🩺 **Patient Management:** Register new patients, assign beds, update patient information, release/discharge patients when they recover, and keep track of their details.
* 🛏️ **Bed Tracking System:** Real-time visual tracking of 50 hospital beds. Empty beds appear in green, while occupied beds turn red.
* 🩸 **Blood Bank & Stock Alerts:** Tracks 8 blood types (`A+`, `A-`, `B+`, `B-`, `AB+`, `AB-`, `O+`, `O-`). Automatically alerts you if any blood type falls to 2 bags or fewer.
* 💳 **Automated Billing System:** 
  * Bed Charge: **3,500 TK / day**
  * Blood Bag Charge: **1,000 TK / bag**
  * **Special Discount:** Automatically applies a **10% discount** if the total bill exceeds **30,000 TK**.

---

## 📋 What Needs to Be Installed First? (Prerequisites)

Before running this project, your computer needs to have **Java** installed. Java is the foundation required to run software written in the Java language.

### Step 1: Check if Java is Already Installed

1. Open your terminal or command prompt:
   * **Windows:** Press `Win + R`, type `cmd`, and press **Enter**.
   * **Mac:** Press `Cmd + Space`, type `Terminal`, and press **Enter**.
2. Type the following command and press **Enter**:
   ```bash
   java -version
   ```
3. **What happens next?**
   * **If you see text showing a version number (e.g., `java version "17.0.2"` or `openjdk version 11...`):** Java is already installed! You are ready to go to the running section below.
   * **If you see an error like "command not found" or "not recognized":** Java is NOT installed yet. Follow Step 2 below.

---

## 📥 How to Install Java (If You Do Not Have It)

Installing Java is quick and completely free:

### Option A: Standard Installation (Recommended for Beginners)
1. Visit the official Amazon Corretto (Java JDK) website:  
   👉 [https://corretto.aws/downloads/latest/amazon-corretto-17-x64-windows-jdk.msi](https://corretto.aws/downloads/latest/amazon-corretto-17-x64-windows-jdk.msi) *(for 64-bit Windows)*
2. Download the installer file.
3. Open the downloaded installer and click **Next -> Next -> Install** (keep default settings).
4. Once completed, close and reopen your Terminal / Command Prompt and test again with `java -version`.

### Option B: Automatic Java Setup (For Mac / Linux Users)
* **Mac (using Homebrew):** Open Terminal and type:
  ```bash
  brew install openjdk
  ```
* **Ubuntu / Debian Linux:** Open Terminal and type:
  ```bash
  sudo apt update && sudo apt install default-jdk
  ```

---

## 🚀 How to Run the Application (Step-by-Step Guide)

You do **NOT** need advanced coding skills or complex programming software (like IntelliJ IDEA or Eclipse) to run this application! Follow these simple steps:

### Step 1: Download the Project Files
Place all project files (`.java` files) in a single folder on your computer (e.g., inside a folder named `HospitalApp` on your Desktop).

### Step 2: Open Terminal / Command Prompt in the Project Folder

* **On Windows:**
  1. Open the folder containing the project files.
  2. Click on the address bar at the top of the folder window.
  3. Type `cmd` and press **Enter**. (This opens Command Prompt directly inside that folder).
* **On Mac:**
  1. Right-click on the folder where your files are saved.
  2. Select **"New Terminal at Folder"**.

### Step 3: Compile the Files
In your Command Prompt / Terminal window, type the following command and press **Enter**:
```bash
javac *.java
```
*(This translates the Java code into runnable computer instructions. It will take 2-3 seconds and create `.class` files in your folder).*

---

### Step 4: Launch the Application!

Choose how you want to interact with the application:

#### 🖥️ Option 1: Launch Graphical Interface (Visual Window) - RECOMMENDED
Type the following command and press **Enter**:
```bash
java HospitalUI
```
A modern application window will open on your screen with buttons, forms, dashboard statistics, and tables!

#### 📟 Option 2: Launch Command Line Interface (Text Menu)
Type the following command and press **Enter**:
```bash
java Main
```
This launches a text menu inside your terminal where you can type numbers (1 to 11) to perform hospital tasks.

---

## 📂 Project Structure Overview

```text
.
├── Person.java        # Base class containing common details (ID, Name, Age, Gender)
├── Doctor.java        # Handles doctor records & specialization
├── Patient.java       # Handles patient records & bed assignment status
├── Bed.java           # Manages 50 hospital beds & thread-safe allocation
├── BloodBank.java     # Manages 8 blood types & low stock alert logic
├── Billing.java       # Calculates daily rates, discounts, and total charges
├── Hospital.java      # Central controller connecting all modules together
├── HospitalUI.java    # Modern Java Swing Graphical User Interface (GUI)
└── Main.java          # Interactive Command Line Interface (CLI)
```

---

## 🛠️ Troubleshooting & Frequently Asked Questions

* **Q: I get the error `javac is not recognized as an internal or external command`.**
  * **Solution:** Java JDK is not installed or not added to your system PATH. Follow the **"How to Install Java"** section above using the Amazon Corretto installer, then restart your Command Prompt.

* **Q: Do I need to re-compile (`javac *.java`) every time I want to run the program?**
  * **Solution:** No! You only need to run `javac *.java` once. Afterwards, you can launch the app anytime directly using `java HospitalUI`.

* **Q: Will closing the program save my added doctors and patients permanently?**
  * **Solution:** Currently, data is stored in temporary memory while the app is running. Closing the application will reset the data back to its clean default state.

---

## 👨‍💻 Author Info

* **Developer:** Ishtiaque Ahmed
* **Built With:** Java Standard Edition (JDK 11+)
* **UI Toolkit:** Java Swing Framework
