# brain_trainer_for_kids
## Description
I had to learn about SharedPreferences (persistent data storage). Here I had to store the user’s maximum score in the game. When the application was restarted, the data was saved. I also decided to make onboarding for the application, to introduce the new user. Accordingly, I applied the theory, and the onboarding will not be shown on the second use.

For onboarding, I used fragments, which are much better than activities in this case.

A primitive children’s game with three levels of difficulty: easy, medium, hard. Accordingly, whichever user chooses the level, a question will be generated, which will be presented from ViewModel (separate business logic).

If the user chooses the wrong option three times, the statistics menu will open.

JUnit tests are not written, because the functionality is very simple. The application is available in Russian and English. When the screen is rotated, the application will work correctly. (ScrollView, ViewModelProvider)

## Stack
Kotlin/MVVM/Room/MocKK

## OnBoardin
<img src="https://github.com/maxnotmaximchik/brain_trainer_for_kids/assets/106059025/7067a5f9-4c10-4f3f-a677-563d689cd6bc" width="250" />
<img src="https://github.com/maxnotmaximchik/brain_trainer_for_kids/assets/106059025/1d6a39ce-0704-4bd5-a6f7-0ebe7948e23c" width="250" />
<img src="https://github.com/maxnotmaximchik/brain_trainer_for_kids/assets/106059025/4958626c-cbe6-4769-892b-f753d09f2e68" width="250" />

## App
<img src="https://github.com/maxnotmaximchik/brain_trainer_for_kids/assets/106059025/42e9ec96-37ec-4a6c-a97a-11759e05ea35" width="250" />
<img src="https://github.com/maxnotmaximchik/brain_trainer_for_kids/assets/106059025/939758b0-dd60-4f1c-a47c-33fe3c5a564b" width="250" />
