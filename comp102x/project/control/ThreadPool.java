package comp102x.project.control;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPool {

	private static final int NUMBER_OF_THREADS = 4;
	private static Map<Runnable, Future> tasks = new HashMap<Runnable, Future>();

	private static ExecutorService threadPool = Executors
			.newFixedThreadPool(NUMBER_OF_THREADS);

	public static void run(Runnable r) {
		tasks.put(r, threadPool.submit(r));
	}

	public static void stop(Runnable r) {
		
		tasks.get(r).cancel(true);
	}

}
