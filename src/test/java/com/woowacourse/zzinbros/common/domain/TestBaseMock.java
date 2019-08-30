package com.woowacourse.zzinbros.common.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class TestBaseMock extends BaseEntity {
    private static final Logger log = LoggerFactory.getLogger(TestBaseMock.class);

    public static <T extends BaseEntity> T mockingId(T mock, final long id) {
        try {
            mock.id = id;
            return mock;
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new IllegalArgumentException("Mock Object Create Failed");
        }
    }

    public static <T extends BaseEntity> T mockingIdAndCreatedDateTime(T mock, final long id, final LocalDateTime createdDateTime) {
        try {
            mock.id = id;
            mock.createdDateTime = createdDateTime;
            return mock;
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new IllegalArgumentException("Mock Object Create Failed");
        }
    }
}
