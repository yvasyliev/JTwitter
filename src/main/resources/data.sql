insert into users (
    id,
    username,
    email,
    first_name,
    last_name,
    password,
    role,
    created_at,
    locked,
    enabled
)
values (
    1,
    'admin',
    'admin@gmail.com',
    'Admin_firstName',
    'Admin_lastName',
    '$2a$10$3B.cWRzBTdV1mOVB9F77uOWE.DmeNhLJC1g43a5vGp8TfN0dJavaG',
    'ADMIN',
    CURRENT_TIMESTAMP,
    false,
    true
);