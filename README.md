# GdriveDownloader

Purpose:
--------
A console based download utility that accesses a particular Google Drive account with the concent of the user and lets the user save the files from the account. Caches a copy of user credentials in unreadable format, which needs to be erased in order to access another account from same server.

Input Specifics:
---------------
Existing functionality dictates that only added users can access the files from this utility. Database has not been used to save credentials. Hence details of users are saved with the project in src/main/resources.

Output Specifics:
----------------
The user will be shown a list of files available to show. The user may choose to save a file for which he will have to specify an existing path in the system to which the file will be saved. Only binary files are supported for download. Hence the user will be notified if the selected file is actually a Google Doc. 

How to test:
-----------------------
Run the main class of the utility with provided JARs in classpath.


