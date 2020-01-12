# Clean-City
A repo containing a Clean City App for Android, free to use :)
## General description:
The idea of the project is a one-button application with a camera for reporting an unauthorized dump. 
Quick and convenient for everyone to use. help to clean our beautiful world. In addition, all sorts of statuses and achievements for those who report a lot, sharing status, etc.

## Step-by-step
1. open the application and take a photo of an unauthorized dump
2. click send
3. wait for the verification results

## Features
With the app, you can:
1. use the site from a mobile device in one click
2. sending a report with a photo and the current coordinates of the device to the server
3. be notified of the result
4. see the history of your requests and their statuses
5. use status system for users (depending on the number of successful cleanups on user reports)
6. share status in social networks.
7. AR extension, to display already fixed points in the camera with luminous markers (so that when looking at the camera, the user immediately understands whether the dump is already damaged or not)

## Requirements
- Android API 21+
- Our test backend: https://ks-test-khack2019.herokuapp.com/
- Here you can read much more details (RUS) https://docs.google.com/document/d/1HgvS-FZP8SDTnoZsgeLydVq8MZwqYGhkIrDBZdUSJ8c/edit

## Screenshots are presented below:
Below you can see the scenario of using the camera, in case the user photographs not some landfill, but some other object. This is an extreme case UX, where the app will show a notification about a unsuitable object for reporting.

<p>
<img src="https://github.com/daniily000/clean-city/blob/master/1.jpg" width="200" height="350" />
<img src="https://github.com/daniily000/clean-city/blob/master/2.jpg" width="200" height="350" />
<img src="https://github.com/daniily000/clean-city/blob/master/3.jpg" width="200" height="350" />
<img src="https://github.com/daniily000/clean-city/blob/master/4.jpg" width="200" height="350" />
</p>

And here is a more realistic scenario with a photo of a real trash. The app processes the snapshot and sends the report to the server. A little later, you can see the result of the check.

<p>
<img src="https://github.com/daniily000/clean-city/blob/master/5.jpg" width="200" height="350" />
<img src="https://github.com/daniily000/clean-city/blob/master/6.jpg" width="200" height="350" />
<img src="https://github.com/daniily000/clean-city/blob/master/7.jpg" width="200" height="350" />
</p>

## Installation
Clone this repository and import into **Android Studio**
```bash
git clone https://github.com/daniily000/clean-city.git
```
## Build variants
Use the Android Studio *Build Variants* button to choose between **production** and **staging** flavors combined with debug and release build types

## Generating signed APK
From Android Studio:
1. ***Build*** menu
2. ***Generate Signed APK...***
3. Fill in the keystore information *(you only need to do this once manually and then let Android Studio remember it)*

## Contributing
1. Fork it
2. Create your feature branch (git checkout -b my-new-feature)
3. Commit your changes (git commit -m 'Add some feature')
4. Run the linter (ruby lint.rb').
5. Push your branch (git push origin my-new-feature)
6. Create a new Pull Request

## Authors

* **Nikita** - *AR CORE* - tg: @NikitaDroid
* **Nikita** - *Android and positive mood* - tg: @Nikita Lyutikov
* **Daniily** - *Teamlead* - tg: @daniily000
* **Alex** - *Backend* - tg: @slimlight
* **Orina** - *Android and UI/UX* - tg: @doldrums3
