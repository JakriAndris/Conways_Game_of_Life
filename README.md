# Game of Life - Conway's Cellular Automaton

This project implements Conway's Game of Life in Java. It is a simulation of a cellular automaton where 
each cell can be either "alive" or "dead", and the state of the cells evolves based on a set of rules.
The simulation is displayed in a graphical window where users can interact with the grid, set up 
initial conditions, and control the simulation.

## Features

- **Start/Stop Simulation**: Control the simulation with a start/stop toggle.
- **Clear/Randomize Grid**: Clear the grid or randomly populate the cells.
- **Save/Load State**: Save the current grid to a file and load previously saved states.
- **Adjust Settings**: Customize the time between generations, grid size, and the birth/survival rules.
- **Interactive Grid**: Click and drag to toggle the state of individual cells.

## Requirements

- **Java 8 or later**: This project uses Java's Swing library for the graphical user interface.

## Installation

To run this project locally:

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/game-of-life.git
   cd game-of-life

2. Compile and run the project using your preferred IDE or from the command line:
   ```bash
   javac -d . src/game_of_life/*.java
   java game_of_life.Main

   ## Usage

Once the program is running, you will see a graphical window with the following controls:

- **Start/Stop**: Begin or halt the simulation.
- **Clear/Randomize Grid**: Clear the grid or randomize it.
- **Set Generation Time**: Define how fast the generations evolve (in milliseconds).
- **Set Grid Size**: Choose between various predefined grid sizes.
- **Set Rules**: Customize the birth and survival rules for the game.
- **Save/Load State**: Save the current board to a file or load a previously saved state.

### Interactions

- Click on the grid to toggle the state of cells.
- Click and drag to change the state of multiple cells at once.

## Classes Overview

- **Main**: The entry point for the application. Initializes and displays the main game window.
- **LifeFrame**: The main window of the game. Contains the game panel, status bar, and menu bar.
- **LifePanel**: Responsible for rendering the grid and handling user interactions.
- **GameFunctions**: Contains the logic for managing the game state, including starting/stopping the simulation, saving/loading the state, and adjusting settings.
- **GameMenu**: Creates the menu bar with options to control the game.

## Customization

### Rules

The game uses the following default birth and survival rules:

- **Birth**: A dead cell becomes alive if it has exactly 3 neighbors.
- **Survival**: A live cell stays alive if it has 2 or 3 neighbors.

You can customize these rules via the menu options.

### Grid Sizes

The game allows different grid sizes, from "Extra Small" to "Extra Large." The grid size will determine the resolution of the simulation.
