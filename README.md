# Java Beer Application

## Database Triggers

Create audit table
```sql
CREATE TABLE public.java_beer_event_audit (
	id int8 NOT NULL,
	"location" varchar(255) NULL,
	sponsor varchar(255) NULL,
	start_at timestamp NULL,
	database_event_type varchar(255) null,
	updatedAt timestamp null,
	
	PRIMARY KEY(id,database_event_type,updatedAt)
);
```
Create audit table function 
```sql 

CREATE OR REPLACE FUNCTION java_beer_audit_trigger_func()
RETURNS trigger AS $body$
BEGIN
   if (TG_OP = 'INSERT') then
       INSERT INTO java_beer_event_audit (
           id ,
           "location",
           sponsor,
           start_at,
           database_event_type,
           updatedAt
       )
       VALUES(
           NEW.id,
           NEW."location",
           NEW."sponsor",
           NEW."start_at",
           'INSERT',
           CURRENT_TIMESTAMP
       );
             
       RETURN NEW;
   elsif (TG_OP = 'UPDATE') then
       INSERT INTO java_beer_event_audit (
           id ,
           "location",
           sponsor,
           start_at,
           database_event_type,
           updatedAt
       )
       VALUES(
           NEW.id,
           NEW."location",
           NEW."sponsor",
           NEW."start_at",
           'UPDATE',
           CURRENT_TIMESTAMP
       );
            
             
       RETURN NEW;
   elsif (TG_OP = 'DELETE') then
        INSERT INTO java_beer_event_audit (
           id ,
           "location",
           sponsor,
           start_at,
           database_event_type,
           updatedAt
       )
       VALUES(
           OLD.id,
           OLD."location",
           OLD."sponsor",
           OLD."start_at",
           'DELETE',
           CURRENT_TIMESTAMP
       );
        
       RETURN OLD;
   end if;
     
END;
$body$
LANGUAGE plpgsql
```

Create trigger
```sql
CREATE TRIGGER java_beer_audit_trigger
AFTER INSERT OR UPDATE OR DELETE ON public.java_beer_event 
FOR EACH ROW EXECUTE FUNCTION java_beer_audit_trigger_func()

```