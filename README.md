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

Run the postgress database
```
docker stop jprofessional2022
```

Configure Kafka Connect

```
curl -X POST -H "Accept:application/json" -H "Content-Type:application/json" localhost:8083/connectors/ -d '
{
 "name": "javabeer-connector",
 "config": {
 "connector.class": "io.debezium.connector.postgresql.PostgresConnector",
 "database.hostname": "postgres",
 "database.port": "5432",
 "database.user": "postgres",
 "database.password": "postgres",
 "database.dbname" : "javabeer",
 "database.server.name": "dbserver1",
 "table.include.list": "public.java_beer_event",
 "database.history.kafka.bootstrap.servers": "kafka:9092",
 "topic.prefix": "javabeer",
 "database.history.kafka.topic": "schema-changes.javabeer"
 }
}'
```


List kafka topics
```
kafka-topics --bootstrap-server kafka:9092 --list
```

Connect to kafka topic
```
kafka-console-consumer --bootstrap-server kafka:9092 --from-beginning --topic javabeer.public.java_beer_event
```