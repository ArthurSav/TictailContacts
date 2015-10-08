#Libraries

The majority of the libraries used remove boilerplate code. Also, we use dependency injection with dagger. The most distinguished ones are [RxAndroid](https://github.com/ReactiveX/RxAndroid) and [Dagger 1.2](http://square.github.io/dagger/)


#Structure

#### .modules
We use dagger to create modules that provide commonly used classes e.g gson, apiservice

####.api

Networking calls are done with retrofit. In **.api** you'll find the api model structure<br/>
Network responses are handled with **observables(rxjava)** instead of callbacks

####.ui
Holds activities, fragments etc...<br>
Folder structure is activity based e.g everything associated with the MainActivity will be added in the .ui.main<br>

####.util
Common utilities

####.views
Custom views

####Application class
Initializes dagger object graph

##Flow
The application class creates our dagger object graph. The graph provides the **apiService** we need to make api calls. Every activity has a custom view that handles most of the ui functionality.

**ActivityContacts** will load all the contacts from the server. Has search functionality<br>
**ActivityAddContact** user can add a new contact<br>
**ActivityContact** loads a single contact selected from the contacts list. Has two modes:
* **Display**, user sees basic info about the contact
* **Edit**, user can edit contact info


######Note
Image upload is done via a 3rd party service [imgur](https://api.imgur.com/endpoints)
