package ru.ilyayudov.yandexartists.app;

import java.io.IOException;
import java.io.InputStream;

public class DebugDataSource extends InputStream {
    @Override
    public int read() throws IOException {
        return 0;
    }


}
