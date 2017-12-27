package cn.mifengkong.frhttp.http.callback;


import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 *
 */
public class RxBus {

    public static synchronized RxBus getInstance() {
        return RxBusHolder.instance;
    }

    private static class RxBusHolder {
        private static final RxBus instance = new RxBus();
    }

    private Subject<Object> bus;

    // PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
    public RxBus() {
        bus = PublishSubject.create().toSerialized();
    }

    // 提供了一个新的事件
    public void post(Object o) {
        bus.onNext(o);
    }

    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
    public <T> Observable<T> toObserverable(Class<T> eventType) {
        return bus.ofType(eventType);
    }
}