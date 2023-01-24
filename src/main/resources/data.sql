insert into `place` (`place_type`, `place_name`, `address`, `phone_number`, `capacity`, `memo`)
values
    ('SPORTS', '서울 배드민턴장', '경기도 성남시 대왕판교로 999', '010-9999-0000', 20, '판교는 정말 체크남방셔츠 뿐인가'),
    ('RESTAURANT', '패캠 레스토랑', '서울시 강남구 가나대로 123', '010-1234-5678', 10, '테스트 메모'),
    ('SPORTS', '천국 스키장', '하늘 천국 천국로 555', '010-1004-1004', 9000, null),
    ('COMMON', '패캠 본사', '111, Gana-ro, Gangnam-gu, Seoul', '010-1111-1111', 50, '패캠마니사랑해주세열'),
    ('PARTY', '패캠 무도회장', '서울시 강남구 가나대로 123 2층', '010-1234-5678', 1, '에블바리')
;

insert into event (`place_id`, `event_name`, `event_status`, `event_start_datetime`, `event_end_datetime`, `current_number_of_people`, `capacity`, `memo`)
values
    (1, '운동1', 'OPENED', '2021-01-01 09:00:00', '2021-01-01 12:00:00', 0, 20, 'test memo1'),
    (1, '운동2', 'OPENED', '2021-01-01 13:00:00', '2021-01-01 12:00:00', 0, 20, 'test memo2'),
    (2, '행사1', 'OPENED', '2021-01-02 09:00:00', '2021-01-02 12:00:00', 0, 30, 'test memo3'),
    (2, '행사2', 'OPENED', '2021-01-03 09:00:00', '2021-01-03 12:00:00', 0, 30, 'test memo4'),
    (2, '행사3', 'CLOSED', '2021-01-04 09:00:00', '2021-01-04 12:00:00', 0, 30, 'test memo5'),
    (3, '오전 스키', 'OPENED', '2021-02-01 08:00:00', '2021-02-01 12:30:00', 12, 50, 'test memo6')
;

insert into `admin` (`email`, `nickname`, `password`, `phone_number`, `memo`)
values
    ('test@test.com', '테스트', '1234', '010-0101-0101', '안녕하세요')
;

insert into `admin_place_map` (`admin_id`, `place_id`)
values
    (1, 1),
    (1, 2),
    (1, 3)
;