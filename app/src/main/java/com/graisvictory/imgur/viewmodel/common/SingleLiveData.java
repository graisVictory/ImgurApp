package com.graisvictory.imgur.viewmodel.common;

import androidx.annotation.MainThread;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

class SingleLiveData<T> extends MediatorLiveData<T> {

    private ConcurrentHashMap<LifecycleOwner, ObserverWrapper<? super T>> observers =
            new ConcurrentHashMap<>();

    @MainThread
    @Override
    public void observe(@NotNull LifecycleOwner owner, @NotNull Observer<? super T> observer) {
        ObserverWrapper<? super T> wrapper = new ObserverWrapper<>(observer);
        observers.put(owner, wrapper);
        super.observe(owner, wrapper);
    }

    @Override
    public void removeObservers(@NotNull LifecycleOwner owner) {
        observers.remove(owner);
        super.removeObservers(owner);
    }

    @Override
    public void removeObserver(@NotNull Observer<? super T> observer) {
        for (Map.Entry<LifecycleOwner, ObserverWrapper<? super T>> item : observers.entrySet()) {
            if (item.getValue() == observer) {
                observers.remove(item.getKey());
            }
        }
        super.removeObserver(observer);
    }

    @MainThread
    @Override
    public void setValue(T t) {
        for (Map.Entry<LifecycleOwner, ObserverWrapper<? super T>> item : observers.entrySet()) {
            item.getValue().newValue();
        }
        super.setValue(t);
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    void call() {
        setValue(null);
    }

    private static class ObserverWrapper<T> implements Observer<T> {

        private Observer<T> observer;
        private AtomicBoolean pending = new AtomicBoolean(false);

        ObserverWrapper(Observer<T> observer) {
            this.observer = observer;
        }

        @Override
        public void onChanged(T t) {
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t);
            }
        }

        void newValue() {
            pending.set(true);
        }
    }
}