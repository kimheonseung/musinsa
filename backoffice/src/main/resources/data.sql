insert into `brand` (`name`) values
    ('A'), ('B'), ('C'), ('D'), ('E'), ('F'), ('G'), ('H'), ('I');

insert into `category` (`name`) values
    ('상의'), ('아우터'), ('바지'),('스니커즈'), ('가방'), ('모자'), ('양말'), ('액세서리');

insert into `item` (`name`, `category_id`, `brand_id`, `price`) values
    ('A의 상의', 1, 1, 11200),
    ('B의 상의', 1, 2, 10500),
    ('C의 상의', 1, 3, 10000),
    ('D의 상의', 1, 4, 10100),
    ('E의 상의', 1, 5, 10700),
    ('F의 상의', 1, 6, 11200),
    ('G의 상의', 1, 7, 10500),
    ('H의 상의', 1, 8, 10800),
    ('I의 상의', 1, 9, 11400),
    ('A의 아우터', 2, 1, 5500),
    ('B의 아우터', 2, 2, 5900),
    ('C의 아우터', 2, 3, 6200),
    ('D의 아우터', 2, 4, 5100),
    ('E의 아우터', 2, 5, 5000),
    ('F의 아우터', 2, 6, 7200),
    ('G의 아우터', 2, 7, 5800),
    ('H의 아우터', 2, 8, 6300),
    ('I의 아우터', 2, 9, 6700),
    ('A의 바지', 3, 1, 4200),
    ('B의 바지', 3, 2, 3800),
    ('C의 바지', 3, 3, 3300),
    ('D의 바지', 3, 4, 3000),
    ('E의 바지', 3, 5, 3800),
    ('F의 바지', 3, 6, 4000),
    ('G의 바지', 3, 7, 3900),
    ('H의 바지', 3, 8, 3100),
    ('I의 바지', 3, 9, 3200),
    ('A의 스니커즈', 4, 1, 9000),
    ('B의 스니커즈', 4, 2, 9100),
    ('C의 스니커즈', 4, 3, 9200),
    ('D의 스니커즈', 4, 4, 9500),
    ('E의 스니커즈', 4, 5, 9900),
    ('F의 스니커즈', 4, 6, 9300),
    ('G의 스니커즈', 4, 7, 9000),
    ('H의 스니커즈', 4, 8, 9700),
    ('I의 스니커즈', 4, 9, 9500),
    ('A의 가방', 5, 1, 2000),
    ('B의 가방', 5, 2, 2100),
    ('C의 가방', 5, 3, 2200),
    ('D의 가방', 5, 4, 2500),
    ('E의 가방', 5, 5, 2300),
    ('F의 가방', 5, 6, 2100),
    ('G의 가방', 5, 7, 2200),
    ('H의 가방', 5, 8, 2100),
    ('I의 가방', 5, 9, 2400),
    ('A의 모자', 6, 1, 1700),
    ('B의 모자', 6, 2, 2000),
    ('C의 모자', 6, 3, 1900),
    ('D의 모자', 6, 4, 1500),
    ('E의 모자', 6, 5, 1800),
    ('F의 모자', 6, 6, 1600),
    ('G의 모자', 6, 7, 1700),
    ('H의 모자', 6, 8, 1600),
    ('I의 모자', 6, 9, 1700),
    ('A의 양말', 7, 1, 1800),
    ('B의 양말', 7, 2, 2000),
    ('C의 양말', 7, 3, 2200),
    ('D의 양말', 7, 4, 2400),
    ('E의 양말', 7, 5, 2100),
    ('F의 양말', 7, 6, 2300),
    ('G의 양말', 7, 7, 2100),
    ('H의 양말', 7, 8, 2000),
    ('I의 양말', 7, 9, 1700),
    ('A의 액세서리', 8, 1, 2300),
    ('B의 액세서리', 8, 2, 2200),
    ('C의 액세서리', 8, 3, 2100),
    ('D의 액세서리', 8, 4, 2000),
    ('E의 액세서리', 8, 5, 2100),
    ('F의 액세서리', 8, 6, 1900),
    ('G의 액세서리', 8, 7, 2000),
    ('H의 액세서리', 8, 8, 2000),
    ('I의 액세서리', 8, 9, 2400);