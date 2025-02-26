DROP TABLE IF EXISTS public.users;
CREATE TABLE public.users (id bigserial PRIMARY KEY, username varchar(255) unique);
CREATE INDEX idx_users_id ON public.users(id);
