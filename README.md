**MFApplication**

The application uses GitHub API to fetch users, details and repos

*Funcationality*
- Can see the list of GitHub users
- Can search for a user based on the displayed users
- Can view details about the user
- Can see the repos of the users which are not the forked ones

*Usage*
- We have used Dagger 2 for Dependency Injection
- View Binding for binding the view with the source
- Retrofit for API implementation

*Testcases*
- Included UI testcase for UserList screen
- Included UI testcase for the User detail screen
- Added UnitTest case for the ViewModel
