<img width="303" alt="image" src="https://github.com/Seiksv/DemoMarvel/assets/38499005/2dce39d2-e3b7-44ec-85e6-e5d4c02f438e">
# DEMOMARVEL

## Introduction

This section provides a brief overview of your project. 
It should explain what your project does and its main features. 
For example, this project is a Marvel characters app built with Kotlin and Java. 
It uses the Marvel API to fetch and display a list of Marvel characters. 
Users can view details of each character, add them to a favorites list, and remove them from the favorites list.

## Project Structure

This section describes the organization of your project. 
It should explain how your project is divided into different packages or modules and what each package or module does. 
For example, the project follows the `MVVM (Model-View-ViewModel)` architecture and is structured as follows:

- `data`: This package contains the data layer of the application, which includes the API service and the repository implementation.
- `domain`: This package contains the domain layer of the application, which includes the repository interface and the domain models.
- `ui`: This package contains the UI layer of the application, which includes the view models and the UI components.

## Key Libraries

This section lists the main libraries or frameworks that your project uses and explains what each one is used for. 
For example, the project uses several key libraries:

- Retrofit: For network operations.
- Gson: For parsing JSON data.
- Jetpack Compose: For building the UI.
- Coroutines: For handling asynchronous tasks.
- Dagger Hilt: For dependency injection.


## Building the Project

This section provides step-by-step instructions for building your project. 
It should explain how to clone your repository, open your project in an IDE, 
and build your project. For example, to build the project, follow these steps:

1. Clone the repository to your local machine.
2. Open the project in Android Studio.
3. Sync the project with Gradle files.
4. Run the project on an emulator or a physical device.

## Project Screenshots

1. Login (No credentials needed)
<img width="303" alt="image" src="https://github.com/Seiksv/DemoMarvel/assets/38499005/3bdba128-0331-4a2e-93e3-570b810e92e8">

2. All characters view (With searchbar, async searchs, statusbar, loading images from cache or network depends if already was loaded, infinity scroll, viewmodel states, compose navigation)
<img width="305" alt="image" src="https://github.com/Seiksv/DemoMarvel/assets/38499005/c73e30e4-e14f-40fd-ae29-52ff957bdfaa">

3. Character details (Loading images from cache or network depends if already was loaded, intent for every link, horizontal sections, favorite feature)
<img width="303" alt="image" src="https://github.com/Seiksv/DemoMarvel/assets/38499005/cf14d7fa-93b5-4e77-b698-1a46dee77a5b">

4. Favs section (With all the characters marked as favorite)
<img width="298" alt="image" src="https://github.com/Seiksv/DemoMarvel/assets/38499005/cb48aac7-1ba5-4d34-afa5-b6b3ecb1eaf6">

