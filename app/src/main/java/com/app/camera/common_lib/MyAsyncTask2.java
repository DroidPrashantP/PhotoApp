package com.app.camera.common_lib;

import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.support.v8.renderscript.Allocation;
import android.util.Log;

import com.app.camera.activities.MirrorActivity;

import java.util.ArrayDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class MyAsyncTask2<Params, Progress, Result> {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$lyrebirdstudio$common_libs$MyAsyncTask2$Status = null;
    private static final int CORE_POOL_SIZE;
    private static final int CPU_COUNT;
    private static final int KEEP_ALIVE = 1;
    private static final String LOG_TAG = "AsyncTask";
    private static final int MAXIMUM_POOL_SIZE;
    private static final int MESSAGE_POST_PROGRESS = 2;
    private static final int MESSAGE_POST_RESULT = 1;
    public static final Executor SERIAL_EXECUTOR;
    public static final Executor THREAD_POOL_EXECUTOR;
    private static volatile Executor sDefaultExecutor;
    private static final InternalHandler sHandler;
    private static final BlockingQueue<Runnable> sPoolWorkQueue;
    private static final ThreadFactory sThreadFactory;
    private final AtomicBoolean mCancelled;
    private final FutureTask<Result> mFuture;
    private volatile Status mStatus;
    private final AtomicBoolean mTaskInvoked;
    private final WorkerRunnable<Params, Result> mWorker;
    private static AsyncTaskResult result;

    /* renamed from: com.lyrebirdstudio.common_libs.MyAsyncTask2.1 */
    public static class C05761 implements ThreadFactory {
        private final AtomicInteger mCount;

        C05761() {
            this.mCount = new AtomicInteger(MyAsyncTask2.MESSAGE_POST_RESULT);
        }

        public Thread newThread(Runnable r) {
            return new Thread(r, "AsyncTask #" + this.mCount.getAndIncrement());
        }
    }

    class C05773 extends FutureTask<Result> {
        C05773(Callable $anonymous0) {
            super($anonymous0);
        }

        protected void done() {
            try {
                MyAsyncTask2.this.postResultIfNotInvoked(get());
            } catch (InterruptedException e) {
                Log.w(MyAsyncTask2.LOG_TAG, e);
            } catch (ExecutionException e2) {
                throw new RuntimeException("An error occured while executing doInBackground()", e2.getCause());
            } catch (CancellationException e3) {
                MyAsyncTask2.this.postResultIfNotInvoked(null);
            }
        }
    }

    private static class AsyncTaskResult<Data> {
        final Data[] mData;
        final MyAsyncTask2 mTask;

        AsyncTaskResult(MyAsyncTask2 task, Data... data) {
            this.mTask = task;
            this.mData = data;
        }
    }

    private static class InternalHandler extends Handler {
        private InternalHandler() {
        }

        public void handleMessage(Message msg) {
            result = (AsyncTaskResult) msg.obj;
            switch (msg.what) {
                case MyAsyncTask2.MESSAGE_POST_RESULT:
                    result.mTask.finish(result.mData[MyAsyncTask2.MAXIMUM_POOL_SIZE]);
                case MyAsyncTask2.MESSAGE_POST_PROGRESS:
                    result.mTask.onProgressUpdate(result.mData);
                default:
            }
        }
    }

    private static class SerialExecutor implements Executor {
        Runnable mActive;
        final ArrayDeque<Runnable> mTasks;

        class C05781 implements Runnable {
            private final /* synthetic */ Runnable val$r;

            C05781(Runnable runnable) {
                this.val$r = runnable;
            }

            public void run() {
                try {
                    this.val$r.run();
                } finally {
                    SerialExecutor.this.scheduleNext();
                }
            }
        }

        private SerialExecutor() {
            this.mTasks = new ArrayDeque();
        }

        public synchronized void execute(Runnable r) {
            this.mTasks.offer(new C05781(r));
            if (this.mActive == null) {
                scheduleNext();
            }
        }

        protected synchronized void scheduleNext() {
            Runnable runnable = (Runnable) this.mTasks.poll();
            this.mActive = runnable;
            if (runnable != null) {
                MyAsyncTask2.THREAD_POOL_EXECUTOR.execute(this.mActive);
            }
        }
    }

    public enum Status {
        PENDING,
        RUNNING,
        FINISHED
    }

    private static abstract class WorkerRunnable<Params, Result> implements Callable<Result> {
        Params[] mParams;

        private WorkerRunnable() {
        }
    }

    /* renamed from: com.lyrebirdstudio.common_libs.MyAsyncTask2.2 */
    class C09502 extends WorkerRunnable<Params, Result> {
        C09502() {
            super();
        }

        public Result call() throws Exception {
            MyAsyncTask2.this.mTaskInvoked.set(true);
            Process.setThreadPriority(10);
            return MyAsyncTask2.this.postResult(MyAsyncTask2.this.doInBackground(this.mParams));
        }
    }

    protected abstract Result doInBackground(Params... paramsArr);

    static /* synthetic */ int[] $SWITCH_TABLE$com$lyrebirdstudio$common_libs$MyAsyncTask2$Status() {
        int[] iArr = $SWITCH_TABLE$com$lyrebirdstudio$common_libs$MyAsyncTask2$Status;
        if (iArr == null) {
            iArr = new int[Status.values().length];
            try {
                iArr[Status.FINISHED.ordinal()] = 3;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[Status.PENDING.ordinal()] = MESSAGE_POST_RESULT;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[Status.RUNNING.ordinal()] = MESSAGE_POST_PROGRESS;
            } catch (NoSuchFieldError e3) {
            }
            $SWITCH_TABLE$com$lyrebirdstudio$common_libs$MyAsyncTask2$Status = iArr;
        }
        return iArr;
    }

    static {
        CPU_COUNT = Runtime.getRuntime().availableProcessors();
        CORE_POOL_SIZE = CPU_COUNT + MESSAGE_POST_RESULT;
        MAXIMUM_POOL_SIZE = (CPU_COUNT * MESSAGE_POST_PROGRESS) + MESSAGE_POST_RESULT;
        sThreadFactory = new C05761();
        sPoolWorkQueue = new LinkedBlockingQueue(Allocation.USAGE_SHARED);
        THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, 1, TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);
        SERIAL_EXECUTOR = new SerialExecutor();
        sHandler = new InternalHandler();
        sDefaultExecutor = SERIAL_EXECUTOR;
    }

    public static void init() {
        sHandler.getLooper();
    }

    public static void setDefaultExecutor(Executor exec) {
        sDefaultExecutor = exec;
    }

    public MyAsyncTask2() {
        this.mStatus = Status.PENDING;
        this.mCancelled = new AtomicBoolean();
        this.mTaskInvoked = new AtomicBoolean();
        this.mWorker = new C09502();
        this.mFuture = new C05773(this.mWorker);
    }

    private void postResultIfNotInvoked(Result result) {
        if (!this.mTaskInvoked.get()) {
            postResult(result);
        }
    }

    private Result postResult(Result result) {
        InternalHandler internalHandler = sHandler;
        Object[] objArr = new Object[MESSAGE_POST_RESULT];
       // objArr[MAXIMUM_POOL_SIZE] = result;
        internalHandler.obtainMessage(MESSAGE_POST_RESULT, new AsyncTaskResult(this, objArr)).sendToTarget();
        return result;
    }

    public final Status getStatus() {
        return this.mStatus;
    }

    protected void onPreExecute() {
    }

    protected void onPostExecute(Result result) {
    }

    protected void onProgressUpdate(Progress... values) {
    }

    protected void onCancelled(Result result) {
        onCancelled();
    }

    protected void onCancelled() {
    }

    public final boolean isCancelled() {
        return this.mCancelled.get();
    }

    public final boolean cancel(boolean mayInterruptIfRunning) {
        this.mCancelled.set(true);
        return this.mFuture.cancel(mayInterruptIfRunning);
    }

    public final Result get() throws InterruptedException, ExecutionException {
        return this.mFuture.get();
    }

    public final Result get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.mFuture.get(timeout, unit);
    }

    public final MyAsyncTask2<Params, Progress, Result> execute(Params... params) {
        return executeOnExecutor(sDefaultExecutor, params);
    }

    public final MyAsyncTask2<Params, Progress, Result> executeOnExecutor(Executor exec, Params... params) {
        if (this.mStatus != Status.PENDING) {
            switch ($SWITCH_TABLE$com$lyrebirdstudio$common_libs$MyAsyncTask2$Status()[this.mStatus.ordinal()]) {
                case MESSAGE_POST_PROGRESS /*2*/:
                    throw new IllegalStateException("Cannot execute task: the task is already running.");
                case MirrorActivity.INDEX_MIRROR_EFFECT /*3*/:
                    throw new IllegalStateException("Cannot execute task: the task has already been executed (a task can be executed only once)");
            }
        }
        this.mStatus = Status.RUNNING;
        onPreExecute();
        this.mWorker.mParams = params;
        exec.execute(this.mFuture);
        return this;
    }

    public static void execute(Runnable runnable) {
        sDefaultExecutor.execute(runnable);
    }

    protected final void publishProgress(Progress... values) {
        if (!isCancelled()) {
            sHandler.obtainMessage(MESSAGE_POST_PROGRESS, new AsyncTaskResult(this, values)).sendToTarget();
        }
    }

    private void finish(Result result) {
        if (isCancelled()) {
            onCancelled(result);
        } else {
            onPostExecute(result);
        }
        this.mStatus = Status.FINISHED;
    }
}
