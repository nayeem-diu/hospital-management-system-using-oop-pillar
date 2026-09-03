<h1 id="hospital-management-system-hms">Hospital Management System (HMS)</h1>
<p>Welcome to the <strong>Hospital Management System (HMS)</strong>! This user-friendly desktop application is designed to easily manage daily hospital operations such as keeping track of doctors, admitting patients, managing hospital beds, booking blood bags, and calculating bills automatically.</p>
<p>It offers two simple interfaces:</p>
<ol>
<li><strong>Graphical User Interface (GUI):</strong> A modern visual screen with buttons, forms, and tables (recommended for most users).</li>
<li><strong>Command Line Interface (CLI):</strong> A text-based menu for terminal users.</li>
</ol>
<hr>
<h2 id="-key-features">🌟 Key Features</h2>
<ul>
<li>🩺 <strong>Doctor Management:</strong> Add new doctors with automatic ID generation (<code>DOC-001</code>, <code>DOC-002</code>), remove doctors, and view all doctors in a neat table.</li>
<li>🩺 <strong>Patient Management:</strong> Register new patients, assign beds, update patient information, release/discharge patients when they recover, and keep track of their details.</li>
<li>🛏️ <strong>Bed Tracking System:</strong> Real-time visual tracking of 50 hospital beds. Empty beds appear in green, while occupied beds turn red.</li>
<li>🩸 <strong>Blood Bank &amp; Stock Alerts:</strong> Tracks 8 blood types (<code>A+</code>, <code>A-</code>, <code>B+</code>, <code>B-</code>, <code>AB+</code>, <code>AB-</code>, <code>O+</code>, <code>O-</code>). Automatically alerts you if any blood type falls to 2 bags or fewer.</li>
<li>💳 <strong>Automated Billing System:</strong>
<ul>
<li>Bed Charge: <strong>3,500 TK / day</strong></li>
<li>Blood Bag Charge: <strong>1,000 TK / bag</strong></li>
<li><strong>Special Discount:</strong> Automatically applies a <strong>10% discount</strong> if the total bill exceeds <strong>30,000 TK</strong>.</li>
</ul>
</li>
</ul>
<hr>
<h2 id="-what-needs-to-be-installed-first-prerequisites">📋 What Needs to Be Installed First? (Prerequisites)</h2>
<p>Before running this project, your computer needs to have <strong>Java</strong> installed. Java is the foundation required to run software written in the Java language.</p>
<h3 id="step-1-check-if-java-is-already-installed">Step 1: Check if Java is Already Installed</h3>
<ol>
<li>Open your terminal or command prompt:
<ul>
<li><strong>Windows:</strong> Press <code>Win + R</code>, type <code>cmd</code>, and press <strong>Enter</strong>.</li>
<li><strong>Mac:</strong> Press <code>Cmd + Space</code>, type <code>Terminal</code>, and press <strong>Enter</strong>.</li>
</ul>
</li>
<li>Type the following command and press <strong>Enter</strong>:
<pre><div style="color: rgb(171, 178, 191); text-shadow: rgba(0, 0, 0, 0.3) 0px 1px;"><span>java -version</span></div></pre>
</li>
<li><strong>What happens next?</strong>
<ul>
<li><strong>If you see text showing a version number (e.g., <code>java version "17.0.2"</code> or <code>openjdk version 11...</code>):</strong> Java is already installed! You are ready to go to the running section below.</li>
<li><strong>If you see an error like "command not found" or "not recognized":</strong> Java is NOT installed yet. Follow Step 2 below.</li>
</ul>
</li>
</ol>
<hr>
<h2 id="-how-to-install-java-if-you-do-not-have-it">📥 How to Install Java (If You Do Not Have It)</h2>
<p>Installing Java is quick and completely free:</p>
<h3 id="option-a-standard-installation-recommended-for-beginners">Option A: Standard Installation (Recommended for Beginners)</h3>
<ol>
<li>Visit the official Amazon Corretto (Java JDK) website:<br>
👉 <a href="https://corretto.aws/downloads/latest/amazon-corretto-17-x64-windows-jdk.msi">https://corretto.aws/downloads/latest/amazon-corretto-17-x64-windows-jdk.msi</a> <em>(for 64-bit Windows)</em></li>
<li>Download the installer file.</li>
<li>Open the downloaded installer and click <strong>Next -&gt; Next -&gt; Install</strong> (keep default settings).</li>
<li>Once completed, close and reopen your Terminal / Command Prompt and test again with <code>java -version</code>.</li>
</ol>
<h3 id="option-b-automatic-java-setup-for-mac--linux-users">Option B: Automatic Java Setup (For Mac / Linux Users)</h3>
<ul>
<li><strong>Mac (using Homebrew):</strong> Open Terminal and type:
<pre><div style="color: rgb(171, 178, 191); text-shadow: rgba(0, 0, 0, 0.3) 0px 1px;"><span>brew install openjdk</span></div></pre>
</li>
<li><strong>Ubuntu / Debian Linux:</strong> Open Terminal and type:
<pre><div style="color: rgb(171, 178, 191); text-shadow: rgba(0, 0, 0, 0.3) 0px 1px;"><span>sudo apt update &amp;&amp; sudo apt install default-jdk</span></div></pre>
</li>
</ul>
<hr>
<h2 id="-how-to-run-the-application-step-by-step-guide">🚀 How to Run the Application (Step-by-Step Guide)</h2>
<p>You do <strong>NOT</strong> need advanced coding skills or complex programming software (like IntelliJ IDEA or Eclipse) to run this application! Follow these simple steps:</p>
<h3 id="step-1-download-the-project-files">Step 1: Download the Project Files</h3>
<p>Place all project files (<code>.java</code> files) in a single folder on your computer (e.g., inside a folder named <code>HospitalApp</code> on your Desktop).</p>
<h3 id="step-2-open-terminal--command-prompt-in-the-project-folder">Step 2: Open Terminal / Command Prompt in the Project Folder</h3>
<ul>
<li><strong>On Windows:</strong>
<ol>
<li>Open the folder containing the project files.</li>
<li>Click on the address bar at the top of the folder window.</li>
<li>Type <code>cmd</code> and press <strong>Enter</strong>. (This opens Command Prompt directly inside that folder).</li>
</ol>
</li>
<li><strong>On Mac:</strong>
<ol>
<li>Right-click on the folder where your files are saved.</li>
<li>Select <strong>"New Terminal at Folder"</strong>.</li>
</ol>
</li>
</ul>
<h3 id="step-3-compile-the-files">Step 3: Compile the Files</h3>
<p>In your Command Prompt / Terminal window, type the following command and press <strong>Enter</strong>:</p>
<pre><div style="color: rgb(171, 178, 191); text-shadow: rgba(0, 0, 0, 0.3) 0px 1px;"><span>javac *.java</span></div></pre>
<p><em>(This translates the Java code into runnable computer instructions. It will take 2-3 seconds and create <code>.class</code> files in your folder).</em></p>
<hr>
<h3 id="step-4-launch-the-application">Step 4: Launch the Application!</h3>
<p>Choose how you want to interact with the application:</p>
<h4 id="️-option-1-launch-graphical-interface-visual-window---recommended">🖥️ Option 1: Launch Graphical Interface (Visual Window) - RECOMMENDED</h4>
<p>Type the following command and press <strong>Enter</strong>:</p>
<pre><div style="color: rgb(171, 178, 191); text-shadow: rgba(0, 0, 0, 0.3) 0px 1px;"><span>java HospitalUI</span></div></pre>
<p>A modern application window will open on your screen with buttons, forms, dashboard statistics, and tables!</p>
<h4 id="-option-2-launch-command-line-interface-text-menu">📟 Option 2: Launch Command Line Interface (Text Menu)</h4>
<p>Type the following command and press <strong>Enter</strong>:</p>
<pre><div style="color: rgb(171, 178, 191); text-shadow: rgba(0, 0, 0, 0.3) 0px 1px;"><span>java Main</span></div></pre>
<p>This launches a text menu inside your terminal where you can type numbers (1 to 11) to perform hospital tasks.</p>
<hr>
<h2 id="-project-structure-overview">📂 Project Structure Overview</h2>
<pre><div style="color: rgb(171, 178, 191); text-shadow: rgba(0, 0, 0, 0.3) 0px 1px;"><span>.</span></div><div style="color: rgb(171, 178, 191); text-shadow: rgba(0, 0, 0, 0.3) 0px 1px;"><span>├── Person.java        # Base class containing common details (ID, Name, Age, Gender)</span></div><div style="color: rgb(171, 178, 191); text-shadow: rgba(0, 0, 0, 0.3) 0px 1px;"><span>├── Doctor.java        # Handles doctor records &amp; specialization</span></div><div style="color: rgb(171, 178, 191); text-shadow: rgba(0, 0, 0, 0.3) 0px 1px;"><span>├── Patient.java       # Handles patient records &amp; bed assignment status</span></div><div style="color: rgb(171, 178, 191); text-shadow: rgba(0, 0, 0, 0.3) 0px 1px;"><span>├── Bed.java           # Manages 50 hospital beds &amp; thread-safe allocation</span></div><div style="color: rgb(171, 178, 191); text-shadow: rgba(0, 0, 0, 0.3) 0px 1px;"><span>├── BloodBank.java     # Manages 8 blood types &amp; low stock alert logic</span></div><div style="color: rgb(171, 178, 191); text-shadow: rgba(0, 0, 0, 0.3) 0px 1px;"><span>├── Billing.java       # Calculates daily rates, discounts, and total charges</span></div><div style="color: rgb(171, 178, 191); text-shadow: rgba(0, 0, 0, 0.3) 0px 1px;"><span>├── Hospital.java      # Central controller connecting all modules together</span></div><div style="color: rgb(171, 178, 191); text-shadow: rgba(0, 0, 0, 0.3) 0px 1px;"><span>├── HospitalUI.java    # Modern Java Swing Graphical User Interface (GUI)</span></div><div style="color: rgb(171, 178, 191); text-shadow: rgba(0, 0, 0, 0.3) 0px 1px;"><span>└── Main.java          # Interactive Command Line Interface (CLI)</span></div></pre>
<hr>
<h2 id="️-troubleshooting--frequently-asked-questions">🛠️ Troubleshooting &amp; Frequently Asked Questions</h2>
<ul>
<li>
<p><strong>Q: I get the error <code>javac is not recognized as an internal or external command</code>.</strong></p>
<ul>
<li><strong>Solution:</strong> Java JDK is not installed or not added to your system PATH. Follow the <strong>"How to Install Java"</strong> section above using the Amazon Corretto installer, then restart your Command Prompt.</li>
</ul>
</li>
<li>
<p><strong>Q: Do I need to re-compile (<code>javac *.java</code>) every time I want to run the program?</strong></p>
<ul>
<li><strong>Solution:</strong> No! You only need to run <code>javac *.java</code> once. Afterwards, you can launch the app anytime directly using <code>java HospitalUI</code>.</li>
</ul>
</li>
<li>
<p><strong>Q: Will closing the program save my added doctors and patients permanently?</strong></p>
<ul>
<li><strong>Solution:</strong> Currently, data is stored in temporary memory while the app is running. Closing the application will reset the data back to its clean default state.</li>
</ul>
</li>
</ul>
<hr>
<h2 id="-author-info">👨‍💻 Author Info</h2>
<ul>
<li><strong>Developer:</strong> Ishtiaque Ahmed</li>
<li><strong>Built With:</strong> Java Standard Edition (JDK 11+)</li>
<li><strong>UI Toolkit:</strong> Java Swing Framework</li>
</ul>
