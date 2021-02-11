package com.jiefang.lock.locker;

import org.redisson.RedissonMultiLock;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RPermitExpirableSemaphore;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RSemaphore;

import java.util.List;
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

    RLock getFairLock(String lockKey);

    RedissonMultiLock getMultiLock(String lockKey1, String lockKey2, String lockKey3);

    RedissonRedLock getRedLock(String lockKey1, String lockKey2, String lockKey3);

    RReadWriteLock getReadWriteLoc(String lockKey);

    RSemaphore getRSemaphore(String semaphoreName) throws InterruptedException;

    RPermitExpirableSemaphore getExpirableSemaphore(String semaphoreName) throws InterruptedException;
}
