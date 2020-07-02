-- ==========================================================
--  Trabajo Practico Integrador - UTNPhones 
-- ==========================================================
CREATE DATABASE utn_phones;
USE utn_phones;
SET GLOBAL time_zone = '-3:00';

CREATE TABLE provinces(
    id_province BIGINT AUTO_INCREMENT,
    province_name VARCHAR(50) NOT NULL,
    CONSTRAINT pk_id_province PRIMARY KEY (id_province),
    CONSTRAINT unq_province_name UNIQUE (province_name) );

CREATE TABLE cities(
    id_city BIGINT AUTO_INCREMENT,
    id_province BIGINT NOT NULL,
    city_name VARCHAR(50) NOT NULL,
    area_code VARCHAR(5) NOT NULL,
    CONSTRAINT pk_id_city PRIMARY KEY (id_city),
    CONSTRAINT fk_id_province FOREIGN KEY (id_province) REFERENCES provinces(id_province) ON DELETE CASCADE,
	CONSTRAINT unq_area_code UNIQUE (area_code) );
    
CREATE TABLE tariffs(
	id_tariff BIGINT AUTO_INCREMENT,
    id_city_origin BIGINT NOT NULL,
    id_city_destination BIGINT NOT NULL,
    cost_price DOUBLE NOT NULL DEFAULT 0,
	price DOUBLE NOT NULL DEFAULT 0,    
    CONSTRAINT pk_id_tariff PRIMARY KEY (id_tariff),
    CONSTRAINT fk_rate_per_minutes_id_city_origin FOREIGN KEY (id_city_origin) REFERENCES cities(id_city),
    CONSTRAINT fk_rate_per_minutes_id_city_destination FOREIGN KEY (id_city_destination) REFERENCES cities(id_city) );    

CREATE TABLE users(
	id_user BIGINT AUTO_INCREMENT,
	id_city BIGINT NOT NULL,
    firstname VARCHAR(50) NOT NULL,
    lastname VARCHAR(50) NOT NULL,
    dni VARCHAR(20) NOT NULL,
    pwd VARCHAR(50) NOT NULL,
    enabled TINYINT NOT NULL DEFAULT 1,
    user_role ENUM ("client", "employee"),
    CONSTRAINT pk_id_user PRIMARY KEY (id_user),
    CONSTRAINT fk_id_city FOREIGN KEY (id_city) REFERENCES cities(id_city) ON UPDATE CASCADE,
    CONSTRAINT unq_dni UNIQUE (dni) );
    
    
CREATE TABLE telephone_lines(
	id_telephone_line BIGINT AUTO_INCREMENT,
    id_user BIGINT NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    type_line ENUM ('mobile', 'home'),
	enabled BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT pk_id_telephone_line PRIMARY KEY (id_telephone_line),
    CONSTRAINT fk_telephone_lines_id_user FOREIGN KEY (id_user) REFERENCES users(id_user) ON UPDATE CASCADE,
    CONSTRAINT unq_phone_number UNIQUE (phone_number) );
    
CREATE TABLE bills(
    id_bill BIGINT AUTO_INCREMENT,
    id_telephone_line BIGINT NOT NULL,
    count_calls INT NOT NULL,
    cost_price DOUBLE NOT NULL DEFAULT 0,
    total_price DOUBLE NOT NULL DEFAULT 0,
    date_bill DATETIME NOT NULL,
    expirate_date DATETIME NOT NULL,
    is_pay TINYINT NOT NULL DEFAULT 0,
    CONSTRAINT pk_bills_id_bill PRIMARY KEY (id_bill),
    CONSTRAINT fk_bills_id_telephone_line FOREIGN KEY (id_telephone_line) REFERENCES telephone_lines(id_telephone_line) );

CREATE TABLE calls(
	id_call BIGINT AUTO_INCREMENT,
	id_telephone_origin BIGINT NOT NULL,
	id_telephone_destination BIGINT NOT NULL,
	id_city_origin BIGINT NOT NULL,
	id_city_destination BIGINT NOT NULL,
	billed TINYINT default 0,
	id_bill BIGINT DEFAULT 0,
    duration INT NOT NULL DEFAULT 0,
    cost_price DOUBLE NOT NULL DEFAULT 0,
    call_price DOUBLE NOT NULL DEFAULT 0,
    call_date DATETIME  NOT NULL,
    CONSTRAINT pk_calls_id_call PRIMARY KEY (id_call),
    CONSTRAINT fk_calls_id_telephone_origin FOREIGN KEY (id_telephone_origin) REFERENCES telephone_lines(id_telephone_line) ,
	CONSTRAINT fk_calls_id_telephone_destination FOREIGN KEY (id_telephone_destination) REFERENCES telephone_lines(id_telephone_line) ,
	CONSTRAINT fk_calls_id_city_origin FOREIGN KEY (id_city_origin) REFERENCES cities(id_city) ,
	CONSTRAINT fk_calls_id_city_destination FOREIGN KEY (id_city_destination) REFERENCES cities(id_city),
    CONSTRAINT fk_calls_id_bill FOREIGN KEY (id_bill) REFERENCES bills(id_bill)  );



-- ==========================================================
--  Insert Data
-- ==========================================================
/*Insert provinces*/
INSERT into provinces(province_name) values('Buenos Aires'),('Catamarca'),('Chaco'),('Chubut'),
('Cordoba'),('Corrientes'),('Entre Rios'),('Formosa'),('Jujuy'),('La Pampa'),('La Rioja'),
('Mendoza'),('Misiones'),('Neuquen'),('Rio Negro'),('Salta'),('San Juan'),('San Luis'),
('Santa Cruz'),('Santa Fe'),('Santiago Del Estero'),('Tierra Del Fuego'),('Tucuman');

/*Insert cities*/
/* link de codigos de area https://es.wikipedia.org/wiki/N%C3%BAmeros_telef%C3%B3nicos_en_Argentina´*/
INSERT into cities(id_province,city_name,area_code) values
(1,'Ciudad Autonoma de Buenos Aires','0011'),(1,'Merlo','0220'),(1,'La Plata','0221'),(1,'Mar del Plata','0223'),
(1,'Pilar','0230'),(1,'Junin','0236'),(1,'Moreno','0237'),(1,'Tandil','0249'),(12,'San Rafael','0260'),
(12,'Mendoza','0261'),(12,'San Martin','0263'),(17,'San Juan','0264'),(18,'San Luis','0266'),
(4,'Trelew','0280'),(1,'Bahia Blanca','0291'),(15,'Bariloche','0294'),(4,'Comodoro Rivadavia','0297'),
(15,'General Roca','0298'),(14,'Neuquen','0299'),(1,'San Nicolas de los Arroyos','0336'),
(20,'Rosario','0341'),(20,'Santa Fe','0342'),(7,'Parana','0343'),(7,'Concordia','0345'),(1,'Belen de Escobar','0348'),
(5,'Cordoba','0351'),(5,'Villa Maria','0353'),(5,'Rio Cuarto','0358'),(3,'Resistencia','0362'),
(3,'Roque Saenz Peña','0364'),(8,'Formosa','0370'),(13,'Posadas','0376'),(6,'Corrientes','0379'),
(11,'La Rioja','0380'),(23,'San Miguel de Tucuman','0381'),(2,'San Fernando del Valle de Catamarca','0383'),
(21,'Santiago del Estero','0385'),(16,'Salta','0387'),(9,'San Salvador de Jujuy','0388'),(1,'Gonzales Catan','2202'),
(1,'Magdalena','2221'),(1,'Brandsen','2223'),(1,'Glew','2224'),(1,'Alejandro Korn','2225'),(1,'Cañuelas','2226'),
(1,'Lobos','2227'),(1,'Juan Maria Gutierrez','2229'),(1,'Chascomus','2241'),(1,'Lezama','2242'),
(1,'General Belgrano','2243'),(1,'Las Flores','2244'),(1,'Dolores','2245'),(1,'Santa Teresita','2246'),
(1,'San Clemente del Tuyu','2252'),(1,'Pinamar','2254'),(1,'Villa Gesell','2255'),(1,'Mar de Ajo','2257'),
(1,'Loberia','2261'),(1,'Necochea','2262'),(1,'Nicanor Olivera','2264'),(1,'Coronel Vidal','2265'),(1,'Balcarce','2266'),
(1,'General Juan Madariaga','2267'),(1,'Maipu','2268'),(1,'San Miguel del Monte','2271'),(1,'Juan Jose Almeyra','2272'),
(1,'Carmen de Areco','2273'),(1,'Carlos Spegazzini','2274'),(1,'Azul','2281'),(1,'Tapalque','2283'),(1,'Olavarria','2284'),
(1,'General La Madrid','2286'),(1,'Miramar ','2291'),(1,'Benito Juarez','2292'),(1,'Ayacucho','2296'),(1,'Rauch','2297'),
(10,'General Pico','2302'),(1,'San Carlos de Bolivar','2314'),(1,'Daireaux','2316'),(1,'Nueve de Julio','2317'),
(1,'Jose C Paz','2320'),(1,'Lujan','2323'),(1,'Mercedes','2324'),(1,'San Andres de Giles','2325'),(1,'San Antonio de Areco','2326'),
(10,'Realico','2331'),(10,'Quemu Quemu','2333'),(10,'Eduardo Castex','2334'),(10,'Caleufu','2335'),(5,'Huinca Renanco','2336'),
(1,'America','2337'),(10,'Victorica','2338'),(1,'Bragado','2342'),(1,'Norberto de la Riestra','2343'),(1,'Saladillo','2344'),
(1,'25 de Mayo','2345'),(1,'Chivilcoy','2346'),(1,'Chacabuco','2352'),(1,'General Arenales','2353'),(1,'Vedia','2354'),
(1,'Lincoln','2355'),(1,'General Pinto','2356'),(1,'Carlos Tejedor','2357'),(1,'Los Toldos','2358'),(1,'Trenque Lauquen','2392'),
(1,'Salazar','2393'),(1,'Tres Lomas','2394'),(12,'Tunuyan','2622'),(12,'Uspallata','2624'),(1,'General Alvear','2625'),
(12,'La Paz','2626'),(17,'Villa San Agustin','2646'),(17,'San Jose de Jachal','2647'),(17,'Calingasta','2648'),
(17,'San Francisco de Monte de Oro','2651'),(17,'La Toma','2655'),(17,'Tilisarao','2656'),(17,'Villa Mercedes','2657'),
(17,'Buena Esperenza','2658'),(22,'Ushuia','2209'),(19,'Rio Turbio','2902'),(4,'Rio Mayo','2903'),(15,'Viedma','2920'),
(15,'Rio Colorado','2931'),(15,'San Antonio Oeste','2934'),(15,'Ingeniero Jacobacci ','2940'),(14,'Zapala','2942'),
(4,'Esquel','2945'),(15,'Choele Choel','2946'),(14,'Chos Malal','2948'),(10,'General Acha','2952'),
(10,'Macachin','2953'),(10,'Santa Rosa','2954'),(19,'Puerto San Julian','2962'),(19,'Perito Moreno','2963'),
(22,'Rio Grande','2964'),(19,'Rio Gallegos','2966'),(14,'San Martin de los Andes','2972'),(20,'Rufino','3382'),
(5,'Laboulaye','3385'),(5,'Buchardo','3387'),(1,'General Villegas','3388'),(20,'Villa Constitucion','3400'),
(20,'El Trebol','3401'),(20,'Arroyo Seco','3402'),(20,'San Carlos Centro','3404'),(7,'Nogoya','3435'),(7,'Victoria','3436'),
(7,'La Paz','3437'),(7,'Bovril','3438'),(7,'Concepcion del Uruguay','3442'),(7,'Gualeguay','3444'),(7,'Rosario del Tala','3445'),
(7,'Gualeguaychu','3446'),(7,'Colon','3447'),(7,'Federal','3454'),(5,'Cruz Alta','3467'),(5,'Corral de Bustos','3468'),
(5,'Marcos Juarez','3472'),(5,'Dean Funes','3521'),(5,'Villa de Maria','3522'),(5,'Jesus Maria','3525'),(5,'Villa Carlos Paz','3541'),
(8,'Ingeniero Juarez','3711'),(8,'Las Lomitas','3715'),(8,'Comandante Fontana','3716'),(3,'Charadai','3721'),
(3,'General Jose de San Martin','3725'),(3,'Charata','3731'),(13,'Bernardo de Yrigoyen','3741'),(13,'El Dorado','3751'),
(13,'Obera','3755'),(13,'Puerto Iguazu','3757'),(6,'Paso de los Libres','3772'),(6,'Mercedes','3773'),(6,'Goya','3777'),
(6,'Saladas','3782'),(11,'Chepes','3821'),(11,'Chilecito','3825'),(11,'Chamical','3826'),(11,'Aimogasta','3827'),
(2,'Recreo','3832'),(2,'Andalgala','3835'),(2,'Tinogasta','3837'),(2,'Santa Maria','3838'),(21,'Monte Quemado','3841'),
(21,'quimili','3843'),(21,'Loreto','3845'),(21,'Termas de Rio Hondo','3858'),(23,'Trancas','3862'),(23,'Monteros','3863'),
(23,'Concepcion','3865'),(23,'Tafi del Valle','3867'),(16,'Cafayate','3868'),(16,'Tartagal','3873'),(16,'Oran','3878'),
(16,'San Jose de Metan','3876'),(9,'La Quiaca','3885'),(9,'Humahuaca','3887'),(9,'Libertador General San Martin','3886'),
(9,'Graneros','3891');

insert into users (id_city, firstname, lastname, dni, pwd, enabled, user_role )
value (4, "Marcelo", "Gallardo", "25000999", md5("admin") ,1, "Employee");


set autocommit = 0;

-- =================================================================
--                          FUNCTIONS
-- =================================================================


/*Verifica que el string solo contenga enteros*/
CREATE FUNCTION isNumeric (word varchar(1024)) 
RETURNS tinyint deterministic
RETURN word REGEXP '^-?[0-9]+$';

/*Verifica que el string no contenga enteros*/
CREATE FUNCTION conteinsNumbers ( word varchar(1024)) 
RETURNS tinyint deterministic
RETURN word REGEXP '-?[0-9]';


# hacer una funcion para obtener el codigo de area de un numero */
DELIMITER $$
CREATE FUNCTION f_getAreaCode (pPhoneNumber varchar(25))
returns varchar(5)
deterministic
BEGIN
	declare vAreaCode varchar(5);
    set vAreaCode = substring(pPhoneNumber,1,4);
	return vAreaCode;
END
$$

#hacer funcion que ingrese numero y me retorne el id ciudad -> obtener tarifa*/
DELIMITER $$
CREATE FUNCTION f_getIdCityByAreaCode (pCodeArea varchar(5))
returns int
deterministic
BEGIN
	DECLARE vIdCity int;
	
	SELECT c.id_city INTO vIdCity 
    FROM cities c
    WHERE c.area_code = pCodeArea;
    
   return vIdCity;
END
$$

# hacer una funcion para obtener el numero de telefono sin el codigo de area*/
DELIMITER $$
CREATE FUNCTION f_getPhoneLineFromCompleteNumber (pPhoneNumber varchar(25))
returns varchar(20)
deterministic
BEGIN
	declare vPhoneNumber varchar(20);
    set vPhoneNumber = substring(pPhoneNumber,5,8);
	return vPhoneNumber;
END
$$

#hacer funcion que ingrese 2 id city y obtenga tarifa*/
DELIMITER $$
CREATE FUNCTION f_getTariffPriceByCity (pIdCityOrigin int,pIdCityDestination int)
returns double
BEGIN
	DECLARE vTariffPrice double;
	
	SELECT t.price INTO vTariffPrice 
    FROM tariffs t
    WHERE t.id_city_origin = pIdCityOrigin and t.id_city_destination = pIdCityDestination;
    
   return vTariffPrice;
END
$$

#hacer funcion que ingrese 2 id city y obtenga el costo de tarifa*/
DELIMITER $$
CREATE FUNCTION f_getTariffCostPriceByCity (pIdCityOrigin int,pIdCityDestination int)
returns double
BEGIN
	DECLARE vTariffCostPrice double;
	
	SELECT t.cost_price INTO vTariffCostPrice 
    FROM tariffs t
    WHERE t.id_city_origin = pIdCityOrigin and t.id_city_destination = pIdCityDestination;
    
   return vTariffCostPrice;
END
$$

-- ===============================================================
--                      PROCEDURES 
-- ===============================================================



DELIMITER $$
CREATE FUNCTION getPhoneLineRandom ()
RETURNS VARCHAR (8)
DETERMINISTIC
BEGIN
    DECLARE vRandom varchar(8);
	SELECT FLOOR(RAND()*(6999999 - 4700001)+4700000) INTO vRandom;
    RETURN vRandom;
END $$;

DELIMITER $$
CREATE PROCEDURE sp_generate_phone_line( IN pIdUser BIGINT, IN pTypeLine VARCHAR(10))
BEGIN
    DECLARE vPhoneNumber varchar(8);
	SET vPhoneNumber = getPhoneLineRandom();
	INSERT INTO telephone_lines (id_user, phone_number, type_line) 
    VALUE (pIdUser,vPhoneNumber, pTypeLine);
END $$;

DELIMITER $$
CREATE PROCEDURE sp_generate_phone_line_by_dni(IN pDni VARCHAR(20), IN pTypeLine VARCHAR(10), OUT pIdPhone BIGINT )
BEGIN
	DECLARE vIdUser INT;
    SELECT u.id_user INTO vIdUser FROM users u WHERE u.dni = pDni;
	CALL sp_generate_phone_line(vIdUser, pTypeLine);
    SET pIdPhone = last_insert_id();
END $$;

DELIMITER $$
CREATE PROCEDURE sp_suspend_phone_line( IN pIdPhoneLine BIGINT)
BEGIN
	UPDATE telephone_lines 
    SET enabled = false
    WHERE id_telephone_line = pIdPhoneLine;
END $$;

DELIMITER $$
CREATE PROCEDURE sp_active_phone_line( pIdPhoneLine BIGINT, OUT pIdPhone BIGINT )
BEGIN
	UPDATE telephone_lines 
    SET enabled = true
    WHERE id_telephone_line = pIdPhoneLine;
    SET pIdPhone = pIdPhoneLine;
END $$;


/* Este procedimiento realiza una insercion de un usuario a la tabla y le genera automaticamente una linea telefonica */
DELIMITER $$
CREATE PROCEDURE sp_insert_user_client( pIdCity BIGINT, pFirstname VARCHAR(50), pLastname VARCHAR(50), pDni VARCHAR(20), pPwd VARCHAR(128), pTypeLine VARCHAR(10) ,OUT pIdUser BIGINT )
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		SELECT MESSAGE_TEXT AS ErrorMessage; 
		ROLLBACK;
	END;
    START TRANSACTION;
		INSERT INTO users(id_city, firstname, lastname, dni, pwd, user_role) 
		VALUE (pIdCity, pFirstname, pLastname, pDni, pPwd, 'client');
		SET pIdUser = last_insert_id();
		call sp_generate_phone_line(pIdUser, pTypeLine);
	COMMIT;
END $$;

# UPDATE USER - *todo
DELIMITER $$
CREATE PROCEDURE sp_update_user (pId_city int, pFirstname VARCHAR(50), pLastname VARCHAR(50), pDni VARCHAR(20), OUT pIdUser BIGINT)
BEGIN
	START TRANSACTION;
		UPDATE users 
		SET id_city = pId_city,
			firstname = pFirstname,
			lastname = pLastname
		 WHERE   dni = pDni;
		 SELECT id_user INTO pIdUser FROM users WHERE dni = pDni;
	COMMIT;
END $$;

#DELETE
DELIMITER $$
CREATE PROCEDURE sp_delete_user (pDni VARCHAR(20))
BEGIN
	DECLARE vIdUser BIGINT;
	START TRANSACTION;
		UPDATE users 
		SET users.enabled = 0
		WHERE users.dni = pDni;
		
		SELECT u.id_user INTO vIdUser FROM users u WHERE u.dni = pDni;
		
		UPDATE telephone_lines
		SET telephone_lines.enabled = 0
		WHERE telephone_lines.id_user = vIdUser;
	COMMIT;
END $$;


#CREATE
#drop  procedure sp_insert_tariff;
DELIMITER $$
CREATE PROCEDURE sp_insert_tariff (pId_city_origin BIGINT, pId_city_destination BIGINT, pCost_price DOUBLE, pPrice DOUBLE )
BEGIN
    START TRANSACTION;
        insert into tariffs(id_city_origin, id_city_destination, cost_price, price) 
            VALUES (pId_city_origin, pId_city_destination, pCost_price, pPrice);
        if(pId_city_origin != pId_city_destination)then
        insert into tariffs(id_city_origin, id_city_destination, cost_price, price) 
            VALUES (pId_city_destination,pId_city_origin, pCost_price, pPrice);
        end if;
    COMMIT;
END $$;

#UPDATE
DELIMITER $$
#drop  procedure sp_update_tariff;
CREATE PROCEDURE sp_update_tariff (pId_city_origin int, pId_city_destination int, pCost_price DOUBLE, pPrice DOUBLE)
BEGIN
    START TRANSACTION;
        update tariffs 
        set cost_price = pCost_price,
            price = pPrice
        WHERE id_city_origin = pId_city_origin and id_city_destination = pId_city_destination;

        if(pId_city_origin != pId_city_destination)then
        update tariffs 
        set cost_price = pCost_price,
            price = pPrice
        WHERE id_city_origin = pId_city_destination and id_city_destination = pId_city_origin;
        end if;
    COMMIT;
END $$;
    
#DELETE
DELIMITER $$
#drop  procedure sp_delete_tariff;
CREATE PROCEDURE sp_delete_tariff (pId_city_origin int, pId_city_destination int)
BEGIN
    START TRANSACTION;
        DELETE FROM tariffs
        WHERE id_city_origin = pId_city_origin and id_city_destination = pId_city_destination;
        if(pId_city_origin != pId_city_destination)then
        DELETE FROM tariffs
        WHERE id_city_origin = pId_city_destination and id_city_destination = pId_city_origin;
        end if;
    COMMIT;
END $$

/* INFRAESTRUCTURA :  Ingresar llamada a la base de datos */
DELIMITER $$
CREATE PROCEDURE sp_insert_call( pPhoneNumberOrigin varchar(25), pPhoneNumberDestination varchar(25), pDuration int, pDate datetime , out pIdCall int)
BEGIN 
    # traer nmero de ciudad
    # calcular tarifa
	DECLARE vAreaCodeOrigin varchar(5);
    DECLARE vAreaCodeDestination varchar(5);
    
    DECLARE vIdCityOrigin int;
    DECLARE vIdCityDestination int;
    
    DECLARE vIdPhoneNumberOrigin int;
    DECLARE vIdPhoneNumberDestination int;
    
    DECLARE vTariffPrice double;
    DECLARE vTariffCostPrice double;
    DECLARE vTotalPrice double;
    
    START TRANSACTION;
    #Get area codes from numbers
	set vAreaCodeOrigin = f_getAreaCode(pPhoneNumberOrigin);
    set vAreaCodeDestination = f_getAreaCode(pPhoneNumberDestination);
    
    #Get id City by area code
    set vIdCityOrigin = f_getIdCityByAreaCode(vAreaCodeOrigin);
    set vIdCityDestination = f_getIdCityByAreaCode(vAreaCodeDestination);
	
    #Get id phone number from phone number
    SELECT id_telephone_line INTO vIdPhoneNumberOrigin FROM telephone_lines WHERE phone_number = f_getPhoneLineFromCompleteNumber(pPhoneNumberOrigin);
    SELECT id_telephone_line INTO vIdPhoneNumberDestination FROM telephone_lines WHERE phone_number = f_getPhoneLineFromCompleteNumber(pPhoneNumberDestination);
    
    #Get price and cost price by id city origin and id city destination
    set vTariffPrice = f_getTariffPriceByCity(vIdCityOrigin,vIdCityDestination);
	set vTariffCostPrice = f_getTariffCostPriceByCity(vIdCityOrigin,vIdCityDestination);
    set vTotalPrice = vTariffPrice * pDuration;
    
    INSERT INTO calls (id_telephone_origin, id_telephone_destination, id_city_origin, id_city_destination,id_bill,
		duration, cost_price, call_price, call_date) 
    VALUE (vIdPhoneNumberOrigin, vIdPhoneNumberDestination, vIdCityOrigin, vIdCityDestination, null, pDuration, 
		vTariffCostPrice, vTotalPrice, pDate);
	SET pIdCall = last_insert_id();
    
    COMMIT;
END
$$



SET GLOBAL event_scheduler = ON;
CREATE EVENT insertion_bills
ON SCHEDULE EVERY 1 MONTH STARTS '2020-07-01 03:30:00'
ON COMPLETION PRESERVE
DO
call sp_create_all_bill()

#Store procedure que crea todas las facturas 
#drop procedure sp_create_all_bill;
DELIMITER $$
CREATE PROCEDURE sp_create_all_bill()
BEGIN
	declare vId_phone_line int;
    #Declaro el cursos para recorrer todas las lineas telefonicas
	declare phone_lines_cursor cursor for 
    select id_telephone_line from telephone_lines tl;
    
    #Declaración de un manejador de error tipo NOT FOUND
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET @done = TRUE;

	#Se abre el cursor. Al abrir el cursor este sitúa un puntero a la primera fila del resultado de la consulta.
	OPEN phone_lines_cursor;
    #Empieza el bucle de lectura
	loop1: LOOP
	#Se guarda el resultado en la variable, hay una variable y un campo en el SELECT de la declaración del cursor
	FETCH phone_lines_cursor INTO vId_phone_line;
    #Llamo al store procedure para crear la factura de esa linea telefonica
    call sp_create_bill(vId_phone_line);

	#Se sale del bucle cuando no hay elementos por recorrer
	IF @done THEN
		LEAVE loop1;
	END IF;
	END LOOP loop1;
	#Se cierra el cursor
	CLOSE phone_lines_cursor;
    
END $$;

DELIMITER $$
CREATE PROCEDURE sp_create_bill( pId_telephone_line int)
BEGIN
    DECLARE vCount_calls int;
    DECLARE vCost_price double;
    DECLARE vTotal_price double;
    DECLARE vDate_bill double;
    DECLARE vExpirate_date double;
       
    #Cuento la cantidad de llamadas de esa linea que estan sin facturar
    select count(*) into vCount_calls from calls c where c.id_telephone_origin = pId_telephone_line and c.billed = 0 ;
    
    IF vCount_calls > 0 then
		#Calculo los costos y las fechas
		select sum(cost_price*duration) into vCost_price from calls c where billed = 0 and c.id_telephone_origin = pId_telephone_line;
        select sum(call_price) into vTotal_price from calls c where c.billed = 0 and c.id_telephone_origin = pId_telephone_line;
        set vDate_bill = now();
        set vExpirate_date = DATE_ADD(NOW(),INTERVAL 15 DAY);
		
        #Inserto la nueva factura
		INSERT INTO bills (id_telephone_line, count_calls, cost_price, total_price, date_bill, expirate_date) 
		VALUE (pId_telephone_line, vCount_calls, vCost_price, vTotal_price, vDate_bill, vExpirate_date);
        
        #Actualizo las llamadas que no estaban facturadas y le asigno la factura creada
        UPDATE calls 
		SET id_bill = last_insert_id(),
			billed = 1
		WHERE id_telephone_origin = pId_telephone_line and billed = 0;
    END IF;

END $$


-- =================================================================
--  TRIGGERS		
-- =================================================================
/*Verifica que el dni contenga solo numeros, y que el nombre y apellido no contenga numeros.*/
#drop trigger TBI_user;
DELIMITER $$
CREATE TRIGGER TBI_user BEFORE INSERT ON users FOR EACH ROW 
BEGIN
	if( !isNumeric(new.dni))then
		signal sqlstate '10001' 
		SET MESSAGE_TEXT = 'El dni tiene un formato incorrecto', 
		MYSQL_ERRNO = 4;
	end if;

	if(conteinsNumbers(new.firstname)) then
		signal sqlstate '10001' 
		SET MESSAGE_TEXT = 'El nombre tiene formato incorrecto', 
		MYSQL_ERRNO = 5;
	end if;

	if(conteinsNumbers(new.lastname)) then
		signal sqlstate '10001' 
		SET MESSAGE_TEXT = 'El apellido tiene formato incorrecto', 
		MYSQL_ERRNO = 6;
	end if;
END $$ ;

-- =================================================================
--                      TARIFFS
-- =================================================================
/*Verifica que el precio de costo sea menor al precio cuando insertamos.*/
DELIMITER $$
#drop trigger TBI_tariff;
CREATE TRIGGER TBI_tariff BEFORE INSERT ON tariffs FOR EACH ROW 
BEGIN
	if(new.cost_price > new.price)then
		signal sqlstate '10001' 
		SET MESSAGE_TEXT = 'El costo no puede ser mayor al precio', 
		MYSQL_ERRNO = 2;
	end if;
END $$;

/*Verifica que no se inserte una tarifa que ya existe.*/
#drop trigger TBI_tariff_exist;
DELIMITER $$
CREATE TRIGGER TBI_tariff_exist BEFORE INSERT ON tariffs FOR EACH ROW 
BEGIN
	declare vIdTariff int;
	if(select id_tariff from tariffs where id_city_origin = new.id_city_origin and id_city_destination = new.id_city_destination)then
		signal sqlstate '10001' 
		SET MESSAGE_TEXT = 'No se puede agregar una tarifa que ya existe', 
		MYSQL_ERRNO = 1;
	end if;
END $$;

-- call sp_insert_tariffs(1,4,3,5);

-- select * from v_tariffs;
-- delete from tariffs where id_tariff = 5;


/*Verifica que el precio de costo sea menor al precio cuando updateamos.*/
DELIMITER $$
#drop trigger TBU_tariff;
CREATE TRIGGER TBU_tariff BEFORE UPDATE ON tariffs FOR EACH ROW 
BEGIN
	if(new.cost_price > new.price)then
		signal sqlstate '10001' 
		SET MESSAGE_TEXT = 'El costo no puede ser mayor al precio', 
		MYSQL_ERRNO = 3;
	end if;
END $$

-- ==========================================================
--                   USERS
-- ==========================================================

/*	
* WEB - APLICACION MOBILE: Donde cada usuario consultar sus llamadas y facturas.
* BACKOFFICE: Donde se van administrar los usuarios(clientes), lineas telefonicas y tarifas.
* INFRAESTRUCTURA: Sistema donde se ingresara información de llamadas a la base de datos.
*/
/* Sistema WEB - APP MOBILE */
create user 'web'@'127.0.0.1' identified by 'w3b' ; /*'mobile'@'10.0.0.%'*/
/*Permisos*/
grant select on utn_phones.users to 'web'@'127.0.0.1';
grant select on utn_phones.calls to 'web'@'127.0.0.1';
grant select on utn_phones.bills to 'web'@'127.0.0.1';


/* Sistema BackOffice */ 
create user 'backoffice'@'127.0.0.1' identified by 'b4ck0ffic3';
/*Permisos*/
grant select, insert ,update on utn_phones.provinces to 'backoffice'@'127.0.0.1';
grant select, insert ,update on utn_phones.cities to 'backoffice'@'127.0.0.1';
grant select, insert ,update on utn_phones.users to 'backoffice'@'127.0.0.1';
grant select, insert ,update on utn_phones.telephone_lines to 'backoffice'@'127.0.0.1';
grant select, insert ,update on utn_phones.tariffs to 'backoffice'@'127.0.0.1';
grant select on utn_phones.bills to 'backoffice'@'127.0.0.1';
grant select on utn_phones.calls to 'backoffice'@'127.0.0.1';


/*Sistema Infraestructura*/
create user 'infra'@'127.0.0.1' identified by '1fr43s7ruc7ur4';
/*Permisos*/
/*tambien se puede implementar utilizando el DEFIINER en el PROCEDURE sp_insert_call*/
grant insert on utn_phones.calls to 'infra'@'127.0.0.1';


-- =================================================================
--                         VIEWS
-- =================================================================

CREATE VIEW v_phone_lines
AS
SELECT 	u.dni,
		tl.id_telephone_line as idPhoneLine, 
		concat(c.area_code,"-",tl.phone_number ) as phoneNumber,
		tl.type_line as typeLine,
        tl.enabled 
FROM telephone_lines tl 
INNER JOIN users u ON tl.id_user = u.id_user
INNER JOIN cities c ON u.id_city = c.id_city ;

CREATE VIEW v_clients
AS
SELECT 
		u.dni,
		concat(u.firstname,", ",u.lastname) as full_name,
        concat(c.city_name,", ",p.province_name) as city_province,
        count(tl.id_user) as count_phone_lines

FROM users u 
INNER JOIN cities c ON u.id_city = c.id_city
INNER JOIN provinces p ON p.id_province = c.id_province
INNER JOIN telephone_lines tl ON u.id_user = tl.id_user
WHERE u.user_role = 'client' and u.enabled = 1
GROUP BY (u.dni);


CREATE VIEW v_calls
AS
SELECT 
		u.dni,
		concat(co.area_code,"-",tlo.phone_number ) as phoneNumberOrigin,
        concat(cd.area_code,"-",tld.phone_number ) as phoneNumberDestination,
        co.city_name as cityOrigin,
        cd.city_name as cityDestination,
        cl.call_price as totalPrice,
        cl.duration,
        cl.call_date as date
        
FROM calls cl
INNER JOIN telephone_lines tlo ON cl.id_telephone_origin = tlo.id_telephone_line
INNER JOIN telephone_lines tld ON cl.id_telephone_destination = tld.id_telephone_line
INNER JOIN cities co ON cl.id_city_origin = co.id_city
INNER JOIN cities cd ON cl.id_city_destination = cd.id_city
INNER JOIN users u ON tlo.id_user = u.id_user
WHERE tlo.enabled = 1  ;

-- =================================================================
--                             INDEX
-- =================================================================

create index idx_calls on calls (call_date) using btree; 
/*si tmb se filtra por usuario no hace falta ya que dni es un indice unq*/
/*si se tiene q filtrar x linea telefonica se debe crear un indice por linea de origen*/

explain SELECT 
		u.dni,
		concat(co.area_code,"-",tlo.phone_number ) as phoneNumberOrigin,
        concat(cd.area_code,"-",tld.phone_number ) as phoneNumberDestination,
        co.city_name as cityOrigin,
        cd.city_name as cityDestination,
        cl.call_price as totalPrice,
        cl.duration,
        cl.call_date as date
        
FROM calls cl
INNER JOIN telephone_lines tlo ON cl.id_telephone_origin = tlo.id_telephone_line
INNER JOIN telephone_lines tld ON cl.id_telephone_destination = tld.id_telephone_line
INNER JOIN cities co ON cl.id_city_origin = co.id_city
INNER JOIN cities cd ON cl.id_city_destination = cd.id_city
INNER JOIN users u ON tlo.id_user = u.id_user
WHERE tlo.enabled = 1 and dni = 43143355 and call_date between "2020-01-30" and "2020-02-30"  ;

