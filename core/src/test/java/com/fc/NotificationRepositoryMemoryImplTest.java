package com.fc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@SpringBootApplication
class NotificationRepositoryMemoryImplTest {

    @Autowired
    private NotificationRepository sut;

    private final Instant now = Instant.now();
    private final Instant deletedAt = Instant.now().plus(90,ChronoUnit.DAYS);

    @Test
    void test_save() {
        // 알림 객체 생성
        // 저장
        // 조회했을 때 객체가 있냐?

        sut.save(
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
        assertEquals(notification.createdAt.getEpochSecond(), now.getEpochSecond());
        assertEquals(notification.deletedAt.getEpochSecond(), deletedAt.getEpochSecond());
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