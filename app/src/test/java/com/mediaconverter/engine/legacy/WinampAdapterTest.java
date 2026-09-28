package com.mediaconverter.engine.legacy;

import com.mediaconverter.exception.MediaProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WinampAdapterTest {

  @Mock
  LegacyWinamp legacyWinamp;

  @Test
  void convert_success() throws MediaProcessingException {
    when(legacyWinamp.transcodeMedia("mp3", "wma", "song.mp3", "song.wma"))
        .thenReturn(LegacyWinamp.OK);

    WinampEngineAdapter adapter = new WinampEngineAdapter(legacyWinamp);
    assertDoesNotThrow(() -> adapter.convert("song.wma", "mp3"));
  }

  @Test
  void convert_fileNotFound_throwsMediaProcessingException() {
    when(legacyWinamp.transcodeMedia(any(), any(), any(), any()))
        .thenReturn(LegacyWinamp.ERR_FILE_NOT_FOUND);

    WinampEngineAdapter adapter = new WinampEngineAdapter(legacyWinamp);
    MediaProcessingException ex = assertThrows(
        MediaProcessingException.class,
        () -> adapter.convert("missing.wma", "mp3"));
    assertTrue(ex.getMessage().contains("not found"));
  }

  @Test
  void convert_formatUnsupported_throwsMediaProcessingException() {
    when(legacyWinamp.transcodeMedia(any(), any(), any(), any()))
        .thenReturn(LegacyWinamp.ERR_FORMAT_UNSUPPORTED);

    WinampEngineAdapter adapter = new WinampEngineAdapter(legacyWinamp);
    MediaProcessingException ex = assertThrows(
        MediaProcessingException.class,
        () -> adapter.convert("song.wma", "xyz"));
    assertTrue(ex.getMessage().contains("not supported"));
  }
}
