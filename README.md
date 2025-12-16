# RoomContacts


**Room Contacts App**

A simple Android app for managing contacts, built with Jetpack Compose, Room Database, and MVVM architecture.


**Features**

* Add and delete contacts
* Sort contacts by first name, last name, or phone number
* Input validation with error highlighting for required fields
* Modern UI using Material3 and Jetpack Compose



**Tech Stack**

* Kotlin
* Jetpack Compose
* Room Database
* MVVM (ViewModel, State management)
* Material3

**Project Structure**
```
com.example.room/
│
├── data/
|   |__ContactDao.kt
|   |__ContactDatabase.kt
|
├── domain/
|   |__Contact.kt
|
├── ui/     
|   ├── contactscreen/
|   |   ├── components/
|   |   |    ├── AddContactDialog.kt
│   |   |    ├── ContactEvent.kt
│   |   |    ├── ContactState.kt
│   |   |    └── SortType.kt
│   |   |
│   |   ├── ContactScreen.kt
│   |   └── ContactViewModel.kt    
│   |
|   ├── theme/     
|   |    ├── Color.kt
│   |    ├── Theme.kt
│   |    └── Type.kt  
│   |
|   └── MainActivity.kt                               
```


I started this project by following a tutorial to understand the fundamentals.  
Once complete, I refactored and revamped the UI to improve usability and visual design.

**Screenshots**

<img width="200" alt="Screenshot_1765897069" src="https://github.com/user-attachments/assets/72c3c847-ec7e-431f-bdaf-97f17b614785" />
<img width="200" alt="Screenshot_1765897923" src="https://github.com/user-attachments/assets/3ef1e358-d413-4549-a1c0-98bb5fb49bbe" />
<img width="200" alt="Screenshot_1765897921" src="https://github.com/user-attachments/assets/bc6fb953-9657-468c-8198-f1cf270b5f04" />
<img width="200" alt="Screenshot_1765897143" src="https://github.com/user-attachments/assets/8e94b2c7-c756-41e7-9d22-1758efd21934" />
