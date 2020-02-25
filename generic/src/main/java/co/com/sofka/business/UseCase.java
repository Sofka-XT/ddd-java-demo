package co.com.sofka.business;

import co.com.sofka.domain.DomainEvent;

import java.util.List;

public abstract class UseCase<Q extends UseCase.RequestValues, P extends UseCase.ResponseEvents> {

    private Q mRequestValues;
    private UseCaseFormat<P> mUseCaseFormat;

    protected Q getRequestValues() {
        return mRequestValues;
    }

    protected void setRequestValues(Q mRequestValues) {
        this.mRequestValues = mRequestValues;
    }

    protected UseCaseFormat<P> emit(){
        return mUseCaseFormat;
    }

    protected void setUseCaseCallback(UseCaseFormat<P> useCaseFormat) {
        mUseCaseFormat = useCaseFormat;
    }

    protected void run(){
        try {
            executeUseCase(mRequestValues);
        }catch (RuntimeException e){
            mUseCaseFormat.onError(e);
        }
    }
    protected abstract void executeUseCase(Q requestValues);


    public interface RequestValues {

    }
    public interface ResponseEvents {
        List<DomainEvent> getDomainEvents();
    }

    public interface UseCaseFormat<R> {
        void onSuccess(R response);

        void onError(RuntimeException e);
    }

}
