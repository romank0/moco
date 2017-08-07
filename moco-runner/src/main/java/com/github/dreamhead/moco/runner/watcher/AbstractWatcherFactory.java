package com.github.dreamhead.moco.runner.watcher;

import static com.github.dreamhead.moco.runner.watcher.Watchers.threadSafe;
import static com.google.common.collect.FluentIterable.from;
import static java.util.Arrays.asList;

import java.io.File;

import com.google.common.base.Function;

public abstract class AbstractWatcherFactory implements FileWatcherFactory {
    protected abstract Watcher doCreate(final File file, final Function<File, Void> listener);

    public Watcher createWatcher(final Function<File, Void> listener, final File... files) {
        if (files.length == 0) {
            throw new IllegalArgumentException("No file is specified");
        }

        if (files.length == 1) {
            return create(listener, files[0]);
        }

        return doCreate(listener, files);
    }

    private Watcher create(final Function<File, Void> listener, final File file) {
        return threadSafe(doCreate(file, listener));
    }

    private Watcher doCreate(final Function<File, Void> listener, File[] files) {
        return new CompositeWatcher(from(asList(files)).transform(new Function<File, Watcher>() {
            @Override
            public Watcher apply(final File file) {
                return create(listener, file);
            }
        }));
    }
}
