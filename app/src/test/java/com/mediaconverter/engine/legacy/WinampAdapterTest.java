package com.mediaconverter.engine.legacy;

import com.mediaconverter.exception.MediaProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WinampAdapterTest {

  WinampEngineAdapter adapter;

  @BeforeEach
  void setUp() {
    adapter = new WinampEngineAdapter(new LegacyWinamp());
  }

  @Test
  void convert_validInput_doesNotThrow() {
    assertDoesNotThrow(() -> adapter.convert("song.wma", "mp3"));
  }

  @Test
  void convert_nullFile_throwsException() {
    assertThrows(MediaProcessingException.class, () -> adapter.convert(null, "mp3"));
  }

  @Test
  void convert_blankFile_throwsException() {
    assertThrows(MediaProcessingException.class, () -> adapter.convert("  ", "mp3"));
  }

  @Test
  void convert_nullFormat_throwsException() {
    assertThrows(MediaProcessingException.class, () -> adapter.convert("song.wma", null));
  }

  @Test
  void convert_blankFormat_throwsException() {
    assertThrows(MediaProcessingException.class, () -> adapter.convert("song.wma", "  "));
  }
}
