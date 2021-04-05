# **1 Person Twitter App**

## **Login Screen**

![Login screen](https://user-images.githubusercontent.com/37413949/113508211-4a039280-9581-11eb-848d-098c302a188b.PNG)

- On first launch, it will show a Login screen where user can input their email and password to login. If the user does not have an account yet, clicking on "Register" will take them to **Register Screen** where they can create an account.
- If there is a user who is logged in, this screen will not be shown and it will directly go to **Feed Screen** instead.

## **Register Screen**

![register screen](https://user-images.githubusercontent.com/37413949/113508213-4a9c2900-9581-11eb-8deb-3311e325ed75.PNG)

- If user clicks "Register" from **Login Screen**, this screen will be shown. 
- Clicking register will register the user to Firebase Auth. If successful, user will automatically be logged in with the account and be taken to **Feed Screen**.
- Clicking on "x" will take the user back to **Login Screen**

## **Feed Screen**

![feed screen](https://user-images.githubusercontent.com/37413949/113516950-b5645900-95af-11eb-944b-e1c579ad6351.PNG)

- User can add tweets in this screen by typing their tweet in the edit text box above and clicking "Tweet" button.
- Clicking "x" on the toolbar will sign out the user and be taken back to **Login Screen**.
- Clicking "x" on individual tweets will delete the tweet from the Firestore database and from the twitter feed.
- Clicking on "pencil" icon will take the user to **Edit Screen** where they can edit the tweet selected.

![tweet](https://user-images.githubusercontent.com/37413949/113516967-d036cd80-95af-11eb-9333-d8019c0d8359.PNG)

- The edit text checks the character count of the tweet being written and once there are less than 50 characters available, the count will start to show.

![exceed](https://user-images.githubusercontent.com/37413949/113516953-bdbc9400-95af-11eb-93a5-9857c7f12882.PNG)

- If there are 280 characters already in the text box, the "tweet" button will be disabled.

## **Edit Screen**

![edit](https://user-images.githubusercontent.com/37413949/113516991-f2305000-95af-11eb-9b0c-46ab0f6b8dde.PNG)

- The text body of the tweet selected is automatically loaded to the edit text box once the screen has loaded.
- Clicking "Edit" will edit the said tweet in the database.

![edit 2](https://user-images.githubusercontent.com/37413949/113516992-f3617d00-95af-11eb-9608-ca0e6ee36761.PNG)

- Edit text box works the same way as posting tweet from **Feed Sscreen**

## **Error Screen**

![error](https://user-images.githubusercontent.com/37413949/113529083-12cec900-95f5-11eb-8ab2-814126824360.PNG)

- **Error Screen** is shown instead of **Feed Screen** when there is an error in loading the user or tweets from the database.
- An instance that this may occur is when the currently logged in user has been deleted from the database.


#### Notes
- Dark mode UI was not configured for the project


