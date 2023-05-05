package com.fredsonchaves.application;

public interface UseCase<IN, OUT> {

    OUT execute(IN in);
}