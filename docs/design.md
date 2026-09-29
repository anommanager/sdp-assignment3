# Design Rationale - Media Converter

Complexity module chosen: Dynamic implementor selection

## Class Diagram
![UML class diagram](diagram.svg)

## Problem Domain

A media conversion system supporting multiple conversion backends. There are different tools for different filetypes: FFmpeg for common audio, GStreamer for video, and a legacy WinAmp-based library for everything else. Conversion tasks can be either audio- or video-specific and have slight variations in target format description and logging behavior.

## Why Bridge Alone Is Not Enough
Using Bridge pattern allows to decouple the task hierarchy (AudioTask, VideoTask) from the engine hierarchy (FFmpeg, GStreamer, WinAmp). Without Bridge, each combination of task type and engine would require its own class: AudioTaskFfmpeg, AudioTaskGStreamer, AudioTaskWinamp, VideoTaskFfmpeg, and so on - a combinatorial explosion. Bridge eliminates this by letting any MediaTask subclass work with any ConversionEngine implementation through the interface. However, Bridge alone cannot handle LegacyWinamp, because it does not implement ConversionEngine — its API is fundamentally different in method name, parameter count, argument order, and failure mechanism.

## Open/Closed Principle on Both Axes
New abstraction variant (new Task type): We can introduce a new subclass of MediaTask, say SubtitleTask, and implement the process() method without changing anything else.

New implementor variant (new Engine type): We can define a new class implementing the ConversionEngine interface, HandbrakeEngine, without changing any of the task classes. All existing tasks can use it right away through App.selectEngine().

## Why LegacyWinamp Is Genuinely Incompatible
There are three fundamental reasons why the LegacyWinamp library cannot be used directly: the method name is different (transcodeMedia vs convert), it takes 4 arguments vs 2, and it returns integer error codes vs throwing exceptions. The WinampEngineAdapter class solves the problem by adapting all three differences: renaming the method, reordering the parameters, and converting error codes to exceptions.

## Dynamic Implementor Selection
The App.selectEngine(filename) method uses the file extension to choose between available ConversionEngine implementations. The client code (main method) does not contain any references to specific engine classes. This satisfies the dynamic implementor selection complexity module as it demonstrates that the concrete implementation (WinampEngineAdapter) can be chosen at run-time depending on the input without requiring changes to the task classes themselves.

## One Limitation
The limitation of the current solution is that the dynamic implementation selection (App.selectEngine(filename)) is hard-coded and does not follow the Open/Closed principle. If a new engine is added to the system, it would require to modify the selectEngine() method to handle the new file extension. This could be solved by using a registry pattern instead, but it was considered unnecessary for this particular task.
