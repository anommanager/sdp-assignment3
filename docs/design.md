# Design Rationale - Media Converter

Complexity module chosen: Dynamic implementor selection

## Class Diagram
![UML class diagram](diagram.svg)

## Problem Domain

A media conversion system supporting multiple conversion backends. There are different tools for different filetypes: FFmpeg for common audio, GStreamer for video, and a legacy WinAmp-based library for everything else. Conversion tasks can be either audio- or video-specific and have slight variations in target format description and logging behavior.

## Why Bridge Alone Is Not Enough
Using Bridge pattern allows to decouple the task hierarchy (AudioTask, VideoTask) from the engine hierarchy (FFmpeg, GStreamer, WinAmp). Without Bridge, each combination of task type and engine would require its own class: AudioTaskFfmpeg, AudioTaskGStreamer, AudioTaskWinamp, VideoTaskFfmpeg, and so on - a combinatorial explosion. Bridge eliminates this by letting any MediaTask subclass work with any ConversionEngine implementation through the interface. However, Bridge alone cannot handle LegacyWinamp, because it does not implement ConversionEngine — its API is fundamentally different in method name, parameter count, argument order, and failure mechanism.

## Why Adapter Alone Is Not Enough
While the Adapter pattern does make LegacyWinamp appear no different from the other engines, it only solves the problem for one particular class. Without Bridge, we would have to create a multitude of classes for each pair of task and engine: AudioTaskFfmpeg, AudioTaskGstreamer, AudioTaskWinamp, VideoTaskFfmpeg and so on. Using Bridge, any MediaTask subclass could be composed with any ConversionEngine.

## Open/Closed Principle on Both Axes
New abstraction variant (new Task type): We can introduce a new subclass of MediaTask, say SubtitleTask, and implement the process() method without changing anything else.

New implementor variant (new Engine type): We can define a new class implementing the ConversionEngine interface, HandbrakeEngine, without changing any of the task classes. All existing tasks can use it right away through App.selectEngine().

## Why LegacyWinamp Is Genuinely Incompatible
There are three fundamental reasons why the LegacyWinamp library cannot be used directly: the method name is different (transcodeMedia vs convert), it takes 4 arguments vs 2, and it returns integer error codes vs throwing exceptions. The WinampEngineAdapter class solves the problem by adapting all three differences: renaming the method, reordering the parameters, and converting error codes to exceptions.

## Dynamic Implementor Selection
The engine-to-extension mapping is read from engine-mapping.properties upon start-up; App.selectEngine(filename) uses the extension to determine which ConversionEngine implementation to use at run-time. The client code (main) contains no engine-specific code. This meets the requirements of the dynamic implementor selection complexity module because it shows that the implementation can be selected at run-time based on the input without changing the task classes.

## One Limitation
The engine-to-extension mapping is loaded from the external resource engine-mapping.properties upon initialization, so that new extensions could be added without touching the code of App. Nevertheless, the addition of a new engine type still requires instantiation of the new class in loadEngineMap(). A truly extensible solution would utilize a self-registering mechanism, such as a factory, but it was deemed out of scope for this assignment.
