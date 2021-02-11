package com.jiefang.lock.locker;

import org.redisson.RedissonMultiLock;
import org.redisson.RedissonRedLock;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>className: RedissonDistributedLocker</p>
 * <p>description: </p>
 *
 * @author
 * @date 2019-08-21 13:49
 */
@Component
public class RedissonDistributedLocker implements DistributedLocker {

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public RLock lock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        return lock;
    }

    @Override
    public RLock lock(String lockKey, int leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(leaseTime, TimeUnit.SECONDS);
        return lock;
    }

    @Override
    public RLock lock(String lockKey, TimeUnit unit ,int timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, unit);
        return lock;
    }

    @Override
        public boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }

    @Override
    public void unlock(RLock lock) {
        lock.unlock();
    }

    @Override
    public RLock getFairLock(String lockKey) {
        return redissonClient.getFairLock(lockKey);
    }

    @Override
    public RedissonMultiLock getMultiLock(String lockKey1, String lockKey2, String lockKey3) {
        RLock lock1 = redissonClient.getLock(lockKey1);
        RLock lock2 = redissonClient.getLock(lockKey2);
        RLock lock3 = redissonClient.getLock(lockKey3);
        RedissonMultiLock lock = new RedissonMultiLock(lock1, lock2, lock3);
        // 同时加锁：lock1 lock2 lock3
        // 所有的锁都上锁成功才算成功。
        lock.lock();
        lock.unlock();
        return lock;
    }

    @Override
    public RedissonRedLock getRedLock(String lockKey1, String lockKey2, String lockKey3) {
        RLock lock1 = redissonClient.getLock(lockKey1);
        RLock lock2 = redissonClient.getLock(lockKey2);
        RLock lock3 = redissonClient.getLock(lockKey3);

        RedissonRedLock lock = new RedissonRedLock(lock1, lock2, lock3);
        // 同时加锁：lock1 lock2 lock3
        // 红锁在大部分节点上加锁成功就算成功。
        lock.lock();
        lock.unlock();
        return lock;
    }

    @Override
    public RReadWriteLock getReadWriteLoc(String lockKey) {
        RReadWriteLock readWriteLock = redissonClient.getReadWriteLock(lockKey);
        readWriteLock.readLock().lock();
        readWriteLock.writeLock().lock();
        return readWriteLock;
    }

    @Override
    public RSemaphore getRSemaphore(String semaphoreName) throws InterruptedException {
        RSemaphore semaphore = redissonClient.getSemaphore(semaphoreName);
        semaphore.trySetPermits(100);
        semaphore.acquire();
        semaphore.release();
        return semaphore;
    }

    @Override
    public RPermitExpirableSemaphore getExpirableSemaphore(String semaphoreName) throws InterruptedException {
        RPermitExpirableSemaphore semaphore = redissonClient.getPermitExpirableSemaphore(semaphoreName);
        semaphore.trySetPermits(100);
        semaphore.acquire(2,TimeUnit.MINUTES);
        semaphore.release(String.valueOf(1));
        return semaphore;
    }


    private void testCountDownLatch() throws InterruptedException {
        RCountDownLatch latch1 = redissonClient.getCountDownLatch("anyCountDownLatch");
        latch1.trySetCount(1);
        latch1.await();

        // 在其他线程或其他JVM里
        RCountDownLatch latch2 = redissonClient.getCountDownLatch("anyCountDownLatch");
        latch2.countDown();
    }
}