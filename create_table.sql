SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`user`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`user` (
  `id` INT NOT NULL ,
  `username` VARCHAR(255) NULL ,
  `password` VARCHAR(255) NULL ,
  `email` VARCHAR(255) NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`party`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`party` (
  `id` INT NOT NULL ,
  `name` VARCHAR(255) NULL COMMENT '	' ,
  `description` VARCHAR(255) NULL ,
  `start_at` DATETIME NULL ,
  `address` VARCHAR(255) NULL ,
  `latitude` DOUBLE NULL ,
  `longitude` DOUBLE NULL ,
  `user_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  INDEX `fk_party_user_idx` (`user_id` ASC) ,
  CONSTRAINT `fk_party_user`
    FOREIGN KEY (`user_id` )
    REFERENCES `mydb`.`user` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`invite`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`invite` (
  `id` INT NOT NULL ,
  `presence` INT NULL ,
  `party_id` INT NOT NULL ,
  `guest_id` INT NOT NULL ,
  `host_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  INDEX `fk_invite_party1_idx` (`party_id` ASC) ,
  INDEX `fk_invite_user1_idx` (`guest_id` ASC) ,
  INDEX `fk_invite_user2_idx` (`host_id` ASC) ,
  CONSTRAINT `fk_invite_party1`
    FOREIGN KEY (`party_id` )
    REFERENCES `mydb`.`party` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_invite_user1`
    FOREIGN KEY (`guest_id` )
    REFERENCES `mydb`.`user` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_invite_user2`
    FOREIGN KEY (`host_id` )
    REFERENCES `mydb`.`user` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `mydb` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
