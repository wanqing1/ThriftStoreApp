# My Personal Project

## A Thrift Store


 *Functions and Target Users*
- This application works as a small thrift store where users can upload their items to be sold and browse all items 
currently on sale in the thrift store. Furthermore, one can view the things they have purchased. People who want to buy
second-hand products or sell something they own may use this application.


*"Why is this project of interest to me"*
- I came up with this idea because I like shopping in thrift stores. For those who would like to sell items, uploading
the information of their items will be more convenient and their items could be seen by more people on the app.

## User Stories
- As a user, I want to be able to add my "almost new" sweater to the thrift store.
- As a user, I want to be able to view what the thrift store is selling right now.
- As a user, I want to be able to view the items I have purchased in the thrift store.
- As a user, I want to be able to save items that I have purchased and all items in the store, if I choose to.
- As a user, I want to be able to load items that I have purchased and all items in the store from file, if I choose to.

## Instructions for Grader

- You can generate the first required action related to adding Xs to a Y by first clicking the button "Upload" from the 
menu bar on the left. Then, enter the required information into blank fields. Finally, click the button "Submit". You 
can view all the items in store by clicking the button "Store" from the menu bar.

- You can generate the second required action related to adding Xs to a Y by first clicking the button "Store" from the
menu bar. Then, choose the item you would like to purchase and click the corresponding button "Buy". You can view all 
the purchased items by clicking the button "Mine" from the menu bar.

- You can locate my visual component by running the MainGUI, and there will be an image of a thrift store appearing in 
the center of screen for about a second before you interact with the store.

- You can save the state of my application by clicking the button "Save" from the menu bar.

- You can reload the state of my application by clicking the button "Load" from the menu bar.

## Phase 4: Task 2

Thu Apr 06 19:25:15 PDT 2023
ZaraDress uploaded to store

Thu Apr 06 19:26:22 PDT 2023
GreenTailoredJacket uploaded to store

Thu Apr 06 19:26:34 PDT 2023
ZaraDress successfully purchased

Thu Apr 06 19:27:06 PDT 2023
NikePants uploaded to store

Thu Apr 06 19:27:16 PDT 2023
NikePants successfully purchased

## Phase 4: Task 3
To improve the diagram, I would probably create an interface for ThriftStore and ItemsPurchased, since both of them have 
a method that adds an item into an ArrayList and a method that removes an item from the ArrayList. However, that could 
make the application less straightforward, so it is a bit hard to name this interface.

## References
- Carter, P. (2021, October). *JsonSerializationDemo*. GitHub. 
https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

- Citizen Vintage. (2022, August 25). [Photograph]. Curated. https://dailyhive.com/montreal/montreal-thrift-stores-
shopping