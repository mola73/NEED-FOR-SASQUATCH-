This project focuses on creating a 2D racing mobile game with casual but fun gameplay to capture the attention of casual gamers seeking simplistic, fast-paced action on their mobile devices. Three modes will focus on different goals and give the player various ways to play.
The first mode, Dash Mode, is an endless runner in which the player must avoid other oncoming cars and holes while his speed constantly increases. In this mode, the player loses if their car gets hit at any point.
The second mode, Destruction Mode, focuses on only causing as much damage as possible. Contrary to Dash Mode, the speed is constant, and the challenge is fighting against a timer that is constantly decreasing while you try to hit as many things as possible. The original idea was that hitting something would increase a timer, but missing objects would make the timer go down, and the player loses as soon as their timer hits zero. 
However, after some testing with another student outside the development team and some other challenges with implementing those functionalities caused by the chosen IDE, the mode gameplay was changed. The details about this change will be given further down the report.
The third mode, Runaway Mode, focuses on the player having to reach a final checkpoint while multiple traps are on their way. Like Destruction Mode, the speed here is also constant, and if the player gets hit by a trap three times, they lose the game, but if they reach the final destination, the game ends with their victory. Noticeably, this is the only mode with an actual end, while the other modes can keep going as long as the player is skilled enough.
Each mode also comes with a customized power-up designed particularly for them. Dash Mode’s power-up allows the player to become temporarily invincible, letting them avoid consequences for a short time as any collision is ignored. Destruction Mode power-up gives the player fifteen seconds on their timer if their timing falls too close to zero, with chances of getting the power-up decreasing every time after it appears. The power-up for Runaway Mode gives the player a speed boost to help them arrive quicker at their final destination and win the game.
Scores are also planned to be implemented, allowing for a ranking showing the player’s performance.

Methodology
Initially, the group chose to implement Agile Development, which mostly focused on the aspects of Incremental Development. However, the first step was closer to Plan Driven, as we focused on a design document specifying the classes, methods, and variables necessary for the game.
Regarding implementation, our methodology returned to Incremental Development because of its flexibility and the potential for feedback on work already done. Since our project is focused on gaming development, we need constant feedback after every program build to check for bugs and confirm that the functionalities are working accordingly.
Another aspect that made us change the methodology to Incremental is that actual implementation might differ from the original plan, so we need the flexibility offered by Incremental to adapt accordingly.
As we were developing the game with three different modes, each team member was assigned to focus on developing a specific mode. Elements of Test Driven Development were heavily used in this stage. At every step, when new functionality is added, the one responsible for that particular mode stops to test if it is working, how it is working in combination with the other elements, and if it is working as intended before implementing more functionalities. It also allowed the members to come up with new plans for different implementations of the modes after some reconsideration of the schedule and what was the best for the game.
For development, the tools used were Java, as the language our team is most familiar and comfortable with, Android Studio, an IDE with support for Android development and supports Java; Krita and Ibis Paint, art programs utilized for the graphical elements of the game, meaning the cars, the road, and other visual elements.
The group also used Github to share the modifications each would make to the code, keeping track of them through branches and ensuring it is possible to go back to previous versions in case code changes broke the project.


Total progress
	Each mode was successfully implemented and was made playable.

	Dash: The original idea for this mode was to have many obstacles in different lanes. However, as the game mode was being developed, it seemed hard and challenging to incorporate multiple obstacles in different lanes while making the game playable. Now, the game only has oncoming cars and holes to dodge, which makes the game playable, and as the speed of the main car increases, it becomes a good challenge for the player.

	Destruction mode: The model has changed from its original idea. Now, there is a counter that increases each time the player successfully hits a car, and to win, the player must manage to hit a total of 15 cars, all of which have much higher speeds. The timer was still implemented, and if the player failed to hit the goal in under a minute, they lost. However, if they need it, there is the possibility of a boost appearing to give the player ten more seconds. The boosts appear at a delay of 40000 milliseconds.

	Runaway mode: This mode focuses on running away from the police. A runaway vehicle has to maneuver through police vehicles and pass ten checkpoints. The player has only one life to pass all checkpoints. Moreover, the vehicle's speed will gradually increase as it passes the checkpoint, increasing difficulty. The player can receive a nitro boost to propel them to the next checkpoint. If the player crashes before passing all the checkpoints, they lose and achieve an approximate score. If the player passes all checkpoints, they win. 


To Run this game, you will need to : 
Download Android Studio.
Implement a virtual device to run the app, which requires a HAXM emulator.
If the virtual device does not work, you will need to connect an Android device via Wifi or USB
To connect the actual device:
Enable developer options
Activate wireless or USB debugging and connect via Android Studio.
5. Once setup is complete, please run the program via MainActivity.java
