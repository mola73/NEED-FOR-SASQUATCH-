Here’s a cleaned-up, professional, and engaging version of your **README file** for the *Need for Sasquatch* game project. It keeps all the important details but improves clarity, structure, and flow for a GitHub audience:

---

# 🚗 Need for Sasquatch  
**A 2D Mobile Racing Game | Java & Android Studio**  
**Team Sasquatch**  
**Contributors:** Mateus Abreu, David Stuart, Muhammad Olaniyan  

---

## 📌 Introduction

*Need for Sasquatch* is a 2D mobile racing game designed for casual players who enjoy fast-paced, easy-to-pick-up gameplay. Built in Java using Android Studio, the game features **three unique game modes**, each offering a different objective and challenge to keep gameplay fresh and engaging:

- **Dash Mode:** An endless runner where the player avoids oncoming cars and road hazards while the vehicle’s speed increases over time. The game ends upon any collision.
- **Destruction Mode:** A chaotic mode where the goal is to hit as many cars as possible before time runs out. Power-ups occasionally extend the timer to keep the game going.
- **Runaway Mode:** The player races toward a final checkpoint while avoiding traps and police vehicles. With limited lives, the player must reach all checkpoints to win.

Each mode includes a **mode-specific power-up**, such as temporary invincibility, timer boosts, or speed bursts, enhancing gameplay dynamics. A scoring system was also planned to rank player performance.

---

## ⚙️ Development Methodology

The project began with a **Plan-Driven** approach, including detailed design documentation outlining classes, variables, and game flow. Once development began, we transitioned into **Agile Incremental Development** for flexibility, allowing us to incorporate feedback quickly and iterate based on testing.

Given the iterative nature of game development, we also applied **Test-Driven Development (TDD)**. Each feature was tested as it was built to ensure compatibility and functionality, especially since visual and gameplay feedback was critical after every build.

### 💻 Tools & Technologies:
- **Language:** Java  
- **IDE:** Android Studio  
- **Graphics:** Krita, Ibis Paint  
- **Version Control:** Git & GitHub  

---

## 👥 Team Roles

- **Muhammad Olaniyan** – *Team Lead & Developer*  
  - Implemented Runaway Mode, managed version control and project structure, introduced notifications, and maintained clean code organization.

- **David Stuart** – *Back-End & Sound Designer*  
  - Developed Dash Mode and the main menu, sourced soundtracks, and assisted in debugging across all modes.

- **Mateus Abreu** – *Lead Designer & Developer*  
  - Designed visual assets (cars, roads, obstacles), authored documentation, and led development of Destruction Mode.

While each member had a primary focus, collaboration increased as the project progressed, particularly in debugging and polishing features.

---

## ✅ Final Progress

### 🏁 Dash Mode
Simplified from its original multi-lane idea, this mode now includes oncoming cars and holes. The increasing speed adds difficulty while maintaining gameplay clarity.

### 💥 Destruction Mode
Now features a kill counter — the player must hit 15 fast-moving cars within one minute. A power-up adds 10 seconds if the player is close to running out of time.

### 🚓 Runaway Mode
The player must outrun the police, avoid obstacles, and pass 10 checkpoints. Speed increases after each checkpoint, with a nitro boost available to assist. One collision ends the game.

---

## 🗓️ Work Schedule Overview

**September – October**
- Finalized proposal and design documentation  
- Began Android Studio integration and basic GUI  
- Developed Dash Mode and core functionality  
- Initial testing and progress reporting  

**November**
- Built Destruction and Runaway Modes  
- Implemented power-ups and scoring  
- Focused on final polish and debugging  
- Final presentation and documentation prep  

Due to time constraints, a few features like siren lights in Runaway Mode had to be excluded.

---

## 🎯 Conclusion

*Need for Sasquatch* successfully delivers a multi-mode racing experience with distinct mechanics for each mode. Through the use of Android Studio and GitHub, the team maintained a strong development workflow rooted in **plan-driven structure**, **iterative delivery**, and **test-driven adjustments**. 

The project showcases strong collaboration, adaptability, and problem-solving. The result is a polished, engaging mobile game that highlights each member’s contributions in both technical and creative areas.

---

## 📲 How to Run the Game

1. **Install Android Studio.**  
2. **Create a virtual device** using a HAXM-enabled emulator.  
3. **If emulator fails**, connect a real Android device via WiFi or USB:
   - Enable *Developer Options*  
   - Activate *USB/Wireless Debugging*  
   - Connect to Android Studio  
4. **Run `MainActivity.java`** to launch the game.

