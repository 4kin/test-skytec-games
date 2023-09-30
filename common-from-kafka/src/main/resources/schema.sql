SET search_path = public;
drop table if exists clan;

create table if not exists clan
(
    id   bigint  not null
        primary key,
    gold integer not null,
    name varchar(255)
        constraint uc_clan_name
            unique
);


CREATE OR REPLACE FUNCTION increase_gold_with_old_value()
    RETURNS TRIGGER AS
'
    BEGIN
        IF TG_OP = ''INSERT'' THEN
            NEW.gold := NEW.gold;
            RETURN NEW;
        ELSIF TG_OP = ''UPDATE'' THEN
            NEW.gold := NEW.gold + OLD.gold;
            RETURN NEW;
        END IF;
    END;
' LANGUAGE plpgsql;


CREATE OR REPLACE TRIGGER increase_gold_with_old_value_trigger
    before INSERT OR UPDATE
    ON clan
    FOR EACH ROW
EXECUTE FUNCTION increase_gold_with_old_value();