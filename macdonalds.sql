-- 생성 쿼리#1
INSERT INTO
  burgers(id, name, price, gram, kcal, protein)
VALUES
  (1, '행운버거 골드 스페셜', 6000, 227, 699, 26) -- 행운버거 어서오고
;

-- 생성 쿼리#2
INSERT INTO
  burgers(id, name, price, gram, kcal, protein)
VALUES
  (2, '행운버거 골드', 5300, 222, 540, 25),     
  (3, '트리플 치즈버거', 6300, 219, 619, 36),    
  (4, '빅맥', 5300, 223, 583, 27)             -- 나머지 버거들도 추가
;

-- 조회 쿼리
SELECT
  *        -- 모든 컬럼 보여줘
FROM
  burgers; -- 버거 테이블에
  
-- 수정 쿼리#1
UPDATE
  burgers         -- 모든 버거
SET
  price = 1000;   -- 1000원 개꿀
  
-- 수정 쿼리#2
UPDATE
  burgers        -- 1: 해당 테이블에서
SET
  price = 500    -- 3: 가격을 500원으로 수정
WHERE
  id = 4;        -- 2: id가 4인 대상의

-- 삭제 쿼리
DELETE FROM
  burgers    -- 1: 버거 테이블에서
WHERE
  id = 4;    -- 2: id가 4인 대상을 삭제
