# RateProgressBar
An Android custom progress bar with rate text

[![Travis](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](https://github.com/jackjustbj/RateProgressBar/blob/master/LICENSE) 
![Maven Central](https://img.shields.io/maven-central/v/org.apache.maven/apache-maven.svg)

RateProgressBar is an Android custom view, it show progress rate in the center of bar.

It's easy to use, just like ProgressBar.
## Getting started
In your `build.gradle`:
```groovy
allprojects {
    repositories {
        jcenter()
    }
}

dependencies {
    implementation 'me.JGeekDev.RateProgressBar:RateProgressBar:1.0.0@aar'
}
```
## Attributes
| name | format | description |
|:----:|:------:|:-----------:|
| rpbProgressBgColor | reference/color | set background color of progress bar |
| rpbStrokeColor | reference/color | set stroke color |
| rpbStrokeWidth | reference/dimension | set stroke width |
| rpbProgressColor | reference/color | set progress color |
| rpbProgressStartColor | reference/color | when progress color is gradient, set start color |
| rpbProgressEndColor | reference/color | when progress color is gradient, set end color |
| rpbProgressGradient | boolean | set line gradient progress color |
| rpbProgressInside | boolean | set progress bounds inside stroke |
| rpbTextColor | reference/color | set rate text color |
| rpbTextCoverColor | reference/color | set rate text color that progress overed |
| rpbTextSize | reference/dimension | set rate text size |
| rpbInitText | reference/string | set rate text in the start of progress |
| rpbCompleteText | reference/string | set rate text in the end of progress |
| rpbMax | integer | set the upper range of the progress |
| rpbProgress | integer | set the current progress to the specified value |
| rpbShowRate | boolean | set rate text show "0%" not the init text when progress is 0 |
## Usage
* set attributs in layout file.
* `setMax()` set the upper range of the progress if necessary, the default value is 100.
* `setProgress()` set the current progress.
## License
```
Copyright 2018 jgeekdev

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
