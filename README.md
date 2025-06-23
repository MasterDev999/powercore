# PowerCore Plugin

## Overview
PowerCore is a Minecraft plugin that introduces a lifesteal system, allowing players to gain and lose hearts based on their actions in the game. This plugin enhances the PvP experience by adding unique mechanics and commands.

## Features
- **Lifesteal System**: Players lose 1 heart upon death and can gain hearts by killing others.
- **Heart Item**: Craftable item that allows players to gain hearts.
- **Revive Beacon System**: OPs can revive banned players using commands.
- **Custom Crafting System**: Supports YAML-defined recipes for crafting items.
- **PvP Toggle System**: Server-wide toggle for PvP, with enforcement on damage events.

## Installation
1. Ensure you have a compatible version of Spigot or PaperMC installed on your server.
2. Download the latest version of the PowerCore plugin.
3. Place the `PowerCore.jar` file into the `plugins` folder of your server.
4. Restart the server to enable the plugin.

## Configuration
- The plugin uses a `config.yml` file located in the `src/main/resources` directory for custom recipes.
- Modify the `config.yml` to change recipe definitions as needed.

## Commands
- `/withdraw`: Convert 1 heart into a heart item.
- `/revive <player>`: Revive a banned player.
- `/recipe <key>`: Show the recipe shape and ingredients in chat.
- `/pvp`: Toggle PvP on or off server-wide.

## File Structure
```
PowerCore
├── build.gradle
├── settings.gradle
├── src
│   └── main
│       ├── java
│       │   └── me
│       │       └── powercore
│       │           ├── PowerCore.java
│       │           ├── commands
│       │           │   ├── PvpCommand.java
│       │           │   ├── RecipeCommand.java
│       │           │   ├── ReviveCommand.java
│       │           │   └── WithdrawCommand.java
│       │           ├── listeners
│       │           │   ├── LifestealListener.java
│       │           │   └── PlayerJoinListener.java
│       │           └── utils
│       │               └── RecipeManager.java
│       └── resources
│           ├── plugin.yml
│           └── config.yml
└── README.md
```

## Contribution
Feel free to contribute to the PowerCore plugin by submitting issues or pull requests on the GitHub repository.

## License
This project is licensed under the MIT License. See the LICENSE file for more details.