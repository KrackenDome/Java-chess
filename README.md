# Java-chess
Singleton Pattern
The Singleton pattern is employed in the Chess class. This creational design pattern restricts the instantiation of the class, ensuring that only one instance exists within the Java Virtual Machine. The Chess class achieves this by having a private constructor and implementing a static method, getInstance(), which returns the single instance of the class.
Composite Pattern
The Composite pattern is utilized in the Board class, representing a structural design pattern. The Board class establishes a part-whole hierarchy of objects that share a common interface. It implements the Drawable interface and maintains a list of Square objects, each also implementing the Drawable interface. The Board class effectively delegates the draw() method to each Square object in its list.
Observer Pattern
The behavioral design pattern employed is the Observer pattern, implemented in the Game class. This pattern extends the Observable class and manages a list of Observer objects, including Board, Player, and GameStatus. The Game class notifies these observers whenever there is a change in the game state, such as when a move is made or when the game concludes. This pattern enhances the coordination and communication between different components within the chess game.
