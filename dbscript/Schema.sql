CREATE TABLE `ShrinkMoneyData` (
  `roomId` varchar(15) NOT NULL COMMENT '대화방 ID',
  `token` varchar(3) NOT NULL COMMENT '토큰값',
  `ShrinkAmount` int NOT NULL DEFAULT '0' COMMENT '뿌릴 전체 금액',
  `personAmount` int NOT NULL DEFAULT '0' COMMENT '인당 받을 금액',
  `shrinkId` varchar(30) NOT NULL COMMENT '뿌린자 ID',
  `validDate` timestamp NOT NULL COMMENT '유효일자',
  `userCount` int NOT NULL COMMENT '받을 인원 수',
  `status` int NOT NULL COMMENT '상태',
  `regDate` timestamp NOT NULL COMMENT '등록일시',
  `regId` varchar(30) NOT NULL COMMENT '등록자',
  `updDate` timestamp NULL DEFAULT NULL COMMENT '수정일시',
  `updId` varchar(30) DEFAULT NULL COMMENT '수정자',
  PRIMARY KEY (`roomId`,`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='뿌리기 데이터 관리';

CREATE TABLE `ShrinkMoneyUserData` (
  `roomId` varchar(15) NOT NULL COMMENT '대화방 ID',
  `token` char(3) NOT NULL COMMENT '토큰값',
  `userId` varchar(30) NOT NULL COMMENT '받은자 ID',
  `ReceivedAmount` int NOT NULL COMMENT '받은 금액',
  `regDate` timestamp NOT NULL COMMENT '등록일시',
  `regId` varchar(30) NOT NULL COMMENT '등록자',
  PRIMARY KEY (`roomId`,`token`,`userId`),
  CONSTRAINT `ShrinkMoneyUserData_FK` FOREIGN KEY (`roomId`, `token`) REFERENCES `ShrinkMoneyData` (`roomId`, `token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='머니 뿌리기 유저 별 관리';