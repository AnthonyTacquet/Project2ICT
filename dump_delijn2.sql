-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema delijn
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema delijn
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `delijn` ;
USE `delijn` ;

-- -----------------------------------------------------
-- Table `delijn`.`stops`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `delijn`.`stops` (
  `stop_id` INT NOT NULL,
  `stop_code` INT NOT NULL,
  `stop_name` VARCHAR(60) NOT NULL,
  `stop_desc` VARCHAR(45) NULL,
  `stop_lat` DOUBLE NOT NULL,
  `stop_long` DOUBLE NOT NULL,
  `stop_zone_id` INT NULL,
  `stop_url` VARCHAR(45) NOT NULL,
  `location_type` VARCHAR(45) NULL,
  `parent_station` VARCHAR(45) NULL,
  `wheelchair_boarding` TINYINT NOT NULL,
  PRIMARY KEY (`stop_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `delijn`.`agencies`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `delijn`.`agencies` (
  `agency_id` INT NOT NULL,
  `agency_name` VARCHAR(45) NOT NULL,
  `agency_url` VARCHAR(45) NOT NULL,
  `agency_timezone` VARCHAR(45) NOT NULL,
  `agnecy_lang` VARCHAR(5) NOT NULL,
  `agency_phone` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`agency_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `delijn`.`routes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `delijn`.`routes` (
  `route_id` INT NOT NULL,
  `agency_id` INT NOT NULL,
  `route_short_name` VARCHAR(45) NOT NULL,
  `route_long_name` VARCHAR(60) NOT NULL,
  `route_desc` TEXT NOT NULL,
  `route_type` INT NOT NULL,
  `route_url` VARCHAR(45) NULL,
  `route_color` VARCHAR(45) NOT NULL,
  `route_text_color` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`route_id`),
  INDEX `fk_routes_agency1_idx` (`agency_id` ASC) VISIBLE,
  CONSTRAINT `fk_routes_agency1`
    FOREIGN KEY (`agency_id`)
    REFERENCES `delijn`.`agencies` (`agency_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `delijn`.`shapes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `delijn`.`shapes` (
  `shape_id` INT NOT NULL,
  `shape_pt_lat` DOUBLE NOT NULL,
  `shape_pt_long` DOUBLE NOT NULL,
  `shape_pt_sequence` INT NOT NULL,
  `shape_dist_travelled` DOUBLE NOT NULL,
  PRIMARY KEY (`shape_id`, `shape_pt_sequence`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `delijn`.`calendar_dates`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `delijn`.`calendar_dates` (
  `service_id` INT NOT NULL,
  `date` DATE NOT NULL,
  `exception_type` TINYINT NOT NULL,
  PRIMARY KEY (`service_id`, `date`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `delijn`.`trips`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `delijn`.`trips` (
  `route_id` INT NOT NULL,
  `service_id` INT NOT NULL,
  `trip_id` INT NOT NULL,
  `trip_headsign` VARCHAR(60) NOT NULL,
  `trip_short_name` VARCHAR(45) NOT NULL,
  `direction_id` TINYINT NOT NULL,
  `block_id` INT NULL,
  `shape_id` INT NULL,
  PRIMARY KEY (`trip_id`),
  INDEX `fk_trips_routes1_idx` (`route_id` ASC) VISIBLE,
  INDEX `fk_trips_shapes1_idx` (`shape_id` ASC) VISIBLE,
  INDEX `fk_trips_calendar_dates1_idx` (`service_id` ASC) VISIBLE,
  CONSTRAINT `fk_trips_routes1`
    FOREIGN KEY (`route_id`)
    REFERENCES `delijn`.`routes` (`route_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_trips_shapes1`
    FOREIGN KEY (`shape_id`)
    REFERENCES `delijn`.`shapes` (`shape_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_trips_calendar_dates1`
    FOREIGN KEY (`service_id`)
    REFERENCES `delijn`.`calendar_dates` (`service_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `delijn`.`stop_times`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `delijn`.`stop_times` (
  `trip_id` INT NOT NULL,
  `arrival_time` TIME NOT NULL,
  `departure_time` TIME NOT NULL,
  `stop_id` INT NOT NULL,
  `stop_sequence` INT NOT NULL,
  `stop_headsign` INT NULL,
  `pick_up_type` INT NOT NULL,
  `drop_off_type` INT NOT NULL,
  `shape_dist_travelled` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`trip_id`, `stop_id`, `stop_sequence`),
  INDEX `fk_stops_has_trips_trips1_idx` (`trip_id` ASC) VISIBLE,
  INDEX `fk_stops_has_trips_stops_idx` (`stop_id` ASC) VISIBLE,
  CONSTRAINT `fk_stops_has_trips_stops`
    FOREIGN KEY (`stop_id`)
    REFERENCES `delijn`.`stops` (`stop_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_stops_has_trips_trips1`
    FOREIGN KEY (`trip_id`)
    REFERENCES `delijn`.`trips` (`trip_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `delijn`.`feed_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `delijn`.`feed_info` (
  `feed_publisher_name` VARCHAR(45) NOT NULL,
  `feed_publisher_url` VARCHAR(45) NOT NULL,
  `feed_lang` VARCHAR(5) NOT NULL,
  `feed_start_date` DATE NOT NULL,
  `feed_end_date` DATE NOT NULL,
  `feed_version` VARCHAR(45) NOT NULL,
  `feed_contact_mail` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`feed_publisher_name`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
