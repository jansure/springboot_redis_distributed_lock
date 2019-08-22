package com.jiefang.lock.locker;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * <p>interfaceName: DistributedLocker</p>
 * <p>description: </p>
 *
 * @author
 * @date 2019-08-21 13:48
 */
public interface DistributedLocker {

    RLock lock(String lockKey);

    RLock lock(String lockKey, int timeout);

    RLock lock(String lockKey, TimeUnit unit, int timeout);

    boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime);

    void unlock(String lockKey);

    void unlock(RLock lock);
}
