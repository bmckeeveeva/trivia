My approach:

* Fixed IDE warnings - commit
* Broke dependency on System.out with PrintWriter- commit
* Get 100% test coverage. - commit
* All of this "if in penalty box, or getting out of the penalty box, or otherwise" logic made me think of the State design pattern.  I made the Actions interface with "roll", "wrong" and "correct" methods.  In order to have them not change the game state I instead added other methods like "shouldAskQuestion" and the other "should" methods, to have the state tell the game what to do.
* The state transitions I encapsulated in the Changer class.  This class was originally an inner non-static class on Game.
* The state changes had some printing associated with it too, and that printing included values the Action objects shouldn't otherwise have like System.out.println(players.get(currentPlayer) + " is getting out of the penalty box"); so I opted to make the Printer class and have it accept a template with token-replacement so the Actions implementations could express what should be printed without knowing the values. This class was originally an inner non-static class on Game.
* After lots of manual changes, way more than recommended - commit!
* Having all the Arrays for place, purse, and names made me think a Player concept could be added which would have those three fields.  I also had an Array of Actions at this point so I added that to Player.  I always wanted the latest Actions object for the player to get used, which I actually got wrong once and had some tests failing.  To make sure that couldn't happen I made Player implement Actions and have those Actions methods just delegate to its internal Actions object.  This way the latest Player's Action was always used. - commit
* I still had Changer and Printer non-static classes on Game so I moved those out and made them depend on Suppliers to get the latest info like Player or category. - commit
* The organization around questions was still super message so I made a QuestionBank concept.  It doesn't make any assumptions about how many categories there might be.  It has a static method which populates it with the original content, but I figured this would be something that consumers of this library might want to control, so I made the class public and injectable in the constructor. - commit
