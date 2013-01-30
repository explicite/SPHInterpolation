package core.concurrent;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

/**
 * User: Jan Paw
 * Date: 08.11.12
 * Time: 16:13
 */
public class Parallel {
    private static final int NUM_CORES = Runtime.getRuntime().availableProcessors();

    private static final ForkJoinPool forkJoinPool = new ForkJoinPool(3, ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true);

    public static <T> void For(final Iterable<T> elements, final Operation<T> operation) {
        forkJoinPool.invokeAll(createCallables(elements, operation));
    }

    public static <T> Collection<Callable<Void>> createCallables(final Iterable<T> elements, final Operation<T> operation) {
        List<Callable<Void>> callables = new LinkedList<Callable<Void>>();
        for (final T elem : elements) {
            callables.add(new Callable<Void>() {

                @Override
                public Void call() {
                    operation.perform(elem);
                    return null;
                }
            });
        }

        return callables;
    }

    public static interface Operation<T> {
        public void perform(T pParameter);
    }
}
