# 🎨 Java ASCII Art Generator
This project is an ASCII art generator developed by [**Noam Kimhi**](https://github.com/noam-kimhi) and [**Or Forshmit**](https://github.com/OrF8) as part of the course [**67125 - Introduction to Object Oriented Programming**](https://shnaton.huji.ac.il/index.php/NewSyl/67125/2/2025/) at [**The Hebrew University of Jerusalem**](https://en.huji.ac.il/). \
The application converts images into ASCII art representations using Java. 
> 🎓 Final Grade: **100**

## 🧩 Features
- **Image to ASCII Conversion**: Transforms images into ASCII art using grayscale mapping.
- **Customizable Character Sets**: Allows selection of different character sets for varying levels of detail.
- **Scalable Output**: Adjusts the size of the output ASCII art based on user preferences.
- **Modular Design**: Structured codebase for easy maintenance and extension.

## 🚀 Getting Started
### Running locally
#### Prerequisites
- Java Development Kit (JDK) 17 or higher.
- An Integrated Development Environment (IDE) such as IntelliJ IDEA or Eclipse (Optional).
#### Installation
1. Clone the repository:
   ````
   git clone https://github.com/OrF8/Java-ASCII-Art.git
   cd Java-ASCII-Art
   ````
2. If using an IDE - Import the project into your IDE:
   - For IntelliJ IDEA:
     - Open IntelliJ IDEA.
     - Select "Open" and choose the `Java-ASCII-Art` directory.
     - IntelliJ will detect the project structure and set it up accordingly.
   - For Eclipse:
     - Open Eclipse.
     - Select "File" > "Import" > "Existing Projects into Workspace".
     - Choose the `Java-ASCII-Art` directory and click "Finish".
3. Build and run the project:
   - Compile and run the `src/ascii_art/Shell.java` class with the path to your image to start the application:
     ````bash
     java -cp bin ascii_art.Shell <path_to_your_image>
     ````

### 📦 Using GitHub Codespaces or Dev Containers
This project supports [**GitHub Codespaces**](https://github.com/features/codespaces) and [**VS Code Dev Containers**](https://code.visualstudio.com/docs/devcontainers/containers).  
A preconfigured development environment is available via the `.devcontainer` folder.

To get started:

- If you're using **Codespaces**, click the **"Code"** button on the repository and choose **"Create codespace on main"**.
- If you're using **VS Code with Dev Containers** locally:
    1. Install the [Dev Containers extension](https://marketplace.visualstudio.com/items?itemName=ms-vscode-remote.remote-containers).
    2. Open the project in VS Code.
    3. Run `Dev Containers: Reopen in Container` from the command palette.

This will compile the project files into `bin` directory and allow you to run the application directly from the container.
  
## 🖼️ Usage
1. Place the image you want to convert into a known directory.
2. Run the application and provide the path to the image file as a program argument.
3. The application will process the image and output the ASCII art representation to the console or save it to a file, depending on your desires.

# 📁 Project Structure
````
Java-ASCII-Art/
├── src/                    # Source code directory
│   └── ...                 # Java packages and classes
├── UML.pdf                 # UML diagram of the project
├── README.md               # Project documentation
└── .gitattributes          # Git attributes configuration
````

# 📄 License
This project is licensed under the MIT License – see the [**LICENSE**](https://github.com/OrF8/Java-ASCII-Art/blob/main/LICENSE) file for details.














