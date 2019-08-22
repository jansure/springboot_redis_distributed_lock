package com.jiefang.test;

import com.jiefang.lock.LockApplication;
import com.jiefang.lock.locker.RedissonDistributedLocker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>className: LockTest</p>
 * <p>description: </p>
 *
 * @author
 * @date 2019-08-21 14:39
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LockApplication.class)
public class LockTest {

    @Autowired
    private RedissonDistributedLocker locker;

    @Test
    public void testLock() {
        try {
            RLock rLock=locker.lock("redisson_lock",30);
            System.out.println("locked");
            rLock.unlock();
            System.out.println("unlocked");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}