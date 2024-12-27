package com.fc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class NotificationRepositoryMemoryImplTest {

    private final NotificationRepositoryMemoryImpl sut = new NotificationRepositoryMemoryImpl();

    private final Instant now = Instant.now();
    private final Instant deletedAt = Instant.now().plus(90,ChronoUnit.DAYS);

    @Test
    void test_save() {
        // 알림 객체 생성

        // 저장
        // 조회했을 때 객체가 있냐?

        Notification save = sut.save(
            new Notification("1", 2L, NotificationType.LIKE, now, deletedAt));

        Optional<Notification> notification = sut.findById("1");

        assertTrue(notification.isPresent());
    }


    @Test
    void test_findById(){
        sut.save(
            new Notification("2", 2L, NotificationType.LIKE, now, deletedAt));

        Optional<Notification> optionalNotification = sut.findById("2");

        Notification notification = optionalNotification.orElseThrow();

        assertEquals(notification.id, "2");
        assertEquals(notification.userId, 2L);
        assertEquals(notification.createdAt, now);
        assertEquals(notification.deletedAt, deletedAt);
    }


    @Test
    void test_deleteById(){
        sut.save(
            new Notification("3", 2L, NotificationType.LIKE, now, deletedAt));

        sut.deleteById("3");

        Optional<Notification> notification = sut.findById("3");
        
        assertTrue(notification.isEmpty());
    }


}