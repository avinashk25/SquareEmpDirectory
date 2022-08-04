## Build tools & versions used
Gradle 7.2.1 Kotlin 1.5.31 Dagger-Hilt 2.42

## Steps to run the app
Project can be run by executing run on 'app' module

## What areas of the app did you focus on?
1. Architecture
The project is implemented with MVVM architecture components. SquareEmployeeService service is used with the help of retrofit APIs to fetch a list of employees from the view model inside
a coroutine and the LiveData is set accordingly to update the UI.
2. Testing
I have created network component as service so that it can be injected in ViewModel constructor and is easier to mock for testing as well.

## What was the reason for your focus? What problems were you trying to solve?
I wanted to make the code modular and cleaner so that it's easier to test business logic separately from view testing.

## How long did you spend on this project?
5-6 hours

## Did you make any trade-offs for this project? What would you have done differently with more time?
Given more time, I would have handled UI scenarios for tablet devices as well.
Also would have added a logging framework.

## What do you think is the weakest part of your project?
Currently app is working keeping in ming a happy-path scenario. Although I have added a text view to show a message to the user when  empty list is returned.
But this can be modified further to handle more network error scenarios. 
Also using paging library to handle larger datasets so that system resources are handled more effectively.

## Did you copy any code or dependencies? Please make sure to attribute them here!

I have used this in my current professional project and found it to be useful. And hence have utilized in this project as well.
https://github.com/android/architecture-samples/blob/dev-todo-mvvm-live/todoapp/app/src/main/java/com/example/android/architecture/blueprints/todoapp/SingleLiveEvent.java |

## Is there any other information you’d like us to know?
I had not done a project from scratch for some time. So it was a refresher to figure out the dependencies while implementing a project from scratch.