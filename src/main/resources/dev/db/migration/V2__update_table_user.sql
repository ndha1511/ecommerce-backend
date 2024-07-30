alter table users
add verify boolean,
add otp varchar(6);

ALTER TABLE users DROP CONSTRAINT users_role_check;

UPDATE users
SET role = CASE
               WHEN role = 'USER' THEN 'ROLE_USER'
               WHEN role = 'ADMIN' THEN 'ROLE_ADMIN'
               ELSE role
    END;
ALTER TABLE users
    ADD CONSTRAINT users_role_check
        CHECK (role::text = ANY (ARRAY['ROLE_USER'::character varying, 'ROLE_ADMIN'::character varying]::text[]));


alter table users
add otp_reset_password varchar(6);

alter table users
add facebook_account_id varchar(255),
add google_account_id varchar(255),
add avatar_url varchar(255)