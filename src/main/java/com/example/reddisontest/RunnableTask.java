package com.example.reddisontest;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by alexandr.efimov@sigma.software on 10/23/2017.
 */
@Slf4j
public class RunnableTask implements Runnable {

    @Override
    public void run() {
       log.info("Hello from task: " + Thread.currentThread());
    }

}
