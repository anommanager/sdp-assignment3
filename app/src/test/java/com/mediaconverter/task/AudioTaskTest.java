package com.mediaconverter.task;

import com.mediaconverter.engine.FfmpegEngine;
import com.mediaconverter.exception.MediaProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class AudioTaskTest {

  AudioTask task;

  @BeforeEach
  void setUp() {
    task = new AudioTask(new FfmpegEngine());
  }

  @Test
  void process_validFile_doesNotThrow() {
    assertDoesNotThrow(() -> task.process("song.flac"));
  }

  @Test
  void process_nullFile_throwsException() {
    assertThrows(MediaProcessingException.class, () -> task.process(null));
  }

  @Test
  void process_blankFile_throwsException() {
    assertThrows(MediaProcessingException.class, () -> task.process("  "));
  }

  @ParameterizedTest
  @ValueSource(strings = { "song.mp3", "track.wav", "album.ogg", "podcast.aac", "file.flac" })
  void process_variousAudioFormats_doesNotThrow(String file) {
    assertDoesNotThrow(() -> task.process(file));
  }
}
