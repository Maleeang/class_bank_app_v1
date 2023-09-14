create table user_tb(
	id int auto_increment primary key,
    username varchar(50) not null unique,
    password varchar(30) not null,
    fullname varchar(50) not null,
    created_at timestamp not null default now()
);


create table account_tb(
	id int auto_increment primary key,
    number varchar(30) not null unique,
    password varchar(20) not null,
    balance bigint not null comment '계좌잔액',
    user_id int,
    created_at timestamp not null default now()
);

create table history_tb(
	id int auto_increment primary key comment '거래 내역 ID',
    amount bigint not null comment '거래 금액',
    w_account_id int comment '출금 계좌 id',
    d_account_id int comment '입금 계좌 id',
    w_balance bigint comment '출금 요쳥된 계좌의 잔액',
    d_balance bigint comment '입금 요청된 계좌의 잔액',
    created_at timestamp not null default now()
);