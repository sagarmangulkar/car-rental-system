
This is a Car Rental System using Swing User Interface. It includes Source Code, MakeFile and ReadMe.
Kindly follow below steps to run the project:


1) Inside extracted CarRentalSystem directory, run either one of below commands in terminal:


    $ java -jar CarRentalSystem.jar

    (Or)

    $ make


2) It will open Swing User Interface when you can make reservation of car for future date. It will also notify if reservation is not possible because of overlapping.






My Choice of Data Stucture for storing newly created Reservations:

Map<Specific_Day, List<Reservation>> reservations = new Hashtable<>();

Background: In future, if system stores millions of Reservation, then I don't want to go and check if new input reservation is overlapping with those millions of reservations.

Solution/Scaling: Separated reservations for each day as a List and stored it in HashTable with that Day as a key for future retrieval. So, if I want to check for reservation overlapping, I go to that day directly in O(1) complexity using Day as my key.

Reason for choosing HashTable: It is thread safe. So we can get Concurrency even when multiple users add new reservations at the same time.

Future Scope for more Scaling: Use hour as a key instead of day. It will make system 24 time more faster when system is dealing with many/millions of reservations.
