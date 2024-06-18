-- Schema dbCM

--CREATE DATABASE "dbCM";
--\connect "dbCM";

DROP TABLE IF EXISTS "Area_Center";
CREATE TABLE "public"."Area_Center"
(
    "Area"   bigint NOT NULL,
    "Center" bigint NOT NULL,
    CONSTRAINT "Area_Center_Area_Center" PRIMARY KEY ("Area", "Center")
) WITH (oids = false);


DROP TABLE IF EXISTS "CentriMonitoraggio";
CREATE TABLE "public"."CentriMonitoraggio"
(
    "CenterID"    serial       NOT NULL DEFAULT 0,
    "Name"        text         NOT NULL,
    "Street"      text         NOT NULL,
    "CivicNumber" integer      NOT NULL,
    "ZIPCode"     character(5) NOT NULL,
    "Town"        text         NOT NULL,
    "Province"    text         NOT NULL,
    CONSTRAINT "CentriMonitoraggio_pkey" PRIMARY KEY ("CenterID")
) WITH (oids = false);

INSERT INTO "CentriMonitoraggio"
VALUES (0, 'Centro non definito', 'Via Delle Vie', 0, '00000', 'Atlantide', 'I Sette Mari');


DROP TABLE IF EXISTS "CoordinateMonitoraggio";
CREATE TABLE "public"."CoordinateMonitoraggio"
(
    "GeoID"       bigint       NOT NULL,
    "Name"        text         NOT NULL,
    "CountryCode" character(2) NOT NULL,
    "Latitude"    real         NOT NULL,
    "Longitude"   real         NOT NULL,
    CONSTRAINT "CoordinateMonitoraggio_GeoID" PRIMARY KEY ("GeoID")
) WITH (oids = false);


DROP TABLE IF EXISTS "OperatoriRegistrati";
CREATE TABLE "public"."OperatoriRegistrati"
(
    "UserID"   serial        NOT NULL,
    "Name"     text          NOT NULL,
    "Surname"  text          NOT NULL,
    "CF"       character(16) NOT NULL,
    "Email"    text          NOT NULL,
    "Password" text          NOT NULL,
    "Center"   bigint        NOT NULL,
    CONSTRAINT "OperatoriRegistrati_pkey" PRIMARY KEY ("UserID")
) WITH (oids = false);


DROP TABLE IF EXISTS "ParametriClimatici";
CREATE TABLE "public"."ParametriClimatici"
(
    "RecordID"             serial                 NOT NULL,
    "Datetime"             timestamptz            NOT NULL,
    "Center"               bigint                 NOT NULL,
    "Area"                 bigint                 NOT NULL,
    "Operator"             bigint                 NOT NULL,
    "Wind"                 smallint DEFAULT '0'   NOT NULL,
    "Humidity"             smallint DEFAULT '0'   NOT NULL,
    "Pressure"             smallint DEFAULT '0'   NOT NULL,
    "Temperature"          smallint DEFAULT '0'   NOT NULL,
    "Precipitation"        smallint DEFAULT '0'   NOT NULL,
    "GlacierAltitude"      smallint DEFAULT '0'   NOT NULL,
    "GlacierMass"          smallint DEFAULT '0'   NOT NULL,
    "WindNotes"            character varying(256) NOT NULL,
    "HumidityNotes"        character varying(256) NOT NULL,
    "PressureNotes"        character varying(256) NOT NULL,
    "TemperatureNotes"     character varying(256) NOT NULL,
    "PrecipitationNotes"   character varying(256) NOT NULL,
    "GlacierAltitudeNotes" character varying(256) NOT NULL,
    "GlacierMassNotes"     character varying(256) NOT NULL,
    CONSTRAINT "ParametriClimatici_pkey" PRIMARY KEY ("RecordID")
) WITH (oids = false);


ALTER TABLE ONLY "public"."Area_Center"
    ADD CONSTRAINT "Area_Center_Area_fkey" FOREIGN KEY ("Area") REFERENCES "CoordinateMonitoraggio" ("GeoID") ON UPDATE CASCADE ON DELETE CASCADE NOT DEFERRABLE;
ALTER TABLE ONLY "public"."Area_Center"
    ADD CONSTRAINT "Area_Center_Center_fkey" FOREIGN KEY ("Center") REFERENCES "CentriMonitoraggio" ("CenterID") ON UPDATE CASCADE ON DELETE CASCADE NOT DEFERRABLE;

ALTER TABLE ONLY "public"."OperatoriRegistrati"
    ADD CONSTRAINT "OperatoriRegistrati_Center_fkey" FOREIGN KEY ("Center") REFERENCES "CentriMonitoraggio" ("CenterID") ON UPDATE CASCADE NOT DEFERRABLE;

ALTER TABLE ONLY "public"."ParametriClimatici"
    ADD CONSTRAINT "ParametriClimatici_Area_fkey" FOREIGN KEY ("Area") REFERENCES "CoordinateMonitoraggio" ("GeoID") ON UPDATE CASCADE NOT DEFERRABLE;
ALTER TABLE ONLY "public"."ParametriClimatici"
    ADD CONSTRAINT "ParametriClimatici_Center_fkey" FOREIGN KEY ("Center") REFERENCES "CentriMonitoraggio" ("CenterID") ON UPDATE CASCADE NOT DEFERRABLE;
ALTER TABLE ONLY "public"."ParametriClimatici"
    ADD CONSTRAINT "ParametriClimatici_Operator_fkey" FOREIGN KEY ("Operator") REFERENCES "OperatoriRegistrati" ("UserID") ON UPDATE CASCADE NOT DEFERRABLE;
