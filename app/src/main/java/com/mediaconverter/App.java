package com.mediaconverter;

import com.mediaconverter.engine.ConversionEngine;
import com.mediaconverter.engine.FfmpegEngine;
import com.mediaconverter.engine.GstreamerEngine;
import com.mediaconverter.engine.legacy.LegacyWinamp;
import com.mediaconverter.engine.legacy.WinampEngineAdapter;
import com.mediaconverter.exception.MediaProcessingException;
import com.mediaconverter.task.AudioTask;
import com.mediaconverter.task.MediaTask;
import com.mediaconverter.task.VideoTask;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class App {

  private static final Map<String, ConversionEngine> ENGINE_MAP = loadEngineMap();

  public static void main(String[] args) {
    String[] files = { "voice.mp3", "film.avi", "podcast.wma" };

    for (String file : files) {
      ConversionEngine engine = selectEngine(file);
      MediaTask task = selectTask(file, engine);

      try {
        task.process(file);
      } catch (MediaProcessingException e) {
        System.err.println("Failed to convert file: " + e.getMessage());
      }
    }
  }

  private static Map<String, ConversionEngine> loadEngineMap() {
    Properties props = new Properties();
    try (InputStream in = App.class.getClassLoader()
        .getResourceAsStream("engine-mapping.properties")) {
      props.load(in);
    } catch (IOException e) {
      throw new RuntimeException("Failed to load engine mapping", e);
    }

    ConversionEngine ffmpeg = new FfmpegEngine();
    ConversionEngine gstreamer = new GstreamerEngine();
    ConversionEngine winamp = new WinampEngineAdapter(new LegacyWinamp());

    Map<String, ConversionEngine> map = new HashMap<>();
    for (String ext : props.stringPropertyNames()) {
      map.put(ext, switch (props.getProperty(ext)) {
        case "ffmpeg" -> ffmpeg;
        case "gstreamer" -> gstreamer;
        default -> winamp;
      });
    }
    return map;
  }

  private static ConversionEngine selectEngine(String filename) {
    String ext = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
    return ENGINE_MAP.getOrDefault(ext, new WinampEngineAdapter(new LegacyWinamp()));
  }

  private static MediaTask selectTask(String filename, ConversionEngine engine) {
    String ext = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
    return switch (ext) {
      case "mp3", "flac", "wav", "wma" -> new AudioTask(engine);
      default -> new VideoTask(engine);
    };
  }
}
