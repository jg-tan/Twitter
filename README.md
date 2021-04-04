# **1 Person Twitter App**

## **Login Screen**

![Login screen](https://user-images.githubusercontent.com/37413949/113508211-4a039280-9581-11eb-848d-098c302a188b.PNG)

- On first launch, it will show a Login screen where user can input their email and password to login. If the user does not have an account yet, clicking on "Register" will take them to **Register Screen** where they can create an account.
- If there is a user who is logged in, this screen will not be shown and it will directly go to **Feed Screen** instead.

## **Register Screen**

![register screen](https://user-images.githubusercontent.com/37413949/113508213-4a9c2900-9581-11eb-8deb-3311e325ed75.PNG)

- If user clicks "Register" from **Login Screen**, this screen will be shown. Clicking register will register the user to Firebase Auth, if successful, user will be automatically be logged in with the account and be taken to **Feed Screen**.
- Clicking on "x" will take the user back to **Login Screen**

## **Feed Screen**

![feed screen](https://user-images.githubusercontent.com/37413949/113508210-496afc00-9581-11eb-9ef8-8e7d14119347.PNG)

- User can add tweets in this screen by typing their tweet in the edit text box above and clicking "Tweet" button.
- Clicking "x" on the toolbar will sign out the user and be taken back to **Login Screen**.
- Clicking "x" on individual tweets will delete the tweet from the Firestore database and from the twitter feed.

![tweet](https://user-images.githubusercontent.com/37413949/113508393-59cfa680-9582-11eb-950b-bcd3482217f0.PNG)

- The edit text counts the character count of the tweet being written and once there are 50 characters left available (230 characters typed), the count will start to show.

![exceed](https://user-images.githubusercontent.com/37413949/113508540-2b9e9680-9583-11eb-8a4c-54b89b4e4b36.PNG)

- If there are 280 characters already in the text box, the "tweet" button will be disabled.
