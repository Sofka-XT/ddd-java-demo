package co.com.sofka.business;

import co.com.sofka.domain.DomainEvent;

import java.util.List;

public abstract class UseCase<Q extends UseCase.RequestValues, P extends UseCase.ResponseEvents> {

    private Q mRequestValues;
    private UseCaseFormat<P> mUseCaseFormat;

    public Q getRequestValues() {
        return mRequestValues;
    }

    public void setRequestValues(Q mRequestValues) {
        this.mRequestValues = mRequestValues;
    }

    public UseCaseFormat<P> emit(){
        return mUseCaseFormat;
    }

    public void setUseCaseCallback(UseCaseFormat<P> useCaseFormat) {
        mUseCaseFormat = useCaseFormat;
    }

    public void run(){
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
