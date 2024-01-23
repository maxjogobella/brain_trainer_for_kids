# brain_trainer_for_kids
Kotlin/MVVM/SharedPreferences. Solve simple and interesting math problems, choose the right answer and see how your brain improves.

I had to learn about SharedPreferences (persistent data storage). Here I had to store the user’s maximum score in the game. When the application was restarted, the data was saved. I also decided to make onboarding for the application, to introduce the new user. Accordingly, I applied the theory, and the onboarding will not be shown on the second use.

For onboarding, I used fragments, which are much better than activities in this case.

A primitive children’s game with three levels of difficulty: easy, medium, hard. Accordingly, whichever user chooses the level, a question will be generated, which will be presented from ViewModel (separate business logic).

If the user chooses the wrong option three times, the statistics menu will open.

JUnit tests are not written, because the functionality is very simple. The application is available in Russian and English. When the screen is rotated, the application will work correctly. (ScrollView, ViewModelProvider)
