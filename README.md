# Random User Finder :clipboard:

This project displays a bulk list of random users fetched via a network call. Users can scroll through the list, search for a specific user,
and view detailed information about a user by tapping on their card.

## Features

- **Bulk User Fetching:** Fetch and display random user data efficiently using [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview).
- **Search Functionality:** Quickly find users by name or other criteria.
- **User Details:** Tap on a user card to view detailed information about the selected user.
- **Smooth Navigation:** Seamlessly navigate between the list and detail screens.
- **Unit Testing:** Some unit test on network and pagination.

## Tech Stack

- **Kotlin:** Prgramming Language
- **Jetpack Components:**
  - **Paging 3:** For efficient data loading and pagination.
  - **Navigation:** For navigating between the list and detail screens.
  - **ViewModel:** To manage UI-related data in a lifecycle-conscious way.
  - **RecyclerView:** For UI rendering
- **Networking:** Retrofit with Gson for API communication.
- **Dependency Injection:** [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for DI.
- **Unit Testing:**
  - **JUnit:** For unit tests.
  - **Mockito:** For mocking dependencies.
  - **MockWebServer:** To mock network responses for Retrofit testing.

## Screenshots

![list](https://github.com/user-attachments/assets/4ecb8e3f-c789-4083-bb65-701126697d32)![info](https://github.com/user-attachments/assets/268a278e-a009-4134-bd79-e38b8cf91b9a)![input](https://github.com/user-attachments/assets/43fb7cfb-abd7-4074-8ba0-2a9d41a00d68)


## Unit Test

- **Repository Testing:** Validates data retrieval and transformation.
- **Retrofit Service Testing:** Uses MockWebServer to test API calls.
- **PagingSource Testing:** Ensures correct data is fetched and handled for pagination.

## Contributions

Contributions are welcome! If you'd like to make improvements or fix issues, feel free to open a pull request or report an issue.
