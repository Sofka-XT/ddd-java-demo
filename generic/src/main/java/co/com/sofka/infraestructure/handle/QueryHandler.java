package co.com.sofka.infraestructure.handle;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.domain.generic.QueryGeneric;

public interface QueryHandler<Q extends QueryGeneric, R extends UseCase.ResponseValues> {
    R handle(Q query);
}