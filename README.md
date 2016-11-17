# HighPassSkinSmoothing-Android


An implementation of [High Pass Skin Smoothing](https://www.google.com/search?ie=UTF-8&q=photoshop+high+pass+skin+smoothing) for Android

Supports for Android 14+ (Tested in MarshMallow Only, Need Some test Results)

##Previews

![Preview 1](http://yuao.github.io/YUCIHighPassSkinSmoothing/previews/1.jpg)

![Preview 2](http://yuao.github.io/YUCIHighPassSkinSmoothing/previews/2.jpg)

![Preview 3](http://yuao.github.io/YUCIHighPassSkinSmoothing/previews/3.jpg)

![Preview 4](http://yuao.github.io/YUCIHighPassSkinSmoothing/previews/4.jpg)

![Preview 5](http://yuao.github.io/YUCIHighPassSkinSmoothing/previews/5.jpg)

![Preview 6](http://yuao.github.io/YUCIHighPassSkinSmoothing/previews/6.jpg)

##Performance Analysis
```
Image Size: 1024 x 1920
Input Radius: 400 or 4.0
API Level: 23 
Device: OnePlus 3
Time: __120 Millis__  
```

##Concepts

The basic routine of `HighPassSkinSmoothing` can be described with the following diagram.

[![Routine](http://yuao.github.io/YUCIHighPassSkinSmoothing/docs/filter-routine.jpg)](http://yuao.github.io/YUCIHighPassSkinSmoothing/docs/filter-routine.jpg)

####Basic Concept

The main theory behind `High Pass Skin Smoothing` is `Frequency Separation`.

Frequency separation splits up the tones and colors of a image from its more detailed textures. It is possible because a digital image can be interpreted as different frequencies represented as sine waves.

> High frequencies in an image will contain information about fine details, such as skin pores, hair, fine lines, skin imperfections.

> Low frequencies are the image data that contains information about volume, tone and color transitions. In other words: shadows and light areas, colors and tones.

> https://fstoppers.com/post-production/ultimate-guide-frequency-separation-technique-8699

By using [High Pass](https://en.wikipedia.org/wiki/High-pass_filter) filter, the image can be separated into high and low spatial frequencies. Then we will be able to smoothing the image while preseving a fine level of detail by applying adjustments (`Curve Adjustment` in the diagram) to certain frequencies of the image.

##Requirements

* Android API 14+ (Might be Work on Below then this! Never Tried.)
* Android Studio 2.2+ (For NDK Build)
* Configured NDK! (Take some Help from Google if Need)

##Usage

Use the `HighPassSkinSmoothing` is Follows.

Make sure that You use below code in a worker Thread As its may take some couple of millis.
```java
AmniXSkinSmooth amniXSkinSmooth = AmniXSkinSmooth.getInstance(); //Get Instance
amniXSkinSmooth.storeBitmap(bitmap, false); // Your Bitmap (define if you want to recycle it or not)
amniXSkinSmooth.initSdk();

amniXSkinSmooth.startFullBeauty(smoothR, whiteR); // Process Smoothness and Whiteness in single line
***OR***
amniXSkinSmooth.startSkinSmoothness(smoothR); //Process for Skin Smoothness
***OR***
amniXSkinSmooth.startSkinWhiteness(whiteR); //Process for Skin Whiteness

Bitmap processedBitmap = amniXSkinSmooth.getBitmapAndFree();
amniXSkinSmooth.unInitSdk();
```

Browse DemoApp and Library for More Information.

__Please run the demo app on an actual device instead of Emulator.__ 

##Installation

For now, You have to Clone or download this repo and include __skinsmoothness__ as a android Module.
Rememer to configure NDKFirst.

##Contributing

You are encouraged to try different input parameters or tweak the interal procedure to make this filter better or just fit your needs.

Don't hesitate to open an issue if you have any idea or suggestion.

If you find a bug and know exactly how to fix it, please open a pull request. Be sure to test the code first.

##Credits

Thanks a lot to [Yu Ao](https://github.com/YuAo) for his iOS [YUCIHighPassSkinSmoothing](https://github.com/YuAo/YUCIHighPassSkinSmoothing) Library.

##License

HighPassSkinSmoothing is Free but under Apache 2.0 licensed. See [LICENSE](https://github.com/AmniX/HighPassSkinSmoothing-Android/blob/master/LICENSE) file for detail.

Copyright © 2016 [AmniX](https://github.com/AmniX)
