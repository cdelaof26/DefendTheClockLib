# Defend The Clock

![Level selector v0. 3. 0](https://github.com/cdelaof26/DefendTheClockLib/blob/main/images/levelSelector_v0.3.0.png?raw=true)

![In game v0. 3. 0](https://github.com/cdelaof26/DefendTheClockLib/blob/main/images/inGame_v0.3.0.png?raw=true)

### What is this?

This is a basic open source minigame with in-built world creator.

Put turrets to kill the all monsters on their way to the clock, survive 
different enemy legions and upgrade your defences!

### Dependencies 

1. Oracle Java/OpenJDK 8 or newer
   - Compilation requieres the JDK
2. libBasicUI

### Running project

Clone this repo

<pre>
$ git clone https://github.com/cdelaof26/DefendTheClockLib.git
</pre>

Move into project directory

<pre>
$ cd DefendTheClockLib
</pre>

Compile and run

<pre>
$ javac -classpath . defendtheclocklib/DefendTheClockLib.java
$ java defendtheclocklib/DefendTheClockLib
</pre>


### Changelog

### v0.4.0
- Fixed an issue where creating a new world would crash the build system
- Improved world resizing
- Disabled antialiasing when rendering the world (this might be added as setting),
  as now block might look worse but there isn't a space in between
- Added `BasicTurret` as first in-game weapon!
  - Added a menu to purchase turrets
- Fixed an issue where layer field in build panel won't update if the block was sent 
  to front or back
- CubeMonsters are rendered as images in an attempt to improve the CPU usage
- Changed "Level" for "Round" in `GameUI`
- `Restart` option is now functional
- Changed turrets damage and improved difficulty level-ups

### v0.3.1
- Renaming field in `WorldPropertiesPanel` now renames the file if exist
- Added a minimum requirement of five path blocks to load a map for a
  `gamemode != GameModes.CONSTRUCTION`
- Moved Clock health bar rendering to `EnemyRenderComponent`
- Added `renderFromImage` property to `World` in an attempt to improve the performance
- _Fixed performance for Windows_
- Fixed cube monster concurrence, monsters will appear at the same distance regardless 
  of the performance or the game state (paused/unpaused)
- Fixed blocks not appearing when added to an empty world
- Fixed crash when reading an empty world

### v0.3.0
- Added three maps: Meadow Island, Desert Island and Remote Island
- Added option to display the route that monsters will follow in `WorldPropertiesPanel`
- Added a layer selector in `ArrangeBlockPanel`
- Added delete button to `WorldPropertiesPanel`
- In an attempt to improve the CPU usage:
  - Added painting by zones to `EnemyRenderComponent`
  - Invisible blocks will be removed for a `gamemode != GameModes.CONSTRUCTION`
- Added support for rendering multiple monsters
- Added smoothness to monsters' movement
- Added shadow to cube monsters
- Added health bar to clock and cube monsters
- Added level label
- Added `StarsMenu` a panel to exchange stars for golden dollars
- Fixed `BlockDragger` not loading the properties of a block in certain cases

### v0.2.0
- Added a very early game ui for easy and other modes
- Added error messages when entering to a world
- Added Clock, EnemyPath, EnemySpawn, Dirt and Sand blocks
- Added EyeMonster and FaceMonster
- Created mechanism to detect the path to the clock from the spawn
- Changed move forward and move backward button's behavior, holding them will 
  accelerate the movement
- Fixed a bug where `null` will be appended to `blocks` array when a block was deleted 
  and its z-index was changed
- Fixed save world button's text changing back to the count-down when saved

### v0.1.0
- Initial project
- This project is a rework of the original DefendTheClock
  - Contains almost the same as the older, except that this version as is,
    it's much more optimized and refined with a much better UI
