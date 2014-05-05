# Shaketobug

###**Shaketobug**: easy to use in-app bug reporting library for android.


# Documentation

Refer to **[documentation site](http://burakdd.github.io/shaketobug/)** for more info.

Shaketobug is easy to integrate.

* Add library initialization code to every **activity** in your application.

* Decleare necessary fields in your **Manifest**.

* Let user shake the phone and report bug with additional device info.

# Customization

Shaketobug allow developers to customize some aspects of the library like feedback activity ui, some configuration data like support email etc.

```java
ShaketobugConfig config = new ShaketobugConfig();
config.setActionbarColor(Color.RED);
config.setActionbarTitle("Sample Title");
config.setEmailSubjectField("Bug Report From User");
config.setEmailToField("burakdede87@gmail.com");
config.setPencilColor(Color.YELLOW);
config.setUseDarkIcons(false);
config.setActionbarBackgrounDrawable(R.drawable.yellow_header_background);
```
# Download

Download latest jar from [here](https://github.com/burakdd/shaketobug/raw/master/shaketobug-release/shaketobug.jar).


# License
 	Copyright (C) Burak Dede.
 
 	Licensed under the Apache License, Version 2.0 (the "License");
 	you may not use this file except in compliance with the License.
 	You may obtain a copy of the License at
 
    	   http://www.apache.org/licenses/LICENSE-2.0
 	
 	Unless required by applicable law or agreed to in writing, software
 	distributed under the License is distributed on an "AS IS" BASIS,
 	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 	See the License for the specific language governing permissions and
 	limitations under the License.

  
